package com.example.ribbonconsumer.command;

import com.example.helloservice.bean.User;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义HystrixCommand, 通过它可以实现同步请求也可实现异步请求
 *
 * .execute() 返回同步执行结果
 * .queue() 异步执行，返回的是Future<User>对象，通过它的get方法获取结果
 * .observe() 每次被订阅时立即执行 返回Observable<User>对象
 * .toObservable() 当所有订阅者订阅后才执行
 *
 */
public class UserCommand extends HystrixCommand<User> {

    private static HystrixCommandGroupKey GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("GroupName");
    private static HystrixCommandKey COMMAND_KEY = HystrixCommandKey.Factory.asKey("CommandName");
    private static HystrixThreadPoolKey THREAD_PO0L_KEY = HystrixThreadPoolKey.Factory.asKey("ThreadPoolKey");

    private RestTemplate restTemplate;
    private Long id;

    protected UserCommand(Setter setter, RestTemplate restTemplate, Long id) {
        //super(setter);
        /*
            默认使用类名作为命令名称，可以通过静态类Setter设置命令名称
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupName")) 设置命令组名
            .andCommandKey(HystrixCommandKey.Factory.asKey("CommandName")) 设置命令名称
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPoolKey")) 设置线程池组，默认使用组名称
            通过线程池实现隔离
            注解式设置，只需要设置对应的参数即可 commandKey,groupKey,threadPoolKey
         */
        super(Setter.withGroupKey(GROUP_KEY).andCommandKey(COMMAND_KEY).andThreadPoolKey(THREAD_PO0L_KEY));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    /**
     * 业务逻辑方法，必须重载
     * @return
     * @throws Exception
     */
    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://USER-SERVICE/users/{id}", User.class, id);
    }

    /**
     * 重在此方法定义服务降级逻辑
     * 在run()方法执行过程中出现错误、超时、线程池拒绝、断路器熔断等情况执行该方法
     * @return
     */
    @Override
    protected User getFallback() {
        // 通过该方法获取具体的异常
        Throwable e = getExecutionException();
        return new User();
    }

    /**
     * 实现请求缓存，使命令得以缓存，当再次请求此参数命令时直接返回第一次调用服务的结果
     * 即根据该方法的值判断请求处理逻辑是否调用同一个依赖服务
     * 在run()或者construct()方法生效
     * @return
     */
    @Override
    protected String getCacheKey() {
        //return super.getCacheKey();
        return String.valueOf(id);
    }

    /**
     * 可以调用此方法清除缓存
     * @param id
     */
    public static void flushCode(Long id) {
        HystrixRequestCache.getInstance(
                COMMAND_KEY, HystrixConcurrencyStrategyDefault.getInstance())
                .clear(String.valueOf(id));
    }
}

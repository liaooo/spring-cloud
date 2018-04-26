package com.example.ribbonconsumer.service;

import com.example.helloservice.bean.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestTemplate restTemplate;

    /**
     * 同步执行
     * ignoreExceptions 对指定异常类型的异常不做服务降级处理
     * 默认对HystrixBadRequestException异常不做降级处理
     *
     * @CacheKey 注解指定缓存key 优先级低于cacheKeyMethod属性
     *
     * @param id
     * @return
     */
    @CacheResult(cacheKeyMethod = "getUserByIdCacheKey") // 该注解设置开启请求缓存
    @HystrixCommand(ignoreExceptions = {HystrixBadRequestException.class})
    public User getUser(@CacheKey("id") Long id) {
        return restTemplate.getForObject("http://HELLO-SERVICE/users/{id}", User.class, id);
    }

    private Long getUserByIdCacheKey(Long id) {
        return id;
    }

    /**
     * 异步执行
     * @param id
     * @return
     */
    @CacheRemove(commandKey = "getUser") // 缓存清理 commandKey属性指定清除哪个命令的缓存
    @HystrixCommand
    public AsyncResult<User> getUserAsync(final Long id) {
        return new AsyncResult<User>(
                restTemplate.getForObject("http://HELLO-SERVICE/users/{id}", User.class, id));
    }

    /**
     * 可以通过observableExecutionMode参数控制使用
     * observe()还是toObservable()
     *
     * observableExecutionMode=ObservableExecutionMode.EAGER
     * observableExecutionMode=ObservableExecutionMode.LAZY
     * @param id
     * @return
     */
    @HystrixCommand
    public Observable<User> getObservableUser(final Long id) {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        User user = restTemplate.getForObject("http://HELLO-SERVICE/users/{id}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * fallbackMethod 参数制定服务降级逻辑处理方法
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "usersFallback")
    public String users(Long id) {
        long callStart = System.currentTimeMillis();
        String url = "http://HELLO-SERVICE/users/" + id;
        User result = restTemplate.getForObject(url, User.class);
        long callEnd = System.currentTimeMillis();
        logger.info("调用" + url + "耗时" + (callEnd - callStart) + "ms");
        return result.toString();
    }

    /**
     * 加入Throwable参数即可获取异常
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "usersFallbackSecond")
    private String usersFallback(Long id, Throwable e) {
        return "error{" + id + "}";
    }

    private String usersFallbackSecond(Long id) {
        return "error {" + id + "} again";
    }
}

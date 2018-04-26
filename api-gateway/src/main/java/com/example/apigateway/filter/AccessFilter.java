package com.example.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求过滤
 * 过滤器完成对请求的拦截和过滤功能
 */
@Component
public class AccessFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 定义过滤器类型，决定请求在哪个阶段执行，默认4种类型
     * 1、pre：请求被路由之前执行
     * 2、routing：在请求路由时调用
     * 3、error：处理请求发生错误时调用
     * 4、post：在routing和error之后调用
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 定义过滤器的执行顺序，同一个阶段的多个过滤器按指定的顺序执行
     * 数值越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 判断过滤器是否需要被执行，可通过该方法指定过滤器的有效范围
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 定义过滤器的具体过滤逻辑，确定是否拦截当前请求不进行后续的路由
     * 或者是在请求路由返回结果之后对结果进行加工等操作
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            logger.warn("access token is empty");
            // 设置为false 过滤该请求
            ctx.setSendZuulResponse(false);
            // 返回错误码
            ctx.setResponseStatusCode(401);
            // 还可以设置其他的返回信息....
            return null;
        }
        logger.info("access token ok");
        return null;
    }
}

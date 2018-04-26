package com.example.apigateway.filter;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Component
public class CustomSendErrorFilter extends SendErrorFilter {

    private static final Logger logger = LoggerFactory.getLogger(SendErrorFilter.class);

    @Value("${error.path:/error}")
    private String errorPath;

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Throwable throwable = ctx.getThrowable();
            Exception exception = (Exception) throwable.getCause();
            HttpServletRequest request = ctx.getRequest();
            request.setAttribute("javax.servlet.error.status_code", ctx.get("error.status_code"));
            logger.warn("Error during filtering", exception);
            request.setAttribute("javax.servlet.error.exception", exception);
            if (StringUtils.hasText(exception.getMessage())) {
                request.setAttribute("javax.servlet.error.message", exception.getMessage());
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPath);
            if (dispatcher != null) {
                ctx.set("sendErrorFilter.ran", true);
                if (!ctx.getResponse().isCommitted()) {
                    ctx.setResponseStatusCode((int) ctx.get("error.status_code"));
                    dispatcher.forward(request, ctx.getResponse());
                }
            }
        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }
        return null;
    }
}

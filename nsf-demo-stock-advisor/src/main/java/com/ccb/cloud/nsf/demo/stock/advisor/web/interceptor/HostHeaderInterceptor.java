package com.ccb.cloud.nsf.demo.stock.advisor.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2020/7/2
 **/
@Component
public class HostHeaderInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HostHeaderInterceptor.class);

    @Value("${http.host:#{null}}")
    private String host;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if (!StringUtils.isEmpty(host)) {
            request.getHeaders().add("Host", host);
        }
        logger.info("Request  --> URI: {}, Header: {}", request.getURI(), request.getHeaders());
        return execution.execute(request, body);
    }
}

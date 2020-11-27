package com.ccb.cloud.nsf.demo.stock.viewer.web.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2019/12/26
 **/
@Component
public class GlobalHeaderHttpInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(HttpHeaders.CONNECTION, "close");
        return execution.execute(request, body);
    }
}

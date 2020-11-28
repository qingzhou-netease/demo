package com.ccb.cloud.nsf.demo.stock.proxy.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Enumeration;

@RestController
@ResponseBody
public class ProxyController {

    private static Logger logger = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${proxy_url}")
    String proxy;

    @RequestMapping("/**")
    public String proxy(HttpServletRequest request) throws IOException {
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String method = request.getMethod();

        Enumeration<String> headerNames = request.getHeaderNames();
        HttpHeaders httpHeaders = new HttpHeaders();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerVal = request.getHeader(headerName);
            httpHeaders.add(headerName, headerVal);
        }

        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        URI url = URI.create(proxy + uri + "?" +queryString);

        logger.info("proxy, url:{}", url);

        ResponseEntity<String> response = restTemplate.exchange(new RequestEntity<>(body, httpHeaders,HttpMethod.resolve(method), url), String.class);
        if (response.getStatusCodeValue() == 200) {
            return response.getBody();
        }
        return "proxy failed";
    }

}

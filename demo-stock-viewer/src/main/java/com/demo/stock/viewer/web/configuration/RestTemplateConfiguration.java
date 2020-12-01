package com.demo.stock.viewer.web.configuration;

import java.util.List;

import javax.annotation.PostConstruct;

import com.demo.stock.viewer.web.interceptor.GlobalHeaderHttpInterceptor;
import com.demo.stock.viewer.web.interceptor.TraceHttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenjiahan
 */
@Configuration
public class RestTemplateConfiguration {

	@Bean("custom")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	TraceHttpInterceptor traceHttpInterceptor;

	@Autowired
	GlobalHeaderHttpInterceptor globalHeaderHttpInterceptor;

	@Autowired
	RestTemplate restTemplate;

	@PostConstruct
	public void restTemplateInterceptors() {
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if(!interceptors.contains(traceHttpInterceptor)) {
			interceptors.add(traceHttpInterceptor);
			interceptors.add(globalHeaderHttpInterceptor);
			restTemplate.setInterceptors(interceptors);
		}
	}
}

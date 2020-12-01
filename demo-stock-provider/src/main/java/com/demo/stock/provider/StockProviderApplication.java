package com.demo.stock.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author chenjiahan
 */
@SpringBootApplication
public class StockProviderApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(StockProviderApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StockProviderApplication.class);
	}
}

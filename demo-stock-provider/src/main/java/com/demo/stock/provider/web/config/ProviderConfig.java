package com.demo.stock.provider.web.config;

import com.demo.stock.provider.web.client.AggregateClient;
import com.demo.stock.provider.web.client.mysql.MysqlClient;
import com.demo.stock.provider.web.client.mysql.StockJsonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.demo.stock.provider.web.client.MockClient;
import com.demo.stock.provider.web.client.NeteaseStockClient;
import com.demo.stock.provider.web.client.StockClient;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class ProviderConfig {

	@Bean(name="remoteRestTemplate")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@ConditionalOnProperty(name="offline", havingValue="true")
	public StockClient mockStockClient() {
		return new MockClient();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public StockClient neteaseStockClient() {
		return new NeteaseStockClient();
	}

	@ConditionalOnProperty(name = "mysql")
	public class MysqlConfig {

		@Bean
		public DataSource dataSource(@Value("${mysql.url}") String url, @Value("${mysql.username}") String username,
									 @Value("${mysql.password}") String password) {
			return DataSourceBuilder.create()
							.username(username)
							.password(password)
							.url(url)
							.driverClassName("com.mysql.jdbc.Driver")
							.build();
		}

		@Bean
		public StockJsonService stockJsonService(JdbcTemplate jdbcTemplate) {
			return new StockJsonService(jdbcTemplate);
		}

		@Bean
		public StockClient MysqlClient(StockJsonService stockJsonService) {
			return new MysqlClient(stockJsonService);
		}
	}

	@Bean
	@ConditionalOnMissingBean
	public DataSource blankDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public AggregateClient aggregateClient(List<StockClient> clients) {
		return new AggregateClient(clients);
	}
}

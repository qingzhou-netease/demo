package com.demo.stock.provider.web.client;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

import com.demo.stock.provider.web.entity.Stock;
import com.demo.stock.provider.web.util.CastKit;
import com.demo.stock.provider.web.util.StringKit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import org.springframework.util.StreamUtils;

public class MockClient implements StockClient, InitializingBean {

	private Map<String, Stock> stockMap = new HashMap<>();

	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public Stock getStockById(String stockId) throws Exception {
		
		if(StringKit.isEmpty(stockId)) return null;
		return stockMap.get(stockId.toLowerCase());
	}

	@Override
	public List<Stock> getStockBatchByIds(String stockIds) throws Exception {
		
		List<Stock> stocks = new ArrayList<>();
		if(StringKit.isEmpty(stockIds)) return stocks;
		
		Arrays.stream(stockIds.split(",")).forEach(id -> {
			stocks.add(stockMap.get(id.toLowerCase()));
		});
		
		return stocks;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Resource resource = resourceLoader.getResource("classpath:stock_data.json");;
		InputStream jsonStream = resource.getInputStream();
		String stockStr = StreamUtils.copyToString(jsonStream, Charset.forName("UTF-8"));

		List<Stock> stocks = CastKit.str2StockList(stockStr);
		if(!CollectionUtils.isEmpty(stocks)) {
			stocks.forEach(s -> {
				stockMap.put(s.getId().toLowerCase(), s);
			});
		}
	}

}

package com.demo.stock.provider.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.demo.stock.provider.web.entity.Stock;
import com.demo.stock.provider.web.client.AggregateClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.stock.provider.web.service.IProviderService;
import com.demo.stock.provider.web.util.StringKit;

@Service
public class ProviderServiceImpl implements IProviderService {

	@Autowired
	AggregateClient client;
	
	private static Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

	//mock
	@Value("${stock_ids}")
	private String stockIds;

	@Override
	public List<Stock> getAllStocks() {

		List<Stock> stocks = new ArrayList<>();
		if(!StringKit.isEmpty(stockIds)) {
			for(String id : stockIds.split(",")) {
				Stock s = getStockById(id);
				if(s != null) {
					stocks.add(s);
				}
			}
		}
		return stocks;
	}

	@Override
	public Stock getStockById(String stockId) {

		Stock stock = null;
		if(StringKit.isEmpty(stockId)) return stock;
		try {
			stock = client.getStockById(stockId);

		} catch (Exception e) {
			log.warn("get stock by id failed ",e);
		}
		return stock;
	}

	@Override
	public List<Stock> getStocksByIds(String stockIds) {

		List<Stock> stocks = new ArrayList<>();
		if(StringKit.isEmpty(stockIds)) return stocks;
		try {
			List<Stock> tempStocks = client.getStockBatchByIds(stockIds);
			stocks.addAll(tempStocks);
		} catch (Exception e) {
			log.warn("get stocks by ids failed ",e);
		}
		return stocks;
	}
}

package com.ccb.cloud.nsf.demo.stock.provider.web.client;

import java.util.List;

import com.ccb.cloud.nsf.demo.stock.provider.web.entity.Stock;

public interface StockClient {

	Stock getStockById(String stockId) throws Exception;
	
	List<Stock> getStockBatchByIds(String stockIds) throws Exception;

	default int order() {
		return 0;
	}
	
}

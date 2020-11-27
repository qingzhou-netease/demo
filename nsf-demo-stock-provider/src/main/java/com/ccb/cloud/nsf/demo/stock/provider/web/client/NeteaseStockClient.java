package com.ccb.cloud.nsf.demo.stock.provider.web.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ccb.cloud.nsf.demo.stock.provider.web.entity.Stock;
import com.ccb.cloud.nsf.demo.stock.provider.web.util.CastKit;
import com.ccb.cloud.nsf.demo.stock.provider.web.util.StringKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class NeteaseStockClient implements StockClient {

	@Autowired
	@Qualifier(value = "remoteRestTemplate")
	RestTemplate restTemplate;
	
	private static final String SEARCH_URL = "http://api.money.126.net/data/feed/%s,money.api";
	
	/**
	 * e.g. http://img1.money.126.net/chart/hs/kline/day/90/1000002.png
	 * 		http://img1.money.126.net/chart/hs/kline/day/30/1000002.png
	 * 		
	 * 		http://img1.money.126.net/chart/hs/kline/week/1000002.png
	 * day:
	 * 30
	 * 60
	 * 180
	 * 
	 * week
	 * month
	 * 
	 * 例外 分时图
	 * http://img1.money.126.net/chart/hs/time/540x360/1000002.png
	 * 
	 */
	private static final String K_LINE_IMG_URL = "http://img1.money.126.net/chart/hs/kline/day/%s/%s.png";
	
	private static final String SH = "SH";
	private static final String SZ = "SZ";
	//do not change order
	private static String[] convertRule = {"SH", "SZ"};
	/**
	 * fixed structure 
	 * 
	 * e.g. 
	 * _ntes_quote_callback({"100001":{"name":"123"}, "100002":{"name":"414"}});
	 * 
	 */
	private static final String RESULT_PREFIX = "_ntes_quote_callback(";
	private static final String RESULT_SUFFIX = ");";
	
	private static final Map<String, String> FIELD_MAPPING = new HashMap<>();
	
	static {
		FIELD_MAPPING.put("code", "id");
		FIELD_MAPPING.put("name", "name");
		FIELD_MAPPING.put("open", "openingPrice");
		FIELD_MAPPING.put("yestclose", "closingPrice");
		FIELD_MAPPING.put("price", "currentPrice");
		FIELD_MAPPING.put("bid1", "inPrice");
		FIELD_MAPPING.put("ask1", "outPrice");
		FIELD_MAPPING.put("high", "topTodayPrice");
		FIELD_MAPPING.put("low", "bottomTodayPrice");
	}
	
	@Override
	public Stock getStockById(String stockId) throws Exception {
		String stockStr = restTemplate.getForObject(String.format(SEARCH_URL, convertStockId(stockId)), String.class);
		List<Stock> stocks = str2Stock(stockStr);
		Stock stock = null;
		if(stocks != null && stocks.size() >= 1) {
			stock = stocks.get(0);
			stock.setDailyKLineAddr(getKLineAddrById(stockId, KLineType.DAY));
			//reverse stock Id
			stock.setId(restoreConvertStockId(stock.getId()));
		}
		return stock;
	}

	@Override
	public List<Stock> getStockBatchByIds(String stockIds) throws Exception {
		String stockStr = restTemplate.getForObject(String.format(SEARCH_URL, convertStockId(stockIds)), String.class);
		List<Stock> stocks = str2Stock(stockStr);
		for(Stock stock : stocks) {
			stock.setDailyKLineAddr(getKLineAddrById(stock.getId(), KLineType.DAY));
			//reverse stock Id
			stock.setId(restoreConvertStockId(stock.getId()));
		}
		return stocks;
	}

	public String getKLineAddrById(String stockId, KLineType type) {
		
		if(StringKit.isEmpty(stockId) || type == null) {
			return null;
		} 
		//hack
		return String.format(K_LINE_IMG_URL, "30", stockId);
	}
	
	private List<Stock> str2Stock(String stockStr) throws Exception {
		
		if(StringKit.isEmpty(stockStr)) return null;

		List<Stock> stocks = new ArrayList<>();
		String raw = stockStr;
		
		//cut head and tail
		if(raw.startsWith(RESULT_PREFIX) && 
				raw.endsWith(RESULT_SUFFIX)) {
			raw = raw.substring(raw.indexOf(RESULT_PREFIX) + RESULT_PREFIX.length(), 
					raw.lastIndexOf(RESULT_SUFFIX));
		}
		
		JsonNode root = CastKit.str2Node(raw);
		if(root == null) return null;
		
		for(JsonNode node : root) {
			Map<String, String> paramMap = new HashMap<>();
			Iterator<String> nameIter = node.fieldNames();
			while(nameIter.hasNext()) {
				String fieldName = nameIter.next();
				if(FIELD_MAPPING.containsKey(fieldName)) {
					paramMap.put(FIELD_MAPPING.get(fieldName), node.get(fieldName).asText());
				}
			}
			Stock stock = CastKit.map2Stock(paramMap);
			stocks.add(stock);
		}
		
		return stocks;
	}
	
	/**
	 * SH -> 0
	 * SZ -> 1
	 */
	private static String convertStockId(String stockId) {
		if(StringKit.isEmpty(stockId)) return stockId;
		return stockId.toUpperCase().replace(SH, "0").replace(SZ, "1");
	}
	
	/**
	 * 0 -> SH
	 * 1 -> SZ
	 */
	private static String restoreConvertStockId(String stockId) {
		if(StringKit.isEmpty(stockId)) return stockId;
		String type = String.valueOf(stockId.charAt(0));
		return stockId.replaceFirst(type, convertRule[Integer.parseInt(type)]);
	}
}

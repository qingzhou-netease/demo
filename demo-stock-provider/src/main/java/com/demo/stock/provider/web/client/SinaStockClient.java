package com.demo.stock.provider.web.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.stock.provider.web.entity.Stock;
import com.demo.stock.provider.web.exception.StockException;
import com.demo.stock.provider.web.util.CastKit;
import com.demo.stock.provider.web.util.StringKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class SinaStockClient implements StockClient{

	
	@Autowired
	RestTemplate restTemplate;
	
	private static final String SEARCH_URL = "http://hq.sinajs.cn/list=";
	
	/**
	 * e.g. http://image.sinajs.cn/newchart/min/n/sz000001.gif
	 */
	private static final String K_LINE_IMG_URL = "http://image.sinajs.cn/newchart/%s/n/%s.gif";
	
	private static Map<KLineType, String> kLineMapping = new HashMap<>();
			
	static {
		kLineMapping.put(KLineType.MINUTE, "min");
		kLineMapping.put(KLineType.DAY, "daily");
		kLineMapping.put(KLineType.WEEK, "weekly");
		kLineMapping.put(KLineType.MONTH, "monthly");
	}
	/**
	 * 新浪返回数据字段顺序，后半段未给出
	 */
	private static final String[] SINA_STOCK_FIELDS = {"name","openingPrice",
			"closingPrice","currentPrice","topTodayPrice","bottomTodayPrice",
			"inPrice","outPrice"};
	
	@Override
	public Stock getStockById(String stockId) throws StockException {
		String stockStr = restTemplate.getForObject(SEARCH_URL + stockId, String.class);
		Stock stock = str2Stock(stockStr);
		stock.setDailyKLineAddr(getKLineAddrById(stock.getId(), KLineType.DAY));
		return stock;
	}
	

	@Override
	public List<Stock> getStockBatchByIds(String stockIds) throws StockException {
		
		List<Stock> stocks = new ArrayList<>();
		if(StringKit.isEmpty(stockIds)) return stocks;
		
		String stockResults = restTemplate.getForObject(SEARCH_URL + stockIds, String.class);
		if(StringKit.isEmpty(stockResults)) return stocks;
		
		stockResults = stockResults.replace("\n", "");
		
		String[] stockStrs = stockResults.split(";");
		for(String stockStr : stockStrs) {
			Stock stock = str2Stock(stockStr);
			stock.setDailyKLineAddr(getKLineAddrById(stock.getId(), KLineType.DAY));
			if(stock != null) {
				stocks.add(stock);
			}
		}
		return stocks;
	}
	
	private String getKLineAddrById(String stockId, KLineType type) {
		
		if(StringKit.isEmpty(stockId) || type == null) {
			return null;
		} 
		return String.format(K_LINE_IMG_URL, kLineMapping.get(type),stockId);
	}

	/**
	 * @param stockStr
	 * 新浪股票返回格式如下:
	 * var hq_str_szxxxxxx="平安银行,11.030,......."
	 * 
	 * @return
	 * @throws StockException
	 */
	private Stock str2Stock(String stockStr) throws StockException {
		
		if(StringKit.isEmpty(stockStr)) {
			return null;
		} 
		
		String[] rawStr = stockStr.split("=");
		if(rawStr.length < 2) {
			throw new StockException("invalid stock data : " + rawStr);
		}
		
		//数据名部分
		String rawTitle = rawStr[0];
		String id = rawTitle.substring(rawTitle.lastIndexOf("_")+1);
		
		//数据部分
		String rawNumStr = rawStr[1].substring(1, rawStr[1].length());
		if(StringKit.isEmpty(rawNumStr)) {
			return null;
		}
		String[] nums = rawNumStr.split(",");
		if(nums.length < SINA_STOCK_FIELDS.length) {
			throw new StockException("incomplete stock fields :" + Arrays.toString(nums));
		}
		
		Map<String, String> params = new HashMap<>();
		params.put("id", id);
		for(int i = 0; i < SINA_STOCK_FIELDS.length; i++) {
			params.put(SINA_STOCK_FIELDS[i], nums[i]);
		}
		
		Stock stock;
		try {
			stock = CastKit.map2Stock(params);
		} catch (Exception e) {
			throw new StockException("cast map to stock fail");
		}
		return stock;
	}
}

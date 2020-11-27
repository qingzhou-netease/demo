package com.ccb.cloud.nsf.demo.stock.provider.web.util;

import java.util.List;
import java.util.Map;

import com.ccb.cloud.nsf.demo.stock.provider.web.entity.Stock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class CastKit {

	private static final ObjectMapper om = new ObjectMapper();
	
	public static List<Stock> str2StockList(String str) throws Exception {
		if(StringKit.isEmpty(str)) return null;
		
		TypeFactory typeFactory = om.getTypeFactory();
		List<Stock> stockList = om.readValue(str, typeFactory.constructCollectionType(List.class, Stock.class));
		return stockList;
	}
	
	public static Stock str2Stock(String str) throws Exception {
		
		if(StringKit.isEmpty(str)) return null;
		
		//remove array symbol
		if(str.indexOf('[') == 0 && str.charAt(str.length()-1) == ']') {
			str = str.substring(1, str.length());
		}
		return om.readValue(str, Stock.class);
	}
	
	public static Stock map2Stock(Map<String, String> map) throws Exception {
		return om.convertValue(map, Stock.class);
	}
	
	public static Map<String, Object> str2Map(String str) throws Exception {
		return om.readValue(str, Map.class);
	}
	
	public static JsonNode str2Node(String str) throws Exception {
		return om.readTree(str);
	}
	
	public static void main(String[] args) {
		
		try {
			JsonNode node = str2Node("{\"10001\":{\"code\": \"1000001\", \"percent\": 0.018165, \"high\": 11.22}}");
			node.forEach(n -> {
				
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

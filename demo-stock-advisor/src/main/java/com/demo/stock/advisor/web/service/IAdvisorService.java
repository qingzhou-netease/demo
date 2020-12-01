package com.demo.stock.advisor.web.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.demo.stock.advisor.web.entity.Stock;

public interface IAdvisorService {
	public List<Stock> getHotStocks() throws Exception;
	
	public List<String> batchHi();
	
	public String deepInvoke(int times);
	
	public String divide(HttpServletRequest request);

	public String echoProviderWithHeaders(HttpServletRequest request, String key, String value);

}

package com.ccb.cloud.nsf.demo.stock.viewer.web.service;

import com.ccb.cloud.nsf.demo.stock.viewer.web.entity.Stock;

import java.util.List;


public interface IStockService {

    public List<Stock> getStockList(int delay) throws Exception;

    public Stock getStockById(String stockId) throws Exception;

    public List<Stock> getHotStockAdvice() throws Exception;

    public String echoAdvisor(int times);

    public String echoProvider(int times);

    public String echoAdvisorWithHeader(int times, String key, String value);

    public String echoProviderWithHeader(int times, String key, String value);

    public String deepInvoke(int times);

}

package com.demo.stock.viewer.web.entity;

import java.util.List;

/**
 * @author Weng Yanghui (wengyanghui@corp.netease.com)
 * @version $Id: Const.java, v 1.0 2018/5/16
 */
public class HttpResponse {

    private String message;
    private List<Stock> stocks;

    public HttpResponse(String message) {
        this.message = message;
    }

    public HttpResponse(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    //    @Override
//    public String toString() {
//        return "Stock [id=" + id + ", name=" + name + ", dailyKLineAddr=" + dailyKLineAddr + ", openingPrice=" + openingPrice
//                + ", closingPrice=" + closingPrice + ", currentPrice=" + currentPrice + ", inPrice=" + inPrice
//                + ", outPrice=" + outPrice + ", topTodayPrice=" + topTodayPrice + ", bottomTodayPrice="
//                + bottomTodayPrice + "]";
//    }

    @Override
    public String toString(){
        return "ssss";
    }
}

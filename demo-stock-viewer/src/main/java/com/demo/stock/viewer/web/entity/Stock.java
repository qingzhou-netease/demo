package com.demo.stock.viewer.web.entity;

public class Stock {

	private String id;
	
	private String name;
	
	/**
	 * 日K线图片地址
	 */
	private String dailyKLineAddr;
	
	/**
	 * 开盘价
	 */
	private String openingPrice;
	
	/**
	 * 收盘价
	 */
	private String closingPrice;
	
	/**
	 * 最近成交价
	 */
	private String currentPrice;
	
	/**
	 * 买入价
	 */
	private String inPrice;
	
	/**
	 * 卖出价
	 */
	private String outPrice;
	
	/**
	 * 今日最高价
	 */
	private String topTodayPrice;
	
	/**
	 * 今日最低价
	 */
	private String bottomTodayPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDailyKLineAddr() {
		return dailyKLineAddr;
	}

	public void setDailyKLineAddr(String dailyKLineAddr) {
		this.dailyKLineAddr = dailyKLineAddr;
	}

	public String getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(String openingPrice) {
		this.openingPrice = openingPrice;
	}

	public String getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(String closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getInPrice() {
		return inPrice;
	}

	public void setInPrice(String inPrice) {
		this.inPrice = inPrice;
	}

	public String getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(String outPrice) {
		this.outPrice = outPrice;
	}

	public String getTopTodayPrice() {
		return topTodayPrice;
	}

	public void setTopTodayPrice(String topTodayPrice) {
		this.topTodayPrice = topTodayPrice;
	}

	public String getBottomTodayPrice() {
		return bottomTodayPrice;
	}

	public void setBottomTodayPrice(String bottomTodayPrice) {
		this.bottomTodayPrice = bottomTodayPrice;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", name=" + name + ", dailyKLineAddr=" + dailyKLineAddr + ", openingPrice=" + openingPrice
				+ ", closingPrice=" + closingPrice + ", currentPrice=" + currentPrice + ", inPrice=" + inPrice
				+ ", outPrice=" + outPrice + ", topTodayPrice=" + topTodayPrice + ", bottomTodayPrice="
				+ bottomTodayPrice + "]";
	}


}

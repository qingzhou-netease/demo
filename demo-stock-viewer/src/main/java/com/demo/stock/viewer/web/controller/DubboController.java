//package com.netease.cloud.nsf.demo.stock.viewer.web.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.netease.cloud.nsf.demo.stock.api.WallService;
//import com.netease.cloud.nsf.demo.stock.api.entity.Stock;
//import com.netease.cloud.nsf.demo.stock.viewer.web.manager.LogManager;
//
//@Controller
//@RequestMapping("/dubbo")
//public class DubboController {
//
//	private static Logger log = LoggerFactory.getLogger(DubboController.class);
//
//    @Reference(timeout = 3000, cluster = "nsf")
//    WallService wallService;
//    
//    @GetMapping(value = "/stocks", produces = "application/json")
//    @ResponseBody
//    public Map<String, Object> getStockList(@RequestParam(name = "delay", required = false, defaultValue = "0") int delay) {
//
//    	Map<String, Object> resultMap = new HashMap<>();
//        List<Stock> stocks = new ArrayList<>();
//        try {
//            stocks = wallService.getStockList(delay);
//        } catch (Exception e) {
//            log.warn("get stock list failed ...");
//        }
//        resultMap.put("stocks", stocks);
//        return resultMap;
//    }
//
//    @GetMapping(value = "/advices/hot", produces = "application/json")
//    @ResponseBody
//    public Map<String, Object> getHotAdvice() {
//
//    	Map<String, Object> resultMap = new HashMap<>();
//        List<Stock> stocks = new ArrayList<>();
//        try {
//            stocks = wallService.getHotAdvice();
//        } catch (Exception e) {
//            log.warn("get hot stock advice failed ...");
//            Stock stock = new Stock();
//			stock.setName("Cannot get any advice from dubbo service");
//			stocks.add(stock);
//			resultMap.put("stocks", stocks);
//			return resultMap;
//        }
//        resultMap.put("stocks", stocks);
//        return resultMap;
//    }
//
//    @GetMapping("/stocks/{stockId}")
//    @ResponseBody
//    public Stock getStockById(@PathVariable String stockId) {
//
//        Stock stock = null;
//        try {
//            stock = wallService.getStockById(stockId);
//        } catch (Exception e) {
//            log.warn("get stock[{}] info failed ...", stockId);
//        }
//        return stock;
//    }
//    
//    @GetMapping("/echo/advisor")
//    @ResponseBody
//    public String echoAdvisor(HttpServletRequest request) {
//    	String result = wallService.echoAdvisor();
//    	LogManager.put(UUID.randomUUID().toString(), result);
//    	return result;
//    }
//    
//    @GetMapping("/echo/provider")
//    @ResponseBody
//    public String echoProvider(HttpServletRequest request) {
//    	String result = wallService.echoProvider();
//    	LogManager.put(UUID.randomUUID().toString(), result);
//    	return result;
//    }
//}

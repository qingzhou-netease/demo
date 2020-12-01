package com.demo.stock.viewer.web.controller;

import com.demo.stock.viewer.web.service.IRandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class RandomController {

	@Autowired
    IRandomService randomService;
	
	@GetMapping("/number")
	public int getNumber() {
		
		return randomService.getRandomNumber();
	}
	
	@GetMapping("/string")
	public String getString() {
		
		return randomService.getRanomString();
	}

	/**
	 * 熔断测试
	 */
	int count = 0;
	@GetMapping("/sleepgw")
	public String sleepgw(HttpServletRequest request, String msg)  throws InterruptedException {
		if (count++ % 5 < 3) {
			TimeUnit.SECONDS.sleep(10);
		}
		return "第" + count + "次sleepgw,参数:" + msg + ",响应服务地址:" + request.getServerName() + ":" + request.getServerPort();
	}
}

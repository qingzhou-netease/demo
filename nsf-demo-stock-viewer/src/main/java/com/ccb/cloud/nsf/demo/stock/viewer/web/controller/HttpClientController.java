package com.ccb.cloud.nsf.demo.stock.viewer.web.controller;

import com.ccb.cloud.nsf.demo.stock.viewer.web.service.IHttpClientRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController()
public class HttpClientController {

	@Autowired private IHttpClientRequestService clientService;

	@GetMapping("/hc_test/post")
	public Object post() {
		HashMap<String, String> testObj = new HashMap<>();
		testObj.put("id", UUID.randomUUID().toString());
		return clientService.echoObject(testObj);
	}
	
}

package com.ccb.cloud.nsf.demo.stock.viewer.web.service.impl;

import com.ccb.cloud.nsf.demo.stock.viewer.web.service.IHttpClientRequestService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 张武(zhangwu@corp.netease.com) at 2018/9/18
 */
@Component
public class HttpClientRequestServiceImpl implements IHttpClientRequestService {


	@Value("${stock_provider_url}")
	private String stockProviderUrl;

	private CloseableHttpClient client = HttpClients.createDefault();
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Object echoObject(Object obj) {
		try {
			String uri = stockProviderUrl + "/echoPost";
//			Object o = restTemplate.postForObject(uri, obj, Object.class);
			HttpPost req = new HttpPost(uri);
			req.setHeader("Content-Type", "application/json");
			req.setEntity(new StringEntity(mapper.writeValueAsString(obj)));
			CloseableHttpResponse resp = client.execute(req);
			String respBody = EntityUtils.toString(resp.getEntity());
			return mapper.readValue(respBody, new TypeReference<Map<String, Object>>(){});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}

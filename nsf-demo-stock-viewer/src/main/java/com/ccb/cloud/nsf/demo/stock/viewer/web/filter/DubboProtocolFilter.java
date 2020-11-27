//package com.netease.cloud.nsf.demo.stock.viewer.web.filter;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import com.alibaba.dubbo.common.extension.Activate;
//import com.alibaba.dubbo.rpc.Filter;
//import com.alibaba.dubbo.rpc.Invocation;
//import com.alibaba.dubbo.rpc.Invoker;
//import com.alibaba.dubbo.rpc.Result;
//import com.alibaba.dubbo.rpc.RpcException;
//import com.netease.cloud.nsf.demo.stock.viewer.web.manager.LogManager;
//
//@Activate
//public class DubboProtocolFilter implements Filter {
//
//	
//	@Override
//	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//		
//		String time = LocalDateTime.now().plusDays(1) + "";
//		String methodName = invocation.getMethodName();
//		String url = invoker.getUrl().getAddress();
//		
//		Result result = invoker.invoke(invocation);
//		
//		String returnCode = result.hasException() ? "error" : "success";
//		String[] infos = {time, url + "/" + methodName, returnCode};
//		String val = String.join("---->", infos);
//		LogManager.put(UUID.randomUUID().toString(), val);
//		
//		return result;
//	}
//
//	
//}

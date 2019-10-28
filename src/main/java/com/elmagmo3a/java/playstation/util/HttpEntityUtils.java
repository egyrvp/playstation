package com.elmagmo3a.java.playstation.util;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpEntityUtils {

	public static HttpEntity<Object> createHttpEntity(Map<String, String> headers) {
		HttpHeaders requestHeaders = prepareRequestHeader(headers);
		return new HttpEntity<>(requestHeaders);
	}

	public static HttpEntity<Object> createHttpEntity(Object body) {
		return createHttpEntity(body, null);
	}

	public static HttpEntity<Object> createHttpEntity(Object body, Map<String, String> headers) {
		HttpHeaders requestHeaders = prepareRequestHeader(headers);
		return new HttpEntity<>(body, requestHeaders);
	}

	private static HttpHeaders prepareRequestHeader(Map<String, String> headers) {
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);
		if (CommonUtils.isNull(headers)) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				requestHeader.set(header.getKey(), header.getValue());
			}
		}
		return requestHeader;
	}
}

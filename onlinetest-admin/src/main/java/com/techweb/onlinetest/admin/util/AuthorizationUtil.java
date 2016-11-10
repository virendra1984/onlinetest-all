package com.techweb.onlinetest.admin.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techweb.onlinetest.admin.OnlinetestAdminException;
import com.techweb.onlinetest.admin.service.cache.CacheService;


@Component
public class AuthorizationUtil {

	private final static Logger logger = LoggerFactory.getLogger(AuthorizationUtil.class);

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private final String scbRestUser;
	private final String password;
	private final HttpUtil httpUtil;
	private final CacheService<String, Object> cacheService;

	@Autowired
	public AuthorizationUtil(@Value("${scb.rest.user}") String scbRestUser,
			@Value("${password}") String password, HttpUtil httpUtil,
			@Qualifier("cacheService") CacheService<String, Object> cacheService) {
		this.scbRestUser = scbRestUser;
		this.password = password;
		this.httpUtil = httpUtil;
		this.cacheService = cacheService;
	}

	public String generateAuthToken() {

		Map<String, String> headers = new HashMap<>();
		headers.put(SCBConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		String response = httpUtil.postOrPut(URLConstants.AUTHORIZATION_URL, getAuthPayload(),
				headers, HttpMethod.POST);

		Map<String, Object> responseData = null;
		try {
			responseData = parseResponse(response);
		} catch (IOException e) {
			logger.error("Error while parsing authorization token response", e);
			throw new OnlinetestAdminException("Error while parsing authorization token response", e);
		}

		if (CollectionUtils.isEmpty(responseData)) {
			logger.error("No response obtained from Authorization api");
			throw new OnlinetestAdminException("No response obtained from Authorization api");
		}

		int statusCode = Integer.parseInt(responseData.get(SCBConstant.STATUS_CODE).toString());

		if (statusCode != 200) {
			String msg = String.format("API responded with error code : %d and message : %s",
					statusCode, responseData.get(SCBConstant.MESSAGE).toString());
			logger.error(msg);
			throw new OnlinetestAdminException(msg);
		}
		String authorizationToken = responseData.get(SCBConstant.DATA).toString();
		cacheService.put(SCBConstant.AUTH_HEADER, authorizationToken);
		return authorizationToken;
	}

	private Map<String, Object> parseResponse(String response) throws JsonParseException,
			JsonMappingException, IOException {
		return OBJECT_MAPPER.readValue(response, new TypeReference<Map<String, Object>>() {
		});
	}

	private Map<String, String> getAuthPayload() {
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(SCBConstant.EMAIL, scbRestUser);
		dataMap.put(SCBConstant.PASSWORD, password);
		return dataMap;
	}

}

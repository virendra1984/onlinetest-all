package com.techweb.onlinetest.admin.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techweb.onlinetest.admin.OnlinetestAdminException;
import com.techweb.onlinetest.admin.service.cache.CacheService;



@Component
public class RestServiceUtil {

  private final static Logger logger = LoggerFactory.getLogger(RestServiceUtil.class);

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final HttpUtil httpUtil;

  private final AuthorizationUtil authorizationUtil;

  private final CacheService<String, Object> cacheService;

  private static int retryCount = 0;

  @Autowired
  public RestServiceUtil(HttpUtil httpUtil, AuthorizationUtil authorizationUtil,
      @Qualifier("cacheService") CacheService<String, Object> cacheService) {
    this.httpUtil = httpUtil;
    this.authorizationUtil = authorizationUtil;
    this.cacheService = cacheService;
  }

  public JsonNode makeRequest(String url, Object payload, Map<String, Object> parameters,
      HttpMethod method) {

    if (StringUtils.isEmpty(url)) {
      logger.info("Url cannot be null/empty");
      throw new IllegalArgumentException("Url cannot be null/empty");
    }

    if ((HttpMethod.POST == method || HttpMethod.PUT == method) && ObjectUtils.isEmpty(payload)) {
      logger.info("Cannot make post or put request with empty/null payload");
      throw new IllegalArgumentException("Cannot make post or put request with empty/null payload");
    }

    Map<String, String> headers = createHeaders();

    String response = null;

    try {
      if (HttpMethod.GET == method) {
        response = httpUtil.get(url, headers);
      } else if (HttpMethod.POST == method || HttpMethod.PUT == method) {
        response = httpUtil.postOrPut(url, payload, headers, method);
      }
    } catch (Exception e) {
      logger.error("Error while making request to rest-api url : " + url, e);
      throw new OnlinetestAdminException("Error while making request to rest-api url : " + url, e);
    }

    JsonNode responseJson = null;
    try {
      responseJson = parseResponse(response);
    } catch (Exception e) {
      logger.error(String.format("Error while parsing response for url : %s", url), e);
      throw new OnlinetestAdminException(String.format("Error while parsing response for url : %s", url),
          e);
    }

    int status = responseJson.get(SCBConstant.STATUS_CODE).asInt();
    if (status == 401) {
      logger.info("The authorization token has expired, hence generating new one.");
      if (++retryCount < 2) {
        authorizationUtil.generateAuthToken();
        retryCount = 0;
        makeRequest(url, payload, parameters, method);
      } else {
        logger.error("The rest service seems to be down, aborting process.");
        throw new OnlinetestAdminException("The rest service seems to be down, aborting process.");
      }
    }
    return responseJson;
  }

  public void writeToStream(String url, OutputStream outputStream) {


    Map<String, String> headers = createHeaders();
    headers.remove(SCBConstant.CONTENT_TYPE);
    httpUtil.fetchDataAndWriteToStream(url, headers, outputStream);

  }

  private JsonNode parseResponse(String responseAsString) throws JsonProcessingException,
      IOException {
    return OBJECT_MAPPER.readTree(responseAsString);
  }

  private Map<String, String> createHeaders() {

    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put(SCBConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    Object authenticationToken = cacheService.get(SCBConstant.AUTH_HEADER);

    if (ObjectUtils.isEmpty(authenticationToken)) {
      logger.info("Authentication token is not generate hence generating it.");
      authorizationUtil.generateAuthToken();
    }

    headerMap.put(SCBConstant.AUTH_HEADER, cacheService.get(SCBConstant.AUTH_HEADER).toString());
    return headerMap;
  }

}

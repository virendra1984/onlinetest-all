package com.techweb.onlinetest.admin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techweb.onlinetest.admin.OnlinetestAdminException;

@Component
public class HttpUtil {

  private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final String scbRestBaseUrl;

  @Autowired
  public HttpUtil(@Value("${scb.rest.base.url}") String scbRestBaseUrl) {
    this.scbRestBaseUrl = scbRestBaseUrl;
  }

  public String postOrPut(String url, Object payloadData, Map<String, String> headers,
      HttpMethod method) {

    if (StringUtils.isEmpty(url)) {
      logger.info("URL cannot be empty/null");
      throw new IllegalArgumentException("URL cannot be empty/null");
    }

    if (ObjectUtils.isEmpty(payloadData)) {
      logger.info("Payload cannot be empty/null");
      throw new IllegalArgumentException("v cannot be empty/null");
    }

    String finalUrl = scbRestBaseUrl + url;

    HttpClient httpClient = HttpClientBuilder.create().build();

    HttpEntityEnclosingRequestBase postRequest = null;

    if (HttpMethod.POST == method) {
      postRequest = new HttpPost(finalUrl);
    } else {
      postRequest = new HttpPut(finalUrl);
    }

    if (!CollectionUtils.isEmpty(headers)) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        postRequest.setHeader(entry.getKey(), entry.getValue());
      }
    }

    try {
      StringEntity payloadEntity =
          new StringEntity(getObjectAsString(payloadData), Charset.forName("UTF8"));
      postRequest.setEntity(payloadEntity);
    } catch (IOException e) {
      logger.error("Error while making request to url : %s", url);
      throw new OnlinetestAdminException(String.format("Error while making request to url : %s", url));
    }

    HttpResponse response = null;
    try {
      response = httpClient.execute(postRequest);
    } catch (IOException e) {
      logger.error("Error while obtaining response from url : %s", url);
      throw new OnlinetestAdminException(
          String.format("Error while obtaining response from url : %s", url));
    }

    if (response == null) {
      logger.error(String.format("No response obtained for url request : %s ", url));
      throw new OnlinetestAdminException(String.format("No response obtained for url request : %s ", url));
    }
    return parseHttpResponse(response, url);
  }

  public String get(String url, Map<String, String> headers) throws UnsupportedEncodingException {

    if (StringUtils.isEmpty(url)) {
      logger.info("URL cannot be empty/null");
      throw new IllegalArgumentException("URL cannot be empty/null");
    }

    String finalUrl = scbRestBaseUrl + url;

    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet getRequest = new HttpGet(finalUrl);

    if (!CollectionUtils.isEmpty(headers)) {
      headers.forEach((headerName, headerValue) -> {
        getRequest.setHeader(headerName, headerValue);
      });
    }

    HttpResponse response = null;
    try {
      response = httpClient.execute(getRequest);
    } catch (IOException e) {
      logger.error("Error while obtaining response from url : %s", url);
      throw new OnlinetestAdminException(
          String.format("Error while obtaining response from url : %s", url));
    }

    if (response == null) {
      logger.error(String.format("No response obtained for url request : %s ", url));
      throw new OnlinetestAdminException(String.format("No response obtained for url request : %s ", url));
    }
    return parseHttpResponse(response, url);

  }

  public void fetchDataAndWriteToStream(String url, Map<String, String> headers,
      OutputStream outputStream) {

    String finalUrl = scbRestBaseUrl + url;
    HttpGet httpGet = new HttpGet(finalUrl);
    if (!CollectionUtils.isEmpty(headers)) {
      headers.forEach((headerName, headerValue) -> {
        httpGet.setHeader(headerName, headerValue);
      });
    }
    try (CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpGet);) {
      HttpEntity entity = response.getEntity();
      entity.writeTo(outputStream);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new OnlinetestAdminException(e.getMessage(), e);
    }

  }

  private String parseHttpResponse(HttpResponse response, String url) {
    StringBuilder responseAsString = new StringBuilder();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        responseAsString.append(line);
      }
    } catch (UnsupportedOperationException | IOException e) {
      logger.error("Error while parsing response for URL : {}", url);
      throw new OnlinetestAdminException("Error while parsing response for URL : " + url, e);
    }
    return responseAsString.toString();
  }

  private String getObjectAsString(Object payload) throws IOException {
    return OBJECT_MAPPER.writeValueAsString(payload);
  }
}

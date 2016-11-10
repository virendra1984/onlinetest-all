package com.techweb.onlinetest.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.onlinetest.model.Constants;

@Component
public class SCBUtility {

	private final static String STATIC_URL = "/static/";

	@Value("${image.location}")
	private String imageLocation;

	@Value("${pdf.location}")
	private String pdfLocation;

	public String getAppUrl(HttpServletRequest request) {

		StringBuilder stemUrl = new StringBuilder();

		stemUrl.append(request.getScheme());
		stemUrl.append("://");
		stemUrl.append(request.getServerName());
		stemUrl.append(":");
		stemUrl.append(request.getServerPort());
		stemUrl.append(request.getContextPath());
		return stemUrl.toString();
	}

	public String createResourceUrl(HttpServletRequest request, String resourceLocation,
			String resourceType, String entity, long id, String fileName) {
		StringBuilder baseUrl = new StringBuilder(getAppUrl(request));
		baseUrl.append(STATIC_URL);
		baseUrl.append(Constants.IMAGE.equalsIgnoreCase(resourceType) ? imageLocation : pdfLocation);
		baseUrl.append(entity);
		baseUrl.append("/");
		baseUrl.append(id);
		baseUrl.append("/");
		baseUrl.append(fileName);
		return baseUrl.toString();
	}
	
}

package com.techweb.onlinetest.admin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinetest.model.SheetUploadHistory;
import com.techweb.onlinetest.admin.OnlinetestAdminException;
import com.techweb.onlinetest.admin.service.cache.CacheService;
import com.techweb.onlinetest.admin.util.RestServiceUtil;
import com.techweb.onlinetest.admin.util.SCBConstant;
import com.techweb.onlinetest.admin.util.URLConstants;



@Component
public class SheetHistoryService {

	private static final Logger logger = LoggerFactory
			.getLogger(SheetHistoryService.class);

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	private RestServiceUtil restServiceUtil;

	@Autowired
	@Qualifier("cacheService")
	private CacheService<String, Object> cacheService;

	@Value("${file.location}")
	private String fileLocation;

	@Value("${folder.processing}")
	private String folderProcessing;

	@Value("${folder.uploaded}")
	private String folderUploaded;

	private static final String FILE_NAME_FORMAT = "%s_%s_%s_%s";
	private static final String FILE_LOCATION = "%s/%s/%s";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"MM-dd-YYYY HH:mm:ss.SSSSSS");

	public void saveSheetUploadHistoryToCache(String section,
			MultipartFile multipartFile, String opertion, String cacheKey) {

		try {
			String fileName = saveUploadedFile(getUserName(), multipartFile,
					section);

			SheetUploadHistory history = new SheetUploadHistory();
			history.setSectionName(section);
			history.setUserName(getUserName());
			history.setFileName(fileName);
			history.setOriginalFileName(multipartFile.getOriginalFilename());
			history.setOperation(opertion);

			cacheService.put(cacheKey, history);
		} catch (Exception e) {
			logger.error("Error while saving sheet history", e);
			throw new OnlinetestAdminException("Error while saving sheet history", e);
		}
	}

	public SheetUploadHistory getSheetUploadHistoryById(long id) {

		String url = String.format(URLConstants.SHEET_GET_HISTORY_BY_ID, id);

		int status = 0;
		try {
			JsonNode response = restServiceUtil.makeRequest(url, null, null,
					HttpMethod.GET);
			status = response.get(SCBConstant.STATUS_CODE).intValue();

			if (status != 200) {
				throw new OnlinetestAdminException(
						String.format(
								"API responded with code : %d while fetching sheet history.",
								status));
			}
			String data = response.get(SCBConstant.DATA).toString();
			return OBJECT_MAPPER.readValue(data,
					new TypeReference<SheetUploadHistory>() {
					});
		} catch (Exception e) {
			logger.error("Error while fetching sheet history", e);
			throw new OnlinetestAdminException("Error while fetching sheet history", e);
		}
	}

	public void saveSheetUploadHistory(String cacheKey) {

		if (StringUtils.isEmpty(cacheKey)) {
			logger.info("Cache key cannot be null/empty");
			return;
		}

		SheetUploadHistory sheetUploadHistory = (SheetUploadHistory) cacheService
				.get(cacheKey);

		if (sheetUploadHistory == null) {
			logger.info("No sheet upload history found in cache for key :"
					+ cacheKey);
			return;
		}
		boolean result = moveFileToUploadedFolder(sheetUploadHistory);

		if (!result) {
			logger.error(
					"Error while moving file {}  from processing to uploaded folder.",
					sheetUploadHistory.getFileName());
		} else {
			int status = 0;
			JsonNode response = restServiceUtil.makeRequest(
					URLConstants.SHEET_HISTORY, sheetUploadHistory, null,
					HttpMethod.POST);
			status = response.get(SCBConstant.STATUS_CODE).intValue();

			if (status != 200) {
				throw new OnlinetestAdminException(
						String.format(
								"API responded with code : %d while saving sheet history.",
								status));
			}

		}
		cacheService.remove(cacheKey);

	}

	private boolean moveFileToUploadedFolder(
			SheetUploadHistory sheetUploadHistory) {
		String fileName = fileLocation + File.separator + folderProcessing
				+ File.separator + sheetUploadHistory.getFileName();

		File file = new File(fileName);

		if (!file.exists()) {
			logger.info("No file with name {} found at configrued location",
					sheetUploadHistory.getFileName());
			return false;
		} else {
			String destFileName = fileLocation + File.separator
					+ folderUploaded;
			File folder = new File(destFileName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			if (file.renameTo(new File(destFileName + File.separator
					+ sheetUploadHistory.getFileName()))) {
				file.delete();
			} else {
				return false;
			}
		}
		return true;
	}

	private String saveUploadedFile(String userName,
			MultipartFile multipartFile, String section) {

		String fileName = String.format(FILE_NAME_FORMAT, userName, section,
				DATE_FORMAT.format(new Date()),
				multipartFile.getOriginalFilename());

		File folder = new File(fileLocation + File.separator + folderProcessing);

		if (!folder.exists()) {
			folder.mkdirs();
		}

		String uploadFileLocation = String.format(FILE_LOCATION, fileLocation,
				folderProcessing, fileName);

		try (FileOutputStream fos = new FileOutputStream(uploadFileLocation)) {
			fos.write(multipartFile.getBytes());
			fos.flush();
		} catch (IOException e) {
			String msg = String
					.format("Error while saving upload operation for user : %s and section : %s",
							userName, section);
			logger.error(msg, e);
			throw new OnlinetestAdminException(msg, e);
		} finally {
			folder = null;
		}
		return fileName;

	}

	public List<SheetUploadHistory> getAllSheetUploadHistory() {

		int status = 0;
		try {
			JsonNode response = restServiceUtil.makeRequest(URLConstants.SHEET_HISTORY, null, null, HttpMethod.GET);
			status = response.get(SCBConstant.STATUS_CODE).intValue();

			if (status != 200) {
				throw new OnlinetestAdminException(String.format("API responded with code : %d while fetching sheet history.",	status));
			}
			String data = response.get(SCBConstant.DATA).toString();
			return OBJECT_MAPPER.readValue(data,
					new TypeReference<List<SheetUploadHistory>>() {	});
		} catch (Exception e) {
			logger.error("Error while fetching sheet history", e);
			throw new OnlinetestAdminException("Error while fetching sheet history", e);
		}

	}

	public File getRequestedFile(SheetUploadHistory sheetUploadHistory) {

		if (sheetUploadHistory == null) {
			throw new IllegalArgumentException(
					"Invalid argument, sheet upload history object cannot be null/empty");
		}

		File file = new File(fileLocation + File.separator + folderUploaded
				+ File.separator + sheetUploadHistory.getFileName());

		if (!file.exists()) {
			throw new OnlinetestAdminException("No file found with name : "
					+ sheetUploadHistory.getOriginalFileName());
		}
		return file;
	}

	private String getUserName() {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			logger.info("User not logged in.");
			throw new OnlinetestAdminException("User not logged in.");
		}
		return context.getAuthentication().getName();
	}

}

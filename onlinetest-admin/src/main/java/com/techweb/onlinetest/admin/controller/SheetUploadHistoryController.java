package com.techweb.onlinetest.admin.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.onlinetest.model.SheetUploadHistory;
import com.techweb.onlinetest.admin.OnlinetestAdminException;
import com.techweb.onlinetest.admin.service.SheetHistoryService;


@Controller
@RequestMapping(value = "admin/history")
public class SheetUploadHistoryController {

	private static final Logger logger = LoggerFactory
			.getLogger(SheetUploadHistoryController.class);

	private static final String VIEW_NAME = "admin/history";

	@Autowired
	private SheetHistoryService sheetHistoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view() {

		ModelAndView mav = new ModelAndView(VIEW_NAME);
		List<SheetUploadHistory> sheetUploadHistoryList = null;
		try {
			sheetUploadHistoryList = sheetHistoryService
					.getAllSheetUploadHistory();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		mav.addObject("history", sheetUploadHistoryList);
		return mav;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadFile(@PathVariable("id") long id) {

		SheetUploadHistory sheetUploadHistory = null;
		HttpHeaders headers = new HttpHeaders();
		byte[] fileStream = null;
		try {
			sheetUploadHistory = sheetHistoryService
					.getSheetUploadHistoryById(id);
			headers.setContentDispositionFormData(
					sheetUploadHistory.getOriginalFileName(),
					sheetUploadHistory.getOriginalFileName());
			File file = sheetHistoryService
					.getRequestedFile(sheetUploadHistory);
			fileStream = Files.readAllBytes(file.toPath());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new OnlinetestAdminException(e.getMessage(), e);
		}
		return new ResponseEntity<>(fileStream, headers, HttpStatus.OK);
	}

}

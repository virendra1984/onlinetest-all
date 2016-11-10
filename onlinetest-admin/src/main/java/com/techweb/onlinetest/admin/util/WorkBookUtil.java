package com.techweb.onlinetest.admin.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.techweb.onlinetest.admin.OnlinetestAdminException;



@Component
public class WorkBookUtil {

	public Workbook getWorkBook(MultipartFile multipartFile) {

		if (ObjectUtils.isEmpty(multipartFile)) {
			throw new IllegalArgumentException("Cannot parse empty/null xlsx file.");
		}

		String fileName = multipartFile.getOriginalFilename();

		InputStream inputStream = null;
		try {
			inputStream = multipartFile.getInputStream();
		} catch (IOException e) {
			throw new OnlinetestAdminException("Error while parsing file with name : " + fileName);
		}

		String fileExtnsion = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

		Workbook workbook = null;

		try {
			if (SCBConstant.FORMAT_XLSX.equalsIgnoreCase(fileExtnsion)
					|| SCBConstant.FORMAT_XLSM.equalsIgnoreCase(fileExtnsion)) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (SCBConstant.FORMAT_XLX.equalsIgnoreCase(fileExtnsion)) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				throw new IllegalArgumentException("Unsupported file format.");
			}
		} catch (IOException e) {
			throw new OnlinetestAdminException("Error while parsing file with name : " + fileName);
		}
		return workbook;
	}

	public Sheet getSheet(Workbook workbook, String sheetName) {

		if (workbook == null) {
			throw new IllegalArgumentException("Workbook cannot be empty/null.");
		}

		if (StringUtils.isEmpty(sheetName)) {
			throw new IllegalArgumentException("Sheet name cannot be empty/null.");
		}

		return workbook.getSheet(sheetName);
	}

}

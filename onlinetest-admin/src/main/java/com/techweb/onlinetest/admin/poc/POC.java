package com.techweb.onlinetest.admin.poc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.JsonProcessingException;

public class POC {

	public static void main(final String[] args) throws IOException {

		/*
		 * IndustryHeader industryHeader = new IndustryHeader();
		 * industryHeader.setId(new Long(1));
		 * industryHeader.setIndustryName("test");
		 * industryHeader.setCorporateSales(213232.232);
		 * industryHeader.setFinancialInsights("inSight");
		 * 
		 * List<IndustryDetail> industryDetails = new
		 * ArrayList<IndustryDetail>(); IndustryDetail id = new
		 * IndustryDetail(); id.setIndustryId(new Long(1));
		 * id.setValueChainComponentId(new Long(1)); industryDetails.add(id);
		 * 
		 * IndustryHeader up = new IndustryHeader(); up.setId(new Long(1));
		 * up.setIndustryName("sdadsas"); up.setCorporateSales(2323.232);
		 * up.setFinancialInsights("sdasdsadssd");
		 */
		Number d1 = new Double(123.21);
		Double d2 = new Double(123.21);
		
		if(d1.equals(d2)){
			System.out.println("true");
		}

	}

	private static void jackson() throws JsonProcessingException {

	}

	private static void excelPoc() throws IOException {
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("test.xlsx");

		final Workbook workbook = new XSSFWorkbook(inputStream);

		final Sheet sheet = workbook.getSheet("TrendImplication");

		final Iterator<Row> iterator = sheet.iterator();
		outer: while (iterator.hasNext()) {
			final Row row = iterator.next();

			Cell cell = row.getCell(0);

			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				break;
			}

			getCell(sheet, cell);

			cell = row.getCell(1);

			getCell(sheet, cell);

			// System.out.println(cell.getStringCellValue());

		}
	}

	private static void getCell(final Sheet sheet, final Cell cell) {

		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			final CellRangeAddress region = sheet.getMergedRegion(i);

			final int colIndex = region.getFirstColumn();
			final int rowNum = region.getFirstRow(); // check first cell of
														// the region if
			if (rowNum == cell.getRowIndex() && colIndex == cell.getColumnIndex()) {
				System.out.println(getCellValue(sheet.getRow(rowNum).getCell(colIndex)));
				return;
			}
		}

		if (cell.getCellType() == Cell.CELL_TYPE_BLANK || cell == null) {
			return;
		}
		printCellValues(cell);
	}

	private static Object getCellValue(Cell cell) {

		if (cell == null) {
			return null;
		}

		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_BLANK: {
			return null;
		}
		case Cell.CELL_TYPE_BOOLEAN: {
			return cell.getBooleanCellValue();
		}
		case Cell.CELL_TYPE_NUMERIC: {
			return cell.getNumericCellValue();
		}
		case Cell.CELL_TYPE_STRING: {
			return cell.getStringCellValue();
		}
		default: {
			return null;
		}
		}

	}

	private static void printCellValues(final Cell cell) {

		System.out.println(getCellValue(cell));
	}

}

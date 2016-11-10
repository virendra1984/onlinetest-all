package com.techweb.onlinetest.admin.poc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class App {

	public static Data readXlsx(Workbook workbook) {
		try {

			Sheet sheet = workbook.getSheet("TrendImplication");

			Row row;
			Cell cell;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			int mcell1 = 0, mcell2 = 0, mcell3 = 0, mcell4 = 0, mcell5 = 0, mcell6 = 0;
			int count1 = 1, count2 = 1, count3 = 1, count4 = 1, count5 = 1, count6 = 1;
			Data data = new Data();
			List<CellM> cels = new ArrayList<CellM>();
			CellM cm = null;
			List<String> co1 = null;
			List<String> co2 = null;
			List<String> co3 = null;
			List<String> co4 = null;
			List<String> co5 = null;
			List<String> co6 = null;
			int t = 0;
			while (rowIterator.hasNext()) {

				row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				/*
				 * while (cellIterator.hasNext()) { cell = cellIterator.next();
				 * cellType(cell); }
				 */
				if (count1 != 0 && count2 != 0 && count3 != 0 && count4 != 0 && count5 != 0
						&& count6 != 0) {

				}

				int chk = 0;
				boolean flg = false;
				chk = cellTypeData(row.getCell(0));
				if (chk != 0) {
					chk = cellTypeData(row.getCell(1));
					if (chk != 0) {
						chk = cellTypeData(row.getCell(2));
						if (chk != 0) {
							chk = cellTypeData(row.getCell(3));
							if (chk != 0) {
								chk = cellTypeData(row.getCell(4));
								if (chk == 0)
									flg = true;
							} else {
								flg = true;
							}
						} else {
							flg = true;
						}
					} else {
						flg = true;
					}
				} else {
					flg = true;
				}

				if (!flg || t == 0) {
					cm = new CellM();
					co1 = new ArrayList<String>();
					co2 = new ArrayList<String>();
					co3 = new ArrayList<String>();
					co4 = new ArrayList<String>();
					co5 = new ArrayList<String>();
					co6 = new ArrayList<String>();
				}

				cell = row.getCell(0);
				count1 = cellType(cell, co1);
				if (count1 == 0) {
					mcell1++;
				} else {
					if (mcell1 != 0)
						mcell1 = 0;
				}
				cell = row.getCell(1);
				count2 = cellType(cell, co2);
				if (count2 == 0) {
					mcell2++;
				} else {
					if (mcell2 != 0)
						mcell2 = 0;
				}
				cell = row.getCell(2);
				count3 = cellType(cell, co3);
				if (count3 == 0) {
					mcell3++;
				} else {
					if (mcell3 != 0)
						mcell3 = 0;
				}
				cell = row.getCell(3);
				count4 = cellType(cell, co4);
				if (count4 == 0) {
					mcell4++;
				} else {
					if (mcell4 != 0)
						mcell4 = 0;
				}
				cell = row.getCell(4);
				count5 = cellType(cell, co5);
				if (count5 == 0) {
					mcell5++;
				} else {
					if (mcell5 != 0)
						mcell5 = 0;
				}
				cell = row.getCell(5);
				count6 = cellType(cell, co6);
				if (count6 == 0) {
					mcell6++;
				} else {
					if (mcell6 != 0)
						mcell6 = 0;
				}
			}
			data.setData(cels);

			return data;
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}
		return null;
	}

	public static Data readXlsx5(CommonsMultipartFile inputFile) {
		try {
			// Get the workbook instance for XLSX file
			// XSSFWorkbook wb = new XSSFWorkbook(new
			// FileInputStream(inputFile));

			ByteArrayInputStream bis = new ByteArrayInputStream(inputFile.getBytes());
			XSSFWorkbook wb = new XSSFWorkbook(bis);
			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(4);

			Row row;
			Cell cell;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			int mcell1 = 0, mcell2 = 0, mcell3 = 0, mcell4 = 0, mcell5 = 0;
			int count1 = 1, count2 = 1, count3 = 1, count4 = 1, count5 = 1;
			Data data = new Data();
			List<CellM> cels = new ArrayList<CellM>();
			CellM cm = null;
			List<String> co1 = null;
			List<String> co2 = null;
			List<String> co3 = null;
			List<String> co4 = null;
			List<String> co5 = null;
			int t = 0;
			while (rowIterator.hasNext()) {

				row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				/*
				 * while (cellIterator.hasNext()) { cell = cellIterator.next();
				 * cellType(cell); }
				 */
				if (count1 != 0 && count2 != 0 && count3 != 0 && count4 != 0 && count5 != 0) {

					if (t != 0) {
						cm.setcOne(co1);
						cm.setcTwo(co2);
						;
						cm.setcThree(co3);
						cm.setcFour(co4);
						cm.setcFive(co5);
						cels.add(cm);
					} else
						t = 1;
				}

				int chk = 0;
				boolean flg = false;
				chk = cellTypeData(row.getCell(0));
				if (chk != 0) {
					chk = cellTypeData(row.getCell(1));
					if (chk != 0) {
						chk = cellTypeData(row.getCell(2));
						if (chk != 0) {
							chk = cellTypeData(row.getCell(3));
							if (chk != 0) {
								chk = cellTypeData(row.getCell(4));
								if (chk == 0)
									flg = true;
							} else {
								flg = true;
							}
						} else {
							flg = true;
						}
					} else {
						flg = true;
					}
				} else {
					flg = true;
				}

				if (!flg || t == 0) {
					cm = new CellM();
					co1 = new ArrayList<String>();
					co2 = new ArrayList<String>();
					co3 = new ArrayList<String>();
					co4 = new ArrayList<String>();
					co5 = new ArrayList<String>();
				}

				cell = row.getCell(0);
				count1 = cellType(cell, co1);
				if (count1 == 0) {
					mcell1++;
				} else {
					if (mcell1 != 0)
						System.out.println("Column1-mergeCell Total= " + mcell1++);
					mcell1 = 0;
				}
				cell = row.getCell(1);
				count2 = cellType(cell, co2);
				if (count2 == 0) {
					mcell2++;
				} else {
					if (mcell2 != 0)
						System.out.println("Column2-mergeCell Total= " + mcell2++);
					mcell2 = 0;
				}
				cell = row.getCell(2);
				count3 = cellType(cell, co3);
				if (count3 == 0) {
					mcell3++;
				} else {
					if (mcell3 != 0)
						System.out.println("Column3-mergeCell Total= " + mcell3++);
					mcell3 = 0;
				}
				cell = row.getCell(3);
				count4 = cellType(cell, co4);
				if (count4 == 0) {
					mcell4++;
				} else {
					if (mcell4 != 0)
						System.out.println("Column3-mergeCell Total= " + mcell4++);
					mcell4 = 0;
				}
				cell = row.getCell(4);
				count5 = cellType(cell, co5);
				if (count5 == 0) {
					mcell5++;
				} else {
					if (mcell5 != 0)
						System.out.println("Column3-mergeCell Total= " + mcell5++);
					mcell5 = 0;
				}
			}
			data.setData(cels);

			return data;
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}
		return null;
	}

	public static Data readXlsx5Client(CommonsMultipartFile inputFile) {
		try {

			// Get the workbook instance for XLSX file
			// XSSFWorkbook wb = new XSSFWorkbook(new
			// FileInputStream(inputFile));

			ByteArrayInputStream bis = new ByteArrayInputStream(inputFile.getBytes());
			XSSFWorkbook wb = new XSSFWorkbook(bis);
			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			Row row;
			Cell cell;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			Data data = new Data();
			List<CellM> cels = new ArrayList<CellM>();
			while (rowIterator.hasNext()) {

				row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				/*
				 * while (cellIterator.hasNext()) { cell = cellIterator.next();
				 * cellType(cell); }
				 */
				CellM cm = new CellM();
				List<String> co1 = new ArrayList<String>();
				List<String> co2 = new ArrayList<String>();
				List<String> co3 = new ArrayList<String>();
				List<String> co4 = new ArrayList<String>();
				/*
				 * List<String> co6 = new ArrayList<String>(); List<String> co7
				 * = new ArrayList<String>();
				 */

				cell = row.getCell(0);
				cellType1(cell, co1);

				cell = row.getCell(1);
				cellType1(cell, co2);

				cell = row.getCell(2);
				cellType1(cell, co3);

				cell = row.getCell(3);
				cellType1(cell, co4);

				cm.setcOne(co1);
				cm.setcTwo(co2);
				;
				cm.setcThree(co3);
				cm.setcFour(co4);
				cels.add(cm);

			}
			data.setData(cels);

			return data;
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}

		return null;
	}

	public static Data readXlsxComment(CommonsMultipartFile inputFile) {
		try {

			// Get the workbook instance for XLSX file
			// XSSFWorkbook wb = new XSSFWorkbook(new
			// FileInputStream(inputFile));

			ByteArrayInputStream bis = new ByteArrayInputStream(inputFile.getBytes());
			XSSFWorkbook wb = new XSSFWorkbook(bis);
			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			Row row;
			Cell cell;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			Data data = new Data();
			List<CellM> cels = new ArrayList<CellM>();
			while (rowIterator.hasNext()) {

				row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				/*
				 * while (cellIterator.hasNext()) { cell = cellIterator.next();
				 * cellType(cell); }
				 */
				CellM cm = new CellM();
				List<String> co1 = new ArrayList<String>();
				List<String> co2 = new ArrayList<String>();
				List<String> co3 = new ArrayList<String>();
				List<String> co4 = new ArrayList<String>();
				List<String> co5 = new ArrayList<String>();
				/*
				 * List<String> co6 = new ArrayList<String>(); List<String> co7
				 * = new ArrayList<String>();
				 */

				cell = row.getCell(0);
				cellType1(cell, co1);

				cell = row.getCell(1);
				cellType1(cell, co2);

				cell = row.getCell(2);
				cellType1(cell, co3);

				cell = row.getCell(3);
				cellType1(cell, co4);

				cell = row.getCell(4);
				cellType1(cell, co5);

				cm.setcOne(co1);
				cm.setcTwo(co2);
				;
				cm.setcThree(co3);
				cm.setcFour(co4);
				cm.setcFour(co5);
				cels.add(cm);

			}
			data.setData(cels);

			return data;
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}

		return null;
	}

	public static Data readXlsxArcheType(CommonsMultipartFile inputFile) {
		try {
			// Get the workbook instance for XLSX file
			// XSSFWorkbook wb = new XSSFWorkbook(new
			// FileInputStream(inputFile));

			ByteArrayInputStream bis = new ByteArrayInputStream(inputFile.getBytes());
			XSSFWorkbook wb = new XSSFWorkbook(bis);
			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			Row row;
			Cell cell;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			Data data = new Data();
			List<CellM> cels = new ArrayList<CellM>();
			while (rowIterator.hasNext()) {

				row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				/*
				 * while (cellIterator.hasNext()) { cell = cellIterator.next();
				 * cellType(cell); }
				 */
				CellM cm = new CellM();
				List<String> co1 = new ArrayList<String>();
				List<String> co2 = new ArrayList<String>();
				List<String> co3 = new ArrayList<String>();
				List<String> co4 = new ArrayList<String>();
				List<String> co5 = new ArrayList<String>();
				/*
				 * List<String> co6 = new ArrayList<String>(); List<String> co7
				 * = new ArrayList<String>();
				 */

				cell = row.getCell(0);
				cellType1(cell, co1);

				cell = row.getCell(1);
				cellType1(cell, co2);

				cell = row.getCell(2);
				cellType1(cell, co3);

				cell = row.getCell(3);
				cellType1(cell, co4);

				cell = row.getCell(4);
				cellType1(cell, co5);

				/*
				 * cell = row.getCell(5); cellType(cell, co6);
				 * 
				 * cell = row.getCell(6); cellType(cell, co7);
				 */

				cm.setcOne(co1);
				cm.setcTwo(co2);
				;
				cm.setcThree(co3);
				cm.setcFour(co4);
				cm.setcFive(co5);
				/*
				 * cm.setcSix(co6); cm.setcSeven(co7);
				 */
				cels.add(cm);

			}
			data.setData(cels);

			return data;
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}
		return null;
	}

	public static int cellType(Cell cell, List<String> co1) {

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			System.out.println(cell.getBooleanCellValue());
			return 1;

		case Cell.CELL_TYPE_NUMERIC:
			System.out.println(cell.getNumericCellValue());
			co1.add(String.valueOf(cell.getNumericCellValue()));
			return 1;

		case Cell.CELL_TYPE_STRING:
			System.out.println(cell.getStringCellValue());
			co1.add(cell.getStringCellValue());
			return 1;

		case Cell.CELL_TYPE_BLANK:
			System.out.println("====================");
			return 0;

		default:
			System.out.println(cell);
			return -1;
		}
	}

	public static int cellType1(Cell cell, List<String> co1) {

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			System.out.println(cell.getBooleanCellValue());
			return 1;

		case Cell.CELL_TYPE_NUMERIC:
			System.out.println(cell.getNumericCellValue());
			co1.add(String.valueOf(cell.getNumericCellValue()));
			return 1;

		case Cell.CELL_TYPE_STRING:
			System.out.println(cell.getStringCellValue());
			co1.add(cell.getStringCellValue());
			return 1;

		case Cell.CELL_TYPE_BLANK:
			System.out.println("====================");
			co1.add(" ");
			return 0;

		default:
			System.out.println(cell);
			return -1;
		}
	}

	public static int cellTypeData(Cell cell) {

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			System.out.println(cell.getBooleanCellValue());
			return 1;

		case Cell.CELL_TYPE_NUMERIC:
			System.out.println(cell.getNumericCellValue());
			return 1;

		case Cell.CELL_TYPE_STRING:
			System.out.println(cell.getStringCellValue());
			return 1;

		case Cell.CELL_TYPE_BLANK:
			System.out.println("====================");
			return 0;

		default:
			System.out.println(cell);
			return -1;
		}
	}

	static void readXl(File inputFile) {

		try {

			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				outer: while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// will iterate over the Merged cells
					for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
						CellRangeAddress region = sheet.getMergedRegion(i); // Region
																			// of
																			// merged
																			// cells

						int colIndex = region.getFirstColumn(); // number of
																// columns
																// merged
						int rowNum = region.getFirstRow(); // number of rows
															// merged
						// check first cell of the region
						if (rowNum == cell.getRowIndex() && colIndex == cell.getColumnIndex()) {
							System.out.println(sheet.getRow(rowNum).getCell(colIndex)
									.getStringCellValue());
							continue outer;
						}
					}
					// the data in merge cells is always present on the first
					// cell. All other cells(in merged region) are considered
					// blank
					if (cell.getCellType() == Cell.CELL_TYPE_BLANK || cell == null) {
						continue;
					}
					System.out.println(cell.getStringCellValue());
				}
			}

		} catch (Exception ee) {

		}

	}

	static void readXls(File inputFile) {
		try {
			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			Cell cell;
			Row row;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					cell = cellIterator.next();

					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_BOOLEAN:
						System.out.println(cell.getBooleanCellValue());
						break;

					case Cell.CELL_TYPE_NUMERIC:
						System.out.println(cell.getNumericCellValue());
						break;

					case Cell.CELL_TYPE_STRING:
						System.out.println(cell.getStringCellValue());
						break;

					case Cell.CELL_TYPE_BLANK:
						System.out.println(" ");
						break;

					default:
						System.out.println(cell);
					}
				}
			}

		}

		catch (FileNotFoundException e) {
			System.err.println("Exception" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception" + e.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {

		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("test.xlsx");

		final Workbook workbook = new XSSFWorkbook(inputStream);

		readXlsx(workbook);

	}

	public static String getKey(String value_you_look_for, Map<String, String> map) {
		Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			if (entry.getValue().equals(value_you_look_for)) {
				return entry.getKey();
			}
		}
		return null;
	}
}

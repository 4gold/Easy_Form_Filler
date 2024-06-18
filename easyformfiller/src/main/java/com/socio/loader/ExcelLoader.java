package com.socio.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelLoader {
		
	   private static final Logger logger = LogManager.getLogger(ExcelLoader.class.getName());
	   
	   private Workbook excelWorkbook;
	   
	   private Sheet currentSheet;
	   
	   private String excelFilePath;
	   	   
	   public ExcelLoader(String excelFilePath) {
		   this.excelFilePath = excelFilePath;
		   loadFile();
	   }
	   
	   // convert column letter to index (A -> 0)
	   public static Integer convertColString2Index(String colString) {
		   return CellReference.convertColStringToIndex(colString);
	   }
	   
	   // convert column index to letter (0 -> A)
	   public static String convertIndex2ColString(Integer index) {
		   return CellReference.convertNumToColString(index);
	   }
	
	   // load to workbook and sheet. sheet default is 0;
	   private Workbook loadFile() {
	    	FileInputStream fis;  	
			try {
				fis = new FileInputStream(excelFilePath);
				excelWorkbook = new XSSFWorkbook(fis);
				currentSheet = excelWorkbook.getSheetAt(0);
			} catch (IOException e) {
				logger.error("Fail to load file : " + excelFilePath);
			}
			logger.info(excelFilePath + " loaded.");
			
			return excelWorkbook;
	   }
	   
	   public Sheet changeSheet(Integer sheetIndex) {
		   currentSheet = excelWorkbook.getSheetAt(sheetIndex);
		   return currentSheet;
	   }
	   
	   public Sheet changeSheet(String sheetName) {
		   currentSheet = excelWorkbook.getSheet(sheetName);
		   return currentSheet;
	   }
	   
	   public Cell setCellData(String data, Integer row, Integer column) {
		   Cell cell = getCell(row, column);
		   cell.setCellValue(data);
		   return cell;
	   }
	   
	   public Cell getCell(Integer row, Integer column) {
		   return currentSheet.getRow(row).getCell(column);
	   }
	   
	   public void outputExcelFile(String outputFilePath) throws IOException {
			try {
				Files.copy(Paths.get(excelFilePath), Paths.get(outputFilePath), StandardCopyOption.REPLACE_EXISTING);
				FileOutputStream outputFile = new FileOutputStream(new File(outputFilePath));
				excelWorkbook.write(outputFile);
			} catch (FileNotFoundException e) {
				logger.error(String.format("File [ %s ] does not exist", excelFilePath));
			} catch (IOException ioe) {
				logger.error(String.format("Data flush fail. extract Path : [ %s ] ", outputFilePath));
				throw ioe;
			}
			logger.info(String.format("File output success on path [ %s ] ", outputFilePath));
	   }
	   
	   

}

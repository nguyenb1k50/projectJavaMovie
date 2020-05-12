package com.myclass.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.myclass.dto.excelDTO;

public class ExcelReportView extends AbstractXlsView {

	private String sheetName;
	private String fileName;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+".xls\"");
		List<String[]> report1 = (List<String[]>) model.get("report1");
		List<String[]> report2 = (List<String[]>) model.get("report2");
		Sheet sheet = workbook.createSheet(sheetName);
		//report1
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		Row report1Name = sheet.createRow(0);
		report1Name.createCell(0).setCellValue("Movie with most seat sold");		
		Row header = sheet.createRow(1);
		header.createCell(0).setCellValue("Movie title");
		header.createCell(1).setCellValue("Total seat sold");
		int rowNum = 2;
		 Iterator<String[]> report1List = report1.iterator();
	        while (report1List.hasNext()) {
	        	Row row = sheet.createRow(rowNum++);
	            System.out.println(report1List.next());
	            ArrayList<String> item =  new ArrayList<>(Arrays.asList(report1List.next()));
	            row.createCell(0).setCellValue(item.get(0));
				row.createCell(1).setCellValue(item.get(1));
	        }	        
		rowNum++;
		//report2
		sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,0,3));
		Row report2Name = sheet.createRow(rowNum);
		report2Name.createCell(0).setCellValue("Most view time frame");	
		rowNum++;
		Row header2 = sheet.createRow(rowNum);
		header2.createCell(0).setCellValue("Time frame");
		header2.createCell(1).setCellValue("Number of seats purchased");
		rowNum++;
		Iterator<String[]> report2List = report2.iterator();
        while (report2List.hasNext()) {
        	Row row = sheet.createRow(rowNum++);
           // System.out.println(report2List.next());
            ArrayList<String> item =  new ArrayList<>(Arrays.asList(report2List.next()));
            row.createCell(0).setCellValue(item.get(0));
			row.createCell(1).setCellValue(item.get(1));
        }	
	}

	public ExcelReportView(String sheetName, String fileName) {
		super();
		this.sheetName = sheetName;
		this.fileName = fileName;
	}
	
	
}

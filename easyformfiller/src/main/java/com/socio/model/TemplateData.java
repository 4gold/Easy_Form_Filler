package com.socio.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 對應 template.json
 */
public class TemplateData {
	
	// template file name, like : kingstone
	private String templateName;
	
	// template file type, like : .xlsx or .docx
	private String templateFileType;
	
	// write data on which sheet, default 0
	private Integer sheetIndx = 0;
	
	// data name on which row, default 0
	private Integer dataNameRowIndx = 0;
	
	// output data
	private List<TemplateCellData> templateCellDatas;
	
	public TemplateData(String templateName, String templateFileType) {
		this.templateName = templateName;
		this.templateFileType = templateFileType;
		templateCellDatas = new ArrayList<>();
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateFileType() {
		return templateFileType;
	}

	public void setTemplateFileType(String templateFileType) {
		this.templateFileType = templateFileType;
	}

	public Integer getSheetIndx() {
		return sheetIndx;
	}

	public void setSheetIndx(Integer sheetNum) {
		this.sheetIndx = sheetNum;
	}

	public Integer getDataNameRowIndx() {
		return dataNameRowIndx;
	}

	public void setDataNameRowIndx(Integer dataNameRowIndx) {
		this.dataNameRowIndx = dataNameRowIndx;
	}

	public List<TemplateCellData> getTemplateCellDatas() {
		return templateCellDatas;
	}

	public void setTemplateCellDatas(List<TemplateCellData> templateCellDatas) {
		this.templateCellDatas = templateCellDatas;
	}
	
	
	
}

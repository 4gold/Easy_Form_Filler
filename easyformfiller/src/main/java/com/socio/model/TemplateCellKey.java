package com.socio.model;

import java.util.Objects;

import com.socio.loader.ExcelLoader;

public class TemplateCellKey {
	// 輸出的行數
	private Integer targetRow;
	// 輸出的列數
	private Integer targetColumn;
	
	public TemplateCellKey(Integer targetRaw, Integer targetColumn) {
		this.targetRow = targetRaw;
		this.targetColumn = targetColumn;
	}
	
	public TemplateCellKey(TemplateCellKey key) {
		this.targetRow = key.getTargetRow();
		this.targetColumn = key.getTargetColumn();
	}
	
	public Integer getTargetRow() {
		return targetRow;
	}
	
	public void setTargetRow(Integer targetRaw) {
		this.targetRow = targetRaw;
	}
	
	public Integer getTargetColumn() {
		return targetColumn;
	}
	
	public String getTargetColumnString() {
		return ExcelLoader.convertIndex2ColString(targetColumn);
	}
	
	public void setTargetColumn(Integer targetColumn) {
		this.targetColumn = targetColumn;
	}
	
	public void setTargetColumn(String targetColString) {
		this.targetColumn = ExcelLoader.convertColString2Index(targetColString);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(targetColumn, targetRow);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemplateCellKey other = (TemplateCellKey) obj;
		return Objects.equals(targetColumn, other.targetColumn) && Objects.equals(targetRow, other.targetRow);
	}

	@Override
	public String toString() {
		return "TemplateCellKey [targetRaw=" + targetRow + ", targetColumn=" + targetColumn + "]";
	}
	
	
	
	
}

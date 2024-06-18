package com.socio.model;

import java.util.Objects;

public class SourceCellData extends CellData {

	// fixed data = 不會隨時被刷新，平時不需要重填的資料，如公司名稱。
	private Boolean isFixedData;
	
	public SourceCellData(String dataName, String data) {
		this.setDataName(dataName);
		this.setData(data);
	}
	
	public SourceCellData(String dataName, String data, boolean isFixedData){
		this.setDataName(dataName);
		this.setData(data);
		this.isFixedData = isFixedData;
	}
	

	public boolean isFixedData() {
		return isFixedData;
	}

	public void setFixedData(boolean isFixedDate) {
		this.isFixedData = isFixedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(isFixedData);
		return result;
	}

	@Override
	public String toString() {
		return "BasicColumnInfo [isFixedDate=" + isFixedData + super.toString() + "]";
	}

	
	
	
	
}

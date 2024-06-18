package com.socio.model;

import java.util.Objects;

public class CellData {
	// 資料名稱
	private String dataName;
	// 資料內容
	private String data;
	
	
	public String getData() {
		return data.toString();
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getDataName() {
		return dataName.toString();
	}
	
	public void setDataName(String columnName) {
		this.dataName = columnName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dataName, data);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellData other = (CellData) obj;
		return Objects.equals(dataName, other.dataName);
	}
	@Override
	public String toString() {
		return ", dataName=" + dataName + ", data=" + data;
	}
	
	
	
}

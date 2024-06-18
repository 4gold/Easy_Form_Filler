package com.socio.model;

import java.util.Objects;

/**
 * 對 templateData 來說，data 是在從sourceData載入的，故 initialize 時不會設定。
 */
public class TemplateCellData extends CellData {
	
	/*
	 *  對應的 source data 名稱
	 */
	private String sourceDataName;
	
	/*
	 *  輸出的位置
	 */
	private TemplateCellKey key;
	
	/*
	 * 輸出資料格格式
	 */
	private CellDataFormat cellFormat = CellDataFormat.SOURCE_DATA;

	public TemplateCellData( String sourceDataName, TemplateCellKey key) {
		this.sourceDataName = sourceDataName;
		this.key = new TemplateCellKey(key);
	}

	public String getSourceDataName() {
		return sourceDataName;
	}

	public void setSourceDataName(String sourceDataName) {
		this.sourceDataName = sourceDataName;
	}

	public CellDataFormat getCellFormat() {
		return cellFormat;
	}

	public void setCellFormat(CellDataFormat cellFormat) {
		this.cellFormat = cellFormat;
	}

	public TemplateCellKey getKey() {
		return key;
	}

	public void setKey(TemplateCellKey key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(sourceDataName, key);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TemplateCellData other = (TemplateCellData) obj;
		return  Objects.equals(key, other.key);
	}

	@Override
	public String toString() {
		return "TemplateCellData [ " + key.toString() + "]";
	}
	
}




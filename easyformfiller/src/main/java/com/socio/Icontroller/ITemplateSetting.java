package com.socio.Icontroller;

import com.socio.model.TemplateCellKey;

/**
 * 針對 Template json 檔案的操作行為
 * 必須在 SourceSetting 後初始化。
 * 所有的變更在呼叫 writeJson() 之前都只作用於Cache
 */
public interface ITemplateSetting {

	// set template data name on which row
	void setTemplateDataNameRowIndex(Integer indx);

	// load template's json setting file
	void loadTemplateInfo();

	boolean isInfoExist(TemplateCellKey key);

	boolean isInfoExist(Integer row, Integer col);

	boolean isInfoExist(Integer row, String colString);

	boolean isFileExist(String filePath);

	/**
	 * 
	 * @param sourceDataName
	 * @param targetRow : least number = 0/A
	 * @param targetColString : least number = 1
	 */
	void addTemplateCellData(String sourceDataName, Integer targetRow, String targetColString);

	void addTemplateCellData(String sourceDataName, Integer targetRow, Integer targetColumn);

	void updateTemplateCellData(String sourceDataName, Integer targetRow, Integer targetColumn);

	// get all data related to Source data to template.json
	void loadSourceData2Cache();

	/**
	 * clean data in the template.json (data name will be remain)
	 * @param cleanAllData : true=clean all data, false=clean only cells from source data
	 */
	void cleanSourceDataInCache(boolean cleanAllData);

	/**
	 * will load current template.json file to cache. unsaved cache setting will be dismiss
	 */
	void writeExcelFile(String outputFilePath);

	/**
	 * write cache data to template.json file.
	 */
	void writeJson();

}
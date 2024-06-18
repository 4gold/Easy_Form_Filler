package com.socio.Icontroller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.socio.model.SourceCellData;

/**
 * 針對 sourceInfo.json 的操作行為
 */
public interface ISourceSetting {

	/**
	 * get all data name
	 * @return
	 */
	List<String> getAllSourceDataName();
	
	/**
	 * get all data
	 * @return all data
	 */
	public Map<String, SourceCellData> getAllSourceData();

	// get basic infos
	void loadSourceInfo();
	
	Map<String, SourceCellData> loadAndReturnSourceInfo();

	// write info into basicInfo.json
	void addSourceInfo(String dataName, String data);

	// write info into sourceInfo.json
	void addSourceInfo(String dataName, String data, boolean isFixedData);

	// update. if exist, overwrite info
	void updateSourceInfo(String dataName, String data, boolean isFixedData);

	boolean isInfoExist(String dataName);

	void removeSourceInfo(String dataName);

	// clean all data, remain the column name
	void cleanAllSourceInfo();

	// clean data, remain the column name
	void cleanSourceInfo(String dataName);

	void writeJson() throws IOException;

}
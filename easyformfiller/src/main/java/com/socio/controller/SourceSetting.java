package com.socio.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.socio.Icontroller.ISourceSetting;
import com.socio.model.SourceCellData;


/**
 * 針對 sourceInfo.json 的操作行為
 */
public class SourceSetting extends Setting<List<SourceCellData>> implements ISourceSetting{
	
	private static final Logger logger = LogManager.getLogger(SourceSetting.class.getName());

	private static final String BASICINFO_FILE = SETTING_PATH + "sourceInfo.json";	
	
	// columnName, string
	public static Map<String, SourceCellData> sourceInfos;
	
	public SourceSetting(){
		try {
			sourceInfos = new HashMap<>();
			loadSourceInfo(); // will initiate
		} catch (Exception e) {
			logger.error("source setting initialization fail. ");
			e.printStackTrace();
		}
		logger.info("source setting initialized. ");
	}

	/**
	 * get all data name
	 * @return all source data names
	 */
	@Override
	public List<String> getAllSourceDataName() {
		return new ArrayList<>(sourceInfos.keySet());
	}
	
	/**
	 * get all data
	 * @return
	 */
	@Override
	public Map<String, SourceCellData> getAllSourceData() {
		return sourceInfos;
	}
	
	/**
	 *  load source info into cache
	 */
	@Override
	public void loadSourceInfo(){
		sourceInfos.putAll(loadAndReturnSourceInfo());
	}
	
	/**
	 * load and return source info
	 * @return source info
	 */
	@Override
	public Map<String, SourceCellData> loadAndReturnSourceInfo(){
		List<SourceCellData> rowData = new ArrayList<>();
		Map<String, SourceCellData> tempData = new HashMap<>();
		try {
			rowData = jsonLoader.loadJson2Data(List.class, SourceCellData.class, BASICINFO_FILE);
			
			// transform list of data to map
			for (SourceCellData data : rowData) {
				tempData.put(data.getDataName(), data);
			}
		} catch (IOException e) {
			logger.error("Fail to load source Infomations");
			e.printStackTrace();
		}
		logger.info("Source Informations loading complete");
		
		return tempData;
	}
	
	/**
	 *  write info into basicInfo.json
	 */
	@Override
	public void addSourceInfo(String dataName, String data){
		addSourceInfo(dataName, data, true);
	}
	
	/**
	 *  write info into sourceInfo.json
	 */
	@Override
	public void addSourceInfo(String dataName, String data, boolean isFixedData){
		try {
			if (isInfoExist(dataName)) {
				logger.info(String.format("data [ %s ] is already exist", dataName));
				throw new IOException();
			}
			sourceInfos.put(dataName, new SourceCellData(dataName, data, isFixedData));
			writeJson();
		} catch (IOException e) {
			logger.error("cannot add data [ " + dataName + " : " + data + " ]");
			e.printStackTrace();
		}
		logger.info("data [ " + dataName + " : " + data + " ] added");
	}
	
	/**
	 *  update. if exist, overwrite info
	 */
	@Override
	public void updateSourceInfo(String dataName, String data, boolean isFixedData){
		try {
			sourceInfos.put(dataName, new SourceCellData(dataName, data, isFixedData));
			writeJson();
		} catch (IOException e) {
			logger.error("cannot add data [ " + dataName + " : " + data + " ]");
			e.printStackTrace();
		}
		logger.info("data [ " + dataName + " : " + data + " ] added");
	}
	
	
	@Override
	public boolean isInfoExist(String dataName) {
		return sourceInfos.containsKey(dataName);
	}
	
	@Override
	public void removeSourceInfo(String dataName) {

		try {
			if (!isInfoExist(dataName)) {
				logger.info(String.format("data [ %s ] is not exist", dataName));
				throw new IOException();
			}
			writeJson();
		} catch (IOException e) {
			logger.error("cannot remove data [ " + dataName + " ]");
			e.printStackTrace();
		}
		logger.info("data [ " + dataName + " ] removed");
	}
	
	/**
	 *  clean all data, remain the column name
	 */
	@Override
	public void cleanAllSourceInfo() {
		sourceInfos.forEach((key, sourceInfo)->{
			if (!sourceInfo.isFixedData()) {
				sourceInfo.setData(null);
				cleanSourceInfo(sourceInfo.getDataName());	
			}
			try {
				writeJson();
			} catch (IOException e) {
				logger.error(String.format("cannot clean data"));
				e.printStackTrace();
			}
		});	
		logger.info("source data cleared");
	}
	
	/**
	 *  clean data, remain the column name
	 */
	@Override
	public void cleanSourceInfo(String dataName) {
		sourceInfos.get(dataName).setData(null);
		try {
			writeJson();
		} catch (IOException e) {
			logger.error(String.format("cannot clean data [ %s ] ", dataName));
			e.printStackTrace();
		}
		logger.info(String.format("data [ %s ] cleared ", dataName));
	}
	
	@Override
	public void writeJson() throws IOException{
		jsonLoader.writeData2Json(new ArrayList<SourceCellData>(sourceInfos.values()), BASICINFO_FILE);
	}
	
	
}

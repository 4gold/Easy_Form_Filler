package com.socio.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.socio.Icontroller.ITemplateSetting;
import com.socio.loader.ExcelLoader;
import com.socio.model.CellDataFormat;
import com.socio.model.SourceCellData;
import com.socio.model.TemplateCellData;
import com.socio.model.TemplateCellKey;
import com.socio.model.TemplateData;

/**
 * 針對 Template json 檔案的操作行為
 * 必須在 SourceSetting 後初始化。
 * 所有的變更在呼叫 writeJson() 之前都只作用於Cache
 */
public class TemplateSetting extends Setting<TemplateData> implements ITemplateSetting{
	
	private static final Logger logger = LogManager.getLogger(SourceSetting.class.getName());
	
	// dataName, data
	public Map<TemplateCellKey, TemplateCellData> templateInfos;
	
	// template.json -> java data
	private TemplateData templateData;
	
	// setting/json
	private String templateSettingFilePath;
	
	// template/xlsx or docx
	private String templateFilePath;
	
	
	public TemplateSetting(String templateName, String templateFileType){
		templateData = new TemplateData(templateName, templateFileType);
		templateInfos = new HashMap<>();
		templateSettingFilePath = SETTING_PATH + templateData.getTemplateName() + SETTING_FILE_TYPE;
		templateFilePath = TEMPLATE_FILE_PATH + templateData.getTemplateName() + templateData.getTemplateFileType();
				
		loadTemplateInfo();
	}
	
	
	
	/**
	 * add or update template to Template folder
	 * @return templateName
	 * @throws IOException
	 */
	public static String uploadTemplate(String uploadFilePath, String templateName, String templateFileType) {
		Path sourceFilePath = Paths.get(uploadFilePath);
		Path targetPath = Paths.get(TEMPLATE_FILE_PATH + templateName + templateFileType);
		try {
			Files.copy(sourceFilePath, targetPath ,StandardCopyOption.REPLACE_EXISTING);
			if (new File(SETTING_PATH, templateName + ".json").createNewFile()) {
				logger.info(String.format("new Setting [ %s %s ] created"), templateName, SETTING_FILE_TYPE);
			} else {
				logger.info(String.format("Setting [ %s %s ] already exist", templateName, SETTING_FILE_TYPE));
			}
			logger.info(String.format("upload success [ %s %s ] ", templateName, templateFileType));
		} catch (IOException e) {
			logger.error(String.format("upload fail [ %s %s ] ", templateName, templateFileType));
		}
		
		return templateName;
	}
	
	// set template data name on which row
	@Override
	public void setTemplateDataNameRowIndex(Integer indx) {
		templateData.setDataNameRowIndx(indx);
	}
	
	// load template's json setting file
	@Override
	public void loadTemplateInfo() {		
		try {
			// check if template.json exist. if not, return
			if (!isFileExist(templateSettingFilePath)) {
				logger.error(String.format("setting file [ %s ] does not exist", templateSettingFilePath));
				return;
			};
			// load json file data to List
			jsonLoader.loadJson2Data(TemplateData.class, templateSettingFilePath);
			
			// transform list of data to map of objects
			for (TemplateCellData data : templateData.getTemplateCellDatas()) {
				templateInfos.put(new TemplateCellKey(data.getKey()), data);
			}
			logger.info("Source Informations loading complete");
		} catch (IOException e) {
			logger.error("Fail to load source Infomations");
		}
	}
	
	@Override
	public boolean isInfoExist(TemplateCellKey key) {
		return templateInfos.containsKey(key);
	}
	
	@Override
	public boolean isInfoExist(Integer row, Integer col) {
		return isInfoExist(new TemplateCellKey(row, col));
	}
	
	@Override
	public boolean isInfoExist(Integer row, String colString) {
		return isInfoExist(row, ExcelLoader.convertColString2Index(colString));
	}
	
	@Override
	public boolean isFileExist(String filePath) {
		File f = new File(filePath);
		if(f.exists() && !f.isDirectory()) 
			return true;
		return false;
	}
	/**
	 * 
	 * @param sourceDataName
	 * @param targetRow : least number = 0/A
	 * @param targetColString : least number = 1
	 */
	@Override
	public void addTemplateCellData(String sourceDataName, Integer targetRow, String targetColString) {
		addTemplateCellData(sourceDataName, targetRow, ExcelLoader.convertColString2Index(targetColString));
	}
	
	@Override
	public void addTemplateCellData(String sourceDataName, Integer targetRow, Integer targetColumn) {
		// target row - 1 to fit the row number on excel sheet
		TemplateCellKey key = new TemplateCellKey(targetRow - 1, targetColumn);
		
		// if sourceData not exist, return
		if (!SourceSetting.sourceInfos.containsKey(sourceDataName)) {
			logger.info(String.format("sourceData [ %s ] does not exist", sourceDataName));
			return;
		}	
		// if exist, use update
		if (isInfoExist(key)) {
			updateTemplateCellData(sourceDataName, targetRow, targetColumn);
			return;
		};
		templateInfos.put(key, new TemplateCellData(sourceDataName, key));	
		logger.info(String.format("data on cell [ %s ] added to 'add' list", key));
	}
	
	@Override
	public void updateTemplateCellData(String sourceDataName, Integer targetRow, Integer targetColumn) {
		TemplateCellKey key = new TemplateCellKey(targetRow, targetColumn);
		templateInfos.put(key, new TemplateCellData(sourceDataName, key));
		//writeJson();
		logger.info(String.format("data on cell [ %s ] added to 'update' list ", key));
	}
	
	// get all data related to Source data to template.json
	@Override
	public void loadSourceData2Cache() {
		templateInfos.forEach((key, templateInfo)->{
			String sourceDataName = templateInfo.getSourceDataName();
			SourceCellData sourceData = SourceSetting.sourceInfos.get(sourceDataName);
			if (sourceData == null) {
				logger.error(String.format("sourceData [ %s ] does not exist.", sourceDataName));
				return;
			}
			// if data is from source, add data
			if (templateInfo.getCellFormat() == CellDataFormat.SOURCE_DATA) {
				templateInfo.setData(sourceData.getData());
				templateInfo.setDataName(sourceData.getDataName());
			}
		});	
		logger.info(String.format("template [ %s ]'s source data loaded. flush to confirm changes. ", templateData.getTemplateName()));
	}
	
	/**
	 * clean data in the template.json (data name will be remain)
	 * @param cleanAllData : true=clean all data, false=clean only cells from source data
	 */
	@Override
	public void cleanSourceDataInCache(boolean cleanAllData) {
		templateInfos.forEach((key, templateInfo)-> {
			
			if (cleanAllData || templateInfo.getCellFormat() == CellDataFormat.SOURCE_DATA)
				templateInfo.setData(null);
		});
		//writeJson();
		logger.info(String.format("template [ %s ] data cleared. flush to confirm changes. ", templateData.getTemplateName()));
	}
	/**
	 * will load current template.json file to cache. unsaved cache setting will be dismiss
	 */
	@Override
	public void writeExcelFile(String outputFilePath) {
		
		loadTemplateInfo();
		
		ExcelLoader excelLoader = new ExcelLoader(templateFilePath);
		
		try {			
			excelLoader.changeSheet(templateData.getSheetIndx());
			for (TemplateCellData data : templateInfos.values()) {
				excelLoader.setCellData(data.getData(), data.getKey().getTargetRow(), data.getKey().getTargetColumn());
			}
			excelLoader.outputExcelFile(outputFilePath+templateData.getTemplateName()+templateData.getTemplateFileType());
			logger.info(String.format(" [ %s %s] File ouput complete", templateData.getTemplateName(), templateData.getTemplateFileType()));
		} catch(IOException e) {
			logger.error(String.format("Cannot output [ %s %s ] excel file.", templateData.getTemplateName(), templateData.getTemplateFileType()));
		}
		
	}
	/**
	 * write cache data to template.json file.
	 */
	@Override
	public void writeJson() {
		try {
			templateData.setTemplateCellDatas(new ArrayList<>(templateInfos.values()));
			jsonLoader.writeData2Json(templateData, templateSettingFilePath);
			logger.info(String.format(" [ %s ] Setting saved", templateData.getTemplateName()));
		} catch(IOException e) {
			logger.error(String.format("Cannot save [ %s ] setting file.", templateData.getTemplateName()));
		}
	}

}

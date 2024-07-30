package com.socio.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.socio.Icontroller.ISetting;
import com.socio.loader.JsonLoader;

/**
 * Setting classes refer to the 'Setting/' folder. 
 * Include two types of file:
 * 1. sourceInfo.json (source date setting)
 * 2. *.json (template setting)
 * 
 * "sourceInfo.json" is all the source data of all template.
 * All the template will get data from source data.
 * 
 * "*.json" files refer to files in 'Template/' folder.
 * These type of files decide how template connect to source data.
 * "*.json" files will have the same name with template.
 * For example, if we upload a file calls "kingstone.xlsx" in 'Template/'
 * , it will create a "kingstone.json" file in 'Setting/'.
 * 
 * 
 * @param <T> any acceptable type for json
 */
public class Setting<T> implements ISetting<T>{
		protected final static String TEMPLATE_FILE_PATH = "Template/";
		protected final static String SETTING_PATH = "Setting/";
		protected final static String SETTING_FILE_TYPE = ".json";
		
		protected JsonLoader<T> jsonLoader = new JsonLoader<>();
		
		
		/**
		 * get all template setting files in Setting folder.
		 * @return template names
		 */
		@Override
		public List<String> getAllTemplateSettingFileNames() {		
			return getSettingFiles(true);
		}
		
		/**
		 * get all setting files in Setting folder. (Include sourceInfo setting.)
		 * @return *.json file names
		 */
		@Override
		public List<String> getAllSettingFileNames() {		
			return getSettingFiles(false);
		}
		
		
		/**
		 * get file names in Setting folder
		 * @param isTemplate
		 * @return false = all file names in Setting folder, true =  template file names in Setting folder
		 */
		private List<String> getSettingFiles(boolean isTemplate){
			List<String> fileNames = new ArrayList<>();
			
			File folder = new File(SETTING_PATH);
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(SETTING_FILE_TYPE)) {
						if (isTemplate && listOfFiles[i].getName().equals("sourceInfo.json"))
							continue;
						String name = listOfFiles[i].getName();
						// remove ".json"
						fileNames.add(name.substring(0, name.length() - 5));
					} 
				}
			}
			return fileNames;
		}
		
		
		
}

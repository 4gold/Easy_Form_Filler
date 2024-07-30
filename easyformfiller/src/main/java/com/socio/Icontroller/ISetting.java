package com.socio.Icontroller;

import java.util.List;

public interface ISetting<T> {
	
	/**
	 * get all template setting files in Setting folder.
	 * @return template file names
	 */
	public List<String> getAllTemplateSettingFileNames();

	/**
	 * get all setting files in Setting folder. (Include sourceInfo setting.)
	 * @return *.json file names
	 */
	List<String> getAllSettingFileNames();
}
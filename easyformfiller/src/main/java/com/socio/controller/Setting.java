package com.socio.controller;

import com.socio.Icontroller.ISetting;
import com.socio.loader.JsonLoader;

/**
 * Info classes are those who set files
 * @param <T> any acceptable type for json
 */
public class Setting<T> implements ISetting<T>{
		protected final static String TEMPLATE_FILE_PATH = "Template/";
		protected final static String SETTING_PATH = "Setting/";
		protected final static String SETTING_FILE_TYPE = ".json";
		
		protected JsonLoader<T> jsonLoader = new JsonLoader<>();
		
		
}

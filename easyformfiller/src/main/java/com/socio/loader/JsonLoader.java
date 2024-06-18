package com.socio.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JsonLoader<T> {
			
			// use this method if the class is multi-layered, such as : Map<MyObject> or List<MyObject>
			public T loadJson2Data(Class<?> containerClazz, Class<?> objClazz, String filePath) throws IOException {
				Type type = TypeToken.getParameterized(containerClazz, objClazz).getType();
				return loadJson2Data(type, filePath);
			}
			
			// use this method if the class is single object, such as : MyObject
			public T loadJson2Data(Class<?> objClazz, String filePath) throws IOException {
				Type type = TypeToken.get(objClazz).getType();
				return loadJson2Data(type, filePath);
			}
			
			// load data from json file and put into the data
			public T loadJson2Data(Type type, String filePath) throws IOException{
				Gson gson = new Gson();
				JsonReader reader = new JsonReader(
						new InputStreamReader(new FileInputStream(filePath), "UTF-8")
						);
				return gson.fromJson(reader, type);
			}
			
			// load specific data field into data
			public T loadJson2Data(Type type, String filePath, String fieldName) throws IOException {
				Gson gson = new Gson();
				FileReader reader = new FileReader(new File(filePath));		
				JsonObject jo = gson.fromJson(reader, JsonObject.class);			
				return gson.fromJson(jo.getAsJsonObject(fieldName).toString(), type);
			}
			
			
			
			// write data into basicInfo.json
			public void writeData2Json(T data, String filePath) throws IOException{
				Gson gson = new Gson();
				File jsonFile = new File(filePath);
				OutputStream outputStream = new FileOutputStream(jsonFile);
				outputStream.write(gson.toJson(data).getBytes());
				outputStream.flush(); 
				outputStream.close();
			}
}

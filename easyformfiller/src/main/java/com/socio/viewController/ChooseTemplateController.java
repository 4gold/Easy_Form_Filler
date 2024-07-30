package com.socio.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.socio.controller.SourceSetting;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class ChooseTemplateController implements Initializable {
	
	private static final Logger logger = LogManager.getLogger(ChooseTemplateController.class.getName());

	
	public ChooseTemplateController() {
		
	}	
	
	@FXML
	public GridPane mainPane;
	
	private SourceSetting setting  = new SourceSetting();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources){ 
		setting.getAllTemplateSettingFileNames();
	}
	



}

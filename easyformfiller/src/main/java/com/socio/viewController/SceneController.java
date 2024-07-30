package com.socio.viewController;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class SceneController {
	
	private final String FXML_URL = "/com/socio/fxml/";
	
	private final String SOURCE_SETTING_SCENE = "source_data.fxml";
	
	private final String SOURCE_SETTING_ADD_SCENE = "source_data_add.fxml";
	
	private final String TEMPLATE_UPLOAD_SCENE = "template_upload.fxml";
	
	private final String TEMPLATE_SETTING_SCENE = "template_setting.fxml";
	
	private final String CHOOSE_TEMPLATE_SCENE = "choose_template.fxml";
	
	private final String OUTPUT_DATA = "output_data.fxml";
	
	
	private Stage stage;
	private Scene scene;	
	
	@FXML
	public void switchToSceneSourceSetting(ActionEvent event) throws IOException {
		switchToScene(event, FXML_URL + SOURCE_SETTING_SCENE);
	}
	
	@FXML
	public void switchToSceneSourceSettingAddPage(ActionEvent event) throws IOException {
		switchToNewStageScene(event, FXML_URL + SOURCE_SETTING_ADD_SCENE);
	}
	
	@FXML
	public void switchToSceneTemplateUpload(ActionEvent event) throws IOException {
		switchToScene(event, FXML_URL + TEMPLATE_UPLOAD_SCENE);
	}
	
	@FXML
	public void switchToSceneTemplateSetting(ActionEvent event) throws IOException {
		switchToScene(event, FXML_URL + TEMPLATE_SETTING_SCENE);
	}
	
	@FXML
	public void switchToSceneChooseTemplate(ActionEvent event) throws IOException {
		switchToScene(event, FXML_URL + CHOOSE_TEMPLATE_SCENE);
	}
	
	@FXML
	public void switchToSceneOutputData(ActionEvent event) throws IOException {
		switchToScene(event, FXML_URL + OUTPUT_DATA);
	}
	
	
	private void switchToScene(ActionEvent event, String sceneUrl) throws IOException {
		switchToScene(event, sceneUrl, false);
	}
	
	private void switchToNewStageScene(ActionEvent event, String sceneUrl) throws IOException {
		switchToScene(event, sceneUrl, true);
	}
	
	private void switchToScene(ActionEvent event, String sceneUrl, boolean isNewWindow) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(sceneUrl));
		stage = isNewWindow ? new Stage() : (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}

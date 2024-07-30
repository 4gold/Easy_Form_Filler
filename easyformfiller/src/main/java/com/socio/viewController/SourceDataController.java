package com.socio.viewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.socio.Icontroller.ISourceSetting;
import com.socio.controller.SourceSetting;
import com.socio.model.SourceCellData;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SourceDataController implements Initializable {
	
	private static final Logger logger = LogManager.getLogger(SourceDataController.class.getName());

	
	public SourceDataController() {
		
	}	
	
	private ISourceSetting sourceSetting  = new SourceSetting();
	
	@FXML
	private TableView<SourceCellData> sourcetable;
	
	@FXML
	private TableColumn<SourceCellData, String> dataName;
	
	@FXML
	private TableColumn<SourceCellData, String> data;
	
	@FXML
	private TableColumn<SourceCellData, Boolean> isFixed;
	
	private ObservableList<SourceCellData> sourceDatas = FXCollections.observableArrayList(
			sourceSetting.loadAndReturnSourceInfo().values()
			);
	
	@Override
	public void initialize(URL location, ResourceBundle resources){ 
		 sourceDatas.addListener((ListChangeListener.Change<? extends SourceCellData> change) -> {
	            while (change.next()) {
	                if (change.wasAdded()) {
	                    System.out.println("Item added: " + change.getAddedSubList());
	                }
	                if (change.wasRemoved()) {
	                    System.out.println("Item removed: " + change.getRemoved());
	                }
	            }
         });
		 dataName.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getDataName()));
		 data.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getData()));
		 isFixed.setCellValueFactory(f -> new SimpleBooleanProperty(f.getValue().isFixedData()));
		 sourcetable.setItems(sourceDatas);
	}
	
	@FXML
	public void switchToAddWindow(ActionEvent event) {
		try {
			SceneController sc = new SceneController();		
			sc.switchToSceneSourceSettingAddPage(event);
		} catch (IOException e) {
			logger.error("page_switching failed");
			e.printStackTrace();
		}
	}
	
	



}

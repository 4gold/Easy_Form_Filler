package com.socio.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import com.socio.Icontroller.ISourceSetting;
import com.socio.controller.SourceSetting;
import com.socio.model.SourceCellData;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SourceDataController implements Initializable {
	
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
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources){ 
		 dataName.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getDataName()));
		 data.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getData()));
		 isFixed.setCellValueFactory(f -> new SimpleBooleanProperty(f.getValue().isFixedData()));
		 sourcetable.setItems(sourceDatas);
	}
	private ObservableList<SourceCellData> sourceDatas = FXCollections.observableArrayList(
			sourceSetting.loadAndReturnSourceInfo().values()
			);
	
	



}

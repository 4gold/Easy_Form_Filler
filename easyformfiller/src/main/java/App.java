import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	// configuration: https://openjfx.io/openjfx-docs/#maven
    @Override
    public void start(Stage stage) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("com/socio/fxml/source_data.fxml"));
        
        Scene scene = new Scene(root);
    
        stage.setTitle("開心報品");
        stage.setScene(scene);
        stage.show();
        
        
    }

    public static void main(String[] args) {
        launch();
    }

}
package hw4JavaFX.task1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main41 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Select user name, input message and select push");
        primaryStage.setScene(new Scene(root, 500, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

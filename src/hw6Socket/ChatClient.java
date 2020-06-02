package hw6Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Platform.*;

public class ChatClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MyChat");
        primaryStage.setScene(new Scene(root, 300, 275));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

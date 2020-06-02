package hw6Socket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    TextField textField;
    @FXML
    TextArea textArea;

    Socket socket;
    DataInputStream input;
    DataOutputStream output;
    final String IP = "127.0.0.1";
    final int PORT = 8189;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket = new Socket(IP, PORT);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {

                try {
                    while (true) {
                        String str = input.readUTF();
                        textArea.appendText("Server: " + str + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("сервер не отвечает");
                    try {
                        socket.close();
                        input.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pushText(ActionEvent actionEvent) {
        try {
            output.writeUTF("Client: " + textField.getText() + "\n");
            textArea.appendText("Client: " + textField.getText() + "\n");
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

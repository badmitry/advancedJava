package hw7Chat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public HBox msgPanel;
    @FXML
    public ListView listView;

    Stage regStage;

    private boolean authenticated = false;
    private boolean showListUsers = false;
    private boolean showRegistrationWindow = false;
    private String nick;

    NetConnection net = new NetConnection(this);

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        msgPanel.setVisible(authenticated);
        msgPanel.setManaged(authenticated);
        if (!authenticated) {
            nick = "";
        }
        setTitle(nick);
    }

    private void setTitle(String nick) {
        Platform.runLater(() -> {
            ((Stage) textField.getScene().getWindow()).setTitle("MyChat " + nick);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setAuthenticated(false);
        regStage = createRegWindow();
        Platform.runLater(() -> {
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    net.sendMsg("/end");
                }
            });
        });
    }

    public void sendMsg() {
        net.sendMsg(textField.getText());
        textField.clear();
        textField.requestFocus();
    }

    public void tryToAuth(javafx.event.ActionEvent actionEvent) {
        net.tryToAuth(loginField.getText().trim(), passwordField.getText().trim());
        passwordField.clear();
    }

    public void showUserList(ActionEvent actionEvent) {
        if (showListUsers) {
            showListUsers = false;
        } else {
            showListUsers = true;
        }
        listView.setVisible(showListUsers);
        listView.setManaged(showListUsers);
    }

    public void authOk(String nick) {
        textArea.clear();
        this.nick = nick;
        setAuthenticated(true);
    }

    public void notAuth(String str) {
        textArea.clear();
        textArea.appendText(str);
        showListUsers = false;
        listView.setVisible(showListUsers);
        listView.setManaged(showListUsers);
    }

    public void addText(String str) {
        textArea.appendText(str + "\n");
    }

    public void writeClientList(String[] arr) {
        listView.getItems().clear();
        for (int i = 1; i < arr.length; i++) {
            listView.getItems().add(arr[i]);
        }

    }

    public void clickUserFromList(MouseEvent mouseEvent) {
        textField.setText("/w " + listView.getSelectionModel().getSelectedItem() + " ");
    }

    private Stage createRegWindow() {
        Stage stage = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("regSample.fxml"));
            Parent root = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, 300, 150));
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            RegController regController = fxmlLoader.getController();
            regController.controller = this;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }

    public void showRegWindow(ActionEvent actionEvent) {
        if (!showRegistrationWindow) {
            regStage.show();
            showRegistrationWindow = true;
            return;
        }else {
            regStage.hide();
            showRegistrationWindow = false;
            return;
        }

    }

    public void tryRegistration(String login, String password, String nick) {
        net.tryRegistration(login, password, nick);
        showRegWindow(null);
    }
}

package hw4JavaFX.task1;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    RadioButton radioButton1;
    @FXML
    RadioButton radioButton2;
    @FXML
    RadioButton radioButton3;
    @FXML
    Button btn1;
    @FXML
    Button btn2;

    ToggleGroup group = new ToggleGroup();

    public void pushText() {
        RadioButton s = (RadioButton) group.getSelectedToggle();
        if (s != null) {
            textArea.appendText(s.getText() + ": " + textField.getText() + "\n");
            textField.clear();
        }
    }

    public void start() {
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);
        radioButton3.setToggleGroup(group);
        textField.setVisible(true);
        textArea.setVisible(true);
        radioButton1.setVisible(true);
        radioButton2.setVisible(true);
        radioButton3.setVisible(true);
        btn1.setVisible(true);
        btn2.setVisible(false);
    }
}

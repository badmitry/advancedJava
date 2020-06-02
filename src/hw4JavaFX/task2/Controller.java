package hw4JavaFX.task2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {
    private String str = "";
    private float calculation = 0;
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    String getStr() {
        return str;
    }

    void setStr(String str) {
        this.str = str;
    }

    float getCalculation() {
        return calculation;
    }

    void setCalculation(float calculation) {
        this.calculation = calculation;
    }

    @FXML
    TextField textField0;

    private void calculationValue() {
        if (getStr().equals("*")) {
            setCalculation(getCalculation() * (Float.parseFloat(textField0.getText())));
        } else if (getStr().equals("/")) {
            setCalculation(getCalculation() / (Float.parseFloat(textField0.getText())));
        } else if (getStr().equals("+")) {
            setCalculation(getCalculation() + (Float.parseFloat(textField0.getText())));
        } else if (getStr().equals("-")) {
            setCalculation(getCalculation() - (Float.parseFloat(textField0.getText())));
        } else {
            setCalculation(Float.parseFloat(textField0.getText()));
        }
    }

    public void action(javafx.event.ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        calculationValue();
        setStr((String) btn.getUserData());
        textField0.setText(String.valueOf(calculation));
        setCounter(0);
    }

    public void pushText(javafx.event.ActionEvent actionEvent) {
        Button btnn = (Button) actionEvent.getSource();
        if (getCounter() == 0){
            textField0.setText(btnn.getText());
            setCounter(1);
        }else{
            textField0.setText(textField0.getText() + btnn.getText());
        }
    }

    public void actionClear(ActionEvent actionEvent) {
        calculationValue();
        setStr("");
        textField0.setText(String.valueOf(calculation));
        setCounter(0);
    }
}

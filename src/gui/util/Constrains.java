package gui.util;

import javafx.scene.control.TextField;

public class Constrains {

    public static void setFiledInteger(TextField txt){
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) txt.setText(oldValue); });
    }

    public static void setFiledLong(TextField txt){
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) txt.setText(oldValue); });
    }

    public static void setFiledFloat(TextField txt){
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) txt.setText(oldValue); });
    }

    public static void setMaxLength(TextField txt, Integer max){
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > max) txt.setText(oldValue); });
    }
}

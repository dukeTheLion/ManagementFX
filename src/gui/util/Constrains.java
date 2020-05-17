package gui.util;

import javafx.application.Platform;
import javafx.scene.control.TextField;

/*
 *    \\d{0,7}([.]\\d{0,4})
 *    |______|||_||______||
 *        |   | |     |   |
 *     Digit  | dot digit |
 *     0 to 7 |     0 to 4|
 *            |___________|
 *                  |
 *            Occurs once or
 *              not at all
 *
 * masks at: https://respostas.guj.com.br/49038-mascaras-de-cpfcnpj-cep-fone-e-outros-em-javafx
 */

public class Constrains {

    public static void setFiledInteger(TextField txt) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) txt.setText(oldValue); });
    }

    public static void setFiledCPF(TextField txt) {
        Constrains.maxField(txt, 14);
        txt.lengthProperty().addListener((obs, oldValue, newValue) -> {
                    String value = txt.getText();
                    if (newValue.intValue() <= 14) {
                        value = value.replaceAll("[^0-9]", "");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                        value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
                    }
                    txt.setText(value);
                    Constrains.positionCaret(txt); });
    }

    public static void setFiledFloat(TextField txt) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*([.]\\d*)?")) txt.setText(oldValue); });
    }

    public static void setMaxLength(TextField txt, Integer max) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > max) txt.setText(oldValue); });
    }

    public static void maxField(TextField textField, Integer length) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue == null || newValue.length() > length) textField.setText(oldValue); });
    }

    private static void positionCaret(TextField textField) {
        Platform.runLater(() -> {
                    if (textField.getText().length() != 0) {
                        textField.positionCaret(textField.getText().length()); } });
    }
}

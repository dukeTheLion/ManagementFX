package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
    public static Stage currentStage (ActionEvent event){
        return (Stage)((Node) event.getSource()).getScene().getWindow();
    }

    public static Integer parseToInt(String str){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long parseToLong(String str){
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseToDouble(String str){
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

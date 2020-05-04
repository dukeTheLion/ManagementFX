package datebase;

import gui.util.Alerts;
import javafx.scene.control.Alert;

public class DBException extends RuntimeException {
    public DBException(String msg) {
        super(msg);
        Alerts.showAlert("SQLException",
                null,
                msg,
                Alert.AlertType.INFORMATION);;
        //System.exit(0);
    }
}

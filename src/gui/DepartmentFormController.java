package gui;

import gui.util.Constrains;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textName;

    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonCreate;

    @FXML
    private Label labelError;

    @FXML
    public void onButtonCancel(){

    }

    @FXML
    public void onButtonCreate(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void initializeNodes(){
        Constrains.setFiledInteger(textId);
        Constrains.setMaxLength(textName, 30);
    }
}

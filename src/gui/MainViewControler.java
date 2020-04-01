package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewControler implements Initializable {

    @FXML
    private MenuItem menuNewEmployee;
    @FXML
    private MenuItem menuNewDepartment;
    @FXML
    private MenuItem menuDeleteEmployee;
    @FXML
    private MenuItem menuDeleteDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuNewEmployeeAction(){
        System.out.println("Action New Employee");
    }

    @FXML
    public void onMenuNewDepartmentAction(){
        System.out.println("Action New Department");
    }

    @FXML
    public void onMenuDeleteEmployeeAction(){
        System.out.println("Action Delete Employee");
    }

    @FXML
    public void onMenuDeleteDepartmentAction(){
        System.out.println("Action Delete Department");
    }

    @FXML
    public void onMenuHelpAboutAction(){
        Alerts.showAlert("Sobre",
                null,
                "Text",
                Alert.AlertType.INFORMATION);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

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
        loadView("../gui/EmployeeNew.fxml");
    }

    @FXML
    public void onMenuNewDepartmentAction(){
        loadView("../gui/DepartmentNew.fxml");
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

    private synchronized void loadView(String path){
        try {
            VBox newVBox = FXMLLoader.load(getClass().getResource(path));

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());
        } catch (IOException e) {
            Alerts.showAlert("Erro de entrada e saida",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }
}

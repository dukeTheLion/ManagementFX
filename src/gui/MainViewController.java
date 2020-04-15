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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.entities.Department;
import model.services.DepartmentService;
import model.services.EmployeeService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuNewEmployee;
    @FXML
    private MenuItem menuNewDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuNewEmployeeAction(){
        loadView("../gui/EmployeeNew.fxml", (EmployeeNewController controller) -> {
            controller.setEmployeeService(new EmployeeService());
            controller.updateTableView(); });
    }

    @FXML
    public void onMenuNewDepartmentAction(){
        loadView("../gui/DepartmentNew.fxml", (DepartmentNewController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView(); });
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

    private synchronized <T> void loadView (String path, Consumer<T> init){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            T controller = loader.getController();
            init.accept(controller);

        } catch (IOException e) {
            Alerts.showAlert("Erro de entrada e saida",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }
}

package gui;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.entities.Department;
import model.services.DepartmentService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentNewController implements Initializable {

    private DepartmentService service;
    private ObservableList<Department> observableList;

    @FXML
    private TableView<Department> tableViewDepartment;
    @FXML
    private TableColumn<Department, Integer> tableColumnID;
    @FXML
    private TableColumn<Department, String> tableColumnName;
    @FXML
    private Button buttonCreate;

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @FXML
    public void onButtonCreateAction(ActionEvent event) {
        createDialogForm("../gui/DepartmentForm.fxml", Utils.currentStage(event));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (service == null){
            throw new IllegalStateException("Service null");
        }

        List<Department> list = service.findAll();
        System.out.print(list);
        observableList = FXCollections.observableList(list);
        tableViewDepartment.setItems(observableList);
    }

    private void createDialogForm(String path, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Novo Departamento");
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e){
            Alerts.showAlert("Erro de entrada e saida",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }
}

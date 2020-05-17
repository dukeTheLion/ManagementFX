package gui;

import gui.listeners.DataChangeListener;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.entities.Department;
import model.services.DepartmentService;

import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentNewController implements Initializable, DataChangeListener {

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
    @FXML
    private Button buttonDelete;

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @FXML
    public void onButtonCreateAction(ActionEvent event) {
        createDialogFormCreate(new Department(), "../gui/DepartmentFormCreate.fxml", Utils.currentStage(event));
    }

    @FXML
    public void onButtonDeleteAction(ActionEvent event){
        createDialogFormDelete("../gui/DepartmentFormDelete.fxml", Utils.currentStage(event));
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
        observableList = FXCollections.observableList(list);
        tableViewDepartment.setItems(observableList);
    }

    private void createDialogFormCreate(Department department, String path, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();

            DepartmentFormCreate controller = loader.getController();
            controller.setDepartment(department);
            controller.setService(new DepartmentService());
            controller.subscribeDataChangeList(this);
            controller.updateFormData();

            stage (pane, parentStage);

        } catch (IOException e){
            Alerts.showAlert("Erro de entrada e saida",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void createDialogFormDelete(String path, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();

            DepartmentFormDelete controller = loader.getController();
            controller.setService(new DepartmentService());
            controller.subscribeDataChangeList(this);

            stage (pane, parentStage);

        } catch (IOException e){
            Alerts.showAlert("Erro de entrada e saida",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void stage (Pane pane, Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Novo Departamento");
        dialogStage.setScene(new Scene(pane));
        dialogStage.setResizable(false);
        dialogStage.initOwner(parentStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }
}

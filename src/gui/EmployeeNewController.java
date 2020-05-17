package gui;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.SimpleObjectProperty;
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
import model.entities.Employee;
import model.services.DepartmentService;
import model.services.EmployeeService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeNewController implements Initializable, DataChangeListener {

    private EmployeeService service;
    private ObservableList<Employee> observableList;

    @FXML
    private Button buttonCreate;
    @FXML
    private Button buttonDelete;

    @FXML
    private TableView<Employee> tableViewEmployee;
    @FXML
    private TableColumn<Employee, Long> tableColumnId;
    @FXML
    private TableColumn<Employee, String> tableColumnName;
    @FXML
    private TableColumn<Employee, String> tableColumnLastName;
    @FXML
    private TableColumn<Employee, String> tableColumnCPF;
    @FXML
    private TableColumn<Employee, Long> tableColumnEmail;
    @FXML
    private TableColumn<Employee,String> tableColumnSalaryHour;
    @FXML
    private TableColumn<Employee, Double> tableColumnWeeklySalary;
    @FXML
    private TableColumn<Employee, Integer> tableColumnDepartmentId;
    @FXML
    private TableColumn<Employee, String> tableColumnDepartment;

    public void setEmployeeService(EmployeeService service) {
        this.service = service;
    }

    @FXML
    public void onButtonCreateAction(ActionEvent event){
        createDialogFormCreate(new Employee(), "../gui/EmployeeFormCreate.fxml", Utils.currentStage(event));
    }

    @FXML
    public void onButtonDeleteAction(){
        System.out.print("Button");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnSalaryHour.setCellValueFactory(new PropertyValueFactory<>("salaryHour"));
        tableColumnWeeklySalary.setCellValueFactory(new PropertyValueFactory<>("WeeklyHour"));
        tableColumnDepartmentId.setCellValueFactory(tf -> new SimpleObjectProperty<>(tf.getValue().getDepartment().getId()));
        tableColumnDepartment.setCellValueFactory(tf -> new SimpleObjectProperty<>(tf.getValue().getDepartment().getName()));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewEmployee.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (service == null){
            throw new IllegalStateException("Service null");
        }

        List<Employee> list = service.findAll();
        observableList = FXCollections.observableList(list);
        tableViewEmployee.setItems(observableList);
    }

    private void createDialogFormCreate(Employee employee, String path, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();

            EmployeeFormCreate controller = loader.getController();
            controller.setEmployee(employee);
            controller.setService(new EmployeeService());
            controller.subscribeDataChangeList(this);
            controller.updateFormData();

            stage (pane, parentStage, "Adicionar ou Atualizar");

        } catch (IOException e){
            Alerts.showAlert("Erro de entrada e saida",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    /*private void createDialogFormDelete(String path, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();

            EmployeeFormDelete controller = loader.getController();
            controller.setService(new EmployeeService());
            controller.subscribeDataChangeList(this);

            stage (pane, parentStage);

        } catch (IOException e){
            Alerts.showAlert("Erro de entrada e saida",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }*/

    private void stage (Pane pane, Stage parentStage, String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
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

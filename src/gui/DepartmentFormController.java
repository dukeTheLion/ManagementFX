package gui;

import datebase.DBException;
import gui.util.Alerts;
import gui.util.Constrains;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private DepartmentService service;
    private Department department;

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
    public void onButtonCancel(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @FXML
    public void onButtonCreate(ActionEvent event){
        if (department == null) throw new IllegalStateException("Null");
        if (service == null) throw new IllegalStateException("Null");
       try {
           department = getFormData();
           service.setDepartmentService(department);

           Utils.currentStage(event).close();
       } catch (DBException e) {
           Alerts.showAlert("Erro ao salvar",
                   null,
                   e.getMessage(),
                   Alert.AlertType.ERROR);
       }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setService(DepartmentService service) {
        this.service = service;
    }

    private void initializeNodes(){
        Constrains.setFiledInteger(textId);
        Constrains.setMaxLength(textName, 30);
    }

    public void updateFormData(){
        if (department == null) throw new IllegalStateException("Departamento nulo");
        textId.setText(String.valueOf(department.getId()));
        textName.setText(department.getName());
    }

    private Department getFormData() {
        return new Department(Utils.parseToInt(textId.getText()), textName.getText());
    }
}

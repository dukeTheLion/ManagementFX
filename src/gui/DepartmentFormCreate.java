package gui;

import datebase.DBException;
import gui.listeners.DataChangeListener;
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
import model.exceptions.ValidationException;
import model.services.DepartmentService;

import java.net.URL;
import java.util.*;

public class DepartmentFormCreate implements Initializable {

    private DepartmentService service;
    private Department department;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

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
           notifyDataChangeListener();

           Utils.currentStage(event).close();
       }
       catch (ValidationException e) {
           setErrorMessages(e.getErros());
       }
       catch (DBException e) {
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

    public void subscribeDataChangeList(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    private void notifyDataChangeListener(){
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
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

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if (fields.contains("name")) labelError.setText(errors.get("name"));
    }

    private Department getFormData() {
        Department obj = new Department();
        ValidationException exception = new ValidationException("error");

        obj.setId(Utils.parseToInt(textId.getText()));

        if (textName.getText() == null || textName.getText().trim().equals("")) {
            exception.addErros("name", "Campo vazio!");
        }
        obj.setName(textName.getText());

        if (exception.getErros().size() > 0) throw exception;

        return obj;
    }
}

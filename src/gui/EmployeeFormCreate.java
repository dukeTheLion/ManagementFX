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
import model.dao.DepartmentDAO;
import model.dao.implementation.DaoFactory;
import model.entities.Employee;
import model.exceptions.ValidationException;
import model.services.EmployeeService;

import java.net.URL;
import java.util.*;

public class EmployeeFormCreate implements Initializable {
    private EmployeeService service;
    private Employee employee;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField textId;
    @FXML
    private TextField textName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textCPF;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textSalaryHour;
    @FXML
    private TextField textWeeklyHour;
    @FXML
    private TextField textDepartmentId;

    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonCreate;

    @FXML
    private Label labelError1;
    @FXML
    private Label labelError2;
    @FXML
    private Label labelError3;
    @FXML
    private Label labelError4;
    @FXML
    private Label labelError5;
    @FXML
    private Label labelError6;
    @FXML
    private Label labelError7;
    @FXML
    private Label labelError8;

    @FXML
    public void onButtonCancel(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @FXML
    public void onButtonCreate(ActionEvent event){
        if (employee == null) throw new IllegalStateException("Null");
        if (service == null) throw new IllegalStateException("Null");
        try {
            employee = getFormData();
            service.setEmployeeService(employee);
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

    public void setService(EmployeeService service) {
        this.service = service;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void subscribeDataChangeList(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    private void notifyDataChangeListener(){
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    public void updateFormData(){
        if (employee == null) throw new IllegalStateException("Departamento nulo");
        textId.setText(String.valueOf(employee.getId()));
        textName.setText(employee.getName());
        textLastName.setText(employee.getLastName());
        textCPF.setText(String.valueOf(employee.getCPF()));
        textEmail.setText(employee.getEmail());
        textSalaryHour.setText(String.valueOf(employee.getSalaryHour()));
        textWeeklyHour.setText(String.valueOf(employee.getWeeklyHour()));
        int temp = employee.getDepartment().getId();
        textDepartmentId.setText(String.valueOf(temp));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constrains.setFiledLong(textId);
        Constrains.setMaxLength(textName, 10);
        Constrains.setMaxLength(textLastName, 30);
        Constrains.setFiledLong(textCPF);
        Constrains.setMaxLength(textEmail, 50);
        Constrains.setFiledFloat(textSalaryHour);
        Constrains.setFiledInteger(textWeeklyHour);
        Constrains.setFiledInteger(textDepartmentId);
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if (fields.contains("id")) labelError1.setText(errors.get("name"));
        if (fields.contains("name")) labelError2.setText(errors.get("name"));
        if (fields.contains("lastName")) labelError3.setText(errors.get("name"));
        if (fields.contains("cpf")) labelError4.setText(errors.get("name"));
        if (fields.contains("emai")) labelError5.setText(errors.get("name"));
        if (fields.contains("salary")) labelError6.setText(errors.get("name"));
        if (fields.contains("weekly")) labelError7.setText(errors.get("name"));
        if (fields.contains("dep")) labelError8.setText(errors.get("name"));
    }

    private Employee getFormData() {
        Employee obj = new Employee();
        DepartmentDAO temp = DaoFactory.newDepartmentDAO();
        ValidationException exception = new ValidationException("error");

        obj.setId(Utils.parseToLong(textId.getText()));
        obj.setName(textName.getText());
        obj.setLastName(textLastName.getText());
        obj.setCPF(Utils.parseToLong(textCPF.getText()));
        obj.setEmail(textEmail.getText());
        obj.setSalaryHour(Utils.parseToDouble(textSalaryHour.getText()));
        obj.setWeeklyHour(Utils.parseToInt(textWeeklyHour.getText()));
        obj.setDepartment(temp.findById(Utils.parseToInt(textDepartmentId.getText())));
        obj.setControlID(Utils.parseToLong(textId.getText()));

        if (textId.getText() == null || textId.getText().trim().equals("")) {
            exception.addErros("id", "Campo vazio!");
        }
        if (textName.getText() == null || textName.getText().trim().equals("")) {
            exception.addErros("name", "Campo vazio!");
        }
        if (textLastName.getText() == null || textLastName.getText().trim().equals("")) {
            exception.addErros("lastName", "Campo vazio!");
        }
        if (textCPF.getText() == null || textCPF.getText().trim().equals("")) {
            exception.addErros("cpf", "Campo vazio!");
        }
        if (textEmail.getText() == null || textEmail.getText().trim().equals("")) {
            exception.addErros("emai", "Campo vazio!");
        }
        if (textSalaryHour.getText() == null || textSalaryHour.getText().trim().equals("")) {
            exception.addErros("salary", "Campo vazio!");
        }
        if (textWeeklyHour.getText() == null || textWeeklyHour.getText().trim().equals("")) {
            exception.addErros("weekly", "Campo vazio!");
        }
        if (textDepartmentId.getText() == null || textDepartmentId.getText().trim().equals("")) {
            exception.addErros("dep", "Campo vazio!");
        }
        if (exception.getErros().size() > 0) throw exception;

        return obj;
    }
}

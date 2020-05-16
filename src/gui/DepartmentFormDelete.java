package gui;

import datebase.DBException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constrains;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

import java.net.URL;
import java.util.*;

public class DepartmentFormDelete implements Initializable {
    private DepartmentService service;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField text;

    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonDelete;

    @FXML
    private Label labelError;

    public void setService (DepartmentService service){
        this.service = service;
    }

    @FXML
    public void onButtonDelete(ActionEvent event){
        try {
            validationException();

            service.deleteDepartmentService(Utils.parseToInt(text.getText()));
            notifyDataChangeListener();

            Utils.currentStage(event).close();
        }
        catch (ValidationException e){
            setErrorMessages(e.getErros());
        }
        catch (DBException e){
            Alerts.showAlert("Erro ao salvar",
                    null,
                    e.getMessage(),
                    Alert.AlertType.ERROR);
        }

    }

    public void subscribeDataChangeList(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onButtonCancel(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNode();
    }

    private void initializeNode(){
        Constrains.setFiledInteger(text);
    }

    private void notifyDataChangeListener(){
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private void validationException (){
        ValidationException exception = new ValidationException("error");

        if (text.getText() == null || text.getText().trim().equals("")) {
            exception.addErros("id", "Campo vazio!");
        }
        if (exception.getErros().size() > 0) throw exception;
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if (fields.contains("id")) labelError.setText(errors.get("id"));
    }

}

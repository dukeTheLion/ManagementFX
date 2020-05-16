package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, String> erros = new HashMap<>();

    public ValidationException (String msg){
        super(msg);
    }

    public Map<String, String> getErros (){
        return erros;
    }

    public void addErros (String field, String errosMessage){
        erros.put(field, errosMessage);
    }

}

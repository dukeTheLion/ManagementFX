package model.services;

import model.dao.EmployeeDAO;
import model.dao.implementation.DaoFactory;
import model.entities.Employee;

import java.util.List;

public class EmployeeService {
    public List<Employee> findAll (){
        EmployeeDAO employee = DaoFactory.newEmployeeDAO();
        return employee.findAll();
    }
}

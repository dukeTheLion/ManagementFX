package model.services;

import model.dao.EmployeeDAO;
import model.dao.implementation.DaoFactory;
import model.entities.Department;
import model.entities.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    EmployeeDAO dao = DaoFactory.newEmployeeDAO();

    public List<Employee> findAll (){
        EmployeeDAO employee = DaoFactory.newEmployeeDAO();
        return employee.findAll();
    }

    public void setEmployeeService(Employee employee){
        List<Employee> list = findAll();
        List<Long> num = new ArrayList<>();
        list.forEach(emp -> num.add(emp.getId()));

        if (!(num.contains(employee.getId())) || employee.getId() == null) dao.insert(employee);
        else dao.update(employee);

    }
}

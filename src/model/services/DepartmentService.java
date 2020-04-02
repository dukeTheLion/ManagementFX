package model.services;

import model.dao.DepartmentDAO;
import model.dao.implementation.DaoFactory;
import model.entities.Department;

import java.util.List;

public class DepartmentService {
    public List<Department> findAll() {
        DepartmentDAO department = DaoFactory.newDepartmentDAO();
        return department.findAll();
    }
}

package model.services;

import model.dao.DepartmentDAO;
import model.dao.implementation.DaoFactory;
import model.entities.Department;

import java.util.List;

public class DepartmentService {
    DepartmentDAO dao = DaoFactory.newDepartmentDAO();

    public List<Department> findAll() {
        return dao.findAll();
    }

    public void setDepartmentService(Department department){
        if (department.getId() == null) dao.insert(department);
        else dao.update(department);
    }

    public void deleteDepartmentService(Integer id){
        dao.delete(id);
    }
}

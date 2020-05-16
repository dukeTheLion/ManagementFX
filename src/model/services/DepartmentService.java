package model.services;

import model.dao.DepartmentDAO;
import model.dao.implementation.DaoFactory;
import model.entities.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DepartmentService {
    DepartmentDAO dao = DaoFactory.newDepartmentDAO();

    public List<Department> findAll() {
        return dao.findAll();
    }

    public void setDepartmentService(Department department){
        List<Department> list = findAll();
        List<Integer> num = new ArrayList<>();
        list.forEach(dep -> num.add(dep.getId()));

        if (!(num.contains(department.getId())) || department.getId() == null) dao.insert(department);
        else dao.update(department);
    }

    public void deleteDepartmentService(Integer id){
        dao.delete(id);
    }
}

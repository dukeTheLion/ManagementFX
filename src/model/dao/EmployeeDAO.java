package model.dao;

import model.entities.Employee;

import java.util.List;

public interface EmployeeDAO {
    void insert(Employee obj);
    void update(Employee obj);
    void delete(Long id);
    Employee findById(Long id);
    Employee findByName(String name, String lastName);
    List<Employee> findAll();
}

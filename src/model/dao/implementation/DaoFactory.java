package model.dao.implementation;

import datebase.DB;
import model.dao.ControlDAO;
import model.dao.DepartmentDAO;
import model.dao.EmployeeDAO;

public class DaoFactory {
    public static DepartmentDAO newDepartmentDAO(){
        return new DepartmentDaoJDBC(DB.connect());
    }

    public static ControlDAO newControlDAO(){
        return new ControlDaoJDBC(DB.connect());
    }

    public static EmployeeDAO newEmployeeDAO(){
        return new EmployeeDaoJDBC(DB.connect());
    }

}

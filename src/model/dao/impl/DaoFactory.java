package model.dao.impl;

import datebase.DB;
import model.dao.DepartmentDAO;

public class DaoFactory {
    public static DepartmentDAO newDepartmentDAO(){
        return new DepartmentDaoJDBC(DB.connect());
    }
}

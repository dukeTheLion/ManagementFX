package model.dao.impl;

import datebase.DB;
import datebase.DBException;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDAO {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO `DataBase`.`Department` "
                    + "(ID, DepName) "
                    + "values (?, ?)");

            st.setLong(1, obj.getId());
            st.setString(2, obj.getName());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE `DataBase`.`Department` "
                    + "SET `DepName` = ? "
                    + "WHERE `Department`.`ID` = ?");

            st.setString(1, obj.getName());
            st.setLong(2, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }
    }

    @Override
    public void delete(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM `DataBase`.`Department` WHERE (`ID` = ?)");

            st.setLong(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM `DataBase`.`Department`"
                    + "WHERE `Department`.`ID` = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                return new Department(rs.getInt("ID"),
                        rs.getString("DepName"));
            }

            return null;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Department`.* FROM `DataBase`.`Department`");
            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()){

                Department dep = new Department(
                        rs.getInt("ID"),
                        rs.getString("DepName"));

                list.add(dep);
            }

            return list;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }
}

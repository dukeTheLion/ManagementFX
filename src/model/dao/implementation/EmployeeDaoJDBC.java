package model.dao.implementation;

import datebase.DB;
import datebase.DBException;
import model.dao.EmployeeDAO;
import model.entities.Department;
import model.entities.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoJDBC implements EmployeeDAO {
    private Connection conn;

    public EmployeeDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Employee obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO `DataBase`.`Employee` "
                    + "(ID, Name, LastName, CPF, Email, SalaryHour, WeeklyHour, DepartmentID, ControlID) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            st.setLong(1, obj.getId());
            st.setString(2, obj.getName());
            st.setString(3, obj.getLastName());
            st.setLong(4, obj.getCPF());
            st.setString(5, obj.getEmail());
            st.setDouble(6, obj.getSalaryHour());
            st.setInt(7, obj.getWeeklyHour());
            st.setInt(8, obj.getDepartment().getId());
            st.setLong(9, obj.getControlID());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }
    }

    @Override
    public void update(Employee obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE `DataBase`.`Employee` "
                    + "SET `Name` = ?, `LastName` = ?, `CPF` = ?, `Email` = ?, "
                    + "`SalaryHour` = ?, `WeeklyHour` = ?, `DepartmentID` = ?, `ControlID` = ? "
                    + "WHERE `Employee`.`ID` = ?");

            st.setString(1, obj.getName());
            st.setString(2, obj.getLastName());
            st.setLong(3, obj.getCPF());
            st.setString(4, obj.getEmail());
            st.setDouble(5, obj.getSalaryHour());
            st.setInt(6, obj.getWeeklyHour());
            st.setInt(7, obj.getDepartment().getId());
            st.setLong(8, obj.getControlID());

            st.setLong(9, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM `DataBase`.`Employee` WHERE (`ID` = ?)");

            st.setLong(1, id);
            ControlDaoJDBC temp = new ControlDaoJDBC(conn);
            temp.delete(id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }

    }

    @Override
    public Employee findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Employee`.*, `Department`.`DepName` " +
                    "FROM `DataBase`.`Employee` " +
                    "INNER JOIN `DataBase`.`Department` ON `Employee`.`DepartmentID` = `Department`.`ID` " +
                    "WHERE `Employee`.`ID` = ?");

            st.setLong(1, id);
            rs = st.executeQuery();

            return instantiateEmployee(rs);

        }catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    @Override
    public Employee findByName(String name, String lastName) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Employee`.*, `Department`.`DepName` "
                    + "FROM `DataBase`.`Employee` "
                    + "INNER JOIN `DataBase`.`Department` ON `Employee`.`DepartmentID` = `Department`.`ID` "
                    + "WHERE `Employee`.`Name` = ? AND `Employee`.`LastName` = ?");

            st.setString(1, name);
            st.setString(2, lastName);
            rs = st.executeQuery();

            return instantiateEmployee(rs);

        }catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    @Override
    public List<Employee> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Employee`.*, `Department`.`DepName` "
                    + "FROM `DataBase`.`Employee` "
                    + "INNER JOIN `DataBase`.`Department` ON `Employee`.`DepartmentID` = `Department`.`ID` ");

            rs = st.executeQuery();

            List<Employee> list = new ArrayList<>();
            Map<Integer, Department> D_map = new HashMap<>();

            while (rs.next()){
                Department dep = D_map.get(rs.getInt("DepartmentID"));

                if (dep == null) {
                    dep = new Department(rs.getInt("DepartmentID"), rs.getString("DepName"));
                    D_map.put(rs.getInt("DepartmentID"), dep);
                }

                Employee emp = new Employee(rs.getLong("ID"),
                        rs.getString("Name"),
                        rs.getString("LastName"),
                        rs.getLong("CPF"),
                        rs.getString("Email"),
                        rs.getDouble("SalaryHour"),
                        rs.getInt("WeeklyHour"),
                        dep,
                        rs.getLong("ControlID"));

                list.add(emp);
            }

            return list;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    private Employee instantiateEmployee (ResultSet rs ) throws SQLException {
        if (rs.next()){
            return new Employee(rs.getLong("ID"),
                    rs.getString("Name"),
                    rs.getString("LastName"),
                    rs.getLong("CPF"),
                    rs.getString("Email"),
                    rs.getDouble("SalaryHour"),
                    rs.getInt("WeeklyHour"),
                    new Department(rs.getInt("DepartmentID"), rs.getString("DepName")),
                    rs.getLong("ControlID"));
        }

        return null;
    }
}

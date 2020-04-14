package model.dao.implementation;

import datebase.DB;
import datebase.DBException;
import model.dao.ControlDAO;
import model.dao.EmployeeDAO;
import model.entities.Control;
import model.entities.Department;
import model.entities.Employee;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class ControlDaoJDBC implements ControlDAO {
    private Connection conn;

    public ControlDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void cardUpdate(@NotNull Control obj){
        Control control = null;
        boolean exist = false;
        int i = 0;

        List<Control> temp = findId(obj.getID());

        for (Control cont : temp) {
            if (cont.getExit().equals("0001-01-01 00:00:00")){
                exist = cont.getExit().equals("0001-01-01 00:00:00");
                control = temp.get(i);
            } i++;
        }

        if (exist) {
            EmployeeDAO emp = DaoFactory.newEmployeeDAO();

            if (control.getEntry().split(" ")[0].equals(obj.getExit().split(" ")[0])){
                double in = time(control.getEntry().split(" ")[1].split(":"));
                double out = time(obj.getExit().split(" ")[1].split(":"));

                obj.setDaySalary((out - in) * emp.findById(control.getID()).getSalaryHour());
                update(obj);
            } else throw new DBException("Incompatible date");
        } else insert(obj);
    }

    @Override
    public Double monthSalary(String name, String lastName, Integer month) {
        EmployeeDAO obj = DaoFactory.newEmployeeDAO();
        long id = obj.findByName(name, lastName).getId();

        return monthSalary(id, month);
    }

    @Override
    public Double monthSalary(Long id, Integer month) {
        double salary = 0d;

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Control`.`DaySalary` "
                    + "FROM `DataBase`.`Control` "
                    + "WHERE (MONTH(`Control`.`Entry`) = ?) AND `Control`.`ID` = ?");

            st.setInt(1, month);
            st.setLong(2, id);
            rs = st.executeQuery();

            while (rs.next()){
                salary += rs.getDouble("DaySalary");
            }

            return salary;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    /**
     * Find by ID via Control:
     * Esta função esta responsável por trazer a lista completa de funcionário, departamento e dias trabalhados.
     */
    @Override
    public List<Control> findById(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Employee`.*, "
                    + "`Department`.`DepName`, `Control`.`Entry`, "
                    + "`Control`.`Exit`, `Control`.`DaySalary`, `Control`.`Index` "
                    + "FROM `DataBase`.`Employee` "
                    + "INNER JOIN `DataBase`.`Department` ON `Employee`.`DepartmentID` = `Department`.`ID` "
                    + "INNER JOIN `DataBase`.`Control` ON `Employee`.`ControlID` = `Control`.ID "
                    + "WHERE `Employee`.`ID` = ?");

            st.setLong(1, id);
            rs = st.executeQuery();

            return instantiateControl(rs);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    /**
     * Find by Name via Control:
     * Esta função esta responsável por trazer a lista completa de funcionário, departamento e dias trabalhados.
     */
    @Override
    public List<Control> findByName(String name, String lastName) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Employee`.*, "
                    + "`Department`.`DepName`, `Control`.`Entry`, "
                    + "`Control`.`Exit`, `Control`.`DaySalary`, `Control`.`Index` "
                    + "FROM `DataBase`.`Employee` "
                    + "INNER JOIN `DataBase`.`Department` ON `Employee`.`DepartmentID` = `Department`.`ID` "
                    + "INNER JOIN `DataBase`.`Control` ON `Employee`.`ControlID` = `Control`.ID "
                    + "WHERE `Employee`.`Name` = ? AND `Employee`.`LastName` = ?");

            st.setString(1, name);
            st.setString(2, lastName);
            rs = st.executeQuery();

            return instantiateControl(rs);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    @Override
    public List<Control> findByNameYMD(String type, String date, String name, String lastName) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql(type.toUpperCase())
                    + "AND `Employee`.`Name` = ? AND `Employee`.`LastName` = ? ");

            if (type.length() == 2){
                String[] num2 = date.split("-");
                st.setString(1, num2[0]);
                st.setString(2, num2[1]);
                st.setString(3, name);
                st.setString(4, lastName);
            } else {
                st.setString(1, date);
                st.setString(2, name);
                st.setString(3, lastName);
            }
            rs = st.executeQuery();

            return instantiateControl(rs);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    protected void delete(Long id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM `DataBase`.`Control` WHERE (`ID` = ?)");

            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }

    }

    //----------------------------------------------------------------//

    private void insert(@NotNull Control obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO `DataBase`.`Control` (`ID`, `Entry`, `Exit`, `DaySalary`)  "
                            + "values (?, ?, '0001-01-01 00:00:00', 0)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDouble(1, obj.getID());
            st.setString(2, obj.getEntry());

            int rows = st.executeUpdate();

            if (rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setIndex(id);
                }
                DB.closeRs(rs);
            } else throw new DBException("None");
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }
    }

    private void update(@NotNull Control obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE `DataBase`.`Control` "
                    + "SET `Exit` = ?, `DaySalary` = ? "
                    + "WHERE `Control`.`Exit` = '0001-01-01 00:00:00' AND `Control`.`ID` = ?;");

            st.setString(1, obj.getExit());
            st.setDouble(2, obj.getDaySalary());
            st.setDouble(3, obj.getID());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeSt(st);
        }

    }

    @NotNull
    private List<Control> findId(Long id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT `Control`.* "
                    + "FROM `DataBase`.`Control` "
                    + "WHERE `Control`.`ID` = ?");

            st.setLong(1, id);
            rs = st.executeQuery();

            List<Control> list = new ArrayList<>();

            while (rs.next()) {
                Control control = new Control(rs.getLong("ID"),
                        rs.getString("Entry"),
                        rs.getString("Exit"));
                control.setDaySalary(rs.getDouble("DaySalary"));

                list.add(control);
            }

            return list;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeRs(rs);
            DB.closeSt(st);
        }
    }

    private List<Control> instantiateControl(@NotNull ResultSet rs) throws SQLException {
        List<Control> list = new ArrayList<>();

        /*
         * Maps são usados aqui para fazer com seja possível seguir a seguinte estrutura:
         *
         * +----------------+
         * |    Control.    |
         * +----------------+
         * | Id             |
         * | Entry          |
         * | .              | -
         * | .              |  \
         * | .              |   \
         * +----------------+    \
         * +----------------+     \
         * |    Control.    |      \            +----------------+           +----------------+
         * +----------------+       \           |    Employee    |           |   Department   |
         * | Id             |        ------->   +----------------+           +----------------+
         * | Entry          |                   | Id             |           | Id             |
         * | .              | -------------->   | Name           | ------->  | DepName        |
         * | .              |                   | .              |           | .              |
         * | .              |        ------->   | .              |           | .              |
         * +----------------+       /           | .              |           | .              |
         * +----------------+      /            +----------------+           +----------------+
         * |    Control.    |     /
         * +----------------+    /
         * | Id             |   /
         * | Entry          |  /
         * | .              | -
         * | .              |
         * | .              |
         * +----------------+
         * */

        Map<Long, Employee> E_map = new HashMap<>();
        Map<Integer, Department> D_map = new HashMap<>();

        // Loop que percorre todas linhas da tabela
        while (rs.next()) {

            // Verifica a cada ciclo se existe um objeto ja implementado para evitar duplicadas
            Department dep = D_map.get(rs.getInt("DepartmentID"));
            Employee emp = E_map.get(rs.getLong("ID"));

            if (dep == null) {
                dep = new Department(rs.getInt("DepartmentID"), rs.getString("DepName"));
                D_map.put(rs.getInt("DepartmentID"), dep);
            }
            if (emp == null) {
                emp = new Employee(rs.getLong("ID"),
                        rs.getString("Name"),
                        rs.getString("LastName"),
                        rs.getLong("CPF"),
                        rs.getString("Email"),
                        rs.getDouble("SalaryHour"),
                        rs.getInt("WeeklyHour"),
                        dep,
                        rs.getLong("ControlID"));
                E_map.put(rs.getLong("ID"), emp);
            }

            // Cira a lista de 'Control' com vários control aprontando apenas pra um 'Employee' e um 'Department'
            Control control = new Control(rs.getLong("ID"),
                    rs.getString("Entry"),
                    rs.getString("Exit"),
                    rs.getDouble("DaySalary"),
                    rs.getInt("Index"),
                    emp);

            list.add(control);

        }

        return list;
    }

    private String sql (String type){
        String std = "SELECT `Employee`.*, "
                + "`Department`.`DepName`, `Control`.`Entry`, `Control`.`Exit`, `Control`.`DaySalary`, `Control`.`Index` "
                + "FROM `DataBase`.`Employee` "
                + "INNER JOIN `DataBase`.`Department` ON `Employee`.`DepartmentID` = `Department`.`ID` "
                + "INNER JOIN `DataBase`.`Control` ON `Employee`.`ControlID` = `Control`.`ID` ";

        switch (type) {
            case "Y":
                return std + "WHERE YEAR(`Control`.`Entry`) = ? ";
            case "M":
                return std + "WHERE MONTH(`Control`.`Entry`) = ? ";
            case "D":
                return std + "WHERE DAY(`Control`.`Entry`) = ? ";
            case "YM":
                return std + "WHERE YEAR(`Control`.`Entry`) = ? AND MONTH(`Control`.`Entry`) = ? ";
            case "YD":
                return std + "WHERE YEAR(`Control`.`Entry`) = ? AND DAY(`Control`.`Entry`) = ? ";
            case "MD":
                return std + "WHERE MONTH(`Control`.`Entry`) = ? AND DAY(`Control`.`Entry`) = ? ";
            default:
                return std + "WHERE DATE(`Control`.`Entry`) = ? ";
        }
    }

    private double time (String[] data){
        int hour = Integer.parseInt(data[0]);
        int minute = Integer.parseInt(data[1]);
        int second = Integer.parseInt(data[2]);


        return (hour*60*60 + minute*60 + second)/3600.0;
    }
}

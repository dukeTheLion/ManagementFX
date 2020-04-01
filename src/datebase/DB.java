package datebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    //Carrega os dados do banco de dados guardados em db.properties
    public static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties info = new Properties();
            info.load(fs);
            return info;
        } catch (IOException e) {
            throw new DBException(e.getMessage());
        }
    }

    //Faz a conexão com o banco de dados
    public static Connection connect() {
        if (conn == null) {
            try {
                Properties info = loadProperties();
                String url = info.getProperty("dburl");
                conn = DriverManager.getConnection(url, info);
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
        return conn;
    }

    //Fecha a conexão com o banco de dados
    public static void closeConn() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    //Fecha o objeto Statement
    public static void closeSt(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    //Fecha o objeto ResultSet
    public static void closeRs(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }
}

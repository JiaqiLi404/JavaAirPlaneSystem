package AirPlaneSystem.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=机票预订管理系统;integratedSecurity=false;", "sa", "123456");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接错误");
        }
        //	System.out.println(conn);
        return conn;

    }

    public static void closeRes(ResultSet rs, PreparedStatement ps, Connection conn) {


        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("连接释放错误");
            e.printStackTrace();
        }

    }

    public static void closeRes(PreparedStatement ps, Connection conn) {

        try {
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("连接释放错误");
            e.printStackTrace();
        }

    }

}

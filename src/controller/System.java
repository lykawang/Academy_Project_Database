package controller;

import main.MainView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class System {

    static Connection con = null;
    static String username = "ora_timothym";
    static String password = "a50819036";

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:stu",
                    username, password);
            new MainView(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

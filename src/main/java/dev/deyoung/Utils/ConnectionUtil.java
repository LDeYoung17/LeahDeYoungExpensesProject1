package dev.deyoung.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){

        String details = System.getenv("CONNECTION");
        try{Connection conn = DriverManager.getConnection(details);

            return conn;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

}
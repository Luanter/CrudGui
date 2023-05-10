package Guipack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlconnect {
public static Connection getConnection() {  
  try {
    Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;database=TBD;trustServerCertificate=true","pyton","12345");
    System.out.println("Conectado");
    return conn;
    }catch (SQLException e) {
    e.printStackTrace();
    return null;
    }
  }
}
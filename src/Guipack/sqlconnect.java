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
    System.out.println("erro de conexão com o banco de dados");
    return null;
    }
  }
}
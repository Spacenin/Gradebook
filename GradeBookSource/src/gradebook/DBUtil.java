package gradebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBUtil {

  private static Connection connection;
  private static Scanner sc = new Scanner(System.in);

  private DBUtil() {}

  public static synchronized Connection getConnection() {
    if (connection != null) {
      return connection;
    } else {
      try {
        // set the db url, username, and password
        String url = "jdbc:mysql://showcreatedb.cqzpcdfdub0n.us-east-1.rds.amazonaws.com/sys";
        String username = new String();
        String password = new String();
        
        System.out.println("Enter in your username and password");
        System.out.print("Username: ");
        
        username = sc.nextLine();
        
        System.out.print("Password: ");
        password = sc.nextLine();

        // get and return connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        return connection;
      } catch (SQLException | ClassNotFoundException e) {
        System.out.println(e);
        
        return(null);
      }
    }
  }

  public static synchronized void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.out.println(e);
      } finally {
        connection = null;
      }
    }
  }
}

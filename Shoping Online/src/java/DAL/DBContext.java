/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.sql.Connection;

/**
 *
 * @author Admin
 */
public class DBContext {
    protected Connection connection;
    public DBContext(){
        try {
            String user = "sa";
            String pass = "123123";
            String url = "jdbc:sqlserver://DELL-PRECISION:1433;databaseName=PRJ301_DB";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}
        public static void main(String[] args) {
        System.out.println(new DBContext().connection);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DAL.Account;
import DAL.Customer;
import DAL.DBContext;
import DAL.Products;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AccountDAO extends DBContext {

    public Account getAccount(String email, String pass) {
        Account acc = null;
        try {
            String sql = "Select * from Accounts where Email=? and Password=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            //Resultset la 1 kieu du lieu trar ve nhieu ket qua nhu q cai bang
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int AccountID = rs.getInt("AccountID");
                String Email = rs.getString("Email");
                String Password = rs.getString("Password");
                String CustomerID = rs.getString("CustomerID");
                int EmployeeID = rs.getInt("EmployeeID");
                int Role = rs.getInt("Role");
                acc = new Account(AccountID, Email, Password, CustomerID, EmployeeID, Role);
            }
        } catch (Exception e) {

        }
        return acc;
    }

    public boolean checkAccount(String email) {
        boolean check = false;
        try {
            String sql = "insert into Accounts where Email='" + email + "' ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public int insertAccount(Customer cust, Account acc) {
        int result1 = 0, result2 = 0;
        try {
            String sql1 = "insert into Customers(CustomerID,CompanyName,ContactName,ContactTitle,Address) values(?,?,?,?,?)";
            String sql2 = "insert into Accounts(Email,Password,CustomerID,Role) values(?,?,?,?)";
            PreparedStatement ps1 = connection.prepareCall(sql1);
            PreparedStatement ps2 = connection.prepareCall(sql2);
            String rd = randomString(5);
            ps1.setString(1, rd);
            ps1.setString(2, cust.getCompanyName());
            ps1.setString(3, cust.getContactName());
            ps1.setString(4, cust.getContactTitle());
            ps1.setString(5, cust.getAddress());
            ps2.setString(1, acc.getEmail());
            ps2.setString(2, acc.getPassword());
            ps2.setString(3, rd);
            ps2.setInt(4, 2);
            result1 = ps1.executeUpdate();
            result2 = ps2.executeUpdate();
        } catch (Exception e) {
            
        }
        if (result1 > 0 && result2 > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public String randomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public Customer getCusByAccID(int accID) {
        Customer cus = new Customer();
        try {
            String sql = "select c.* from Customers c join Accounts a on a.CustomerID=c.CustomerID\n"
                    + "  where a.AccountID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String CusID = rs.getString("CustomerID");
                String comName = rs.getString("CompanyName");
                String contName = rs.getString("ContactName");
                String contT = rs.getString("ContactTitle");
                String address = rs.getString("Address");

                cus = new Customer(CusID, comName, contName, contT, address);
            }
        } catch (Exception e) {
        }
        return cus;
    }

    public Account getAccByAccID(int accID) {
        Account a = new Account();
        try {
            String sql = "select a.* from Accounts a where a.AccountID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String email = rs.getString("Email");
                String pass = rs.getString("Password");
                String cusID = rs.getString("CustomerID");
                int emID = rs.getInt("EmployeeID");
                int role = rs.getInt("Role");
                a = new Account(accID, email, pass, cusID, emID, role);
            }
        } catch (Exception e) {
        }
        return a;
    }

    public int insertCus(Customer cus) {
        int check = 0;
        try {
            String sql = "insert into Customers(CustomerID,CompanyName,ContactName,ContactTitle,Address)\n"
                    + "  values (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cus.getCustomerID());
            ps.setString(2, cus.getCompanyName());
            ps.setString(3, cus.getContactName());
            ps.setString(4, cus.getContactTitle());
            ps.setString(5, cus.getAddress());
            check=ps.executeUpdate();
        } catch (SQLException e) {
        }
        return check;
    }

    public int UpdateCus(Customer cus) {
        int check = 0;
        try {
            String sql = "UPDATE Customers\n"
                    + "SET  CompanyName= ?,ContactName=?,ContactTitle=?,Address=?\n"
                    + "WHERE CustomerID=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cus.getCompanyName());
            ps.setString(2, cus.getContactName());
            ps.setString(3, cus.getContactTitle());
            ps.setString(4, cus.getAddress());
            ps.setString(5, cus.getCustomerID());
            check = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return check;
    }
}

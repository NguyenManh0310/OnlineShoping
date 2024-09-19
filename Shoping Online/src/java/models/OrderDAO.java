/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DAL.Account;
import DAL.Category;
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
public class OrderDAO extends DBContext {

    public int addToCartCus(Products p, int accID) {
        AccountDAO ad = new AccountDAO();
        Customer cus = ad.getCusByAccID(accID);
        Account acc = ad.getAccByAccID(accID);
        int orderID = 0;
        int check = 0;
        try {
            String sqlcheck = "select o.OrderID from Orders o where o.CustomerID=? and o.OrderDate is null";
            PreparedStatement pscheck = connection.prepareStatement(sqlcheck);
            pscheck.setString(1, cus.getCustomerID());

            ResultSet rs = pscheck.executeQuery();
            int Quantity = 0, OrderID = 0;
            while (rs.next()) {
                OrderID = rs.getInt("OrderID");
            }
            if (OrderID == 0) {

                String sql12 = "insert into Orders(CustomerID,Freight)\n"
                        + " values(?,?)";
                PreparedStatement ps12 = connection.prepareStatement(sql12);
                ps12.setString(1, acc.getCustomerID());
                ps12.setDouble(2, 50.0);
                check = ps12.executeUpdate();

                String sql1 = "select o.OrderID from Orders o where o.CustomerID=? and o.OrderDate is null";
                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ps1.setString(1, acc.getCustomerID());
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    orderID = rs1.getInt("OrderID");
                }

                String sql11 = "insert into [Order Details] (OrderID,ProductID,UnitPrice,Quantity,Discount) values(?,?,?,?,?)";
                PreparedStatement ps11 = connection.prepareStatement(sql11);
                ps11.setInt(1, orderID);
                ps11.setInt(2, p.getProductID());
                ps11.setDouble(3, p.getUnitPrice());
                ps11.setInt(4, 1);
                ps11.setInt(5, 0);
                check = ps11.executeUpdate();

            } else {

                int getOID = 0;
                String sqlOID = "select od.OrderID,od.Quantity from [Order Details] od where od.OrderID=? and od.ProductID=?";
                PreparedStatement psOID = connection.prepareStatement(sqlOID);
                psOID.setInt(1, OrderID);
                psOID.setInt(2, p.getProductID());
                ResultSet rsOID = psOID.executeQuery();
                while (rsOID.next()) {
                    getOID = rsOID.getInt("OrderID");
                    Quantity = rsOID.getInt("Quantity");
                }

                if (getOID == 0) {
                    String sqlin = "insert into [Order Details] (OrderID,ProductID,UnitPrice,Quantity,Discount) values(?,?,?,?,?)";
                    PreparedStatement psin = connection.prepareStatement(sqlin);
                    psin.setInt(1, OrderID);
                    psin.setInt(2, p.getProductID());
                    psin.setDouble(3, p.getUnitPrice());
                    psin.setInt(4, 1);
                    psin.setInt(5, 0);
                    check = psin.executeUpdate();
                } else {
                    Quantity = Quantity + 1;
                    String sqlup = "UPDATE [Order Details]\n"
                            + "SET Quantity=?\n"
                            + "WHERE [Order Details].OrderID=? and [Order Details].ProductID=?";
                    PreparedStatement psup = connection.prepareStatement(sqlup);
                    psup.setInt(1, Quantity);
                    psup.setInt(2, getOID);
                    psup.setInt(3, p.getProductID());

                    check = psup.executeUpdate();
                }

            }

        } catch (SQLException e) {

        }
        return check;
    }

    public int RemoveCartCus(int OrderID, int pID) {
        AccountDAO ad = new AccountDAO();

        int check = 0;
        try {
            String sql1 = " delete from [Order Details] where [Order Details].OrderID=? and [Order Details].ProductID=?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, OrderID);
            ps1.setInt(2, pID);
            check = ps1.executeUpdate();
            int x = 0;
            String sqlcheck = "select o.OrderID from [Order Details] od join Orders o on o.OrderID=od.OrderID \n"
                    + "  where o.OrderID=?";
            PreparedStatement pscheck = connection.prepareStatement(sqlcheck);
            pscheck.setInt(1, OrderID);
            ResultSet rscheck = pscheck.executeQuery();
            while (rscheck.next()) {
                x = rscheck.getInt("OrderID");
            }
            if (x == 0) {
                String sqldelete = "Update Orders \n"
                        + "  set RequiredDate = null\n"
                        + "  where Orders.OrderID=?";
                PreparedStatement psdelete = connection.prepareStatement(sqldelete);
                psdelete.setInt(1, OrderID);
                psdelete.executeUpdate();
            }
        } catch (SQLException e) {

        }
        return check;
    }

    public int MinusCartCus(int OrderID, int Quantity, int pID) {

        int check = 0;
        try {
            if (Quantity > 0) {
                String sql1 = "UPDATE [Order Details]\n"
                        + "SET Quantity = ?\n"
                        + "WHERE [Order Details].OrderID=? and [Order Details].ProductID=?";
                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ps1.setInt(1, Quantity);
                ps1.setInt(2, OrderID);
                ps1.setInt(3, pID);
                check = ps1.executeUpdate();
            } else {
                RemoveCartCus(OrderID, pID);
            }

        } catch (SQLException e) {

        }
        return check;
    }

    public int CancelOrderCus(int OrderID) {
        AccountDAO ad = new AccountDAO();

        int check = 0;
        try {

            String sqldelete = "Update Orders \n"
                    + "  set RequiredDate = null\n"
                    + "  where Orders.OrderID=?";
            PreparedStatement psdelete = connection.prepareStatement(sqldelete);
            psdelete.setInt(1, OrderID);
            psdelete.executeUpdate();

        } catch (SQLException e) {

        }
        return check;
    }

    public int OrderCartCus(Customer cus, String OrderDate, String RequiredDate) {
        int check = 0;
        try {
            String sql = "UPDATE [Orders]\n"
                    + "SET OrderDate=?,RequiredDate=?,ShipAddress=?,ShipCity=?,ShipCountry=?\n"
                    + "WHERE [Orders].OrderID in \n"
                    + "(select o.OrderID from [Orders] o where o.CustomerID=? and o.OrderDate is null)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, OrderDate);
            ps.setString(2, RequiredDate);
            ps.setString(3, cus.getAddress());
            ps.setString(4, cus.getAddress());
            ps.setString(5, cus.getAddress());
            ps.setString(6, cus.getCustomerID());
            check = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return check;
    }

    public int OrderCartGuest(ArrayList<Products> list, Customer cus, String OrderDate, String RequiredDate) {
        int check = 0;
        try {
            String sql = "insert into Orders(CustomerID,OrderDate,RequiredDate,ShipAddress,ShipCity,ShipCountry)\n"
                    + "  values(?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cus.getCustomerID());
            ps.setString(2, OrderDate);
            ps.setString(3, RequiredDate);
            ps.setString(4, cus.getAddress());
            ps.setString(5, cus.getAddress());
            ps.setString(6, cus.getAddress());
            check = ps.executeUpdate();
            int OrderID = 0;
            String sql1 = "select o.OrderID from Orders o where o.CustomerID=?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setString(1, cus.getCustomerID());
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                OrderID = rs1.getInt("OrderID");
            }
            String sqlin = "insert into [Order Details](OrderID,ProductID,UnitPrice,Quantity,Discount)\n"
                    + "  values(?,?,?,?,?)";
            PreparedStatement psin = connection.prepareStatement(sqlin);
            for (Products p : list) {

                psin.setInt(1, OrderID);
                psin.setInt(2, p.getProductID());
                psin.setDouble(3, p.getUnitPrice());
                psin.setInt(4, p.getNumber());
                psin.setDouble(5, 0);
                check = psin.executeUpdate();
            }
        } catch (SQLException e) {
        }
        return check;
    }

    public static void main(String[] args) {
        OrderDAO od = new OrderDAO();
        ProductDAO pd = new ProductDAO();
        Products p = pd.getProductbyID(75);
        System.out.println(od.addToCartCus(p, 1));
    }
}

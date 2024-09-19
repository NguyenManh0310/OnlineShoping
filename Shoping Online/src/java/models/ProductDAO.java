/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DAL.DBContext;
import DAL.OrderDetails;
import DAL.Orders;
import DAL.Products;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductDAO extends DBContext {

    public ArrayList<Products> getAllProduct() {
        ArrayList<Products> list = new ArrayList<>();
        try {
            String sql = "select p.*,c.CategoryName from Products p join Categories c on c.CategoryID=p.CategoryID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                String CategoryName = rs.getString("CategoryName");
                int CategoryID = rs.getInt("CategoryID");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                list.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));

            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Products> page(int num, int page, ArrayList<Products> list) {
        ProductDAO pd = new ProductDAO();

        ArrayList<Products> listpage = new ArrayList<>();

        if (page * num <= list.size()) {
            for (int i = (page - 1) * num; i < page * num; i++) {
                listpage.add(list.get(i));
            }
        } else {
            for (int i = (page - 1) * num; i < list.size(); i++) {
                listpage.add(list.get(i));
            }
        }
        return listpage;
    }

    public Products getProductbyID(int ID) {
        Products list = new Products();
        try {
            String sql = "select p.*,c.CategoryName from Products p join Categories c on c.CategoryID=p.CategoryID where p.ProductID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String CategoryName = rs.getString("CategoryName");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                list = new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public int getAccID(String email) {
        int accID = 0;
        try {
            String sql1 = "select AccountID from Accounts where Email=?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setString(1, email);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                accID = rs1.getInt("AccountID");
            }
        } catch (SQLException e) {
        }
        return accID;
    }

    public ArrayList<Products> getAllProductOfCusByID(int accID) {
        ArrayList<Products> listprocus = new ArrayList<>();
        try {

            String sql2 = "select p.*,c.CategoryName,od.Quantity as 'number',o.OrderID from Products p\n"
                    + "             join Categories c on c.CategoryID=p.CategoryID\n"
                    + "            join [Order Details] od on od.ProductID=p.ProductID\n"
                    + "           join Orders o on o.OrderID=od.OrderID\n"
                    + "              join Customers cus on cus.CustomerID=o.CustomerID\n"
                    + "            join Accounts a on a.CustomerID=cus.CustomerID\n"
                    + "        where a.AccountID=? and o.OrderDate IS NULl \n"
                    + "  group by p.ProductID,p.ProductName,p.CategoryID,p.Discontinued,p.QuantityPerUnit,p.ReorderLevel,\n"
                    + "             p.UnitPrice,p.UnitsInStock,p.UnitsOnOrder,c.CategoryName,a.Email,o.OrderDate,od.Quantity,o.OrderID\n"
                    + "                 order by o.OrderDate desc";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, accID);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                int ProductID = rs2.getInt("ProductID");
                String ProductName = rs2.getString("ProductName");
                int CategoryID = rs2.getInt("CategoryID");
                String CategoryName = rs2.getString("CategoryName");
                String QuantityPerUnit = rs2.getString("QuantityPerUnit");
                double UnitPrice = rs2.getDouble("UnitPrice");
                int UnitsInStock = rs2.getInt("UnitsInStock");
                int UnitsOnOrder = rs2.getInt("UnitsOnOrder");
                int ReorderLevel = rs2.getInt("ReorderLevel");
                boolean Discontinued = rs2.getBoolean("Discontinued");
                int number = rs2.getInt("number");
                int OrderID = rs2.getInt("OrderID");
                listprocus.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued, number, OrderID));
            }
        } catch (Exception e) {
        }
        return listprocus;
    }

    public ArrayList<Products> SearchByName(String pName) {
        ArrayList<Products> listsearch = new ArrayList<>();
        try {
            String sql = "select p.*,c.CategoryName  from Products p join Categories c on p.CategoryID=c.CategoryID\n"
                    + "  where p.ProductName like ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + pName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String CategoryName = rs.getString("CategoryName");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                listsearch.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));
            }
        } catch (SQLException e) {
        }
        return listsearch;
    }
    public ArrayList<Products> SearchByNameCateID(String pName,int cateID) {
        ArrayList<Products> listsearch = new ArrayList<>();
        try {
            String sql = "select p.*,c.CategoryName  from Products p join Categories c on p.CategoryID=c.CategoryID\n"
                    + "  where c.CategoryID=? and p.ProductName like ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cateID);
            ps.setString(2, "%" + pName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ProductID = rs.getInt("ProductID");
                String ProductName = rs.getString("ProductName");
                int CategoryID = rs.getInt("CategoryID");
                String CategoryName = rs.getString("CategoryName");
                String QuantityPerUnit = rs.getString("QuantityPerUnit");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");
                int UnitsOnOrder = rs.getInt("UnitsOnOrder");
                int ReorderLevel = rs.getInt("ReorderLevel");
                boolean Discontinued = rs.getBoolean("Discontinued");
                listsearch.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));
            }
        } catch (SQLException e) {
        }
        return listsearch;
    }
    public ArrayList<Products> getPendingOfCus(String cusID) {
        ArrayList<Products> list = new ArrayList<>();

        try {
            String sql = "select od.* from [Order Details] od join Orders o on o.OrderID=od.OrderID\n"
                    + "where o.ShippedDate is null and o.CustomerID=? and o.RequiredDate is not null\n"
                    + "order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cusID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                int ProductID = rs.getInt("ProductID");
                Products p = getProductbyID(ProductID);
                String ProductName = p.getProductName();
                int CategoryID = p.getCategoryID();
                String CategoryName = p.getCategoryName();
                String QuantityPerUnit = p.getQuantityPerUnit();
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = p.getUnitsInStock();
                int UnitsOnOrder = p.getUnitsOnOrder();
                int ReorderLevel = p.getReorderLevel();
                boolean Discontinued = p.isDiscontinued();
                int number = rs.getInt("Quantity");
                list.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued, number, OrderID));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Products> getCancelOfCus(String cusID) {
        ArrayList<Products> list = new ArrayList<>();

        try {
            String sql = "select od.* from [Order Details] od join Orders o on o.OrderID=od.OrderID\n"
                    + "   where o.ShippedDate is null and o.CustomerID=? and o.RequiredDate is null and o.OrderDate is not null\n"
                    + "   order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cusID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                int ProductID = rs.getInt("ProductID");
                Products p = getProductbyID(ProductID);
                String ProductName = p.getProductName();
                int CategoryID = p.getCategoryID();
                String CategoryName = p.getCategoryName();
                String QuantityPerUnit = p.getQuantityPerUnit();
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = p.getUnitsInStock();
                int UnitsOnOrder = p.getUnitsOnOrder();
                int ReorderLevel = p.getReorderLevel();
                boolean Discontinued = p.isDiscontinued();
                int number = rs.getInt("Quantity");
                list.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued, number, OrderID));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Products> getComOfCus(String cusID) {
        ArrayList<Products> list = new ArrayList<>();

        try {
            String sql = "select od.* from [Order Details] od join Orders o on o.OrderID=od.OrderID\n"
                    + "where o.ShippedDate is not null and o.CustomerID=? order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cusID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                int ProductID = rs.getInt("ProductID");
                Products p = getProductbyID(ProductID);
                String ProductName = p.getProductName();
                int CategoryID = p.getCategoryID();
                String CategoryName = p.getCategoryName();
                String QuantityPerUnit = p.getQuantityPerUnit();
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = p.getUnitsInStock();
                int UnitsOnOrder = p.getUnitsOnOrder();
                int ReorderLevel = p.getReorderLevel();
                boolean Discontinued = p.isDiscontinued();
                int number = rs.getInt("Quantity");
                list.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued, number, OrderID));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Orders> getOrderPending(String cusID) {
        ArrayList<Orders> list = new ArrayList<>();
        try {
            String sql = "select o.OrderDate,o.OrderID from Orders o where o.OrderID in\n"
                    + " (select od.OrderID from [Order Details] od join Orders o on o.OrderID=od.OrderID\n"
                    + " where o.ShippedDate is null and o.CustomerID=? and o.RequiredDate is not null\n"
                    + " group by od.OrderID) order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cusID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String OrderDate = rs.getString("OrderDate");

                Orders ode = new Orders();
                ode.setOrderID(OrderID);
                ode.setOrderDate(OrderDate);
                list.add(ode);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Orders> getOrderCom(String cusID) {
        ArrayList<Orders> list = new ArrayList<>();
        try {
            String sql = "select top 5 o.OrderDate,o.ShippedDate,o.OrderID from Orders o where o.OrderID in\n"
                    + " (select od.OrderID from [Order Details] od join Orders o on o.OrderID=od.OrderID\n"
                    + " where o.ShippedDate is not null and o.CustomerID=? \n"
                    + " group by od.OrderID) order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cusID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String OrderDate = rs.getString("OrderDate");
                String ShippedDate = rs.getString("ShippedDate");
                Orders ode = new Orders();
                ode.setShippedDate(ShippedDate);
                ode.setOrderID(OrderID);
                ode.setOrderDate(OrderDate);
                list.add(ode);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Orders> getOrderCancel(String cusID) {
        ArrayList<Orders> list = new ArrayList<>();
        try {
            String sql = "select o.OrderDate,o.OrderID from Orders o where o.OrderID in\n"
                    + " (select od.OrderID from [Order Details] od join Orders o on o.OrderID=od.OrderID\n"
                    + " where o.ShippedDate is null and o.CustomerID=? and o.RequiredDate is null and o.OrderDate is not null\n"
                    + " group by od.OrderID) order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cusID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String OrderDate = rs.getString("OrderDate");

                Orders ode = new Orders();
                ode.setOrderID(OrderID);
                ode.setOrderDate(OrderDate);
                list.add(ode);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void main(String[] args) {
        ProductDAO pd = new ProductDAO();
        ArrayList<Products> listprocus = pd.getAllProductOfCusByID(1);
        System.out.println(listprocus);
    }
}

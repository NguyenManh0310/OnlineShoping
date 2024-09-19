/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DAL.Customer;
import DAL.DBContext;
import DAL.Orders;
import DAL.Products;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AdminDAO extends DBContext {

    public ArrayList<Orders> getAllOrderByDate(String SDate, String EDate) {
        ArrayList<Orders> list = new ArrayList<>();
        try {
            String sql = "select o.OrderID,o.OrderDate,o.RequiredDate,o.ShippedDate,e.FirstName+' '+e.LastName as 'EName',\n"
                    + "  c.ContactName as 'CusName',o.Freight from Orders o \n"
                    + "  join Customers c on c.CustomerID=o.CustomerID\n"
                    + "  join Employees e on e.EmployeeID=o.EmployeeID\n"
                    + "  where o.OrderDate is not null and o.OrderDate>= ? and o.OrderDate<= ?\n"
                    + "  order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, SDate);
            ps.setString(2, EDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String OrderDate = rs.getString("OrderDate");
                String RequiredDate = rs.getString("RequiredDate");
                String ShippedDate = rs.getString("ShippedDate");
                String EName = rs.getString("EName");
                String CusName = rs.getString("CusName");
                Double Freight = rs.getDouble("Freight");
                list.add(new Orders(OrderID, OrderDate, RequiredDate, ShippedDate, Freight, EName, CusName));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int CancelOrder(int OrderID) {
        int check = 0;
        try {

            String sqldelete = " Update Orders \n"
                    + "set RequiredDate = null\n"
                    + " where Orders.OrderID=?";
            PreparedStatement psdelete = connection.prepareStatement(sqldelete);
            psdelete.setInt(1, OrderID);
            check = psdelete.executeUpdate();
        } catch (SQLException e) {
        }
        return check;
    }
    
    public String getEnameByID(int eID) {
        String name = "";
        try {
            String sql = "select e.FirstName+' '+e.LastName as 'EName' from Employees e where e.EmployeeID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, eID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("EName");
            }
        } catch (Exception e) {
        }
        return name;
    }

    public ArrayList<Orders> getAllOrder() {
        ArrayList<Orders> list = new ArrayList<>();
        try {
            String sql = "select o.OrderID,o.OrderDate,o.RequiredDate,o.ShippedDate,o.EmployeeID,\n"
                    + "   c.ContactName as 'CusName',o.Freight from Orders o \n"
                    + "   join Customers c on c.CustomerID=o.CustomerID          \n"
                    + "   where o.OrderDate is not null\n"
                    + "   order by o.OrderID desc";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                String OrderDate = rs.getString("OrderDate");
                String RequiredDate = rs.getString("RequiredDate");
                String ShippedDate = rs.getString("ShippedDate");
                int ID = rs.getInt("EmployeeID");
                String EName = getEnameByID(ID);
                String CusName = rs.getString("CusName");
                Double Freight = rs.getDouble("Freight");
                list.add(new Orders(OrderID, OrderDate, RequiredDate, ShippedDate, Freight, EName, CusName));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public ArrayList<Orders> page(int num, int page, ArrayList<Orders> list) {

        ArrayList<Orders> listpage = new ArrayList<>();

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

    public int createPro(Products p) {
        int check = 0;
        try {
            String sql = "insert into Products(ProductName,CategoryID,QuantityPerUnit,UnitPrice,UnitsInStock,Discontinued)\n"
                    + "  values(?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryID());
            ps.setString(3, p.getQuantityPerUnit());
            ps.setDouble(4, p.getUnitPrice());
            ps.setInt(5, p.getUnitsInStock());
            ps.setBoolean(6, p.isDiscontinued());
            check = ps.executeUpdate();
        } catch (Exception e) {
        }
        return check;
    }

    public ArrayList<Products> getProductByOrderID(int OrderID) {
        ArrayList<Products> list = new ArrayList<>();
        ProductDAO pd = new ProductDAO();
        try {
            String sql = " select * from [Order Details] where [Order Details].OrderID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, OrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int ProductID = rs.getInt("ProductID");
                Products p = pd.getProductbyID(ProductID);
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

        } catch (SQLException e) {
        }

        return list;
    }

    public Orders getOrderDatebyOrderID(int OrderID) {
        Orders st = new Orders();
        try {
            String sql = "select o.OrderDate,o.RequiredDate,o.ShippedDate from Orders o where o.OrderID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, OrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String OrderDate = rs.getString("OrderDate");
                if (rs.getString("ShippedDate") == null) {
                    if (rs.getString("RequiredDate") == null) {
                        st.setOrderDate(OrderDate);//canceled
                    } else {
                        String RequiredDate = rs.getString("RequiredDate");//pending
                        st.setOrderDate(OrderDate);
                        st.setRequiredDate(RequiredDate);
                    }
                } else {//complete
                    String ShippedDate = rs.getString("ShippedDate");
                    String RequiredDate = rs.getString("RequiredDate");
                    st.setOrderDate(OrderDate);
                    st.setShippedDate(ShippedDate);
                    st.setRequiredDate(RequiredDate);
                }

            }

        } catch (Exception e) {
        }
        return st;
    }

    public int UpdateProduct(Products p) {
        int check = 0;
        try {
            String sql = "update Products\n"
                    + "  set ProductName=?,CategoryID=?,QuantityPerUnit=?,UnitPrice=?,UnitsInStock=?\n"
                    + "  where Products.ProductID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, p.getProductName());
            ps.setInt(2,p.getCategoryID());
            ps.setString(3, p.getQuantityPerUnit());
            ps.setDouble(4, p.getUnitPrice());
            ps.setInt(5,p.getUnitsInStock());
            ps.setInt(6, p.getProductID());
            check=ps.executeUpdate();
        } catch (SQLException e) {
        }
        return check;
    }
    public ArrayList<Customer> getAllCus(){
        ArrayList<Customer> list=new ArrayList<>();
        try {
            String sql="select * from Customers o order by o.ContactName asc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String CustomerID=rs.getString("CustomerID");
                String CompanyName=rs.getString("CompanyName");
                String ContactName=rs.getString("ContactName");
                String ContactTitle=rs.getString("ContactTitle");
                String Address=rs.getString("Address");
                list.add(new Customer(CustomerID, CompanyName, ContactName, ContactTitle, Address));
            }
        } catch (SQLException e) {
        }
        return list;
    }
    public int checkCus(String cusID){
        int check=0;
        try {
            String sql="select count(c.CustomerID) as num from Customers c where c.CustomerID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cusID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                check=rs.getInt("num");
            }
        } catch (Exception e) {
        }
        return check;
    }
    public static void main(String[] args) {
        AdminDAO add = new AdminDAO();
        //System.out.println(add.getAllOrderByDate("1", EDate));
    }
}

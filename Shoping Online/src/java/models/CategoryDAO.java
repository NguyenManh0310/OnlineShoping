/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DAL.Category;
import DAL.DBContext;
import DAL.Products;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CategoryDAO extends DBContext {

    public ArrayList<Products> getCateByID(int ID) {
        ArrayList<Products> list = new ArrayList<>();
        try {
            String sql = "select p.*,c.CategoryName from Products p join Categories c on c.CategoryID=p.CategoryID\n"
                    + "  where c.CategoryID=?";
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
                list.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));
            }

        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Category> getAllCateName() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            String sql = "Select CategoryName from Categories";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            int d = 0;
            while (rs.next()) {
                String CateName = rs.getString("CategoryName");
                Category c = new Category();
                d++;
                c.setCategoryID(d);
                c.setCategoryName(CateName);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;

    }

    public ArrayList<Products> getTop4Discount() {
        ArrayList<Products> list = new ArrayList<>();
        try {
            String sql = "select top 4 p.*,c.CategoryName from [Order Details] od \n"
                    + "  join Products p on p.ProductID=od.ProductID\n"
                    + "  join Categories c on c.CategoryID=p.CategoryID\n"
                    + "  order by od.Discount desc";
            PreparedStatement ps = connection.prepareStatement(sql);
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
                list.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));

            }
        } catch (Exception e) {
        }
        return list;

    }

    public ArrayList<Products> getTop4OID() {
        ArrayList<Products> list = new ArrayList<>();
        try {
            String sql = " select top 4 p.*,c.CategoryName,count(od.ProductID) as number from [Order Details] od,Categories c,Products p \n"
                    + " where od.ProductID = p.ProductID and c.CategoryID = p.CategoryID group by od.ProductID,c.CategoryName,p.CategoryID,p.Discontinued,p.ProductID,p.ProductName,p.QuantityPerUnit,p.ReorderLevel,\n"
                    + " p.UnitPrice,p.UnitsInStock,p.UnitsOnOrder order by number desc";
            PreparedStatement ps = connection.prepareStatement(sql);
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
                list.add(new Products(ProductID, ProductName, CategoryName, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued));

            }
        } catch (Exception e) {
        }
        return list;

    }

    public static void main(String[] args) {
        CategoryDAO ct = new CategoryDAO();
        ArrayList<Category> list;
        list = ct.getAllCateName();
        for (Category p : list) {
            System.out.println(p.getCategoryName());
        }

    }
}

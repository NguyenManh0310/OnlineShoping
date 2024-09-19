/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
import DAL.Account;
import DAL.Customer;
import DAL.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.AccountDAO;
import models.OrderDAO;
import models.ProductDAO;

/**
 *
 * @author Admin
 */
public class OrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDAO ad = new AccountDAO();
        ProductDAO pd = new ProductDAO();
        OrderDAO od = new OrderDAO();
        LocalDate currentdate = LocalDate.now();
        String OrderDate = currentdate.toString();
        if (req.getSession().getAttribute("AccSession") == null) {
            ArrayList<Products> listproguest = (ArrayList<Products>) req.getSession().getAttribute("cart");

            String CompanyName = req.getParameter("txtCompanyName");
            String ContactName = req.getParameter("txtContactName");
            String ContactTitle = req.getParameter("txtConTitle");
            String Address = req.getParameter("txtAddress");
            String RequiredDate = req.getParameter("txtRequiredDate");
            if (RequiredDate.compareTo(OrderDate) <= 0) {
                req.getSession().setAttribute("RD", "* Required Date is required and must before today");
            }
            if (CompanyName.equals("")) {
                req.getSession().setAttribute("company", "* Company Name is required");
            }
            if (ContactName.equals("")) {
                req.getSession().setAttribute("contactName", "* Contact Name is required");
            }
            if (ContactTitle.equals("")) {
                req.getSession().setAttribute("contactTitle", "* Contact Title is required");
            }
            if (Address.equals("")) {
                req.getSession().setAttribute("address", "* Address is required");
            }
            if (CompanyName.equals("") || ContactName.equals("") || ContactTitle.equals("") || Address.equals("") || RequiredDate.compareTo(OrderDate) <= 0) {
                resp.sendRedirect("/BaiTap/cart?page=1");
            } else {
                String guestID=ad.randomString(5);
                Customer cus=new Customer();
                cus.setAddress(Address);
                cus.setCompanyName(CompanyName);
                cus.setContactName(ContactName);
                cus.setContactTitle(ContactTitle);
                cus.setCustomerID(guestID);
                ad.insertCus(cus);               
                od.OrderCartGuest(listproguest, cus, OrderDate, RequiredDate);
                req.getSession().removeAttribute("cart");
                resp.sendRedirect("/BaiTap/cart?page=1");
            }

        } else {
            Account acc = (Account) req.getSession().getAttribute("AccSession");
            int accID = pd.getAccID(acc.getEmail());
            Customer cus = ad.getCusByAccID(accID);

            if (req.getParameter("OrderID") != null && req.getParameter("pID") != null) {
                int OrderID = Integer.parseInt(req.getParameter("OrderID"));
                int pID = Integer.parseInt(req.getParameter("pID"));
                od.RemoveCartCus(OrderID, pID);
                resp.sendRedirect("/BaiTap/profile?p=allorder");
                req.getSession().setAttribute("cancelOD", "Cancel Successfully");
            } else {
                //cancel
                if (req.getParameter("CancelOrderID") != null) {
                    int COrderID = 0;
                    try {
                        COrderID = Integer.parseInt(req.getParameter("CancelOrderID"));
                    } catch (NumberFormatException e) {
                    }
                    od.CancelOrderCus(COrderID);
                    resp.sendRedirect(req.getContextPath() + "/profile?p=allorder");
                } else {

                    String RequiredDate = req.getParameter("txtRequiredDate");
                    if (RequiredDate.compareTo(OrderDate) <= 0) {
                        req.getSession().setAttribute("RD", "Required Date is required and must before today");
                    } else {

                        od.OrderCartCus(cus, OrderDate, RequiredDate);
                        req.getSession().setAttribute("ODok", "Order Successfully");

                    }

                    resp.sendRedirect("/BaiTap/cart?page=1");
                }
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}

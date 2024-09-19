/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAL.Account;
import DAL.Customer;
import DAL.OrderDetails;
import DAL.Orders;
import DAL.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.AccountDAO;
import models.ProductDAO;

/**
 *
 * @author Admin
 */
public class ProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO pd = new ProductDAO();
        AccountDAO ad = new AccountDAO();

        Account acc = (Account) req.getSession().getAttribute("AccSession");
        int accID = pd.getAccID(acc.getEmail());
        Customer cus = ad.getCusByAccID(accID);
        String companyName;
        String contactName;
        String contactTitle;
        String address;
        if (req.getParameter("txtComName").equalsIgnoreCase("")) {
            companyName = cus.getCompanyName();
        } else {
            companyName = req.getParameter("txtComName");
        }
        if (req.getParameter("txtConName").equalsIgnoreCase("")) {
            contactName = cus.getContactName();
        } else {
            contactName = req.getParameter("txtConName");
        }
        if (req.getParameter("txtConTitle").equalsIgnoreCase("")) {
            contactTitle = cus.getContactTitle();
        } else {
            contactTitle = req.getParameter("txtConTitle");
        }
        if (req.getParameter("txtAddress").equalsIgnoreCase("")) {
            address = cus.getAddress();
        } else {
            address = req.getParameter("txtAddress");
        }
        cus = new Customer(cus.getCustomerID(), companyName, contactName, contactTitle, address);
        ad.UpdateCus(cus);
        req.setAttribute("ok", "Update Information success!");

        resp.sendRedirect("/BaiTap/profile?p=editinfo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO pd = new ProductDAO();
        AccountDAO ad = new AccountDAO();
        String p = "";

        if (req.getSession().getAttribute("AccSession") == null) {
            resp.sendRedirect("/BaiTap/Home");
        } else {
            if (req.getParameter("p") == null) {
                resp.sendRedirect("/BaiTap/profile?p=personinfo");
            } else {
                p = req.getParameter("p");
                Account acc = (Account) req.getSession().getAttribute("AccSession");
                int accID = pd.getAccID(acc.getEmail());
                Customer cus = ad.getCusByAccID(accID);
                Account account = ad.getAccByAccID(accID);
                if (p.equalsIgnoreCase("personinfo")) {

                    req.setAttribute("cus", cus);
                    req.setAttribute("account", account);
                    req.setAttribute("personinfo", "personinfo");

                }
                if (p.equalsIgnoreCase("allorder")) {
                    req.setAttribute("allorder", "allorder");
                    ArrayList<Products> listpending = pd.getPendingOfCus(cus.getCustomerID());
                    req.setAttribute("listpending", listpending);
                    ArrayList<Orders> listOP=pd.getOrderPending(cus.getCustomerID());
                    req.setAttribute("listOP", listOP);
                    
                    
                    ArrayList<Products> listcom = pd.getComOfCus(cus.getCustomerID());
                    req.setAttribute("listcom", listcom);
                    
                    ArrayList<Orders> listOC=pd.getOrderCom(cus.getCustomerID());
                    req.setAttribute("listOC", listOC);

                }
                if (p.equalsIgnoreCase("cancelorder")) {
                    ArrayList<Products> listcancel = pd.getCancelOfCus(cus.getCustomerID());
                    req.setAttribute("listcancel", listcancel);
                    ArrayList<Orders> listOrderCancel=pd.getOrderCancel(cus.getCustomerID());
                    req.setAttribute("listOrderCancel", listOrderCancel);
                    req.setAttribute("cancelorder", "cancelorder");

                }
                if (p.equalsIgnoreCase("editinfo")) {
                    
                    req.setAttribute("editinfo", "editinfo");
                    req.setAttribute("cus", cus);
                    req.setAttribute("account", account);
                }
                req.setAttribute("p", p);
                req.getRequestDispatcher("./profile.jsp").forward(req, resp);

            }
        }
    }

}

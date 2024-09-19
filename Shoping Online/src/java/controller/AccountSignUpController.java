/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAL.Account;
import DAL.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.AccountDAO;

/**
 *
 * @author Admin
 */
public class AccountSignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("txtEmail");
        String pass = req.getParameter("txtPass");
        String companyName = req.getParameter("txtCompanyName");
        String contactName = req.getParameter("txtContactName");
        String contactTiltle = req.getParameter("txtContactTiltle");
        String address = req.getParameter("txtAddress");
        String rePass = req.getParameter("txtRePass");
        if (email.equals("")) {
            req.setAttribute("msgEmail", "Email is required");
        }
        if (pass.equals("")) {
            req.setAttribute("msgPass", "Password is required");
        }
        if (companyName.equals("")) {
            req.setAttribute("msgcompanyName", "Company Name is required");
        }
        if (contactName.equals("")) {
            req.setAttribute("msgcontactName", "Contact Name is required");
        }
        if (contactTiltle.equals("")) {
            req.setAttribute("msgcontactTiltle", "Contact Tiltle is required");
        }
        if (address.equals("")) {
            req.setAttribute("msgaddress", "Address is required");
        }
        if (rePass.equals("")) {
            req.setAttribute("msgrePass", "Re Pass is required");
        }
        if (email.equals("") || pass.equals("") || companyName.equals("") || contactName.equals("")
                || contactTiltle.equals("") || address.equals("") || rePass.equals("")) {
            req.getRequestDispatcher("../signup.jsp").forward(req, resp);
        } else {

            if (rePass.equals(pass) == false) {
                req.setAttribute("msgRePassf", "Re-Password is not match with password");
                req.getRequestDispatcher("../signup.jsp").forward(req, resp);
            } else {
                Customer cust = new Customer("", companyName, contactName, contactTiltle, address);
                Account acc = new Account(0, email, pass, "", 0, 0);
                if (new AccountDAO().insertAccount(cust, acc) > 0) {
                    resp.sendRedirect("/BaiTap/account/signin");
                } else {

                    req.getRequestDispatcher(".././signup.jsp").forward(req, resp);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(".././signup.jsp").forward(req, resp);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAL.Account;
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
public class AccountSignInController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("../signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("txtEmail");
        String pass = req.getParameter("txtPass");
        if (email.equals("")) {
            req.setAttribute("msgEmail", "Email is required");
        }
        if (pass.equals("")) {
            req.setAttribute("msgPass", "Password is required");
        }
        if (pass.equals("") || email.equals("")) {
            req.getRequestDispatcher("../signin.jsp").forward(req, resp);
        } else {

            Account acc = new AccountDAO().getAccount(email, pass);

            if (acc != null) {
                if (acc.getRole() == 2) {
                    req.getSession().setAttribute("AccSession", acc);
                    req.getSession().removeAttribute("cart");
                } else {
                    if (acc.getRole() == 1) {
                        req.getSession().setAttribute("admin", acc);
                        req.getSession().removeAttribute("cart");
                    }
                }

                resp.sendRedirect("/BaiTap/Home");

            } else {

                req.setAttribute("msg", "Email or Password is incorrect");

                req.getRequestDispatcher("../signin.jsp").forward(req, resp);

            }
        }

    }

}

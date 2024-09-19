/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAL.Account;
import DAL.Category;
import DAL.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.AdminDAO;
import models.CategoryDAO;
import models.ProductDAO;

/**
 *
 * @author Admin
 */
public class DetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO pd = new ProductDAO();
        ArrayList<Products> listallpro = pd.getAllProduct();
        try {
            
                int pID = Integer.parseInt(req.getParameter("pid"));
                int size = pd.getAllProduct().size();
                if (pID <= 1 || pID > size) {
                    resp.sendRedirect(req.getContextPath()+"/Home");
                } else {
                    req.setAttribute("pID", pID);
                    Products pro;

                    req.setAttribute("size", size);
                    pro = pd.getProductbyID(pID);
                    req.setAttribute("pro", pro);
                    int cartnum = 0;
                    if (req.getSession().getAttribute("AccSession") != null) {
                        Account acc = (Account) req.getSession().getAttribute("AccSession");
                        int accID = pd.getAccID(acc.getEmail());
                        ArrayList<Products> listprocus = pd.getAllProductOfCusByID(accID);
                        cartnum = listprocus.size();
                        req.setAttribute("csize", cartnum);
                    } else {
                        if (req.getSession().getAttribute("cart") != null) {
                            ArrayList<Products> listcart = (ArrayList<Products>) req.getSession().getAttribute("cart");
                            cartnum = listcart.size();
                        }
                    }
                    req.setAttribute("cartnum", cartnum);
                    req.getRequestDispatcher("./detail.jsp").forward(req, resp);
                }
            
        } catch (ServletException | IOException | NumberFormatException e) {
            resp.sendRedirect("/BaiTap/Home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}

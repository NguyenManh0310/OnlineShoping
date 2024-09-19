/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

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
public class CartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") == null) {
            int id = 0;
            if (req.getParameter("id") != null) {
                id = Integer.parseInt(req.getParameter("id"));

            }
            ProductDAO pd = new ProductDAO();
            OrderDAO od = new OrderDAO();
            ArrayList<Products> listproguest;
            if (req.getSession().getAttribute("AccSession") == null) {
                if (req.getSession().getAttribute("cart") == null) {
                    listproguest = new ArrayList<>();
                    Products p = pd.getProductbyID(id);
                    p.setNumber(1);
                    listproguest.add(p);

                } else {
                    listproguest = (ArrayList<Products>) req.getSession().getAttribute("cart");
                    Products p = pd.getProductbyID(id);
                    boolean ck = false;
                    for (int i = 0; i < listproguest.size(); i++) {
                        if (listproguest.get(i).getProductID() == p.getProductID()) {
                            listproguest.get(i).setNumber(listproguest.get(i).getNumber() + 1);
                            ck = true;
                            break;
                        }
                    }
                    if (ck == false) {
                        p.setNumber(1);
                        listproguest.add(p);
                    }
                }
                req.getSession().setAttribute("cart", listproguest);

            } else {
                Account acc = (Account) req.getSession().getAttribute("AccSession");
                int accID = pd.getAccID(acc.getEmail());
                Products p = pd.getProductbyID(id);

                if (req.getParameter("id") != null) {
                    od.addToCartCus(p, accID);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/detail?pid=" + id);
        } else {
            resp.sendRedirect(req.getContextPath() + "/Home");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDAO ac = new AccountDAO();
        int page = 1;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException e) {
            page = 1;
        }

        req.setAttribute("page", page);
        ProductDAO pd = new ProductDAO();
        if (req.getSession().getAttribute("AccSession") == null) {
            ArrayList<Products> listproguest;

            if (req.getSession().getAttribute("cart") == null) {
                listproguest = new ArrayList<>();
                req.getSession().setAttribute("cart", listproguest);
                req.setAttribute("gsize", listproguest.size());

            } else {
                listproguest = (ArrayList<Products>) req.getSession().getAttribute("cart");
                double total = 0;
                for (Products p : listproguest) {
                    total += p.getUnitPrice() * p.getNumber();
                }
                double numpage = (double) Math.floor(listproguest.size() / 8);
                req.setAttribute("numpage", numpage);
                ArrayList<Products> listguestperpage = pd.page(8, page, listproguest);
                req.setAttribute("total", total);
                req.setAttribute("listguestperpage", listguestperpage);
                req.setAttribute("gsize", listproguest.size());

            }

        } else {
            Account acc = (Account) req.getSession().getAttribute("AccSession");
            int accID = pd.getAccID(acc.getEmail());
            Customer cus = ac.getCusByAccID(accID);
            req.setAttribute("cus", cus);
            ArrayList<Products> listprocus = pd.getAllProductOfCusByID(accID);
            req.setAttribute("listprocus", listprocus);
            double total = 0;
            for (Products p : listprocus) {
                total += p.getUnitPrice() * p.getNumber();
            }
            ArrayList<Products> listcusperpage = pd.page(8, page, listprocus);
            req.setAttribute("total", total);

            req.setAttribute("listcusperpage", listcusperpage);
            req.setAttribute("csize", listcusperpage.size());
            double numpage = (double) Math.floor(listprocus.size() / 8);
            req.setAttribute("numpage", numpage);

        }
        int cartnum = 0;
        if (req.getSession().getAttribute("AccSession") != null) {
            Account acc = (Account) req.getSession().getAttribute("AccSession");
            int accID = pd.getAccID(acc.getEmail());
            ArrayList<Products> listprocus = pd.getAllProductOfCusByID(accID);
            cartnum = listprocus.size();
        } else {
            if (req.getSession().getAttribute("cart") != null) {
                ArrayList<Products> listcart = (ArrayList<Products>) req.getSession().getAttribute("cart");
                cartnum = listcart.size();
            }
        }
        req.setAttribute("cartnum", cartnum);
        req.getRequestDispatcher("./cart.jsp").forward(req, resp);
    }

}

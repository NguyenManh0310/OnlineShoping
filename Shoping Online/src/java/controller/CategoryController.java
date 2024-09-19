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
import models.CategoryDAO;
import models.ProductDAO;

/**
 *
 * @author Admin
 */
public class CategoryController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO pd = new ProductDAO();
        CategoryDAO ct = new CategoryDAO();

        if (req.getParameter("txtSearch") == null || req.getParameter("txtSearch").equalsIgnoreCase("")) {
            resp.sendRedirect("/BaiTap/category?pid=0&page=1");
        } else {
            ArrayList<Products> listsearch;
            String sname = "";
            ArrayList<Category> listcate = ct.getAllCateName();
            sname = req.getParameter("txtSearch");
            listsearch = pd.SearchByName(sname);
            req.setAttribute("listsearch", listsearch);
            req.setAttribute("sname", sname);
            req.setAttribute("listcate", listcate);
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
            req.getRequestDispatcher("./search.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO pd = new ProductDAO();
        CategoryDAO ct = new CategoryDAO();
        ArrayList<Category> listcate = new ArrayList<>();
        ArrayList<Products> listpro = new ArrayList<>();
        listcate = ct.getAllCateName();
        try {
            if (req.getParameter("pid") == null || "".equals(req.getParameter("pid"))) {
                resp.sendRedirect("/BaiTap/Home");
            } else {

                req.setAttribute("listcate", listcate);
                int cateID = 0;

                if (Integer.parseInt(req.getParameter("pid")) == 0) {
                    int page = 1;
                    try {
                        if (req.getParameter("page") != null) {
                            page = Integer.parseInt(req.getParameter("page"));
                        }
                    } catch (Exception e) {
                        page = 1;
                    }
                    listpro = pd.getAllProduct();
                    ArrayList<Products> listperpage;
                    listperpage = pd.page(12, page, listpro);
                    double numpage = (double) Math.floor(listpro.size() / 12);
                    req.setAttribute("listperpage", listperpage);
                    req.setAttribute("numpage", numpage);
                    req.setAttribute("page", page);

                    req.setAttribute("listpro", listpro);
                    req.setAttribute("all", "all");

                } else {

                    cateID = Integer.parseInt(req.getParameter("pid"));
                    ArrayList<Products> list = new ArrayList<>();
                    list = ct.getCateByID(cateID);
                    req.setAttribute("cateID", cateID);
                    req.setAttribute("list", list);
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
                req.getRequestDispatcher("./category.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException | NumberFormatException e) {
            resp.sendRedirect("/BaiTap/Home");
        }

    }

}

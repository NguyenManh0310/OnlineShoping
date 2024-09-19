/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAL.Account;
import DAL.Category;
import DAL.Customer;
import DAL.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import models.AccountDAO;
import models.CategoryDAO;
import models.OrderDAO;
import models.ProductDAO;

/**
 *
 * @author Admin
 */
public class Home extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDAO od = new OrderDAO();
        ProductDAO pd = new ProductDAO();
        AccountDAO ac = new AccountDAO();
        if (req.getSession().getAttribute("AccSession") != null) {
            if (req.getParameter("OrderID") != null) {
                int OrderID = Integer.parseInt(req.getParameter("OrderID"));
                int pID = Integer.parseInt(req.getParameter("pID"));
                if (req.getSession().getAttribute("AccSession") != null) {

                    od.RemoveCartCus(OrderID, pID);
                    req.getSession().setAttribute("rmok", "Remove Successfully");
                }
            } else if (req.getParameter("minusOrderID") != null && req.getParameter("Quantity") != null) {
                int minusOrderID = Integer.parseInt(req.getParameter("minusOrderID"));
                int Quantity = Integer.parseInt(req.getParameter("Quantity"));
                int pID = Integer.parseInt(req.getParameter("pID"));
                if (req.getSession().getAttribute("AccSession") != null) {
                    Quantity = Quantity - 1;
                    od.MinusCartCus(minusOrderID, Quantity, pID);

                }
            } else if (req.getParameter("addID") != null) {
                int addID = Integer.parseInt(req.getParameter("addID"));
                if (req.getSession().getAttribute("AccSession") != null) {
                    Account acc = (Account) req.getSession().getAttribute("AccSession");
                    int accID = pd.getAccID(acc.getEmail());
                    Customer cus = ac.getCusByAccID(accID);
                    Products p = pd.getProductbyID(addID);
                    od.addToCartCus(p, acc.getAccountID());

                }
            }
        } else {
            if (req.getParameter("guestrmID") != null) {
                ArrayList<Products> listproguest = (ArrayList<Products>) req.getSession().getAttribute("cart");
                int rmID = Integer.parseInt(req.getParameter("guestrmID"));
                Products p = pd.getProductbyID(rmID);
                for (int i = 0; i < listproguest.size(); i++) {
                    if (listproguest.get(i).getProductID() == p.getProductID()) {
                        listproguest.remove(i);
                        req.getSession().setAttribute("rmok", "Remove Successfully");
                        break;
                    }
                }
            }
            if (req.getParameter("minuspID") != null) {
                ArrayList<Products> listproguest = (ArrayList<Products>) req.getSession().getAttribute("cart");
                int minusID = Integer.parseInt(req.getParameter("minuspID"));
                Products p = pd.getProductbyID(minusID);
                for (int i = 0; i < listproguest.size(); i++) {
                    if (listproguest.get(i).getProductID() == p.getProductID()) {
                        listproguest.get(i).setNumber(listproguest.get(i).getNumber() - 1);
                        if(listproguest.get(i).getNumber()==0){
                            listproguest.remove(i);
                        }
                        break;
                    }
                }
            }
            if (req.getParameter("addpID") != null) {
                ArrayList<Products> listproguest = (ArrayList<Products>) req.getSession().getAttribute("cart");
                int minusID = Integer.parseInt(req.getParameter("addpID"));
                Products p = pd.getProductbyID(minusID);
                for (int i = 0; i < listproguest.size(); i++) {
                    if (listproguest.get(i).getProductID() == p.getProductID()) {
                        listproguest.get(i).setNumber(listproguest.get(i).getNumber() + 3);
                        
                        break;
                    }
                }
            }
        }
        resp.sendRedirect("/BaiTap/cart?page=1");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        CategoryDAO cd = new CategoryDAO();
        ProductDAO pd = new ProductDAO();
        ArrayList<Category> listcate = new ArrayList<>();
        ArrayList<Products> listpro;
        ArrayList<Products> listdis;
        ArrayList<Products> listoid;
        ArrayList<Products> listperpage;
        listcate = cd.getAllCateName();
        listpro = pd.getAllProduct();
        listdis = cd.getTop4Discount();
        listoid = cd.getTop4OID();
        listperpage = pd.page(12, page, listpro);
        double numpage = (double) Math.floor(listpro.size() / 12);
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
        req.setAttribute("listcate", listcate);
        req.setAttribute("listpro", listpro);
        req.setAttribute("listdis", listdis);
        req.setAttribute("listdoid", listoid);
        req.setAttribute("listperpage", listperpage);
        req.setAttribute("numpage", numpage);
        req.setAttribute("page", page);
        req.getRequestDispatcher("./index.jsp").forward(req, resp);
    }

}

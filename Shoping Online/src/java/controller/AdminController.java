/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAL.Category;
import DAL.Customer;
import DAL.Orders;
import DAL.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import models.AdminDAO;
import models.CategoryDAO;
import models.ProductDAO;

/**
 *
 * @author Admin
 */
public class AdminController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDAO add = new AdminDAO();
        CategoryDAO cd = new CategoryDAO();
        ProductDAO pd = new ProductDAO();
        if (req.getSession().getAttribute("admin") != null) {
            if (req.getParameter("p") == null) {
                int OrderID = 0;

                if (req.getParameter("AdminOrderID") != null) {
                    OrderID = Integer.parseInt(req.getParameter("AdminOrderID"));
                    add.CancelOrder(OrderID);

                    if (req.getParameter("in") == null) {
                        resp.sendRedirect(req.getContextPath() + "/admin?p=orders&page=1");
                    } else {
                        if (req.getParameter("incus") == null) {

                            resp.sendRedirect(req.getContextPath() + "/admin?p=orderdetail&oid=" + OrderID);
                        } else {

                            String customID = req.getParameter("incus");
                            resp.sendRedirect(req.getContextPath() + "/admin?p=customerdetail&cid=" + customID);
                        }
                    }

                }
            } else {
                String p = req.getParameter("p");
                if (p.equals("createproduct")) {
                    String pName = req.getParameter("txtProductName");
                    if (pName.equals("")) {
                        req.setAttribute("msgpName", "Product name is required.");
                    }
                    double unitPrice = -1;
                    try {
                        unitPrice = Double.parseDouble(req.getParameter("txtUnitPrice"));
                    } catch (Exception e) {
                        unitPrice = -1;
                    }
                    String quan = " ";
                    if (req.getParameter("txtQuantityPerUnit") != null) {
                        quan = req.getParameter("txtQuantityPerUnit");
                    }
                    int UnitIn = 0;

                    try {
                        UnitIn = Integer.parseInt(req.getParameter("txtUnitsInStock"));
                    } catch (Exception e) {

                        req.setAttribute("msgU", "Units in stock is required.");
                    }

                    int CateID = Integer.parseInt(req.getParameter("ddlCategory"));
                    boolean dis = false;
                    if (pName.equals("") || UnitIn <= 0) {
                        ArrayList<Category> listcate = cd.getAllCateName();
                        req.setAttribute("listcate", listcate);
                        req.getRequestDispatcher("createpro.jsp").forward(req, resp);
                    } else {
                        Products pr = new Products();
                        pr.setCategoryID(CateID);
                        pr.setProductName(pName);
                        pr.setUnitPrice(unitPrice);
                        pr.setQuantityPerUnit(quan);
                        pr.setUnitsInStock(UnitIn);
                        pr.setDiscontinued(dis);
                        add.createPro(pr);
                        resp.sendRedirect(req.getContextPath() + "/admin?p=products");
                    }
                }
                if (p.equals("editproduct")) {
                    int proID = (int) req.getSession().getAttribute("editID");

                    Products prod = pd.getProductbyID(proID);
                    req.setAttribute("pro", prod);
                    ArrayList<Category> listcate = cd.getAllCateName();
                    req.setAttribute("listcate", listcate);
                    String pName = req.getParameter("txtProductName");
                    boolean ck = true;
                    if (pName.equals("") == false) {
                        prod.setProductName(pName);
                    }
                    if (req.getParameter("txtUnitPrice") != null) {
                        double unitPrice = -1;
                        try {
                            unitPrice = Double.parseDouble(req.getParameter("txtUnitPrice"));
                        } catch (Exception e) {
                            unitPrice = -1;
                            req.setAttribute("unitok", "Unit Price must be number and greater than 0");
                            ck = false;
                        }
                        if (unitPrice > 0) {
                            prod.setUnitPrice(unitPrice);
                        }
                    }

                    String quan = req.getParameter("txtQuantityPerUnit");
                    if (quan.equals("") == false) {
                        prod.setQuantityPerUnit(quan);
                    }
                    if (req.getParameter("txtUnitsInStock") != null) {
                        int UnitIn = -1;
                        try {
                            UnitIn = Integer.parseInt(req.getParameter("txtUnitsInStock"));
                        } catch (Exception e) {
                            UnitIn = -1;
                            req.setAttribute("unitinok", "Unit In Stock must be number and greater than 0");
                            ck = false;
                        }
                        if (UnitIn >= 0) {
                            prod.setUnitsInStock(UnitIn);
                        }
                    }
                    int CateID = Integer.parseInt(req.getParameter("ddlCategory"));
                    prod.setCategoryID(CateID);
                    if (ck == true) {
                        add.UpdateProduct(prod);
                        req.getSession().removeAttribute("editID");
                        req.getSession().setAttribute("updatepro", "Update Product Successfully !");
                        resp.sendRedirect(req.getContextPath() + "/admin?p=editproduct&pid=" + prod.getProductID());
                    } else {
                        req.getRequestDispatcher("editpro.jsp").forward(req, resp);
                    }
                }
            }

        } else {
            resp.sendRedirect(req.getContextPath() + "/Home");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDAO add = new AdminDAO();
        ProductDAO pd = new ProductDAO();
        CategoryDAO cd = new CategoryDAO();
        if (req.getSession().getAttribute("admin") != null) {
            if (req.getParameter("p") != null) {
                String p = req.getParameter("p");
                req.setAttribute("p", p);
                if ("dashboard".equals(p)) {
                    req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
                }
                if ("orders".equals(p)) {

                    ArrayList<Orders> listall = add.getAllOrder();
                    //req.setAttribute("lists", listall);
                    ArrayList<Orders> listperpage;
                    int page = 1;
                    try {
                        page = Integer.parseInt(req.getParameter("page"));
                        listperpage = add.page(12, page, listall);
                        double numpage = (double) Math.floor(listall.size() / 12);
                        req.setAttribute("listperpage", listperpage);
                        req.setAttribute("numpage", numpage);
                        req.setAttribute("page", page);

                        req.getRequestDispatcher("./order.jsp").forward(req, resp);
                    } catch (Exception e) {
                        resp.sendRedirect(req.getContextPath() + "/admin?p=orders&page=1");
                    }

                }
                if ("products".equals(p)) {
                    ArrayList<Products> listpro = pd.getAllProduct();
                    req.setAttribute("listpro", listpro);
                    ArrayList<Products> listperpagepro;
                    ArrayList<Category> listcatename = cd.getAllCateName();
                    req.setAttribute("listcatename", listcatename);
                    int page = 1;
                    if (req.getParameter("page") != null) {
                        page = Integer.parseInt(req.getParameter("page"));

                    }

                    req.setAttribute("listpro", listpro);
                    listperpagepro = pd.page(12, page, listpro);
                    double numpage = (double) Math.floor(listpro.size() / 12);
                    req.setAttribute("listperpage", listperpagepro);
                    req.setAttribute("numpage", numpage);
                    req.setAttribute("page", page);

                    req.getRequestDispatcher("product.jsp").forward(req, resp);
                }
                if ("createproduct".equals(p)) {
                    ArrayList<Category> listcate = cd.getAllCateName();
                    req.setAttribute("listcate", listcate);
                    req.getRequestDispatcher("createpro.jsp").forward(req, resp);
                }
                if ("orderdetail".equals(p)) {
                    int OrderDetailID = 0;
                    try {
                        OrderDetailID = Integer.parseInt(req.getParameter("oid"));
                    } catch (Exception e) {
                    }
                    ArrayList<Products> listprobyOID = add.getProductByOrderID(OrderDetailID);
                    req.setAttribute("listprobyOID", listprobyOID);
                    req.setAttribute("OrderID", OrderDetailID);
                    Orders date = add.getOrderDatebyOrderID(OrderDetailID);
                    req.setAttribute("date", date);
                    req.setAttribute("inorderdetail", "inorderdetail");
                    req.getRequestDispatcher("orderdetail.jsp").forward(req, resp);
                }
                if ("customer".equals(p)) {
                    ArrayList<Customer> listcus = add.getAllCus();
                    req.setAttribute("listcus", listcus);
                    req.getRequestDispatcher("customer.jsp").forward(req, resp);
                }
                if ("editproduct".equals(p)) {

                    int pID = 0;
                    try {

                        pID = Integer.parseInt(req.getParameter("pid"));
                        Products pro = pd.getProductbyID(pID);
                        req.setAttribute("pro", pro);
                        ArrayList<Category> listcate = cd.getAllCateName();
                        req.getSession().setAttribute("editID", pID);
                        req.setAttribute("listcate", listcate);
                        req.getRequestDispatcher("editpro.jsp").forward(req, resp);
                    } catch (ServletException | IOException | NumberFormatException e) {
                        resp.sendRedirect(req.getContextPath() + "/admin?p=products&page=1");
                    }

                }
                if ("customerdetail".equals(p)) {
                    String cusID = req.getParameter("cid");
                    if (add.checkCus(cusID) == 0) {
                        resp.sendRedirect(req.getContextPath() + "/admin?p=customer");
                    } else {
                        ArrayList<Products> listpending = pd.getPendingOfCus(cusID);
                        req.setAttribute("listpending", listpending);
                        ArrayList<Orders> listOP = pd.getOrderPending(cusID);
                        req.setAttribute("listOP", listOP);
                        req.setAttribute("incusdetail", cusID);

                        ArrayList<Products> listcom = pd.getComOfCus(cusID);
                        req.setAttribute("listcom", listcom);

                        ArrayList<Orders> listOC = pd.getOrderCom(cusID);
                        req.setAttribute("listOC", listOC);
                        ArrayList<Products> listCA = pd.getCancelOfCus(cusID);
                        ArrayList<Orders> listOCA = pd.getOrderCancel(cusID);
                        req.setAttribute("listCA", listCA);
                        req.setAttribute("listOCA", listOCA);
                        req.setAttribute("listOC", listOC);
                        req.setAttribute("customerdetail", "customerdetail");
                        req.getRequestDispatcher("customer.jsp").forward(req, resp);
                    }
                }
            } else {

                if (req.getParameter("StartDate") == null && req.getParameter("EndDate") == null) {
                    int page = 1;
                    if (req.getParameter("page") != null) {
                        page = Integer.parseInt(req.getParameter("page"));
                    }
                    String search = req.getParameter("txtSearch"); 
                    if (req.getParameter("CategoryID").equals("all")) {
                        if (search.equals("")) {
                            resp.sendRedirect(req.getContextPath() + "/admin?p=products&page=1");
                        } else {
                            
                            ArrayList<Products> listsearchpro = pd.SearchByName(search);
                            ArrayList<Products> listperpage = pd.page(12, page, listsearchpro);
                            double numpage = 0;
                            if (listsearchpro.size() % 12 == 0) {
                                numpage = (double) Math.floor(listsearchpro.size() / 12) - 1;
                            } else {
                                numpage = (double) Math.floor(listsearchpro.size() / 12);
                            }
                            req.setAttribute("listperpage", listperpage);
                            req.setAttribute("numpage", numpage);
                            req.setAttribute("page", page);
                            ArrayList<Category> listcatename = cd.getAllCateName();
                            req.setAttribute("listcatename", listcatename);
                            req.getRequestDispatcher("product.jsp").forward(req, resp);
                        }
                    } else {
                        if (search.equals("")) {
                            int cateID = 0;
                            try {
                                cateID = Integer.parseInt(req.getParameter("CategoryID"));
                                ArrayList<Products> listsearchpro = cd.getCateByID(cateID);
                                ArrayList<Products> listperpage = pd.page(12, page, listsearchpro);
                                double numpage = 0;
                                if (listsearchpro.size() % 12 == 0) {
                                    numpage = (double) Math.floor(listsearchpro.size() / 12) - 1;
                                } else {
                                    numpage = (double) Math.floor(listsearchpro.size() / 12);
                                }
                                req.setAttribute("listperpage", listperpage);
                                req.setAttribute("numpage", numpage);
                                req.setAttribute("page", page);
                                ArrayList<Category> listcatename = cd.getAllCateName();
                                req.setAttribute("listcatename", listcatename);
                                req.getRequestDispatcher("product.jsp").forward(req, resp);
                            } catch (Exception e) {
                                resp.sendRedirect(req.getContextPath() + "/admin?p=products&page=1");
                            }
                        } else {
                            int cateID = 0;
                            try {
                                
                                cateID = Integer.parseInt(req.getParameter("CategoryID"));
                                ArrayList<Products> listsearchpro = pd.SearchByNameCateID(search, cateID);
                                ArrayList<Products> listperpage = pd.page(12, page, listsearchpro);
                                double numpage = 0;
                                if (listsearchpro.size() % 12 == 0) {
                                    numpage = (double) Math.floor(listsearchpro.size() / 12) - 1;
                                } else {
                                    numpage = (double) Math.floor(listsearchpro.size() / 12);
                                }
                                req.setAttribute("listperpage", listperpage);
                                req.setAttribute("numpage", numpage);
                                req.setAttribute("page", page);
                                ArrayList<Category> listcatename = cd.getAllCateName();
                                req.setAttribute("listcatename", listcatename);
                                req.getRequestDispatcher("product.jsp").forward(req, resp);
                            } catch (Exception e) {
                                resp.sendRedirect(req.getContextPath() + "/admin?p=products&page=1");
                            }

                        }

                    }

                } else {
                    ArrayList<Orders> lists;
                    ArrayList<Orders> listall;
                    String SDate = req.getParameter("StartDate");
                    String EDate = req.getParameter("EndDate");
                    //search order by date
                    if (SDate.equals("") == false && EDate.equals("") == false && SDate.compareTo(EDate) <= 0) {

                        lists = add.getAllOrderByDate(SDate, EDate);
                        req.setAttribute("lists", lists);
                        ArrayList<Orders> listperpage;
                        int page = 1;
                        if (req.getParameter("page") != null) {
                            page = Integer.parseInt(req.getParameter("page"));
                        }
                        listperpage = add.page(12, page, lists);
                        double numpage = 0;
                        if (lists.size() % 12 == 0) {
                            numpage = (double) Math.floor(lists.size() / 12) - 1;
                        } else {
                            numpage = (double) Math.floor(lists.size() / 12);
                        }
                        req.setAttribute("listperpage", listperpage);
                        req.setAttribute("numpage", numpage);
                        req.setAttribute("page", page);

                        req.getRequestDispatcher("order.jsp").forward(req, resp);

                    } else {
                        LocalDate currentdate = LocalDate.now();
                        String now = currentdate.toString();
                        if (SDate.compareTo(EDate) > 0) {
                            req.getSession().setAttribute("msg", "Start Date must before End Date ");
                        }
                        if (SDate.compareTo(now) > 0 || EDate.compareTo(now) > 0) {
                            req.getSession().setAttribute("msg", "Start Date and End Date can not after today");
                        }
                        if (SDate.equals("") || EDate.equals("")) {
                            req.getSession().setAttribute("msg", "Start Date and End Date must not be empty");
                        }

                        resp.sendRedirect(req.getContextPath() + "/admin?p=orders&page=1");

                    }
                }
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/Home");
        }
    }

}

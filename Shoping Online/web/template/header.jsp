<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <link href="css/style.css" rel="stylesheet"/>
        <link href="css/stylecontentmain.css" rel="stylesheet"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div id="logo">
                    <a href="${pageContext.request.contextPath}/Home"><img src="img/logo.png"/></a>
                </div>
                <div id="banner">
                    <ul>
                        <li>
                            <c:if test="${admin eq null}">
                                <a href="${pageContext.request.contextPath}/cart?page=1">Cart: 
                                    <c:if test="${cartnum ne null}">${cartnum}</c:if>
                                    <c:if test="${cartnum eq null}">0</c:if>
                                    </a>

                                </li>
                            <%if (session.getAttribute("AccSession")==null){%>
                            <li><a href="${pageContext.request.contextPath}/account/signin">SignIn</a></li>
                            <li><a href="${pageContext.request.contextPath}/account/signup">SignUp</a></li>

                            <% } else{%>      

                            <li><a href="${pageContext.request.contextPath}/profile?p=personinfo">Profile</a></li>
                            <li><a href="${pageContext.request.contextPath}/account/signout">SignOut</a></li>
                                <%}%>
                            </c:if>
                            <c:if test="${admin ne null}">
                            <li><a href="${pageContext.request.contextPath}/admin?p=dashboard">Admin Manager</a></li>
                            <li><a href="${pageContext.request.contextPath}/account/signout">SignOut</a></li>
                            </c:if>
                    </ul>
                </div>
            </div>
            <div style="background-color: sienna;color: white;margin-bottom: 1%">
                <form style="margin-left: 2%" action="category" method="post">
                    <label>Search: </label>
                    <input style="padding: 5px;margin: 10px; width: 30%" type="text" name="txtSearch" placeholder="Enter to search ..."/>
                    <input style="padding: 5px;margin: 10px" type="submit" value="Search"/>
                </form>           
            </div>
            <div id="content">

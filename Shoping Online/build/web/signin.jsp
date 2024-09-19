
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <link href=".././css/style.css" rel="stylesheet"/>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div id="logo">
                    <a href="${pageContext.request.contextPath}/Home"><img src=".././img/logo.png"/></a>
                </div>
                <div id="banner">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/cart">Cart: 0</a></li>
                            <%
                
                                if (session.getAttribute("AccSession")==null){
                            %>
                        <li><a href="/BaiTap/account/signin">SignIn</a></li>
                        <li><a href="/BaiTap/account/signup">SignUp</a></li>

                        <% }else{%>      

                        <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                        <li><a href="#">SignOut</a></li>
                            <%}%>
                    </ul>
                </div>
            </div>
            <div id="content">

                <div id="form">
                    <div id="form-title">
                        <span><a href="/BaiTap/account/signup">SIGN UP</a></span>
                        <span><a href="/BaiTap/account/signin" style="color: red;">SIGN IN</a></span>
                    </div>
                    <div id="form-content">
                        <form action="/BaiTap/account/signin" method="post">
                            <div style="color:red">
                                <%
                                    if (request.getAttribute("msg") != null){
                                        out.print(request.getAttribute("msg"));
                                    }
                                %>
                            </div>
                            <label>Email<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtEmail"/><br/>
                            <span class="msg-error"><h3>${requestScope.msgEmail}</h3></span>                           
                            <label>Password<span style="color: red;">*</span></label><br/>
                            <input type="password" name="txtPass"/><br/>
                            <span class="msg-error"><h3>${requestScope.msgPass}</h3></span>
                            <div><a href="forgot.jsp">Forgot password?</a></div>
                            <input type="submit" value="SIGN IN"/><br/>
                            <input type="button" value="FACEBOOK LOGIN" style="background-color: #3b5998;"/><br/>
                            <input type="button" value="ZALO LOGIN" style="background-color: #009dff;margin-bottom: 30px;"/>
                        </form>
                    </div>
                </div>



                <%@include file="template/footer.jsp" %>

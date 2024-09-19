<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        ${ok}
                        <li><a href="${pageContext.request.contextPath}/cart">Cart: 0</a></li>
                            <%
                
                                if (session.getAttribute("AccSession")==null){
                            %>
                        <li><a href="/BaiTap/account/signin">SignIn</a></li>
                        <li><a href="/BaiTap/account/signup">SignUp</a></li>

                        <% } else{%>      

                        <li><a href="#">Profile</a></li>
                        <li><a href="/BaiTap/account/signout">SignOut</a></li>
                            <%}%>
                    </ul>
                </div>
            </div>
            <div id="content">
                <div id="form">
                    <div id="form-title">
                        <span><a href="/BaiTap/account/signup" style="color: red;">SIGN UP</a></span>
                        <span><a href="/BaiTap/account/signin">SIGN IN</a></span>
                    </div>
                    <div id="form-content">
                        <form action="/BaiTap/account/signup" method="post">
                            <label>Company name<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtCompanyName"/><br/>
                            <span class="msg-error">${msgcompanyName}</span><br/>
                            <label>Contact name<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtContactName"/><br/>
                            <span class="msg-error">${msgcontactName}</span><br/>
                            <label>Contact title<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtContactTiltle"/><br/>
                            <span class="msg-error">${msgcontactTiltle}</span><br/>
                            <label>Address<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtAddress"/><br/>
                            <span class="msg-error">${msgaddress}</span><br/>
                            <label>Email<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtEmail"/><br/>
                            <span class="msg-error">${msgEmail}</span><br/>
                            <label>Password<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtPass"/><br/>
                            <span class="msg-error">${msgPass}</span><br/>
                            <label>Re-Password<span style="color: red;">*</span></label><br/>
                            <input type="text" name="txtRePass"/><br/>
                            <span class="msg-error" style="color: red;">${msgrePass}</span><br/>
                            <span class="msg-error" style="color: red;">${msgRePassf}</span><br/>
                            <div></div>
                            <input type="submit" value="SIGN UP" style="margin-bottom: 30px;"/>
                        </form>
                    </div>
                </div>



                <%@include file="template/footer.jsp" %>

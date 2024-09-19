<%@include file="template/header.jsp" %>
<div id="content-left">
    <ul>
        <a href="${pageContext.request.contextPath}/admin?p=dashboard"><li>Dashboard</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=orders&page=1"><li>Orders</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=products&page=1"><li style="background-color: sienna;color: white">
                Products</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=customer"><li>Customers</li></a>
    </ul>
</div>
<div id="content-right">
    <div class="path-admin">PRODUCTS LIST</b></div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <div id="product-title-header">
                <form action="admin" method="get" style="display: flex;width: 100%">
                    <div id="product-title-1" style="width: 25%;">
                        <b>Filter by Catetory:</b>

                        <select name="CategoryID" value="${param.CategoryID}">
                            <option value="all">All</option>
                            <c:forEach items="${listcatename}" var="lcn">
                                <option value="${lcn.getCategoryID()}">${lcn.getCategoryName()}</option>
                            </c:forEach>


                        </select>

                    </div>
                    <div id="product-title-2" style="width: 55%;">

                        <input type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Enter product name to search"/>

                    </div>
                    <input style="height: 40%;margin-top: 3.8%" type="submit" value="Search"/>
                </form>
                <div id="product-title-3" style="width: 20%;">
                    <a href="${pageContext.request.contextPath}/admin?p=createproduct">Create a new Product</a>
                    <form action="">
                        <label for="upload-file">Import .xls or .xlsx file</label>
                        <input type="file" name="file" id="upload-file" />
                    </form>
                </div>
            </div>
            <div id="order-table-admin">
                <table id="orders">
                    <tr>
                        <th>ProductID</th>
                        <th>ProductName</th>
                        <th>UnitPrice</th>
                        <th>Unit</th>
                        <th>UnitsInStock</th>
                        <th>Category</th>
                        <th>Discontinued</th>
                        <th></th>
                    </tr>
                    
                    <c:forEach items="${listperpage}" var="lp">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/detail?pid=${lp.getProductID()}">#${lp.getProductID()}</a></td>
                            <td>${lp.getProductName()}</td>
                            <td>${lp.getUnitPrice()}</td>
                            <td>${lp.getQuantityPerUnit()}</td>
                            <td>${lp.getUnitsInStock()}</td>
                            <td>${lp.getCategoryName()}</td>
                            <td>${lp.isDiscontinued()}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin?p=editproduct&pid=${lp.getProductID()}">Edit</a> &nbsp; | &nbsp; 
                                <a href="delete.html?id=5">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div id="paging" >
                <div class="pagination">

                    <c:forEach begin="1" end="${numpage+1}" var="ii">
                        <c:url value="/admin" var="ps">
                            <c:if test="${param.CategoryID==null and param.txtSearch==null}">
                                <c:param name="p" value="products"/>
                            </c:if>
                            <c:param name="page" value="${ii}"/>     
                            <c:if test="${param.CategoryID!=null}"><c:param name="CategoryID" value="${param.CategoryID}"/> </c:if>
                            <c:if test="${param.txtSearch!=null}"><c:param name="txtSearch" value="${param.txtSearch}"/> </c:if>

                        </c:url>
                        <a <c:if test="${page eq ii}"> style="background-color: sienna; color: white;"
                                                       </c:if>href="${ps}">${ii}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="template/footer.jsp" %>

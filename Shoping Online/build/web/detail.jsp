<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<div id="content-detail">
    <div id="content-title">
        <a href="${pageContext.request.contextPath}/Home">Home</a> >
        <a href="${pageContext.request.contextPath}/category?pid=${pro.getCategoryID()}">${pro.getCategoryName()}</a> >
        Model: ${pro.getProductName()}
    </div>
    <c:if test="${pID <= size and pID>0}">
        <div id="product">
            <div id="product-name">
                <h2>${pro.getProductName()}</h2>
                <div id="product-detail">
                    <div id="product-detail-left">
                        <div id="product-img">
                            <img src="img/1.jpg"/>
                        </div>
                        <div id="product-img-items">
                            <div><a href="#"><img src="img/1.jpg"/></a></div>
                            <div><a href="#"><img src="img/1.jpg"/></a></div>
                            <div><a href="#"><img src="img/1.jpg"/></a></div>
                            <div><a href="#"><img src="img/1.jpg"/></a></div>
                        </div>
                    </div>
                    <div id="product-detail-right">
                        <div id="product-detail-right-content">
                            <div id="product-price">${pro.getUnitPrice()} $</div>
                            <div id="product-status">In Stock: ${pro.getUnitsInStock()}</div>
                            <div id="product-detail-buttons">
                                <div id="product-detail-button">
                                    <c:url value="/cart" var="add">
                                        <c:param name="id" value="${pro.getProductID()}"/>
                                        
                                    </c:url>
                                    <c:if test="${pro.isDiscontinued()==true}">
                                        This Product is no longer being sell
                                    </c:if>
                                    <c:if test="${pro.getProductID()<=size and pro.getProductID()>=1 and pro.isDiscontinued()==false}">
                                        <form action="${add}" method="post">

                                            <c:if test="${pro.getUnitsInStock() >0}">
                                                <input type="button" value="BY NOW"/>
                                                <button type="submit" >ADD TO CART</button>
                                            </c:if>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="info-detail">
            <div id="info-detail-title">
                <h2>Information deltail</h2>
                <div style="margin:10px auto;">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Illum, debitis. Asperiores soluta eveniet eos accusantium doloremque cum suscipit ducimus enim at sapiente mollitia consequuntur dicta quaerat, sunt voluptates autem. Quam!
                    Lorem ipsum dolor, sit amet consectetur adipisicing elit. Rem illum autem veritatis maxime corporis quod quibusdam nostrum eaque laborum numquam quos unde eveniet aut, exercitationem voluptatum veniam fugiat, debitis esse?
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio eligendi ratione vitae nobis numquam dolorum assumenda saepe enim cumque blanditiis, deleniti neque voluptate vel ducimus in omnis harum aut nisi.
                </div>
            </div>
        </div>
    </c:if>
</div>



<%@include file="template/footer.jsp" %>

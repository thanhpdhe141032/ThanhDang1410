<%-- 
    Document   : SearchResult
    Created on : Aug 12, 2021, 11:18:39 AM
    Author     : letha
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>E-Commerce</title>
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-light bg-light">
            <a href="/ECommerce" class="btn btn-outline-success my-2 my-sm-0">Home</a>
            <form class="form-inline" action="Search?visit=${visit}" method="post">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="searchStr">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </nav>
        <div class="container-xxl">
            <div class="table-responsive">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6">
                                <h2>Search <b>Product</b></h2>
                            </div>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${searchProduct == null || searchProduct.size() <= 0}">
                            <h3>Not Found!</h3>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Image</th>
                                        <th>Price</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${searchProduct}" var="i">
                                        <tr>
                                            <td>${i.getId()}</td>
                                            <td>${i.getName()}</td>
                                            <td>
                                                <img src="${imagePath}${i.getImage()}" class="img-thumbnail" width="100px">
                                            </td>
                                            <td>${i.getFormattedPrice()}</td>
                                            <td>
                                                <a href="EditProduct?id=${i.getId()}" class="edit"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                                                <a href="DeleteProduct?id=${i.getId()}" class="delete" onclick="return confirm('Are you sure you want to delete this record?')"><i class="material-icons" title="Delete">&#xE872;</i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>

                    <div class="d-flex justify-content-center">
                        <ul class="pagination">
                            <c:if test="${maxPage >= 1}">
                                <c:forEach begin="1" end="${maxPage}" var="i">
                                    <li class="page-item ${i == index? "active" : ""}"><a href="Home?index=${i}&searchStr=${searchStr}" class="page-link">${i}</a></li>
                                    </c:forEach>
                                </c:if>
                        </ul>
                    </div>
                    <div class="clearfix fixed-bottom visited">
                        <div class="hint-text">Visited <b>${visit}</b> time(s)</div>
                    </div>
                </div>
            </div>        
        </div>
    </body>
</html>
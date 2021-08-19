<%-- 
    Document   : Edit
    Created on : Aug 11, 2021, 3:43:01 PM
    Author     : Thanh Dang
--%>

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
        <script src="js/script.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-xl">
            <div class="table-responsive">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6">
                                <h2>Edit <b>Product</b></h2>
                                <div>ID: ${product.getId()}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-content">
                <form action="EditProduct" method="post" enctype="multipart/form-data">
                    <div class="modal-body">
                        <input name="id" type="hidden" class="form-control" value="${product.getId()}">
                        <div class="form-group">
                            <label>Name</label>
                            <input name="name" type="text" class="form-control" value="${product.getName()}" required>
                        </div>
                        <div class="form-group">
                            <label>Image</label>
                            <input name="image" type="file" class="form-control-file">
                        </div>
                        <div class="form-group">
                            <label>Price</label>
                            <input name="price" type="number" class="form-control" value="${product.getPrice()}" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <a href="/ECommerce" class="btn btn-default">Back</a>
                        <input type="submit" class="btn btn-success" value="Save">
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>

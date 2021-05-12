<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@page import="com.Payment"%>
 <%@ page import="java.util.*"%> 
 <%@ page import="java.sql.*"%> 
 <%@ page import="javax.servlet.*"%> 
 <%@ page import="javax.servlet.http.*"%>  
    
  


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payments.js"></script>
</head>
<body> 
<div class="container"><div class="row"><div class="col-6"> 
<h1>Payments Management</h1>

<form id="formPayment" name="formPayment" method="post" action="payments.jsp">
	 User ID: 
	 <input id="userID" name="userID" type="text"  class="form-control form-control-sm">
	 <br> Project ID: 
	 <input id="projectID" name="projectID" type="text"  class="form-control form-control-sm">
	 <br> Method: 
	 <input id="method" name="method" type="text" class="form-control form-control-sm">
	 <br> Amount: 
	 <input id="amount" name="amount" type="text" class="form-control form-control-sm">
	 <br>
	 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
	 <input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
</form>

<br>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>

<div id="divPaymentsGrid">
 <%
 Payment paymentObj = new Payment(); 
 out.print(paymentObj.readPaymnets()); 
 %>
</div>
</div> </div> </div> 
</body>
</html>
package com;

import java.sql.*;

public class Payment {

	private Connection connect() { 
		Connection con = null; 
		try{ 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment?serverTimezone=UTC", "root", "");	
			} catch (Exception e) { 
			e.printStackTrace(); 
			System.out.println(e);
		} 
		return con;
	}
	
	public String readPaymnets(){ 
	 System.out.println("readpayments sout");
		String output = ""; 
	 
		try{ 
			Connection con = connect(); 
			if (con == null){ 
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed
			output = "<table border='1'><tr> <th>Payment ID</th><th>User ID</th><th>Project ID</th>"
					+ "<th>Method</th> <th>Amount</th> <th>Update</th> <th>Remove</th></tr>"; 
			
			String query = "select * from payments"; 
			
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			
			// iterate through the rows in the result set
			while (rs.next()){ 
				String paymentID = Integer.toString(rs.getInt("paymentid")); 
				String userID = Integer.toString(rs.getInt("userid")); 
				String projectID = Integer.toString(rs.getInt("projectid"));
				String method = rs.getString("method"); 
				String amount = Integer.toString(rs.getInt("amount")); 
				
				// Add into the html table
				output += "<tr><td><input id='hidPaymnetIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='" + paymentID
				+ "'>" + paymentID + "</td>"; 
				output += "<td>" + userID + "</td>";
				output += "<td>" + projectID + "</td>"; 
				output += "<td>" + method + "</td>"; 
				output += "<td>" + amount + "</td>"; 
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
				+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid'"+paymentID+"'>" + "</td></tr>"; 
				} 
	 
			con.close(); 
			// Complete the html table
	 
			output += "</table>"; 
	 
		} 
		catch (Exception e) { 
			output = "Error while reading the payments."; 
			System.err.println(e.getMessage()); 
			System.out.println(e);
		} 
		
		return output; 
	 
	} 
	
	
	public String insertPayment(int userID, int projectID, String method, int amount) 
	{ System.out.println("insertpayments sout");
		String output = "";
		try{ 
			Connection con = connect();
			if (con == null){ 
				return "Error while connecting to the database for inserting."; 
			} 
			// create a prepared statement
			String query = " insert into payments (`userid`,`projectid`,`method`,`amount`) values (?, ?, ?, ?)";
			 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			
			preparedStmt.setInt(1, userID); 
			preparedStmt.setInt(2, projectID); 
			preparedStmt.setString(3, method); 
			preparedStmt.setInt(4, amount); 
			
			// execute the statement
			preparedStmt.execute(); 
			
			con.close(); 
			String newPayments = readPaymnets(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newPayments + "\"}"; 

		}catch (Exception e) { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}"; 
			System.err.println(e.getMessage()); 
		} 
		
		return output;
		
	} 
	
	
	
	public String updatePayment(int paymentID, int userID, int projectID, String method, int amount) 
	{
		String output = ""; 
		
		try
		{
			Connection con = connect(); 
			if (con == null) 
			{ 
				return "Error while connecting to the database for updating."; 
			}
			
			// create a prepared statement
			String query = "UPDATE payments SET userid=?,projectid=?,method=?,amount=? WHERE paymentid=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			
			preparedStmt.setInt(1, userID); 
			preparedStmt.setInt(2, projectID); 
			preparedStmt.setString(3, method); 
			preparedStmt.setInt(4, amount); 
			preparedStmt.setInt(5, paymentID);
	 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newPayments = readPaymnets(); 
			output = "{\"status\":\"success\", \"data\": \"" + 
			newPayments + "\"}"; 
			
		} 
		
		catch (Exception e){
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}"; 
			System.err.println(e.getMessage()); 
		} 
			 
		return output;
		
	} 
			
	
	
	public String deletePayment(String paymentID) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from payments where paymentid=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1,Integer.parseInt(paymentID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newPayments = readPaymnets(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newPayments + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 }
}

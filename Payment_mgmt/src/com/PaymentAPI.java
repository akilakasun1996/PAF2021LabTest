package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Payment payment = new Payment();

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = payment.insertPayment(Integer.parseInt(request.getParameter("userID")),
				Integer.parseInt(request.getParameter("projectID")),
				request.getParameter("method"),
				Integer.parseInt(request.getParameter("amount")));
		
		response.getWriter().write(output);
	}
	
	
	private static Map<String, String> getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try 
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
			for(String param : params)
			{
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
			
		} catch (Exception e) {
			
		}
		
		return map;
		
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		String output = payment.updatePayment(Integer.parseInt(paras.get("paymentID").toString()), 
				Integer.parseInt(paras.get("userID").toString()), 
				Integer.parseInt(paras.get("projectID").toString()), 
				paras.get("method").toString(), 
				Integer.parseInt(paras.get("amount").toString()));
		response.getWriter().write(output);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request); 
		String output = payment.deletePayment(paras.get("paymentID").toString()); 
		response.getWriter().write(output);
	}

}

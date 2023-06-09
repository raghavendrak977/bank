package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/CreditAmount")
public class  CreditAmount extends HttpServlet
{
 @Override
protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException 
{
	
	String tamount=request.getParameter("amount");
	double amount=Double.parseDouble(tamount);
	
	Connection connection;
	
	PrintWriter printWriter=responce.getWriter();
	responce.setContentType("text/html");
	
	 HttpSession session2=request.getSession();
	String mobilenumber=(String) session2.getAttribute("mobilenumber");
	String password=(String) session2.getAttribute("password");
	
	 
	  String url="jdbc:mysql://localhost:3307/teca41";
			String user="root";
			String ipassword="12345";
	 
	if(amount>0)
	{
		
        String select="select * from bank where mobilenumber=? and password=? ";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
	     connection=DriverManager.getConnection(url,user,ipassword);	
	     PreparedStatement preparedStatement=connection.prepareStatement(select); // incomplete query
	     preparedStatement.setString(1, mobilenumber);
	     preparedStatement.setString(2, password); 
	     ResultSet resultSet=preparedStatement.executeQuery(); //completed query
	     if (resultSet.next()) 
	    {
	        	    double databaseamount=resultSet.getDouble("amount");
	        	
	        		double remainingbalance=databaseamount+amount;
	        		String update="update bank set amount=? where mobilenumber=?";
	        		 PreparedStatement preparedStatement1=connection.prepareStatement(update);
	        		 preparedStatement1.setDouble(1, remainingbalance);
	        		 preparedStatement1.setString(2, mobilenumber);
	        		 int number=preparedStatement1.executeUpdate();
	        		 if(number!=0)
	        		 {
	        			 String script2 = "<script>alert('amount credited');</script>";
	        			  	printWriter.println(script2);
	        			  	 RequestDispatcher requestDispatcher=request.getRequestDispatcher("Welcomepage.html");
	        				   requestDispatcher.include(request, responce);
	        		 }
	        		 else
	        		 {
	        			 String script2 = "<script>alert('Error 404');</script>";
	        			  	printWriter.println(script2);
	        			  	 RequestDispatcher requestDispatcher=request.getRequestDispatcher("Welcomepage.html");
	        				   requestDispatcher.include(request, responce);
	        		 }
		}
	     else
	     {
	    	 String script2 = "<script>alert('Session Timeout');</script>";
			  	printWriter.println(script2);
	    	 RequestDispatcher requestDispatcher=request.getRequestDispatcher("credit.html");
			   requestDispatcher.include(request, responce);
	     }
	     
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
	}
	else
	{
		 String script2 = "<script>alert('Invalid Amount');</script>";
		  	printWriter.println(script2);
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("Amount.html");
		requestDispatcher.include(request, responce);
		
	}
}
}

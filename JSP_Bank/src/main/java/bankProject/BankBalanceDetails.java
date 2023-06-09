package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/BankBalanceDetails")
public class BankBalanceDetails extends HttpServlet
{
	 protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException 
	 {
	 	
	 	Connection connection;	
	 	PrintWriter printWriter=responce.getWriter();
	 	responce.setContentType("text/html");
	 	
	 	 HttpSession session=request.getSession();
	 	String mobilenumber=(String) session.getAttribute("mobilenumberb");
	 	String password=(String) session.getAttribute("passwordb");
	 	
	 	 
	 	  String url="jdbc:mysql://localhost:3307/teca41";
	 			String user="root";
	 			String ipassword="12345";
	 	 
	 	
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
	            	printWriter.println("<center><h1 style=color:red>"+resultSet.getInt("accountid")+"</h1></center>");
	            	printWriter.println("<center><h1 style=color:red>"+resultSet.getString("accountname")+"</h1></center>");
	            	printWriter.println("<center><h1 style=color:red>"+resultSet.getDouble("amount")+"</h1></center>");
	            	
	            	
//                for (int i = 0; i <= mobilenumber.length(); i++) 
//                {
//                    if (i > 2 && i < 8)
//                    {
//                    	printWriter.print("<center>*</center>");
//  
//	                    } 
//	                    else 
//	                    {
//                    	printWriter.print("<center>"+mobilenumber.charAt(i)+"</center>");
//                      
//                    }
//              }
	 		}
	 	     else
	 	     {
	 	    	String script2 = "<script>alert('Session TimeOut');</script>";
			  	printWriter.println(script2);
	 	    	 RequestDispatcher requestDispatcher=request.getRequestDispatcher("balanceEnquiry.html");
	 			   requestDispatcher.include(request, responce);
	 	     }
	 	     
	 		} 
	 		catch (Exception e)
	 		{
	 			// TODO: handle exception
	 		}
	 		
	 	}
	 	
}

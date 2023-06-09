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
@WebServlet("/PasswordChangedFinal")
public class PasswordChangedFinal extends HttpServlet
{
	 @Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException 
	 {
	 	
	 	String oldpassword=request.getParameter("oldpassword");
	 	String newpassword=request.getParameter("newpassword");
	 	String conformpassword=request.getParameter("conformpassword");
	
	 	
	 	Connection connection;
	 	
	 	PrintWriter printWriter=responce.getWriter();
	 	responce.setContentType("text/html");
	 	
	 	 HttpSession session=request.getSession();
	 	String mobilenumber=(String) session.getAttribute("mobilenumberx");
	 	String password=(String) session.getAttribute("passwordx");
	 	
	 	 
	 	  String url="jdbc:mysql://localhost:3307/teca41";
	 			String user="root";
	 			String ipassword="12345";
	 	 
	//****************************
	 			  String select="select * from bank where mobilenumber=? and password=? ";
	 			    try
	 				{
	 			     connection=DriverManager.getConnection(url,user,ipassword);
	 			     PreparedStatement preparedStatement=connection.prepareStatement(select);
	 			     preparedStatement.setString(1, mobilenumber);
	 			     preparedStatement.setString(2, password);
	 			     ResultSet resultSet=preparedStatement.executeQuery();
	 			     if (resultSet.next()) 
	 			    {		    	
	 			        if(oldpassword.equals(password))
	 			        {
	 			        	    if(newpassword.equals(conformpassword))
	 			        	    {
	 			        		String update="update bank set password=? where mobilenumber=?";
	 			        		 PreparedStatement preparedStatement1=connection.prepareStatement(update);
	 			        		 preparedStatement1.setString(1, conformpassword);
	 			        		 preparedStatement1.setString(2, mobilenumber);
	 			        		 int number=preparedStatement1.executeUpdate();
	 			        		 if(number!=0)
	 			        		 {
	 			        			String script2 = "<script>alert('password set successfully');</script>";
	 			   			     	printWriter.println(script2);
	 			   			    RequestDispatcher requestDispatcher=request.getRequestDispatcher("Welcomepage.html");
	 			 		     	   requestDispatcher.include(request, responce);
	 			        		 }
	 			        		 else
	 			        		 {
	 			        			String script2 = "<script>alert('Error 404');</script>";
	 			   			     	printWriter.println(script2);
	 			 				    RequestDispatcher requestDispatcher=request.getRequestDispatcher("changepassword.html");
	 			 		     	   requestDispatcher.include(request, responce);
	 			        		 }	
	 			        	    }
	 			        	    else
	 			        	    {
	 			        	    	String script2 = "<script>alert('Entered new password doesnot match with conform password');</script>";
	 			   			     	printWriter.println(script2);
	 			        	    	RequestDispatcher requestDispatcher=request.getRequestDispatcher("changepassword.html");
		 			 		     	   requestDispatcher.include(request, responce);
	 			        	    	
	 			        	    }
	 			        }
	 			        else
	 			        {
	 			        	String script2 = "<script>alert('Entered  password doesnot match with old password');</script>";
			   			     	printWriter.println(script2);
			        	    	RequestDispatcher requestDispatcher=request.getRequestDispatcher("changepassword.html");
 			 		     	   requestDispatcher.include(request, responce);
	 			        }
	 				}
	 			     else
	 			     {
	 			    	String script2 = "<script>alert('session timeout');</script>";
		   			     	printWriter.println(script2);
		 				  RequestDispatcher requestDispatcher=request.getRequestDispatcher("changepassword.html");
		 			   requestDispatcher.include(request, responce);
	 			     }
	 				} 
	 				catch (Exception e)
	 				{
	 					
	 				}		
	 			    
	 }
}

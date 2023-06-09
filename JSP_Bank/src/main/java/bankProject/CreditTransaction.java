package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/CreditTransaction")
public class CreditTransaction extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException

{
	
	String mobilenumber=request.getParameter("mb");
	String password=request.getParameter("password");
	  String url="jdbc:mysql://localhost:3307/teca41";
		String user="root";
		String ipassword="12345";
		   HttpSession session=request.getSession();
		   HttpSession session2=request.getSession();
	  
	  String select="select * from bank where  mobilenumber=? and password=?";
	  PrintWriter printwriter=responce.getWriter();
	  
	  try 
	  {
		  Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,ipassword);
		PreparedStatement ps=connection.prepareStatement(select);
		ps.setString(1, mobilenumber);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			String mbn=rs.getString("mobilenumber"); //accessing amount to store in session object
			String pass=rs.getString("password");
			Random random=new Random();
    		int otp=random.nextInt(10000);
    		if(otp<1000)
    		{
    			otp=otp+1000;
    		}
    		// to alert on browser
    		String script = "<script>alert('Your OTP is: " + otp + "'); </script>";
			
			printwriter.println(script);
			session.setAttribute("otp", otp);
			session2.setAttribute("mobilenumber", mbn);
			session2.setAttribute("password", pass);
		
			
			session.setMaxInactiveInterval(20);
			//session.set
			   RequestDispatcher requestDispatcher=request.getRequestDispatcher("Otpbank.html");
			   requestDispatcher.include(request, responce);
			
		}
		else
		{
			String script2 = "<script>alert('data not found');</script>";
		  	printwriter.println(script2);
			
			 RequestDispatcher requestDispatcher=request.getRequestDispatcher("credit.html");
			   requestDispatcher.include(request, responce);
		}
	   } 
	  catch (Exception e)
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}

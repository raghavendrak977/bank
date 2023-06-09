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
@WebServlet("/MobileTransaction")
public class MobileTransaction extends HttpServlet
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
	   	
		
		 String select="select * from bank where  mobilenumber=? and password=?";
		 PrintWriter writer=responce.getWriter();
		 responce.setContentType("text/html");
		 
		 try
		 {
			Class.forName("com.mysql.jdbc.Driver");
			//class not found exception occurs when we dont mention Driver class here
			Connection connection=DriverManager.getConnection(url, user, ipassword);
			//SQL exception occurs when getConnection() method has invalid url
			PreparedStatement ps=connection.prepareStatement(select);
			ps.setString(1, mobilenumber);
			ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
		double senderdatabaseamount=	rs.getDouble("amount");
			session.setAttribute("SenderMobileNumber", mobilenumber);
			session.setAttribute("SenderDatabaseAmount", senderdatabaseamount);
			
			String script2 = "<script>alert('Login success');</script>";
			 writer.println(script2);
			RequestDispatcher rd=request.getRequestDispatcher("mobile.html");
			rd.include(request, responce);
			
			
		}
		else
		{
			RequestDispatcher rd=request.getRequestDispatcher("mobiletransaction.html");
			rd.include(request, responce);
			 String script2 = "<script>alert('Invalid mobilenumber and password');</script>";
			 writer.println(script2);
		}
			
	     	}
		 catch (Exception e) 
		 {
			
		}
}
}

package bankProject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/PasswordChangeOtpValidation")
public class PasswordChangeOtpValidation extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException 
	{
		  HttpSession session=request.getSession();
	      Integer sessionotp=(Integer) session.getAttribute("otpx");  
	 System.out.println(sessionotp);
	  
	  
	  String totp=request.getParameter("otp");
	  int otp=Integer.parseInt(totp);
	  
		PrintWriter printwriter=responce.getWriter();
			responce.setContentType("text/html");
			if(sessionotp !=null)
			{
			if(otp == sessionotp)
			{
				String script2 = "<script>alert('login success');</script>";
			  	printwriter.println(script2);
	
			 RequestDispatcher requestDispatcher=request.getRequestDispatcher("passwordchanged.html");
				 requestDispatcher.include(request, responce);
			}
			else
			{
				String script2 = "<script>alert('invalid otp');</script>";
			  	printwriter.println(script2);
				
				 RequestDispatcher requestDispatcher=request.getRequestDispatcher("passwordOtpValidation.html");
				   requestDispatcher.include(request, responce);
			}
			}
			else
			{
				String script2 = "<script>alert('session time out');</script>";
			  	printwriter.println(script2);
				  RequestDispatcher requestDispatcher=request.getRequestDispatcher("changepassword.html");
			   requestDispatcher.include(request, responce);
			}
	  
	}
	
}

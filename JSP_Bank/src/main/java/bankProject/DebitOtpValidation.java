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
@WebServlet("/DebitOtpValidation")
public class DebitOtpValidation extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException 
	{
		  HttpSession session=request.getSession();
	      Integer sessionotp=(Integer) session.getAttribute("otpdebit");  
	 System.out.println(sessionotp);
	  
	  
	  String totp=request.getParameter("otp");
	  int otp=Integer.parseInt(totp);
	  
		PrintWriter printwriter=responce.getWriter();
			responce.setContentType("text/html");
			if(sessionotp !=null)
			{
			if(otp == sessionotp)
			{
				 printwriter.println("<center><h1 style=color:red>Please Enter the amount </h1></center>");
				
			 RequestDispatcher requestDispatcher=request.getRequestDispatcher("debitAmount.html");
				 requestDispatcher.include(request, responce);
			}
			else
			{
				 String script2 = "<script>alert('Invalid OTP');</script>";
				  	printwriter.println(script2);
				 RequestDispatcher requestDispatcher=request.getRequestDispatcher("debitOtp.html");
				   requestDispatcher.include(request, responce);
			}
			}
			else
			{
				 String script2 = "<script>alert('Session TimeOut');</script>";
				  	printwriter.println(script2);
				  RequestDispatcher requestDispatcher=request.getRequestDispatcher("Withdraw.html");
			   requestDispatcher.include(request, responce);
			}
	  
	}
}

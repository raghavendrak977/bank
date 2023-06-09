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
@WebServlet("/BalanceEnquiryOtpValidation")
public class BalanceEnquiryOtpValidation extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException 
	{
		  HttpSession session=request.getSession();
	      Integer sessionotp=(Integer) session.getAttribute("otpb");  
	 System.out.println(sessionotp);
	  
	  
	  String totp=request.getParameter("otp");
	  int otp=Integer.parseInt(totp);
	  
		PrintWriter printwriter=responce.getWriter();
			responce.setContentType("text/html");
			if(sessionotp !=null)
			{
			if(otp == sessionotp)
			{
				String script2 = "<script>alert('Otp validation success');</script>";
			  	printwriter.println(script2);
			   RequestDispatcher requestDispatcher=request.getRequestDispatcher("bankbalancedetails.html");
				 requestDispatcher.include(request, responce);
			}
			else
			{
				String script2 = "<script>alert('Invalid Otp');</script>";
			  	printwriter.println(script2);
				 RequestDispatcher requestDispatcher=request.getRequestDispatcher("balanceEnquiry.html");
				   requestDispatcher.include(request, responce);
			}
			}
			else
			{
				String script2 = "<script>alert('Session TimeOut');</script>";
			  	printwriter.println(script2);
				  RequestDispatcher requestDispatcher=request.getRequestDispatcher("balanceEnquiry.html");
			   requestDispatcher.include(request, responce);
			}
	  
	}
}

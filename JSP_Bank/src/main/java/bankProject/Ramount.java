package bankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Ramount")
public class Ramount extends HttpServlet
{
 @Override
protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
	
	 String tsendingAmount=request.getParameter("senderAmount");
	 double sendingAmount=Double.parseDouble(tsendingAmount);
	 
	 HttpSession session=request.getSession();
		Double senderdatabaseamount=(Double) session.getAttribute("SenderDatabaseAmount");
		Double receiverdatabaseamount=(Double) session.getAttribute("ReceiverDatabaseAmount");
		String receivermobilenumber=(String) session.getAttribute("ReceiverMobileNumber");
		String sendermobilenumber=(String) session.getAttribute("SenderMobileNumber");
		
		
		 String url="jdbc:mysql://localhost:3307/teca41";
		    String user="root";
		   	String ipassword="12345";
			
			 String updateSender="update bank  set amount=? where mobilenumber=?";
			 String updateReceiver="update bank  set amount=? where mobilenumber=?";
			 PrintWriter writer=responce.getWriter();
			 responce.setContentType("text/html");
			 
			 if(sendingAmount>0)
			 {
				 if(senderdatabaseamount>=sendingAmount)
				 {
					 double sub=senderdatabaseamount-sendingAmount;
					 double add=receiverdatabaseamount+sendingAmount;
					 
					 try 
					 {
						 Class.forName("com.mysql.jdbc.Driver");
						Connection connection=DriverManager.getConnection(url, user, ipassword);
						PreparedStatement ps=connection.prepareStatement(updateSender);
						ps.setDouble(1, sub);
						ps.setString(2,sendermobilenumber);
						int num1=ps.executeUpdate();
						if(num1!=0)
						{
							PreparedStatement ps2=connection.prepareStatement(updateReceiver);
							ps2.setDouble(1, add);
							ps2.setString(2,receivermobilenumber);
							int num2=ps2.executeUpdate();
							if(num2!=0)
							{
								String script2 = "<script>alert('TransactionSuccessfull');</script>";							writer.println(script2);
								 RequestDispatcher rd=request.getRequestDispatcher("Welcomepage.html");
									rd.include(request, responce);
							}	
							else
							{
								String script2 = "<script>alert('Error 404');</script>";
						     	writer.println(script2);
						     	 RequestDispatcher rd=request.getRequestDispatcher("sendingAmount.html");
									rd.include(request, responce);
						     	
							}
							
//							String script2 = "<script>alert('TransactionSuccessfull');</script>";
//							writer.println(script2);
						}
						else
						{
							String script2 = "<script>alert('Error 404');</script>";
					     	writer.println(script2);
					     	 RequestDispatcher rd=request.getRequestDispatcher("sendingAmount.html");
								rd.include(request, responce);
						}

					} 
					 catch (Exception e)
					 {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				 else
				 {
					 RequestDispatcher rd=request.getRequestDispatcher("sendingAmount.html");
						rd.include(request, responce);
						String script2 = "<script>alert('insufficient amount');</script>";
						 writer.println(script2); 
				 }
			 }
			 else
			 {
				 RequestDispatcher rd=request.getRequestDispatcher("sendingAmount.html");
					rd.include(request, responce);
					String script2 = "<script>alert('Invalid amount');</script>";
					 writer.println(script2);
			 }
}
}

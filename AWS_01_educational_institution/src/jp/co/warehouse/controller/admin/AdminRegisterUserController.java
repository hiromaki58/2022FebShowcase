package jp.co.warehouse.controller.admin;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.admin.AdminSetUserInfoDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.mail.SendMail;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.security.Token;

/*
 * registering the new user and send the invitation e-mail
 */
@WebServlet("/admin/register_user")
public class AdminRegisterUserController extends HttpServlet {

	private static final long serialVersionUID = 5696309707150262446L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin login = null;
		//Set the session data
		HttpSession session = request.getSession();
		//"action" determines whether asking just show the page or register the new user
		String action = request.getParameter("action");
		login = (Admin) session.getAttribute("admin");
		
		//Move to the new user registration page
		if (login != null && action == null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/register_user.jsp").forward(request, response);
		}
		/**
		 * Coming back from confirmation page
		 *if register confirmation is done and ready for registration the new user. 
		 */
		else if (login != null && action.equals("done")) {
			/*
			 * Check the token originally made at the first new user input page and
			 * the token it is duplicated at the registration confirmation page are same
			 * 
			 * cross site counter measure
			 */
			String originalTokenID = session.getAttribute("originalTokenID").toString();
			
			//The token for confirmation page
			String checkedTokenId = request.getParameter("checkedTokenid");
			
			//Check the token matching
			if(originalTokenID.equals(checkedTokenId)) {
				AdminRegisterUser adminRegisteruser = (AdminRegisterUser) session.getAttribute("adminRegisteruser");
	
				try {
					//Register the new user into the database
			        AdminSetUserInfoDAO adminSetUserInfoDao = new AdminSetUserInfoDAO();
			        adminSetUserInfoDao.addUserByAdmin(adminRegisteruser);
	
			        //Get the info which is needed to send the email
			        String lastName = adminRegisteruser.getUser_last_name();
			        String firstName = adminRegisteruser.getUser_first_name();
			        String mailAddress = adminRegisteruser.getUser_mail();
			        //E-mail title
			        String subject = "Registration confirmation mail";
			        //The email content is below
			        String content = "Dear " + firstName + " " + lastName + "\r\n"
			        					+ "\r\n"
			        					+ "Thank you for your interest in our website!\r\n"
			        					+ "\r\n"
	
			        					+ "Please set the password to login\r\n"
			        					+ "with the URL below.\r\n"
			        					+ "\r\n"
	
			        					+ "Password setting URL : https://aws-warehouse58th.com/login/set"
			        					+ "\r\n"
			        					+ "\r\n"
	
										+ "============================ \r\n"
										+ "Warehouse Association\r\n"
										+ "100-8111\r\n"
										+ "1-1 Chiyoda Chiyoda-ward Tokyo Japan\r\n"
										+ "Tel: 1-800-000-0000\r\n"
										+ "https://aws-warehouse58th.com/index\r\n"
			        					+ "============================ \r\n";
			        SendMail sendMail = new SendMail();
			        //Send the invitation mail to the new user
			        sendMail.sendMail(mailAddress, subject, content);
	
			        //Show the message of E-mail sending completion
			        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/register_user_complete.jsp");
			  	  	dispatcher.forward(request, response);
			  	}
			  	catch (DatabaseException e) {
			  	  e.printStackTrace();
			  	  session.setAttribute("Except", e);
	
			  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/db_access_error.jsp").forward( request, response);
			  	}
			    catch (SystemException e) {
					e.printStackTrace();
				    session.setAttribute("Except", e);
	
				    getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/sending_mail_error.jsp").forward( request, response);
				}
			}
			//If the token ID doesn't match up
			else {
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/unvalid_access.jsp").forward( request, response);
			}
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    String user_last_name = null;
	    String user_first_name = null;
	    String user_mail = null;
	    
	    try {
	    	user_last_name = request.getParameter("user_last_name");
		    user_first_name = request.getParameter("user_first_name");
		    user_mail = request.getParameter("user_mail");
	      }
	      catch (NumberFormatException e) {
	        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

	        pe.printStackTrace();
	        HttpSession session = request.getSession();
	        session.setAttribute("Except", pe);

	        getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/error.jsp").forward(request, response);
	        return;
	      }

	    //Create the user's instance
	    AdminRegisterUser adminRegisteruser = new AdminRegisterUser();
	    adminRegisteruser.setUser_first_name(user_first_name);
	    adminRegisteruser.setUser_last_name(user_last_name);
	    adminRegisteruser.setUser_mail(user_mail);

	    HttpSession session = request.getSession();
	    session.setAttribute("adminRegisteruser", adminRegisteruser);

		//Create the token for the cross site counter measure
    	Token tokenGenerator = new Token();
    	String tokenID = tokenGenerator.tokenCreater();
    	
	    //Generate the token and collect into the tokenID
	  	session.setAttribute("originalTokenID", tokenID);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/register_user_confirm.jsp");
  	  	dispatcher.forward(request, response);
	  }
}
package jp.co.warehouse.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.dao.user.UserLoginDAO;
import jp.co.warehouse.dao.utility.SecurityDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.mail.SendMail;
import jp.co.warehouse.security.Token;

/**
 * This class is for asking the email when the user doesn't remember the password,
 *  he needs to rest it.
 *
 *  Just ask the e-mail address here.
 *  The e-mail is the proof to check the person who try to change the password is genuine.
 *
 *  if he is genuine he will receive the code, and the code will be asked to access to the password reissue page.
 * @author hirog
 *
 */
@WebServlet("/login/dismissed_reset")
public class DismissedPasswordResetController extends HttpServlet {

	private static final long serialVersionUID = 764444246791141465L;
	private boolean mailInfoBean;

	/**
	 * Ask to input the e-mail address and check the person who try to is already we know
	 * ${inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/dismissed_reset.jsp");
  	  	dispatcher.forward(request, response);
	}

	/**
	 * ${inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		//Receive the email which is written in the input form
	    request.setCharacterEncoding("UTF-8");
	    String dismissed_mail = request.getParameter("dismissed_mail");

	    //Check the email does exists in the DB
        UserLoginDAO userLoginDao = new UserLoginDAO();
        try {
        	mailInfoBean = userLoginDao.checkEmail(dismissed_mail);
        }
        catch (jp.co.warehouse.exception.SystemException | DatabaseException e) {
  			e.printStackTrace();
        }
        /*
         * If the mail address is found, the code will be sent to the E-mail address
         */
        if(mailInfoBean) {
        	//Register the code into the DB
        	SecurityDAO securityDao = new SecurityDAO();
        	//Create the code
        	Token tokenGenerator = new Token();
        	String token = tokenGenerator.tokenCreater();

        	//Have the name who will receive the code
        	AdminRegisterUser adminRegisterUser = null;
			try {
				UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
				adminRegisterUser = userGetUserInfoDao.getRegisteredUserInfo(dismissed_mail);
			} catch (DatabaseException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
        	String lastName = adminRegisterUser.getUser_last_name();
	        String firstName = adminRegisterUser.getUser_first_name();

	        try {
	        	securityDao.addTokenDB(token, dismissed_mail);

	        	//E-mail title
		        String subject = "Code for password resetting";
		        //Content of the email is below
		        String content = "Dear " + firstName + " " + lastName + "\r\n"
		        					+ "\r\n"
		        					+ "This is the code is required\r\n"
		        					+ "to reset the password.\r\n"
		        					+ "\r\n"

		        					+ "Please set the password to login\r\n"
		        					+ "with the URL below.\r\n"

		        					+ "Code: " + token + "\r\n"
		        					+ "\r\n"

		        					+ "Password re-issue URL : https://aws-warehouse58th.com/login/code_check"
		        					+ "\r\n"
		        					+ "\r\n"
		        					
									+ "============================ \r\n"
									+ "Warehouse Association\r\n"
									+ "100-8111\r\n"
									+ "1-1 Chiyoda Chiyoda-ward Tokyo Japan\r\n"
									+ "Tel: 1-800-000-0000\r\n"
									+ "Email: warehouse58th@example.com\r\n"
									+ "https://aws-warehouse58th.com/index\r\n"
									+ "============================ \r\n";
		        /*
		         * Send the e-mail with the code to check the email receiver is the person who try to modify
		         * the password
		         */
		        SendMail sendMail = new SendMail();
		        sendMail.sendMail(dismissed_mail, subject, content);

		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/code_sent.jsp");
		  	  	dispatcher.forward(request, response);
	        }
	        catch (DatabaseException | SystemException e) {
	  	  	  e.printStackTrace();
	  	  	  HttpSession session = request.getSession();
	  	  	  session.setAttribute("Except", e);

	  	  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/code_issue_fail.jsp").forward( request, response);
	  	  	}
        }
        else {
        	//If not registered, the error message "The e-mail is not registered" will be shown.
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/no_email_registered.jsp");
	  	  	dispatcher.forward(request, response);
        }
	}
}
package jp.co.warehouse.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.UserLoginDAO;
import jp.co.warehouse.entity.Mail;
import jp.co.warehouse.exception.DatabaseException;

/**
 * This controller is for checking the email which is registered in the DB.
 * If so, the user is genuine and can proceed to set the initial password.
 * 
 * @author hirog
 */
@WebServlet("/login/set")
public class PasswordSetController extends HttpServlet{
	private static final long serialVersionUID = 3463090321628603846L;
	private boolean mailInfoBean;

	/**
	 * Show the email checking page
	 * 
	 *  @author	H.Maki
	 *  @throws	ServletException is if there is no jsp file is assigned.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/set.jsp");
  	  	dispatcher.forward(request, response);
	}

	/**
	 * Check the email address and 
	 * move on to the page.
	 * 
	 *  @author	H.Maki
	 *  @throws	ServletException is if there is no jsp file is assigned.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		//Receive the email which is written in the input
	    request.setCharacterEncoding("UTF-8");
	    String mail = request.getParameter("mail");
	    
	    // Create the instance to hold the mail information
	    Mail mailAddress = new Mail(mail);

	    //Check the email does exists in the DB
	    try {
	    	UserLoginDAO userLoginDao = new UserLoginDAO();
	        try {
	        	mailInfoBean = userLoginDao.checkEmail(mail);
	        }
	        catch (jp.co.warehouse.exception.SystemException e) {
	  			e.printStackTrace();
	        }
	        
	        /*
	         * If the mail addresss is found, 
	         * the user moves on to the first password set up page.
	         */
	        if(mailInfoBean) {
		        HttpSession session = request.getSession();
		  	  	session.setAttribute("mailAddress", mailAddress);

		  	  	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue.jsp");
		  	  	dispatcher.forward(request, response);
	        }
	        else {
	        	//If not registered, the error message "The e-mail is not registered" will be shown.
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/no_email_registered.jsp");
		  	  	dispatcher.forward(request, response);
	        }
	  	}
	  	catch (DatabaseException e) {
	  	  e.printStackTrace();
	  	  HttpSession session = request.getSession();
	  	  session.setAttribute("Except", e);

	  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/login/reissue_error.jsp").forward( request, response);
	  	}
	}
}

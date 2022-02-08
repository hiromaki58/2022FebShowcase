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
import jp.co.warehouse.security.Hash;

/**
 * This class is used for setting up the initial password to login by the users.
 * After email existing check at PasswordSetController class is passed, 
 * the user can move on to this function.
 * 
 * @author hirog
 */
@WebServlet("/login/issue")
public class PasswordIssueController extends HttpServlet {

	private static final long serialVersionUID = -3539967758374999921L;

	/**
	 * Just show the page.
	 * 
	 * @author	H.Maki
	 * @throws	ServletException, if there is no jsp file is assigned.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue.jsp");
  	  	dispatcher.forward(request, response);
	}

	/**
	 * Get the new password and another passoword to make sure the password is fine.
	 * 
	 * @author	H.Maki
	 * @throws	DatabaseException, if DB connection is fail.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    Hash hash = new Hash();
	    
	    //Get the new password and password which is used to confirm these password is not miss typed.
	    String password_new = request.getParameter("password_new");
	    String password_confirm = request.getParameter("password_confirm");
	    
	    //The password is encrypted by the hash function
	    String encryptedPassword_new = hash.hashGenerator(password_new);

	    //Receive the email info which is confirmed to be registered in the DB.
	    HttpSession session = request.getSession();
	    Mail mailAddress = (Mail) session.getAttribute("mailAddress");

	    //If the new password is not miss typed.
	    if(password_new.equals(password_confirm) && !(password_new.isEmpty())) {
	    	try {
	    		UserLoginDAO userLoginDao = new UserLoginDAO();
		        try {
		        	userLoginDao.addPasswordByUser(mailAddress, encryptedPassword_new);
			      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_done.jsp");
			  	  	dispatcher.forward(request, response);
		        }
		        catch (jp.co.warehouse.exception.SystemException e) {
		  			e.printStackTrace();
		  			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_error.jsp");
			  	  	dispatcher.forward(request, response);
		        }
		  	}
		  	catch (DatabaseException e) {
		  		e.printStackTrace();
			  	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_error.jsp");
		  	  	dispatcher.forward(request, response);
		  	}
	    }
	    /*
	     * If the password and the password which is input as the confirming the password don't match
	     * which means miss typing is happened.
	     * So, ask input the new password again.
	     */
	    else {
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/password_not_same.jsp");
	  	  	dispatcher.forward(request, response);
	    }
	  }
}
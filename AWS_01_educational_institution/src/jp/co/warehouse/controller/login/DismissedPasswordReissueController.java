package jp.co.warehouse.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.UserSetUserInfoDAO;
import jp.co.warehouse.dao.utility.SecurityDAO;
import jp.co.warehouse.entity.Token;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.security.Hash;

/*
 * If the token is confirmed, the user can move on to the password modification page
 */
@WebServlet("/login/dismissed_reissue")
public class DismissedPasswordReissueController extends HttpServlet {

	private static final long serialVersionUID = -8167301926335587282L;

	//No access to the dismissed password re-issue page if there is no token information is held.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Token receivedToken = null;

		//Try to have the login information from the session.
		HttpSession session = request.getSession();
		if((Token) session.getAttribute("tokenInfo") != null) {
			receivedToken = (Token) session.getAttribute("tokenInfo");
		}

		//If there is token session information, the password reissue page will show up.
		if (receivedToken != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/dismissed_reissue.jsp");
	  	  	dispatcher.forward(request, response);
		}
		else {
			//In case of the session doesn't exit.
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/no_access.jsp");
	  	  	dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialize the session
		Token tokenInfo = null;

	    request.setCharacterEncoding("UTF-8");
	    //Get the new password and password which is used to confirm these password is not miss typed.
	    String dismissed_password_new = request.getParameter("dismissed_password_new");
	    String dismissed_password_confirm = request.getParameter("dismissed_password_confirm");

	    //Initialize the parts what will be used.
	    String mail ="";
	    Hash hash = new Hash();

	    //Receive the token info which is allowing to re-issue the password
	    HttpSession session = request.getSession();
	    tokenInfo = (Token) session.getAttribute("tokenInfo");

	    /*
	     * Due to all of passwords are stored by the hashed info,
	     * it is required all info is needed to convert by hash function.
	     */
	    String encryptedPassword_new = hash.hashGenerator(dismissed_password_new);

	    if(tokenInfo != null) {
	    	try {
	    		SecurityDAO securityDao = new SecurityDAO();
	    		mail = securityDao.getEmailByToken(tokenInfo.getToken());
	        }
	        catch (jp.co.warehouse.exception.SystemException | DatabaseException e) {
	        	getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
	  			e.printStackTrace();
	        }
	    }
	    else {
	    	getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
	    }

	    /*
	     *If the new password is not miss typed, the new password will be stored into the DB
	     */
	    if(dismissed_password_new.equals(dismissed_password_confirm) && !(dismissed_password_new.isEmpty()) && tokenInfo != null) {
			try {
				UserSetUserInfoDAO userSetUserInfoDao = new UserSetUserInfoDAO();
				userSetUserInfoDao.updatePasswordByUser(mail, encryptedPassword_new);
		      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_done.jsp");
		  	  	dispatcher.forward(request, response);
		    }
		    catch (jp.co.warehouse.exception.SystemException | DatabaseException e) {
				e.printStackTrace();
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_error.jsp");
		  	  	dispatcher.forward(request, response);
		    }
	    }
	    /*
	     * If the password and the password which is input as the confirming the password don't match
	     * which means miss typing is happened. So, ask input the new password again.
	     */
	    else {
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/password_not_same.jsp");
	  	  	dispatcher.forward(request, response);
	    }
	}
}
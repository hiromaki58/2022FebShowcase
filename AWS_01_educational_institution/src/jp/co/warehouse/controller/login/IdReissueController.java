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
import jp.co.warehouse.dao.user.UserSetUserInfoDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.security.Hash;

/*
 * This controller provides the ID-reissue function.
 */
@WebServlet("/login/id_reissue")
public class IdReissueController extends HttpServlet {

	private static final long serialVersionUID = -4236910480466937412L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;
		User userLogin = null;

		//Try to have one of login information from the session.
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		else if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		//If admin or user is login the password reissue page will show up.
		if (adminLogin != null || userLogin != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/id_reissue.jsp");
	  	  	dispatcher.forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		//Initialize the session
		User userLogin = null;

	    request.setCharacterEncoding("UTF-8");
	    //Get the new password and password which is used to confirm these password is not miss typed.
	    String id_old = request.getParameter("id_old");
	    String id_new = request.getParameter("id_new");
	    String id_confirm = request.getParameter("id_confirm");

	    //The information is used for identifying which E-mail address will be modified
	    int registeredUserId = 0;
	    int selfRegisteredUserId = 0;
	    
	    /*
	     * Due to all of passwords are stored by the hashed info,
	     * it is required all info is needed to convert by hash function.
	     */
	    String encryptedPassword = "";
	    
	    //Receive the email info which is confirmed to be registered in the DB.
	    HttpSession session = request.getSession();
	    userLogin = (User) session.getAttribute("user");

	    if(userLogin != null) {
	    	/*
	    	 * Get the encrypted password to identify the mail address in the selfRegisteredUser_login table
	    	 */
	    	Hash hash = new Hash();
	    	encryptedPassword = hash.hashGenerator(userLogin.getPassword());

	    	//Get the ID from the both of registered_user and selfRegisteredUser table
	    	try {
	    		UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
	    		registeredUserId = userGetUserInfoDao.getRegisteredUserIdByUserMail(userLogin.getEmail());
	    		selfRegisteredUserId = userGetUserInfoDao.getSelfRegisteredUserIdByRegisteredUserId(registeredUserId);
	    	} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
	    }
	    else {
	    	// No login action is done yet
	    	getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
	    }

	    /*
	     * If the E-mail address is not typo and matched the new one with the confirm e-mail address,
	     * the user is able to update the e-mail address
	     */
	    if(id_new.equals(id_confirm) && !(id_new.isEmpty()) && userLogin != null) {
	        try {
	        	UserLoginDAO userLoginDao = new UserLoginDAO();
	        	
	        	//Check the old ID(E-mail address) is fine 
				if(userLoginDao.checkMatchOldIdByUser(encryptedPassword, id_old)) {
					//If the old ID(E-mail address) is right, the user is considered as a genuine user. So, he can change the E-mail.
					try {
						UserSetUserInfoDAO userSetUserInfoDao = new UserSetUserInfoDAO();
						
						//Update selfRegisteredUser_login table
						userLoginDao.updateIdByUser(encryptedPassword, id_new);
				    	//Update registered_user table
						userSetUserInfoDao.updateRegisteredInstrutorMailById(registeredUserId, id_new);
				    	//Update selfRegisteredUser table
						userSetUserInfoDao.updateSelfRegisteredUserMailById(selfRegisteredUserId, id_new);

				    	//Show the mail address changing process is completed
				      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/id_reissue_done.jsp");
				  	  	dispatcher.forward(request, response);
				    }
				    catch (jp.co.warehouse.exception.SystemException e) {
						e.printStackTrace();
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/id_reissue_error.jsp");
				  	  	dispatcher.forward(request, response);
				    }
				}
				/*
				 *If old mail address doesn't match the mail address which is registered in DB,
				 *the man who is trying to change the mail address should not be able to do that.
				 */
				else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/reissue_error_old_email.jsp");
				  	dispatcher.forward(request, response);
				}
			} catch (DatabaseException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
	    }
	    /*
	     * If the mail address and the address which is input as the confirming don't match
	     * which means typo. So, ask input the new E-mail again.
	     */
	    else {
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/email_not_same.jsp");
	  	  	dispatcher.forward(request, response);
	    }
	}
}
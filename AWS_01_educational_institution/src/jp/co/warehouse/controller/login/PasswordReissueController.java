package jp.co.warehouse.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.admin.AdminLoginDAO;
import jp.co.warehouse.dao.user.UserLoginDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.Mail;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.security.Hash;

/*
 * This class is used for modification the password by the user.
 */
@WebServlet("/login/reissue")
public class PasswordReissueController extends HttpServlet {

	private static final long serialVersionUID = -3539967758374999921L;

	//As long as login by the user account, the page is shown.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLoginInfo = null;
		User userLoginInfo = null;
		Mail mailAddress = null;

		//Try to have one of login information from the session.
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLoginInfo = (Admin) session.getAttribute("admin");
		}
		else if((User) session.getAttribute("user") != null) {
			userLoginInfo = (User) session.getAttribute("user");
		}
		else if((Mail) session.getAttribute("mailAddress") != null) {
			mailAddress = (Mail) session.getAttribute("mailAddress");
		}

		//If admin or user is login the password reissue page will show up.
		if (adminLoginInfo != null || userLoginInfo != null || mailAddress != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/reissue.jsp");
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
		Admin adminLoginInfo = null;
		User userLoginInfo = null;
		Mail mailAddress = null;

	    request.setCharacterEncoding("UTF-8");
	    //Get the new password and password which is used to confirm these password is not miss typed.
	    String password_old = request.getParameter("password_old");
	    String password_new = request.getParameter("password_new");
	    String password_confirm = request.getParameter("password_confirm");
	    String mail ="";
	    
	    AdminLoginDAO adminLoginDao = null;
	    UserLoginDAO userLoginDao = null;
	    Hash hash = new Hash();
	    //Receive the email info which is confirmed to be registered in the DB.
	    HttpSession session = request.getSession();
	    adminLoginInfo = (Admin) session.getAttribute("admin");
	    userLoginInfo = (User) session.getAttribute("user");
	    mailAddress = (Mail) session.getAttribute("mailAddress");

	    /*
	     * Due to all of passwords are stored by the hashed info,
	     * it is required all info is needed to convert by hash function.
	     */
	    String encryptedPassword_old = hash.hashGenerator(password_old);
	    String encryptedPassword_new = hash.hashGenerator(password_new);
	    String encryptedId_admin = "";

	    if(userLoginInfo != null) {
	    	mail = userLoginInfo.getEmail();
	    	userLoginDao = new UserLoginDAO();
	    }
	    else if(adminLoginInfo != null) {
	    	mail = adminLoginInfo.getUserid();
	    	encryptedId_admin = hash.hashGenerator(mail);
	    	adminLoginDao = new AdminLoginDAO();
	    }
	    else if(mailAddress != null) {
	    	mail = mailAddress.getMail();
	    }

	    /*
	     *If the new password is not miss typed.
	     *This part is for the user wishes to change the password.
	     */
	    if(password_new.equals(password_confirm) && !(password_new.isEmpty()) && userLoginInfo != null) {
	        try {
				if(userLoginDao.checkMatchOldPassword(mail, encryptedPassword_old)) {
					//If the old password is right, the user is considered as a genuine user. So, he can change the password.
					try {
						userLoginDao.updatePassword(mail, encryptedPassword_new);
				      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_done.jsp");
				  	  	dispatcher.forward(request, response);
				    }
				    catch (SystemException e) {
						e.printStackTrace();
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_error.jsp");
				  	  	dispatcher.forward(request, response);
				    }
				}
				/*
				 *If old password doesn't match the password which is registered in DB,
				 *the man who is trying to change the password should not be able to do that.
				 */
				else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/reissue_error_old_password.jsp");
				  	dispatcher.forward(request, response);
				}
			} catch (DatabaseException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
	    }
	    /*
	     * This part is controlling if the admin account wishes to change the password.
	     */
	    else if(password_new.equals(password_confirm) && !(password_new.isEmpty()) && adminLoginInfo != null) {
	    	try {
				if(adminLoginDao.checkMatchOldPassword(encryptedId_admin, encryptedPassword_old)) {
					//If the old password is right, the user is considered as a genuine user. So, he can change the password.
					try {
						adminLoginDao.updatePassword(encryptedId_admin, encryptedPassword_new);
				      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/admin_issue_done.jsp");
				  	  	dispatcher.forward(request, response);
				    }
				    catch (jp.co.warehouse.exception.SystemException e) {
						e.printStackTrace();
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/issue_error.jsp");
				  	  	dispatcher.forward(request, response);
				    }
				}
				/*
				 *If old password doesn't match the password which is registered in DB,
				 *the man who is trying to change the password should not be able to do that.
				 */
				else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/reissue_error_old_password.jsp");
				  	dispatcher.forward(request, response);
				}
			} catch (DatabaseException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
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
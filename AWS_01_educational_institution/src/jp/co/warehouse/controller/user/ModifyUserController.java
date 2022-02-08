package jp.co.warehouse.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.co.warehouse.DaoInterface.RegisterInfoInterface;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.dao.user.UserSetUserInfoDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * Changing the user information
 * @author hirog
 *
 */
@WebServlet("/user/modification_01")
public class ModifyUserController extends HttpServlet {

	private static final long serialVersionUID = 3303147476052087286L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		User userLogin = null;

		//Create the instances which are needed in this class.
		AdminRegisterUser retrieveRegisterUserInfo = new AdminRegisterUser();
		UserRegisterUser retrieveSelfRegisteredUserInfo = new UserRegisterUser();
		UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();

		//Check successfully login
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		/**
		 * Session is already set
		 * Login with user account 
		 */
		if (userLogin != null) {
			//Get the E-mail address which is used to login
			String mailAddress = userLogin.getEmail();

			try {
				//Get the needed data from the database
				retrieveRegisterUserInfo = userGetUserInfoDao.getRegisteredUserInfo(mailAddress);
				retrieveSelfRegisteredUserInfo = userGetUserInfoDao.getSelfRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e1) {
				e1.printStackTrace();
			}
			//Get the E-mail address which is registered in the database.
			String userEmailFromDB = retrieveRegisterUserInfo.getUser_mail();

			/*
			 * This if sentence set the restriction to access the user detailed info.
			 * If the E-mail which is registered in DB and the E-mail which is in the login info session are identical,
			 * user_info page is available.
			 */
			if(mailAddress.equals(userEmailFromDB)) {
				//Set the retrieve data into the session and show the user info modification page
				session.setAttribute("retrieveRegisterUserInfo", retrieveRegisterUserInfo);
				session.setAttribute("retrieveSelfRegisteredUserInfo", retrieveSelfRegisteredUserInfo);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/modification_01.jsp").forward(request, response);
			}
			else {
				/*
				 * The email doesn't match, so "No access" message will be shown.
				 */
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_access.jsp").forward(request, response);
			}
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	/**
	 * Receive the information it might be changed from the user info modification page
	 * ${inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		User userLogin = null;

		HttpSession session = request.getSession();
		//Get the user info from the session which is created when the user logins.
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

	    request.setCharacterEncoding("UTF-8");
	    String mailAddress = userLogin.getEmail();
	    String gender_profile = null;

	    String user_phone = null;
	    String user_website = null;
	    String open_mail = null;
	    String profile = null;
	    String profile_cl = null;

	    //Content will be changed by the user
	    try {
	    	gender_profile = request.getParameter("gender_profile");
		    user_phone = request.getParameter("user_phone");
		    user_website = request.getParameter("user_website");
		    open_mail = request.getParameter("open_mail");
		    profile = request.getParameter("profile");
		    //Keep the carriage return even the profile is stored in the database.
		    if (profile != null) {
		    	//profile_cl = profile.replaceAll("<br/>", "").replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll(",", "&nbsp;");
		    	profile_cl = profile.replaceAll("\\;", "&#059;").replaceAll("<br/>", "").replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll("\\'", "&#039;").replaceAll("\"", "&#082;").replaceAll("\\(", "&#040;").replaceAll("\\)", "&#041;").replaceAll("\\:", "&#058;").replaceAll("\\'", "&#039;");
			}
	      }
        catch (NumberFormatException e) {
          ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

          pe.printStackTrace();
          session.setAttribute("Except", pe);

          getServletContext().getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
          return;
        }

	    //Create the instance to keep the new user information
	    UserRegisterUser userModifyOnlyUser = new UserRegisterUser();
	    userModifyOnlyUser.setGender_profile(gender_profile);
	    userModifyOnlyUser.setEmail(mailAddress);
	    userModifyOnlyUser.setPhone(user_phone);
	    userModifyOnlyUser.setWeb_site(user_website);
	    userModifyOnlyUser.setOpenMail(open_mail);
	    userModifyOnlyUser.setProfile(profile_cl);
	    session.setAttribute("userModifyOnlyUser", userModifyOnlyUser);
	    
	    //Update the DB
        try {
        	RegisterInfoInterface rii = new UserSetUserInfoDAO();
        	rii.updateUserInfo(userModifyOnlyUser, mailAddress);
		} catch (DatabaseException | SystemException e) {
			e.printStackTrace();
		}

  	  	response.sendRedirect("https://aws-warehouse58th.com/user/user_info");
  	  	session.removeAttribute("userModifyOnlyUser");
	  	session.removeAttribute("retrieveRegisterUserInfo");
	  	session.removeAttribute("retrieveSelfRegisteredUserInfo");
	  }
}
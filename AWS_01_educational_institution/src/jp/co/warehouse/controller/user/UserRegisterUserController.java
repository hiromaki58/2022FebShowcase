package jp.co.warehouse.controller.user;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.DaoInterface.RegisterInfoInterface;
import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.dao.user.UserSetUserInfoDAO;
import jp.co.warehouse.parameter.ExceptionParameters;

/*
 * This class is used for the new user register his info
 */
@WebServlet("/user/register_user_01")
public class UserRegisterUserController extends HttpServlet {

	private static final long serialVersionUID = -1749937546598828067L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session info
		User login = null;

		//Holding session
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			login = (User) session.getAttribute("user");
		}
		//"action" determines whether asking just show the page or register the new user
		String action = request.getParameter("action");

		//1, Just show the page
		if (login != null && action == null) {
			String mailAddress = login.getEmail();
			AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
			AdminRegisterUser adminRegisterUserForNew = new AdminRegisterUser();

			try {
				adminRegisterUserForNew = adminGetUserInfoDao.getRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e1) {
				e1.printStackTrace();
			}

			session.setAttribute("adminRegisterUserForNew", adminRegisterUserForNew);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/register_user_01.jsp").forward(request, response);
		}
		//2,Showing the page with the info what user wish to register the DB.
		else if (login != null && action.equals("done")) {
			UserRegisterUser userRegisterUser = (UserRegisterUser) session.getAttribute("userRegisterUser");

			try {
		        RegisterInfoInterface rii = new UserSetUserInfoDAO();
		        //Because the email is already registered at regsitered_user table, the method is "updated".
		        rii.updateUserInfo(userRegisterUser, login.getEmail());

		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/register_user_complete_01.jsp");
		  	  	dispatcher.forward(request, response);

		  	  	session.removeAttribute("userRegisterUser");
		  	}
		  	catch (DatabaseException e) {
		  	  e.printStackTrace();
		  	  session.setAttribute("Except", e);

		  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/register_user_error.jsp").forward( request, response);
		  	}
		    catch (SystemException e) {
				e.printStackTrace();
			    session.setAttribute("Except", e);

			    getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/register_user_error.jsp").forward( request, response);
			}
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialize the session info
		User login = null;

		//Set the session data
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			login = (User) session.getAttribute("user");
		}
		String mailAddress = login.getEmail();
	    request.setCharacterEncoding("UTF-8");
	    
	    String gender_profile = null;
	    String user_phone = null;
	    String user_website = null;
	    String open_mail = null;
	    String profile = null;
	    String profile_cl = null;

	    try {
		    gender_profile = request.getParameter("gender_profile");
		    user_phone = request.getParameter("user_phone");
		    user_website = request.getParameter("user_website");
		    open_mail = request.getParameter("open_mail");
		    profile = request.getParameter("profile");
		    
		    if (profile != null) {
		    	profile_cl = profile.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll(",", "&nbsp;").replaceAll("\\\'", "\\\\\\\'");
			}
	      }
	      catch (NumberFormatException e) {
	        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

	        pe.printStackTrace();
	        session.setAttribute("Except", pe);

	        getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/error.jsp").forward(request, response);
	        return;
	      }

	    //Create the userRegisterUser instance
	    UserRegisterUser userRegisterUser = new UserRegisterUser();
	    userRegisterUser.setEmail(mailAddress);
	    userRegisterUser.setGender_profile(gender_profile);

	    userRegisterUser.setPhone(user_phone);
	    userRegisterUser.setWeb_site(user_website);
	    userRegisterUser.setOpenMail(open_mail);
	    userRegisterUser.setProfile(profile_cl);
	    session.setAttribute("userRegisterUser", userRegisterUser);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/register_user_confirm_01.jsp");
  	  	dispatcher.forward(request, response);
	  }
}
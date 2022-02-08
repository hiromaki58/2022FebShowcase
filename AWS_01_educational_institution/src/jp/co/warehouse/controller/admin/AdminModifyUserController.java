package jp.co.warehouse.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.DaoInterface.RegisterInfoInterface;
import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.admin.AdminSetUserInfoDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * 
 * This class is used for change the user information by the administration authorization.
 * @author H.Maki
 *
 */
@WebServlet("/admin/modification_user_01")
public class AdminModifyUserController extends HttpServlet {

	private static final long serialVersionUID = -1189551187835006424L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session information
		Admin adminLogin = null;
		AdminRegisterUser retrieveRegisterUserInfo = new AdminRegisterUser();
		UserRegisterUser retrieveSelfRegisteredUserInfo = new UserRegisterUser();

		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		//Session is already set
		//Login with admin account
		if (adminLogin != null) {
			String stringUserId = request.getParameter("userId");
			int intUserId = Integer.parseInt(stringUserId);

			try {
				AdminGetUserInfoDAO adminGetUserInfoDAO = new AdminGetUserInfoDAO();
				UserGetUserInfoDAO getUserInfoDao = new UserGetUserInfoDAO();
				retrieveRegisterUserInfo = adminGetUserInfoDAO.getUserByAdminWithUserId(intUserId);
				retrieveSelfRegisteredUserInfo = getUserInfoDao.getSelfRegisteredUserInfo(retrieveRegisterUserInfo.getUser_mail());
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			session.setAttribute("retrieveRegisterUserInfo", retrieveRegisterUserInfo);
			session.setAttribute("retrieveSelfRegisteredUserInfo", retrieveSelfRegisteredUserInfo);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/modification_01.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}

	/**
	 * ${inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialize the session information
		Admin adminLogin = null;

		//Set the session info
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		AdminRegisterUser retrieveAdminRegisteruserInfo = new AdminRegisterUser();
		retrieveAdminRegisteruserInfo = (AdminRegisterUser) session.getAttribute("retrieveRegisterUserInfo");
		String email = retrieveAdminRegisteruserInfo.getUser_mail();

	    request.setCharacterEncoding("UTF-8");

	    String user_last_name = null;
	    String user_first_name = null;

	    String gender_profile = null;
	    String user_phone = null;
	    String user_website = null;
	    String open_mail = null;
	    String profile = null;

	    //Able to change everything even the user's name
	    if(adminLogin != null) {
	    	try {
	    		user_last_name = request.getParameter("user_last_name");
			    user_first_name = request.getParameter("user_first_name");
			    gender_profile = request.getParameter("gender_profile");
			    user_phone = request.getParameter("user_phone");
			    user_website = request.getParameter("user_website");
			    open_mail = request.getParameter("open_mail");
			    profile = request.getParameter("profile");
		      }
		      catch (NumberFormatException e) {
		        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

		        pe.printStackTrace();
		        session.setAttribute("Except", pe);

		        getServletContext().getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
		        return;
		      }

	    	//Create the bean only admin can modify
	    	AdminRegisterUser adminModifyuser = new AdminRegisterUser();
	    	adminModifyuser.setUser_first_name(user_first_name);
	    	adminModifyuser.setUser_last_name(user_last_name);
	    	adminModifyuser.setUser_mail(email);

		    //Create the userRegisterUser instance
		    UserRegisterUser userModifyuser = new UserRegisterUser();

		    userModifyuser.setGender_profile(gender_profile);
		    userModifyuser.setPhone(user_phone);
		    userModifyuser.setWeb_site(user_website);
		    userModifyuser.setOpenMail(open_mail);
		    userModifyuser.setProfile(profile);

	        try {
	        	AdminSetUserInfoDAO adminSetUserInfoDAO = new AdminSetUserInfoDAO();
	        	adminSetUserInfoDAO.updateUserByAdmin(adminModifyuser, email);
	        	RegisterInfoInterface rii = new UserGetUserInfoDAO();
	        	rii.updateUserInfo(userModifyuser, email);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
	        response.sendRedirect("https://aws-warehouse58th.com/user/user_info");
	    }
	}
}
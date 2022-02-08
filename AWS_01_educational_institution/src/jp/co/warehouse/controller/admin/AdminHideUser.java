package jp.co.warehouse.controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.utility.MatchDAO;
import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.admin.AdminSetUserInfoDAO;
import jp.co.warehouse.dao.article.AdminSetArticleDAO;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This class hides the user from the public page.
 * @author hirog
 * Sep 18, 2021
 *
 */
@WebServlet("/admin/suspension")
public class AdminHideUser extends HttpServlet {

	private static final long serialVersionUID = 8032339176753653320L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the login information
		Admin adminLogin = null;
		int intId = 0;

		//Check login is done by the administration account
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		//Get the article id from the get method
		if(request.getParameter("userId") != null) {
			String stringId = request.getParameter("userId");
			intId = Integer.parseInt(stringId);
		}
		else {

		}

		//"execution" parameter tells the user is testified to make sure to delete the article
		String release_or_not = request.getParameter("user_released");

		if (adminLogin != null && release_or_not == null) {
			/*
			 * Try to have the target user information
			 */
			AdminRegisterUser registeredUserInfo = new AdminRegisterUser();
			AdminGetUserInfoDAO adminGetUserInfoDAO = new AdminGetUserInfoDAO();
			UserRegisterUser userRegisterUser = new UserRegisterUser();
			UserGetUserInfoDAO getUserInfoDao = new UserGetUserInfoDAO();

			try {
				//Get user info by the e-mail
				registeredUserInfo = adminGetUserInfoDAO.getUserByAdminWithUserId(intId);
				userRegisterUser = getUserInfoDao.getSelfRegisteredUserInfo(registeredUserInfo.getUser_mail());
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			/*
			 * Send the information which is needed to show the page
			 */
			session.setAttribute("registeredUserInfo", registeredUserInfo);
			session.setAttribute("userRegisterUser", userRegisterUser);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/suspension.jsp").forward(request, response);
		}
		/*
		 * If the administration account selects release or not,
		 * the selected choice will be registered into the DB
		 */
		else if (adminLogin != null && release_or_not != null) {

			//Get the user's information
			AdminRegisterUser registeredUserInfo = (AdminRegisterUser) session.getAttribute("registeredUserInfo");

			//Make the list of the articles what user creates
			ArrayList<Integer> articleIdList = new ArrayList<Integer>();

			//Create the data access object;
			AdminSetUserInfoDAO adminSetUserInfoDao = new AdminSetUserInfoDAO(); 
			MatchDAO matchDao = new MatchDAO();

			try {
				//Add administaration's choice into the DB
				adminSetUserInfoDao.addUserReleasedByAdmin(release_or_not, registeredUserInfo.getUser_mail());
//				adminSetUserInfoDao.addUserReleasedByAdmin(release_or_not, registeredUserInfo.getSelfRegisteredUserId());
				adminSetUserInfoDao.updateUserLoginByAdmin(release_or_not, registeredUserInfo.getUser_mail());

				//Find out the articles which are registered by the target user
				articleIdList = matchDao.getArticleIdByUserId(registeredUserInfo.getRegisteredUserId());

				/*
				 *If the administration account chooses not release his information,
				 *his articles also won't be in public
				 */
				for(int i = 0; i < articleIdList.size(); i++) {
					int intArticleId = articleIdList.get(i);
					try {
						//Change the status of the article
						AdminSetArticleDAO adminSetArticleDao = new AdminSetArticleDAO();
						adminSetArticleDao.adminModifyArticleRelease(intArticleId, release_or_not);
					} catch (DatabaseException e) {
						e.printStackTrace();
					} catch (SystemException e) {
						e.printStackTrace();
					}
				}
				//Show the completion message of setting the release information
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/suspension_complete.jsp");
		  	  	dispatcher.forward(request, response);
			}
			catch (NumberFormatException e) {
				ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

				pe.printStackTrace();
				session = request.getSession();
				session.setAttribute("Except", pe);

				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
			} catch (DatabaseException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		else {
			// If there is no session is created
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}
}
package jp.co.warehouse.controller.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.dao.user.UserSetUserInfoDAO;
import jp.co.warehouse.dao.utility.MatchDAO;
import jp.co.warehouse.dao.article.UserSetArticleDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * Servlet implementation class MypageController
 */
@WebServlet("/user/user_release")
public class UserReleaseController extends HttpServlet {

	private static final long serialVersionUID = 5786819525164958017L;

	public UserReleaseController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session information
		User userLogin = null;

		//Set the session data
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		/*
		 * Get the e-mail address to identify who wishes to change the release information
		 */
		String email = userLogin.getEmail();
		String release_or_not = request.getParameter("user_released");
		UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO(); 

		//Session is already set
		if (userLogin != null && release_or_not == null) {
			String mailAddress = userLogin.getEmail();
			UserRegisterUser selfRegisteredUserInfo = new UserRegisterUser();

			try {
				//Get user info by the e-mail
				selfRegisteredUserInfo = userGetUserInfoDao.getSelfRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("selfRegisteredUserInfo", selfRegisteredUserInfo);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/user_release.jsp");
	  	  	dispatcher.forward(request, response);
		}
		/*
		 * If the user chooses he wish to release his info or not,
		 * the selected choice will be registered into the DB
		 */
		else if (userLogin != null && release_or_not != null) {
			AdminRegisterUser registeredUserInfo = new AdminRegisterUser();
			ArrayList<Integer> articleIdList = new ArrayList<Integer>();
			MatchDAO matchDao = new MatchDAO();

			try {
				//Add user's choice into the DB
				UserSetUserInfoDAO userSetUserInfoDAO = new UserSetUserInfoDAO(); 
				userSetUserInfoDAO.addUserReleasedByUser(release_or_not, email);
				registeredUserInfo = userGetUserInfoDao.getRegisteredUserInfo(email);

				//Find out the articles which are registered by the user
				articleIdList = matchDao.getArticleIdByUserId(registeredUserInfo.getRegisteredUserId());

				/*
				 *If the user chooses not release his information,
				 *his articles also won't be in public
				 */
				for(int i = 0; i < articleIdList.size(); i++) {
					int intArticleId = articleIdList.get(i);
					try {
						//Change the status of the article
						UserSetArticleDAO userSetArticleDao = new UserSetArticleDAO();
						userSetArticleDao.userModifyArticleRelease(intArticleId, release_or_not);
					} catch (DatabaseException e) {
						e.printStackTrace();
					} catch (SystemException e) {
						e.printStackTrace();
					}
				}
				//Show the completion message of setting the release information
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/user_release_complete.jsp");
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
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialize the session information
		User userLogin = null;
	    request.setCharacterEncoding("UTF-8");

	    //Set the session info
	    HttpSession session = request.getSession();
		userLogin = (User) session.getAttribute("user");
		String email = userLogin.getEmail();
	    String release_or_not = request.getParameter("user_released");

		try {
			UserSetUserInfoDAO userSetUserInfoDAO = new UserSetUserInfoDAO();

			//Set the release information into the DB
			userSetUserInfoDAO.addUserReleasedByUser(release_or_not, email);
			//Show the completion message of setting the release information
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/user_release_complete.jsp");
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
}

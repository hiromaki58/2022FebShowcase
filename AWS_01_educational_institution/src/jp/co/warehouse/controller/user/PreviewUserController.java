package jp.co.warehouse.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.article.PublicGetArticleDAO;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This class is handling user preview function.
 * 1, If login with user account, only the user which is created by same user is shown.
 *
 */
@WebServlet("/previewuser")
public class PreviewUserController extends HttpServlet {

	private static final long serialVersionUID = 9014403661446835450L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the login information
		User userLogin = null;
		//This array is used to show 3 seminars at the bottom of the page
		ArticleInfoArray threeArticleInfoSelectedByUserId = new ArticleInfoArray();

		//Check login is done or not either admin or user
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		//Login is done yet
		if (userLogin != null) {
			//Due to the login is succeeded, there should be the session which has an E-mail address.
			String mailAddress = userLogin.getEmail();
			UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
			PublicGetArticleDAO publicArticleDao = new PublicGetArticleDAO();
			AdminRegisterUser registeredUserInfo = new AdminRegisterUser();
			UserRegisterUser selfRegisteredUserInfo = new UserRegisterUser();

			try {
				/*
				 *Get the E-mail address from the login info session, and bring the info from DB
				 *with usinng the E-mail as a key.
				 */
				registeredUserInfo = userGetUserInfoDao.getRegisteredUserInfo(mailAddress);
				selfRegisteredUserInfo = userGetUserInfoDao.getSelfRegisteredUserInfo(mailAddress);
				
				/*
				 * Get the 3 articles which are written by the user
				 * with the user id.
				 */
				int selfRegisteredUserId = registeredUserInfo.getSelfRegisteredUserId();
				threeArticleInfoSelectedByUserId = publicArticleDao.getThreeArticleInfoArraySelectedByUser(selfRegisteredUserId);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			String userEmailFromDB = registeredUserInfo.getUser_mail();

			/*
			 * This if sentence set the restriction to access the user detailed info.
			 * If the E-mail which is registered in DB and the E-mail which is in the login info session are identical,
			 * user_info page is available.
			 */
			if(mailAddress.equals(userEmailFromDB)) {
				session.setAttribute("registeredUserInfoAtPublicUserDetail", registeredUserInfo);
				session.setAttribute("selfRegisteredUserInfoAtPublicUserDetail", selfRegisteredUserInfo);
				session.setAttribute("threeArticleInfoSelectedByUserId", threeArticleInfoSelectedByUserId);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/public/public_user_detail.jsp").forward(request, response);
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
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/no_session_user.jsp").forward( request, response);
		}
	}
}
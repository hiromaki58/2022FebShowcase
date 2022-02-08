package jp.co.warehouse.controller.article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.dao.utility.MatchDAO;
import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.article.AdminGetArticleDAO;
import jp.co.warehouse.dao.article.UserGetArticleDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This class is handling article preview function.
 * 1, If login with user account, only the article which is created by same user is shown.
 *
 */
@WebServlet("/previewarticle")
public class PreviewArticleController extends HttpServlet {

	private static final long serialVersionUID = 6042001064592147013L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the login information
		Admin adminLogin = null;
		User userLogin = null;

		//Check login is done or not either admin or user
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}
		else if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		//Initialize the user id
		int userId = 0;
		Article articleInfo = new Article();
		AdminRegisterUser registeredUserForArticle = new AdminRegisterUser();
		UserRegisterUser userRegisterUserForArticle = new UserRegisterUser();
		MatchDAO matchDao = new MatchDAO();

		/*
		 * Get the article id from the get method.
		 * The preview button in the article information page is holding the number in the <a> tag.
		 */
		String stringArticleId = request.getParameter("articleId");
		int intArticleId = Integer.parseInt(stringArticleId);

		if (adminLogin != null) {
			try {
				//Comparison the date and present time to determine the article should be in public or not
				AdminGetArticleDAO adminGetArticleDao = new AdminGetArticleDAO();
				articleInfo = adminGetArticleDao.getArticleInfoByArticleId(intArticleId);
				userId = matchDao.getUserIdByArticleId(intArticleId);
				
				AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
				registeredUserForArticle = adminGetUserInfoDao.getUserByAdminWithUserId(userId);
				userRegisterUserForArticle = adminGetUserInfoDao.getSelfRegisteredUserInfoById(registeredUserForArticle.getSelfRegisteredUserId());
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("articleInfo", articleInfo);
			session.setAttribute("registeredUserForArticle", registeredUserForArticle);
			session.setAttribute("userRegisterUserForArticle", userRegisterUserForArticle);
			if(session.getAttribute("articleInfo") == null) {
				//It is not in the time period when the article is in public.
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/out_of_article_period.jsp").forward(request, response);
			}
			else {
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/public/article.jsp").forward(request, response);
			}
		}
		else if (userLogin != null) {
			try {
				UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
				registeredUserForArticle = userGetUserInfoDao.getRegisteredUserInfo(userLogin.getEmail());
				
				//Comparison the date and present time to determine the article should be in public or not
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				articleInfo = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
				userId = matchDao.getUserIdByArticleId(intArticleId);
				userRegisterUserForArticle = userGetUserInfoDao.getSelfRegisteredUserInfoById(registeredUserForArticle.getSelfRegisteredUserId());
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			/*
			 *Compare login email holder name with registered user name in the article and
			 *determine the user can watch the article info or not.
			 */
			StringBuilder sbi = new StringBuilder();
			String userName ="";
			String userNameFromDB = "";
			sbi.append(registeredUserForArticle.getUser_last_name());
			sbi.append(" ");
			sbi.append(registeredUserForArticle.getUser_first_name());
			userName = sbi.toString();
			userNameFromDB = articleInfo.getUser().toString();

			//The user name and the name of the viewer should be same.
			if(userName.equals(userNameFromDB)) {
				session.setAttribute("articleInfo", articleInfo);
				session.setAttribute("registeredUserForArticle", registeredUserForArticle);
				session.setAttribute("userRegisterUserForArticle", userRegisterUserForArticle);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/public/article.jsp").forward(request, response);
			}
			else {
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/no_access.jsp").forward(request, response);
			}
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session.jsp").forward( request, response);
		}
	}
}

package jp.co.warehouse.controller.article;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.article.AdminGetArticleDAO;
import jp.co.warehouse.dao.article.UserGetArticleDAO;
import jp.co.warehouse.date.CompareDate;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This controller is gathering the information of the article and show them.
 */
@WebServlet("/article/article_info")
public class ArticleInfoController extends HttpServlet {

	private static final long serialVersionUID = -6730766329226541984L;

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

		Article articleInfo = new Article();
		CompareDate compare = new CompareDate();

		/*
		 *Login is done yet and registeredArticle is already existed
		 *registeredArticle is used for passing the article id due to article info controller needs to know which article will show up
		 *Post method is passing the article id to the article modification controller.
		 *
		 *The "registeredArticle" is created in the article modification page and return back to this info page.
		 */
		if (adminLogin != null && (Article) session.getAttribute("registeredArticle") != null) {
			articleInfo = (Article) session.getAttribute("registeredArticle");
			boolean checkReleasePeriod = false;

			try {
				//Comparison the date and now to determine the article should be in public or not
				AdminGetArticleDAO adminGetArticleDao = new AdminGetArticleDAO();
				articleInfo = adminGetArticleDao.getArticleInfoByArticleId(articleInfo.getArticleId());
				Date openingDay = articleInfo.getOpening_day();
				Date closingDay = articleInfo.getClosing_day();
				checkReleasePeriod = compare.checkPublicity(openingDay, closingDay);
				if(checkReleasePeriod) {
					articleInfo.setReleased("yes");
				}
				else {
					articleInfo.setReleased("no");
				}
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("articleInfo", articleInfo);
			session.removeAttribute("registeredArticle");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/article_info.jsp").forward(request, response);
		}
		//if login with admin account
		else if (adminLogin != null) {
			String stringArticleId = request.getParameter("articleId");
			int articleId = Integer.parseInt(stringArticleId);
			boolean checkReleasePeriod = false;

			try {
				//Comparison the date and present time to determine the article should be in public or not
				AdminGetArticleDAO adminGetArticleDao = new AdminGetArticleDAO();
				articleInfo = adminGetArticleDao.getArticleInfoByArticleId(articleId);
				Date openingDay = articleInfo.getOpening_day();
				Date closingDay = articleInfo.getClosing_day();
				checkReleasePeriod = compare.checkPublicity(openingDay, closingDay);
				if(checkReleasePeriod) {
					articleInfo.setReleased("yes");
				}
				else {
					articleInfo.setReleased("no");
				}
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("articleInfo", articleInfo);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/article_info.jsp").forward(request, response);
		}
		//if user just wanna see
		else if (userLogin != null) {
			String email = userLogin.getEmail();
			int articleId = 0;

			/*
			 * The parameter is comming from the get method
			 * when the single article page is called. 
			 */
			String stringArticleId = request.getParameter("articleId");
			if(stringArticleId != null) {
				//If the page is accessed from mypage.
				articleId = Integer.parseInt(stringArticleId);
			}
			else {
				//If the page is opened after the modification is done.
				Article articleReturnInfo = (Article) session.getAttribute("registeredArticle");
				articleId = articleReturnInfo.getArticleId();
			}

			AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
			AdminRegisterUser userInfo = new AdminRegisterUser();
			boolean checkReleasePeriod = false;

			try {
				userInfo = adminGetUserInfoDao.getRegisteredUserInfo(email);
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				articleInfo = userGetArticleDao.getArticleInfoByAritcleId(articleId);

				//Comparison the date and now to determine the article should be in public or not
				Date openingDay = articleInfo.getOpening_day();
				Date closingDay = articleInfo.getClosing_day();
				checkReleasePeriod = compare.checkPublicity(openingDay, closingDay);
				if(checkReleasePeriod) {
					articleInfo.setReleased("yes");
				}
				else {
					articleInfo.setReleased("no");
				}
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
			sbi.append(userInfo.getUser_last_name());
			sbi.append(" ");
			sbi.append(userInfo.getUser_first_name());
			userName = sbi.toString();
			userNameFromDB = articleInfo.getUser().toString();

			/*
			 * This part is working as like a passport control.
			 * It means if the user who login's name doesn't match the user name of the article,
			 * he or she cannot take a look the article information
			 */
			if(userName.equals(userNameFromDB)) {
				session.setAttribute("articleInfo", articleInfo);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/article_info.jsp").forward(request, response);
			}
			else {
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_access.jsp").forward(request, response);
			}
		}
		else {
			// If no login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/no_session_admin.jsp").forward( request, response);
		}
	}
}
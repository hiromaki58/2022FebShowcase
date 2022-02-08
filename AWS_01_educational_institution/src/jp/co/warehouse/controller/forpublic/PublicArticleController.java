package jp.co.warehouse.controller.forpublic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.dao.utility.MatchDAO;
import jp.co.warehouse.dao.article.PublicGetArticleDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This class is used for showing the article detail.
 */
@WebServlet("/article")
public class PublicArticleController extends HttpServlet {

	private static final long serialVersionUID = 264493449805826262L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//Check login is done or not either admin or user
		HttpSession session = request.getSession();
		int userId = 0;
		int intId = 0;
		Article articleInfo = new Article();
		AdminRegisterUser registeredUserForArticle = new AdminRegisterUser();
		UserRegisterUser userRegisterUserForArticle = new UserRegisterUser();

		if(request.getParameter("articleId") != null) {
			String stringId = request.getParameter("articleId");
			intId = Integer.parseInt(stringId);

			try {
				//Comparison the date and present time to determine the article should be in public or not
				PublicGetArticleDAO publicGetArticleDAO = new PublicGetArticleDAO();
				articleInfo = publicGetArticleDAO.getArticleInfoByPublic(intId);
				
				//Find the user who registers the article from the userregisteruser_article_match table.
				MatchDAO matchDao = new MatchDAO();
				userId = matchDao.getUserIdByArticleId(intId);
				
				//Have the user information from regsitered_user table
				UserGetUserInfoDAO userGetUserInfoDAO = new UserGetUserInfoDAO();
				registeredUserForArticle = userGetUserInfoDAO.getUserByUserWithUserId(userId);
				//Have the user information from userregisteruser table
				userRegisterUserForArticle = userGetUserInfoDAO.getSelfRegisteredUserInfoById(registeredUserForArticle.getSelfRegisteredUserId());
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			//Set all of data which is received from above SQL sentences.
			session.setAttribute("articleInfo", articleInfo);
			session.setAttribute("registeredUserForArticle", registeredUserForArticle);
			session.setAttribute("userRegisterUserForArticle", userRegisterUserForArticle);

			/*
			 * If no article data is returned from DB which means there is no data or,
			 * the article is not between the period of which is in public.
			 */
			if(session.getAttribute("articleInfo") == null) {
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/out_of_article_period.jsp").forward(request, response);
			}
			//Indication of the message which is there is no such a article.
			else if(articleInfo.getOpening_day() == null) {
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_article.jsp").forward(request, response);
			}
			//Show the article information
			else {
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/public/article.jsp").forward(request, response);
			}
		}
		else {
			response.sendRedirect("https://aws-warehouse58th.com/index");
		}
	}
}
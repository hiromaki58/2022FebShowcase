package jp.co.warehouse.controller.article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.article.AdminSetArticleDAO;
import jp.co.warehouse.dao.article.UserSetArticleDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 *This class provides the function to delete the article.
 */
@WebServlet("/delete")
public class DeleteArticleController extends HttpServlet {
	private static final long serialVersionUID = -693819938882105092L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the login information
		Admin adminLogin = null;
		User userLogin = null;
		Article articleInfo = null;
		int intArticleId = 0;

		//Check login is done or not either admin or user
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}
		else if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		else if((Article) session.getAttribute("articleInfo") != null) {
			articleInfo = (Article) session.getAttribute("articleInfo");
		}

		//"execution" parameter tells the user is testified to make sure to delete the article
		String execution = request.getParameter("execution");

		//Get the article id from the get method
		if(request.getParameter("articleId") != null) {
			String stringId = request.getParameter("articleId");
			intArticleId = Integer.parseInt(stringId);
		}
		else {
			intArticleId = articleInfo.getArticleId();
		}

		if (adminLogin != null && execution == null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/asking_delete.jsp").forward(request, response);
		}
		else if (userLogin != null && execution == null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/asking_delete.jsp").forward(request, response);
		}
		else if (adminLogin != null && execution.equals("go")) {
			try {
				AdminSetArticleDAO adminSetArticleDao = new AdminSetArticleDAO();
				adminSetArticleDao.deleteArticleById(intArticleId);
				
				session.removeAttribute("articleInfo");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/delete_complete.jsp").forward(request, response);
			}
			catch(DatabaseException | SystemException e) {
				e.printStackTrace();
				session.setAttribute("Except", e);

				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/delete_article_error.jsp").forward( request, response);
			}
		}
		else if (userLogin != null && execution.equals("go")) {
			try {
				UserSetArticleDAO userSetArticleDao = new UserSetArticleDAO();
				userSetArticleDao.deleteArticleById(intArticleId);
				
				session.removeAttribute("articleInfo");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/delete_complete.jsp").forward(request, response);
			}
			catch(DatabaseException | SystemException e) {
				e.printStackTrace();
				session.setAttribute("Except", e);

				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/delete_article_error.jsp").forward( request, response);
			}
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/no_session.jsp").forward( request, response);
		}
	}
}

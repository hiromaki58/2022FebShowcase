package jp.co.warehouse.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.article.AdminSetArticleDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/**
 *This class provides the function to delete the article. 
 * @author hirog
 * Sep 18, 2021
 *
 */
@WebServlet("/hide")
public class AdminHideArticle extends HttpServlet {

	private static final long serialVersionUID = -693819938882105092L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the login information
		Admin adminLogin = null;
		int intArticleId = 0;

		//Check login is done or not either admin or user
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		//"execution" parameter tells the user is testified to make sure to delete the article
		String execution = request.getParameter("execution");

		//Get the article id from the get method
		if(request.getParameter("articleId") == null) {
			
		}
		else {
			String stringArticleId = request.getParameter("articleId");
			intArticleId = Integer.parseInt(stringArticleId);
		}

		if (adminLogin != null && execution == null) {
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
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}
}
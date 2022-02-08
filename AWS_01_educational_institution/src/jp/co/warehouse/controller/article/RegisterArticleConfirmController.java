package jp.co.warehouse.controller.article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.User;

/*
 * Show the pages to confirm the article information is fine.
 */
@WebServlet("/article/confirm")
public class RegisterArticleConfirmController extends HttpServlet {

	private static final long serialVersionUID = 4174308385886915092L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;
		User userLogin = null;

		//Set the session
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		if (adminLogin != null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/register_confirm.jsp").forward(request, response);
		}
		else if (userLogin != null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/register_confirm.jsp").forward(request, response);
		}
	}
}
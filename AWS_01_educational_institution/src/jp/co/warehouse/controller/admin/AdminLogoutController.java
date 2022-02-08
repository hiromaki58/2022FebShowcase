package jp.co.warehouse.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Used for admin account logout function
 */
@WebServlet("/admin/logout")
public class AdminLogoutController extends HttpServlet {

	private static final long serialVersionUID = -6182089914905561113L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//Destroy the session info
		HttpSession session = request.getSession();
		session.removeAttribute("admin");
		if (session != null){
			session.invalidate();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/logout.jsp");
  	  	dispatcher.forward(request, response);
	}
}

package jp.co.warehouse.controller.login;

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
 * This controller confirms the intention of re-setting the ID.
 */
@WebServlet("/login/id_reset")
public class IdResetController extends HttpServlet {

	private static final long serialVersionUID = 926385964337331981L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;
		User userLogin = null;

		//Set the login session information
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		//Make sure the login process is completed.
		if (adminLogin != null || userLogin != null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/login/id_reset.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			response.sendRedirect("https://aws-warehouse58th.com/user/login");
		}
	}
}
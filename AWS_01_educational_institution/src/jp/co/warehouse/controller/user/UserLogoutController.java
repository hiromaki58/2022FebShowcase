package jp.co.warehouse.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class is for the users to logout.
 * @author hirog
 * Sep 14, 2021
 * 
 */
@WebServlet("/user/logout")
public class UserLogoutController extends HttpServlet {
	private static final long serialVersionUID = 2534644840308379547L;

	/**
	 * Show the logout page
	 * @author hirog
	 * Sep 14, 2021
	 *
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Remove the login information session
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		if (session != null){
			session.invalidate();
		}
		//Show the logout message
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/logout.jsp");
  	  	dispatcher.forward(request, response);
	}
}

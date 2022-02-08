package jp.co.warehouse.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.entity.Admin;

/*
 * Show the pages to confirm the article information is fine.
 */
@WebServlet("/admin/register_mv_confirm")
public class AdminRegisterMvConfirmController extends HttpServlet {

	private static final long serialVersionUID = -8963551932586588908L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;

		//Set the session
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		if (adminLogin != null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/register_mv_confirm.jsp").forward(request, response);
		}
	}
}
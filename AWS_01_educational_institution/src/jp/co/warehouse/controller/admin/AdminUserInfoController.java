package jp.co.warehouse.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

@WebServlet("/admin/user_info")
public class AdminUserInfoController extends HttpServlet {

	private static final long serialVersionUID = -7747173089030395968L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Session initialization
		Admin adminLogin = null;

		AdminRegisterUser retrieveAdminRegisteruserInfo = new AdminRegisterUser();
		//Set the session info
		HttpSession session = request.getSession();
		int intUserId = 0;
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		if((AdminRegisterUser) session.getAttribute("retrieveAdminRegisteruserInfo") != null) {
			retrieveAdminRegisteruserInfo = (AdminRegisterUser) session.getAttribute("retrieveAdminRegisteruserInfo");
			intUserId = retrieveAdminRegisteruserInfo.getRegisteredUserId();
		}
		else {
			String stringUserId = request.getParameter("userId");
			intUserId = Integer.parseInt(stringUserId);
		}

		//Session is already set
		if (adminLogin != null ) {
			UserGetUserInfoDAO getUserInfoDAO = new UserGetUserInfoDAO();
			AdminRegisterUser registeredUser = new AdminRegisterUser();
			UserRegisterUser selfRegisterUser = new UserRegisterUser();

			try {
				AdminGetUserInfoDAO adminGetUserInfoDAO = new AdminGetUserInfoDAO();
				registeredUser = adminGetUserInfoDAO.getUserByAdminWithUserId(intUserId);
				selfRegisterUser = getUserInfoDAO.getSelfRegisteredUserInfo(registeredUser.getUser_mail());
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.removeAttribute("retrieveAdminRegisteruserInfo");
			session.setAttribute("registeredUser", registeredUser);
			session.setAttribute("selfRegisterUser", selfRegisterUser);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/user_info.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}
}
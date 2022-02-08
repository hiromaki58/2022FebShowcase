package jp.co.warehouse.controller.user;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.image.GetImageDAO;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This controller show the detailed information about the user.
 */
@WebServlet("/user/user_info")
public class UserInfoController extends HttpServlet {

	private static final long serialVersionUID = -6730766329226541984L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session information
		User userLogin = null;

		//Check login is done or not either admin or user
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
		  userLogin = (User) session.getAttribute("user");
		}

		//Login is done yet
		if (userLogin != null) {
			//Due to the login is succeeded, there should be the session which has an E-mail address.
			String mailAddress = userLogin.getEmail();
			UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
			AdminRegisterUser registeredUser = new AdminRegisterUser();
			UserRegisterUser useRegisterdUser = new UserRegisterUser();

			try {
				/*
				 *Get the E-mail address from the login info session, and bring the info from DB
				 *with usinng the E-mail as a key.
				 */
				registeredUser = userGetUserInfoDao.getRegisteredUserInfo(mailAddress);
				useRegisterdUser = userGetUserInfoDao.getSelfRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			String userEmailFromDB = registeredUser.getUser_mail();

			/*
			 * This if sentence set the restriction to access the user detailed info.
			 * If the E-mail which is registered in DB and the E-mail which is in the login info session are identical,
			 * user_info page is available.
			 */
			if(mailAddress.equals(userEmailFromDB)) {
				session.setAttribute("registeredUser", registeredUser);
				session.setAttribute("selfRegisterUser", useRegisterdUser);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/user_info.jsp").forward(request, response);
			}
			else {
				/*
				 * The email doesn't match, so "No access" message will be shown.
				 */
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_access.jsp").forward(request, response);
			}
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		User userLogin = null;
		HttpSession session = request.getSession();
		userLogin = (User) session.getAttribute("user");

		//Login is done yet
		if (userLogin != null) {
			String mailAddress = userLogin.getEmail();
			BufferedImage bimg = null;
			try {
				//Call the image by the E-mail address which is stored in the login session
				GetImageDAO getImageDao = new GetImageDAO();
				bimg = getImageDao.selectImageByEmail(mailAddress);

				//Show the user image
				response.setContentType("image/jpeg");
		        OutputStream OS = response.getOutputStream();
		        ImageIO.write(bimg, "jpg", OS);
		        OS.flush();
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/user_info.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session.jsp").forward( request, response);
		}
	}
}
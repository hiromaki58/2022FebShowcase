package jp.co.warehouse.controller.forpublic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.article.PublicGetArticleDAO;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This class is used for show the user's detail information
 */
@WebServlet("/user_detail")
public class PublicAuthorDetailController extends HttpServlet {

	private static final long serialVersionUID = -76446025288772046L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//Check login is done or not either admin or user
		HttpSession session = request.getSession();
		//This array is used to show 3 articles at the bottom of the page
		ArticleInfoArray threeArticleInfoSelectedByUserId = new ArticleInfoArray();

		//Initialize the user id number
		int intUserId = 0;
		//Receive the user id from the get method
		String stringUserId = request.getParameter("userId");
		intUserId = Integer.parseInt(stringUserId);

		AdminRegisterUser registeredUserInfoAtPublicUserDetail = new AdminRegisterUser();
		UserRegisterUser selfRegisteredUserInfoAtPublicUserDetail = new UserRegisterUser();

		//Try to find the user information from the database
		try {
			UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
			PublicGetArticleDAO publicGetArticleDao = new PublicGetArticleDAO();
			
			registeredUserInfoAtPublicUserDetail = userGetUserInfoDao.getUserByUserWithUserId(intUserId);
			selfRegisteredUserInfoAtPublicUserDetail = userGetUserInfoDao.getSelfRegisteredUserInfo(registeredUserInfoAtPublicUserDetail.getUser_mail());
			threeArticleInfoSelectedByUserId = publicGetArticleDao.getThreeArticleInfoArraySelectedByUser(registeredUserInfoAtPublicUserDetail.getSelfRegisteredUserId());
		}
		catch (DatabaseException | SystemException e) {
			e.printStackTrace();
		}

		/*
		 * Set the information from the databese into the session,
		 * and send them to the jsp page.
		 */
		session.setAttribute("registeredUserInfoAtPublicUserDetail", registeredUserInfoAtPublicUserDetail);
		session.setAttribute("selfRegisteredUserInfoAtPublicUserDetail", selfRegisteredUserInfoAtPublicUserDetail);
		session.setAttribute("threeArticleInfoSelectedByUserId", threeArticleInfoSelectedByUserId);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/public/public_user_detail.jsp").forward(request, response);
	}
}

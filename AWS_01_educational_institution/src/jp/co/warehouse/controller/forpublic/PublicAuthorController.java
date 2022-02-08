package jp.co.warehouse.controller.forpublic;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.PublicGetUserDAO;
import jp.co.warehouse.entity.UserArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This is the list which is the index of the users.
 */
@WebServlet("/user")
public class PublicAuthorController extends HttpServlet{
	private static final long serialVersionUID = -5156357172487307220L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserArray authorList = new UserArray();
		PublicGetUserDAO publicGetUserDao = new PublicGetUserDAO();

		/*
		 * The reason why the information is divided by 4 is we wish to
		 * separate the users into 4 groups.
		 *
		 * And, each group will show up at specific area in the jsp page
		 */
		try {
			authorList = publicGetUserDao.getUserInfoOrderedByName();
		} catch (DatabaseException | SystemException e) {
			e.printStackTrace();
		}

		//Set the data into the sessions and send them to the jsp page
		session.setAttribute("authorList", authorList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/public/users.jsp");
	  	dispatcher.forward(request, response);
	}
}

package jp.co.warehouse.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.DaoInterface.LoginStrategy;
import jp.co.warehouse.dao.user.CheckUserRegisteredStatusDAO;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.dao.user.UserLoginDAO;
import jp.co.warehouse.security.Hash;

/**
 * This class provides the function for the users to login to the web site.
 * @author hirog
 * Sep 8, 2021
 *
 */
@WebServlet("/user/login")
public class UserLoginController extends HttpServlet {

	private static final long serialVersionUID = 2801089075757422161L;

	/**
	 * Show the login page 
	 * @author hirog
	 * Sep 8, 2021
	 *
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp");
		//If we have to close the login page due to the maintenance, the jsp file below will be deployed.
  	  	dispatcher.forward(request, response);
	}

	/**
	 * Receive user id and password
	 * @author hirog
	 * Sep 8, 2021
	 *
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		boolean positiveAcknowledgment = false;
		boolean negativeAcknowledgment = false;
		HttpSession session = request.getSession();

	    //Get the ID and password
	    request.setCharacterEncoding("UTF-8");
	    String userId = request.getParameter("userid");
	    String password = request.getParameter("password");

	    //Create the instance to keep the info about who login
	    User user = new User(userId, password);
	    Hash hash = new Hash();
	    //Turn the raw sentence into the encrypted code
	    String encryptedPassword = hash.hashGenerator(password);
	    
	    //Call the interface for open close principal
	    LoginStrategy li = new UserLoginDAO();

	    //Check the person who input the ID and password is genuine.
	    try {
	        try {
	        	CheckUserRegisteredStatusDAO checkUserRegisteredStatusDao = new CheckUserRegisteredStatusDAO();
	        	positiveAcknowledgment = li.checkLogin(userId, encryptedPassword);
	        	negativeAcknowledgment = checkUserRegisteredStatusDao.checkNegativeAdminAcknowledgment(userId, encryptedPassword);
	        }
	        catch (jp.co.warehouse.exception.SystemException e) {
	  			e.printStackTrace();
	        }
	  	}
	  	catch (DatabaseException e) {
	  	  e.printStackTrace();
	  	  session.setAttribute("Except", e);
	  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
	  	}

        if(positiveAcknowledgment) {
        	//Check the user is genuine by the E-mail and password
	  	  	session.setAttribute("user", user);
	  	  	response.sendRedirect("https://aws-warehouse58th.com/user/mypage");
        }
        else if(negativeAcknowledgment) {
        	//Show the error message because there is no data in the database.
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/negative_acknowledgment.jsp");
	  	  	dispatcher.forward(request, response);
        }
        else {
        	//Show the error message because there is no data in the database.
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/login_error.jsp");
	  	  	dispatcher.forward(request, response);
        }
	}
}
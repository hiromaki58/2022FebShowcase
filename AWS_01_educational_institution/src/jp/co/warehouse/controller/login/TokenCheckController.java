package jp.co.warehouse.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.utility.SecurityDAO;
import jp.co.warehouse.entity.Token;
import jp.co.warehouse.exception.DatabaseException;

/*
 * This controller is for checking the email which is registered in the DB
 * If so, the user is genuine and can proceed to the initial password setting page
 */
@WebServlet("/login/code_check")
public class TokenCheckController extends HttpServlet {

	private static final long serialVersionUID = 662213936766477841L;

	//Show the token input page
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/token_input.jsp");
  	  	dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		String tokenExist = "";

		//Have the token which is sent to check the person who try to change the password is genuine.
	    request.setCharacterEncoding("UTF-8");
	    String dismissed_token = request.getParameter("dismissed_token");

	    //Check the email does exists in the DB
	    try {
	        SecurityDAO securityDao = new SecurityDAO();
	        try {
	        	tokenExist = securityDao.checkTokenExist(dismissed_token);
	        }
	        catch (jp.co.warehouse.exception.SystemException e) {
	        	getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
	  			e.printStackTrace();
	        }

	        //Create the instance to make the person who try to re-issue the password enable to open the page
	        Token tokenInfo = new Token();
	        tokenInfo.setToken(dismissed_token);
	        /*
	         * If the token is stored in the DB, he can move on to the dismissed password reissue page.
	         */
	        if(tokenExist.equals("exist")) {
	        	//Pass the token info to the next phase
		        HttpSession session = request.getSession();
		  	  	session.setAttribute("tokenInfo", tokenInfo);

		  	  	//Move to the dismissed password re-issue page
		  	    response.sendRedirect("https://aws-warehouse58th.com/login/dismissed_reissue");
	        }
	        else if(tokenExist.equals("notExist")) {
	        	//If the token is not registered in the DB, the error message "No token registered" will be issued.
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error/no_token_registered.jsp");
		  	  	dispatcher.forward(request, response);
	        }
	  	}
	  	catch (DatabaseException e) {
	  	  e.printStackTrace();
	  	  HttpSession session = request.getSession();
	  	  session.setAttribute("Except", e);

	  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
	  	}
	}
}
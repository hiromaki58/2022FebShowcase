package jp.co.warehouse.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.admin.AdminLoginDAO;
import jp.co.warehouse.entity.AdminId;
import jp.co.warehouse.exception.DatabaseException;

/*
 * This class is used for indicating the password resetting page, if the user forgets the password and cannot login.
 * If the man who try to access can input the registered e-mail, he can move on to the password changing page.
 */
@WebServlet("/login/admin_reset")
public class AdminPasswordResetController extends HttpServlet {

	private static final long serialVersionUID = -3842290681492706483L;
	private boolean tentativeAdminInfoBean;

	//Show the email checking page
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/reset.jsp");
  	  	dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		//Receive the email which is written in the input
	    request.setCharacterEncoding("UTF-8");
	    String stringTentativeAdminId = request.getParameter("mail");

	    // Create the instance to hold the tentative admin login id information
	    AdminId tentativeAdminId = new AdminId(stringTentativeAdminId);

	  //Check the email does exists in the DB
	    try {
	        AdminLoginDAO adminLoginDao = new AdminLoginDAO();
	        try {
	        	tentativeAdminInfoBean = adminLoginDao.checkAdminEmail(stringTentativeAdminId);
	        }
	        catch (jp.co.warehouse.exception.SystemException e) {
	  			e.printStackTrace();
	        }
	        /*
	         * If the mail addresss is found, the user moves on to the first password set up page.
	         */
	        if(tentativeAdminInfoBean) {
		        HttpSession session = request.getSession();
		  	  	session.setAttribute("tentativeAdminId", tentativeAdminId);

		  	  	response.sendRedirect("https://aws-warehouse58th.com/login/admin_reissue");
	        }
	        else {
	        	//If not registered, the error message "The e-mail is not registered" will be issued.
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login/no_admin_account_registered.jsp");
		  	  	dispatcher.forward(request, response);
	        }
	  	}
	  	catch (DatabaseException e) {
	  	  e.printStackTrace();
	  	  HttpSession session = request.getSession();
	  	  session.setAttribute("Except", e);

	  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/login/issue_error.jsp").forward( request, response);
	  	}
	  }
}
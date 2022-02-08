package jp.co.warehouse.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.DaoInterface.LoginStrategy;
import jp.co.warehouse.dao.admin.AdminLoginDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.security.Hash;

/**
 * To admin login
 * @author hirog
 * Sep 18, 2021
 *
 */
@WebServlet("/admin/login")
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 2801089075757422161L;
	private boolean adminInfoBean;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Bring up the login page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp");
	  	dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    String adminId = request.getParameter("userid");
	    String password = request.getParameter("password");

	    //Create the administrator instance
	    Admin admin = new Admin(adminId, password);
	    Hash hash = new Hash();
	    //Turn the raw sentence into the encrypted code
	    String encryptedAdminId = hash.hashGenerator(adminId);
	    String encryptedPassword = hash.hashGenerator(password);
	    
	    //Call the interface for open close principal
	    LoginStrategy li = new AdminLoginDAO();

        try {
        	/*
        	 * Check the admin id is genuine,
        	 * and set true in the session.
        	 * 
        	 * The admin account can access to the web
        	 */
        	adminInfoBean = li.checkLogin(encryptedAdminId, encryptedPassword);
        }
        catch (jp.co.warehouse.exception.SystemException e) {
  			e.printStackTrace();
        } 
        catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(adminInfoBean) {
	        HttpSession session = request.getSession();
	  	  	session.setAttribute("admin", admin);
	  	  	//Move on to the my page
	  	  	response.sendRedirect("https://aws-warehouse58th.com/admin/mypage");
        }
        else {
        	//Show the login fail page
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/admin_loginerror.jsp");
	  	  	dispatcher.forward(request, response);
        }
	}
}

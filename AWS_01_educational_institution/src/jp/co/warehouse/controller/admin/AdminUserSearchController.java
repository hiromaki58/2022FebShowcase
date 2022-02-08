package jp.co.warehouse.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.utility.PaginationDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.UserArray;
import jp.co.warehouse.entity.SearchWord;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/**
 * Servlet implementation class MypageController
 * This controller will be used for searching the user
 */
@WebServlet("/admin/user_search")
public class AdminUserSearchController extends HttpServlet {

    private static final long serialVersionUID = 9067426083170714758L;;

	public AdminUserSearchController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;

		//Set the session information
		HttpSession session = request.getSession();
		adminLogin = (Admin) session.getAttribute("admin");
		PaginationDAO paginationDao = new PaginationDAO();
		UserArray userInfoArray = new UserArray();
		//This "word" distinguishes between just showing the page or there is a keyword to search the user
		String word = request.getParameter("word");

		int pageNumber = 1;
		int recordsPerPage = 20;
		int noOfRecords = 0;
		int noOfPages = 0;
		//Get the page number of the page which is shown now
		if(request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}

		//Session is already set
		if (adminLogin != null && word == null) {
			try {
				/*
				 * Get the article info as many as the number of the article is assinged,
				 * and receive the info from the DB and set into the array.
				 * This function will work for pagination.
				 */
				userInfoArray = paginationDao.userPagination((pageNumber-1) * recordsPerPage, recordsPerPage);
				noOfRecords = paginationDao.getNoOfRecords();
				noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", pageNumber);
			session.setAttribute("userInfoArray", userInfoArray);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/search_user.jsp");
	  	  	dispatcher.forward(request, response);
		}
		//word.equals("in") means the search keyword exists in the search box
		else if ((adminLogin != null) && word.equals("in")) {

			//Get the key word in the search box
			String search = request.getParameter("search");
			SearchWord searchWord = new SearchWord();
			searchWord.setWordSearch(search);
			try {
				userInfoArray = paginationDao.userSearchPagination((pageNumber-1) * recordsPerPage, recordsPerPage, search);
				noOfRecords = paginationDao.getNoOfRecords();
				noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", pageNumber);
			request.setAttribute("searchWord", searchWord);
			request.setAttribute("searchWord", searchWord);
			session.setAttribute("userInfoArray", userInfoArray);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/search_user.jsp");
	  	  	dispatcher.forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}
}
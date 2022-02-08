package jp.co.warehouse.controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.utility.PaginationDAO;
import jp.co.warehouse.dao.utility.MatchDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.SearchWord;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/**
 * This controller controls the my page indication by admin account
 */
@WebServlet("/admin/mypage")
public class AdminMypageController extends HttpServlet {

    private static final long serialVersionUID = 9067426083170714758L;
    public AdminMypageController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialization
		Admin adminLogin = null;

		//Set the session info
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		PaginationDAO paginationDao = new PaginationDAO();
		MatchDAO matchDao = new MatchDAO();
		ArticleInfoArray articleInfoArray = new ArticleInfoArray();
		ArrayList<String> userId = new ArrayList<String>();

		//This "word" distinguishes between just showing the page or there is a keyword to search the article
		String word = request.getParameter("word");
		String search = "";
		int intuserId = 0;
		int pageNumber = 1;
		int recordsPerPage = 10;
		int noOfRecords = 0;
		int noOfPages = 0;
		//Get the page number of the page which is shown now
		if(request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}

		//Session is already set
		if (adminLogin != null && word == null) {
			/*
			 * If the user registration session is already made once and try to make up the new registration,
			 * the last session supplies the last person information.
			 *
			 * So if the last person's data is still stored into the session, the session needs to be destroyed.
			 */
			if(session.getAttribute("adminRegisteruser") != null) {
				//Remove the session which is used.
		  	  	session.removeAttribute("adminRegisteruser");
			}
			
			try {
				/*
				 * Get the article info as many as the number of the article is assigned,
				 * and receive the info from the DB and set into the array.
				 * This function will work for pagination.
				 */
				articleInfoArray = paginationDao.articlePagination((pageNumber-1) * recordsPerPage, recordsPerPage);
				noOfRecords = paginationDao.getNoOfRecords();
				noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			/*
			 *Find out who is the user of the article
			 */
			for(int i = 0; i < articleInfoArray.getArticleInfoArray().size(); i++) {
				try {
					intuserId = matchDao.getUserIdByArticleId(articleInfoArray.getArticleInfoArray().get(i).getArticleId());
				} catch (DatabaseException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
				userId.add(String.valueOf(intuserId));
			}
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", pageNumber);
			session.setAttribute("articleInfoArray", articleInfoArray);
			session.setAttribute("userId", userId);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/mypage.jsp");
	  	  	dispatcher.forward(request, response);
		}
		/*
		 * word.equals("in") means the search keyword exists in the search box
		 */
		else if ((adminLogin != null) && word.equals("in")) {

			/*
			 * Get the key word in the search box
			 * searchWordArray will collect the search word as array
			 */
			ArrayList<String> searchWordArray = new ArrayList<String>();

			//Replace the space by the punctuation
			search = request.getParameter("search").replace(" ", ",").replace("　", ",");
			String[] searchWordList = search.split(",", 0);

			//The search word is separated each and set into the array
			for(int i = 0; i < searchWordList.length; i++) {
				searchWordArray.add(searchWordList[i]);
			}

			//Keep the word in the search window
			SearchWord searchWord = new SearchWord();
			searchWord.setWordSearch(search.replace(",","　"));

			//Send the search word to the data access object
			try {
				articleInfoArray = paginationDao.articleSearchPagination((pageNumber-1) * recordsPerPage, recordsPerPage, searchWordArray);
				noOfRecords = paginationDao.getNoOfRecords();
				noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			/*
			 *Find out who is the user of the article
			 */
			for(int i = 0; i < articleInfoArray.getArticleInfoArray().size(); i++) {
				try {
					intuserId = matchDao.getUserIdByArticleId(articleInfoArray.getArticleInfoArray().get(i).getArticleId());
				} catch (DatabaseException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
				userId.add(String.valueOf(intuserId));
			}
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", pageNumber);
			session.setAttribute("searchWord", searchWord);
			session.setAttribute("articleInfoArray", articleInfoArray);
			session.setAttribute("userId", userId);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/mypage.jsp");
	  	  	dispatcher.forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}
}
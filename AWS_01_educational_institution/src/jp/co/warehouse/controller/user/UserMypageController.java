package jp.co.warehouse.controller.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.user.CheckUserRegisteredStatusDAO;
import jp.co.warehouse.dao.article.UserGetArticleDAO;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.SearchWord;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/**
 * Servlet implementation class MypageController
 * @author hirog
 * Sep 14, 2021
 *
 */
@WebServlet("/user/mypage")
public class UserMypageController extends HttpServlet {

    private static final long serialVersionUID = 9067426083170714758L;

	private boolean infoRegisteredDone;
	private boolean imgRegisteredDone;
	private boolean releaseRegisteredDone;
	private boolean articleRegisteredDone;
	private boolean userReleasedYes;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public UserMypageController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialization
		User login = null;
		String email = "";
		String stringUserId = "";
		int userId = 0;
		AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
	  	CheckUserRegisteredStatusDAO  cursDao = new CheckUserRegisteredStatusDAO();
	  	AdminRegisterUser nameAtMypageArticle = new AdminRegisterUser();
	  	ArticleInfoArray articleInfoArray = new ArticleInfoArray();
		StringBuilder sbi = new StringBuilder();
		String userName ="";

		//Set the session info
		HttpSession session = request.getSession();
		//Check login as an user
		if ((User) session.getAttribute("user") != null) {
			login = (User) session.getAttribute("user");
			email = login.getEmail();

			try {
				nameAtMypageArticle = adminGetUserInfoDao.getRegisteredUserInfo(email);
				//Check the user information is already registered.
				infoRegisteredDone = cursDao.checkUserInfoRegistered(login.getEmail());
				//Check the user profile image is set
				imgRegisteredDone = cursDao.checkUserImgRegistered(login.getEmail());
				//Check released or not released is selected
				releaseRegisteredDone = cursDao.checkUserReleaseRegistered(login.getEmail());
				//Check the user chooses yes to release the information
				userReleasedYes = cursDao.checkUserReleaseRegisteredAsYes(login.getEmail());

				sbi.append(nameAtMypageArticle.getUser_last_name());
				sbi.append(" ");
				sbi.append(nameAtMypageArticle.getUser_first_name());
				userName = sbi.toString();
				/*
				 * To check the authors register the article already.
				 */
				userId = nameAtMypageArticle.getSelfRegisteredUserId();
				stringUserId = Integer.toString(userId);
				CheckUserRegisteredStatusDAO checkUserRegisteredStatusDao = new CheckUserRegisteredStatusDAO();
				articleRegisteredDone = checkUserRegisteredStatusDao.isArticleRegistered(stringUserId);
			} catch (DatabaseException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}

		//This "word" distinguishes between just showing the page or there is a keyword to search the article.
		String word = request.getParameter("word");
		String search = "";
		
		/*
		 * The user page is required to show 6 type of pages.
		 * 1, Bland new user without any individual info registered who will be promoted to register the user info.
		 * 2, The user is completed to registered the private info however no image is registered.
		 * 3, Image and the user info is done, but still it is not in public still.
		 * 4, All of user side info is done, and no class is registered.
		 * 5, The class is also registered, and the class is open for public.
		 * 6, Search word filter the article if the user has multiple articles.
		 */
		
		//#1 The bland new user
		if (login != null && (!infoRegisteredDone || !imgRegisteredDone || !releaseRegisteredDone)) {
			String mailAddress = login.getEmail();
			adminGetUserInfoDao = new AdminGetUserInfoDAO();
			AdminRegisterUser regiseredUser = new AdminRegisterUser();

			try {
				regiseredUser = adminGetUserInfoDao.getRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e1) {
				e1.printStackTrace();
			}

			session.setAttribute("regiseredUser", regiseredUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/mypage_non_activated.jsp");
	  	  	dispatcher.forward(request, response);
		}
		//#5 The article is already registered.
		else if (login != null && articleRegisteredDone && word == null){
			/*
			 * Get the articles info written by the author.
			 * The author is identified by the user id. 
			 */
			try {
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				articleInfoArray = userGetArticleDao.getArticleInfoArrayByUserId(stringUserId);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("articleInfoArray", articleInfoArray);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/mypage_activated.jsp");
	  	  	dispatcher.forward(request, response);
		}
		//#6 Search word filter
		else if (login != null && articleRegisteredDone && word.equals("in")){
			/*
			 * Get the key word in the search box
			 * searchWordArray will collect the search word as array
			 */
			ArrayList<String> searchWordArray = new ArrayList<String>();

			//Replace the space by the punctuation to accept the multiple keyword for searching
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
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				articleInfoArray = userGetArticleDao.getArticleInfoArrayByUserNameWithSearchWord(userName, searchWordArray);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			//Set the session to show the information to the page
			session.setAttribute("searchWord", searchWord);
			session.setAttribute("articleInfoArray", articleInfoArray);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/mypage_activated.jsp");
	  	  	dispatcher.forward(request, response);
		}
		//#3 Private mode
		else if (login != null && !userReleasedYes){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/mypage_user_non_released.jsp");
	  	  	dispatcher.forward(request, response);
		}
		//#4 No class is registered yet
		else if (login != null && infoRegisteredDone && imgRegisteredDone && releaseRegisteredDone){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/mypage_article.jsp");
	  	  	dispatcher.forward(request, response);
		}
		else {
			//Checking the session is still alive.
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}
}
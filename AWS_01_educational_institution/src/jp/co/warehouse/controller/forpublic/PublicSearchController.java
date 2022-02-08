package jp.co.warehouse.controller.forpublic;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.entity.SearchWord;
import jp.co.warehouse.dao.article.PublicGetArticleDAO;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This controller is gathering the information of the article and show them.
 */
@WebServlet("/search")
public class PublicSearchController extends HttpServlet {

	private static final long serialVersionUID = -5653125942379081462L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		String search = "";
		/*
		 * This array is used to have the articles. They are selected by the keyword.
		 * "searchWordArray" will collect the search word as array  
		 */
		ArrayList<String> searchWordArray = new ArrayList<String>();
		ArticleInfoArray articleSearchResult = new ArticleInfoArray();

		/*
		 * Retrieve the key word at the search input, if it exists
		 *
		 * This part will split the multiple search word into the single word by the "nbsp;", and then
		 * the single word will be collected into the array
		 */
		if(request.getParameter("articleSearch") != null) {

			//Replace the space by the punctuation
			search = request.getParameter("articleSearch").replace(" ", ",").replace("　", ",");
			String[] searchWordList = search.split(",", 0);

			//The search word is separated each and set into the array
			for(int i = 0; i < searchWordList.length; i++) {
				searchWordArray.add(searchWordList[i]);
			}
		}
		else {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_search_word.jsp").forward(request, response);
		}

		//Create the instance to show the keyword or anything to find the article at the search page
		SearchWord searchWord = new SearchWord();
		searchWord.setWordSearch(search.replace(",", " "));

		//Find the article with the information which is input by the search SQL
		try {
			PublicGetArticleDAO publicGetArticleDao = new PublicGetArticleDAO();
			articleSearchResult = publicGetArticleDao.searchArticleArrayBySearch(searchWordArray);
		} catch (DatabaseException | SystemException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute("searchWord", searchWord);
		session.setAttribute("articleSearchResult", articleSearchResult);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/public/search.jsp").forward(request, response);
	}
}

package jp.co.warehouse.controller.forpublic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.article.AddressDAO;
import jp.co.warehouse.dao.article.PublicGetArticleDAO;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * This page is used to show the site top page
 */
@WebServlet("/index")
public class PublicIndexController extends HttpServlet {

	private static final long serialVersionUID = -5653125942379081462L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		HttpSession session = request.getSession();
		//This array is used to show 6 articles at the top of the top page with the weight which is assigned by Ms. Momose.
		ArticleInfoArray orderByWeightLimit6 = new ArticleInfoArray();

		//This array is used to show 3 articles at the top page. The articles are Ordered by the start day is closer to now.
		ArticleInfoArray orderByDayLimit3 = new ArticleInfoArray();

		//This array is used to show 6 articles in the bottom of the top page. The articles are Ordered by the start day is closer to now.
		ArticleInfoArray orderByDayLimit6 = new ArticleInfoArray();

		//Read all of the articles and step by step the article information card will show up
		ArticleInfoArray comingArticleArray = new ArticleInfoArray();

		//To have the link URL at the top of the page
		String linkAddress = "";
		
		/*
		 * The reason why the number of the article is limted in 6 or 10 is
		 * due to the design.
		 *
		 * The clienst wished to show as many as limited.
		 */
		try {
			//Have the article card information
			PublicGetArticleDAO publicGetArticleDAO = new PublicGetArticleDAO();
			orderByWeightLimit6 = publicGetArticleDAO.getArticleInfoArrayByWeight();
			orderByDayLimit3 = publicGetArticleDAO.getArticleInfoArrayByDate();
			orderByDayLimit6 = publicGetArticleDAO.getSixArticleInfoArrayByDate();
			comingArticleArray = publicGetArticleDAO.getComingArticleArray();
			//Get the link data
			AddressDAO addressDao = new AddressDAO();
			linkAddress = addressDao.getMvLinkAddress();
		} catch (DatabaseException | SystemException e) {
			e.printStackTrace();
		}

		//Set the article information into the session and send them to the /public/index.jsp
		session.setAttribute("orderByWeightLimit6", orderByWeightLimit6);
		session.setAttribute("orderByDayLimit3", orderByDayLimit3);
		session.setAttribute("orderByDayLimit6", orderByDayLimit6);
		session.setAttribute("comingArticleArray", comingArticleArray);
		session.setAttribute("linkAddress", linkAddress);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/public/index.jsp").forward(request, response);
	}
}
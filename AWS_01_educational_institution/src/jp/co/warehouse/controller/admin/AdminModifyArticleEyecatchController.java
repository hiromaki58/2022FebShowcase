package jp.co.warehouse.controller.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jp.co.warehouse.dao.article.AdminGetArticleDAO;
import jp.co.warehouse.dao.image.SetImageDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.stream.StreamProcessor;

/**
 * 
 * @author hirog
 * Sep 18, 2021
 *
 */
@WebServlet("/admin/modification_article_02")
@MultipartConfig
public class AdminModifyArticleEyecatchController extends HttpServlet {

	private static final long serialVersionUID = -6652972191142141120L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialization
		Admin adminLogin = null;

		//Receive the article id from the get method
		String stringArticleId = request.getParameter("articleId");
		int articleId = Integer.parseInt(stringArticleId);
		Article articleInfo = new Article();

		//Set the session info
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		//Session is already set
		if ((adminLogin != null)) {
			try {
				AdminGetArticleDAO adminGetArticleDao = new AdminGetArticleDAO();
				articleInfo = adminGetArticleDao.getArticleInfoByArticleId(articleId);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			session.setAttribute("articleInfo", articleInfo);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_02.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialization
		@SuppressWarnings("unused")
		Admin adminLogin = null;

		request.setCharacterEncoding("UTF-8");
		String stringArticleId = request.getParameter("articleId");
		int articleId = Integer.parseInt(stringArticleId);

		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		
		Article articleInfo = new Article();
		StreamProcessor sp = new StreamProcessor();
		Part filePart = null;

	    try {
	    	filePart = request.getPart("article_eyecatch"); // Retrieves <input type="file" name="file">

	        InputStream fileContent = filePart.getInputStream();
	        byte[] byteData = sp.readAll(fileContent);
	        BufferedImage bufferedImage = ImageIO.read(filePart.getInputStream());

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", baos);
			baos.flush();
			baos.close();

			try {
				AdminGetArticleDAO adminGetArticleDao = new AdminGetArticleDAO();
				SetImageDAO setImageDao = new SetImageDAO();
				
				articleInfo = adminGetArticleDao.getArticleInfoByArticleId(articleId);
		        byte[] is2 = byteData;
		        setImageDao.updateEyeCatchById(is2, articleInfo.getImg_id());

		        response.sendRedirect("https://aws-warehouse58th.com/article/article_info");
		  	}
		  	catch (DatabaseException e) {
		  	  e.printStackTrace();
		  	  session.setAttribute("Except", e);

		  	  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
		  	}
		    catch (SystemException e) {
				e.printStackTrace();
			    session.setAttribute("Except", e);

			    getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
			}
	      }
	      catch (NumberFormatException e) {
	        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

	        pe.printStackTrace();
	        session.setAttribute("Except", pe);

	        getServletContext().getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
	        return;
	      }
	}
}

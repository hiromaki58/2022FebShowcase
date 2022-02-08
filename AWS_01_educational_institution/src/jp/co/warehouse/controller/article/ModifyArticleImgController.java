package jp.co.warehouse.controller.article;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jp.co.warehouse.dao.image.SetImageDAO;
import jp.co.warehouse.dao.article.AdminGetArticleDAO;
import jp.co.warehouse.dao.article.UserGetArticleDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.stream.StreamProcessor;

/*
 * This class changes the article images
 */
@MultipartConfig
@WebServlet("/article/modification_03")
public class ModifyArticleImgController extends HttpServlet {

	private static final long serialVersionUID = -6285882633765103808L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the login info
		Admin adminLogin = null;
		User userLogin = null;

		String stringArticleId = request.getParameter("articleId");
		int intArticleId = Integer.parseInt(stringArticleId);
		Article registeredArticle = new Article();

		//Set the login info
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		//Login is done already
		if (adminLogin != null) {
			try {
				AdminGetArticleDAO adminGetArticleDao = new AdminGetArticleDAO();
				registeredArticle = adminGetArticleDao.getArticleInfoByArticleId(intArticleId);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			session.setAttribute("registeredArticle", registeredArticle);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_03.jsp").forward(request, response);
		}
		else if (userLogin != null) {
			try {
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				registeredArticle = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			session.setAttribute("registeredArticle", registeredArticle);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_03.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	/*
	 * If the submit button is pushed.
	 * The image will be stored in to the database by the process below.
	 *
	 * (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		/*
		 * Article id is receive as the input which is set the bottom of the page jsp, name as article Id
		 * Due to this is post method we cannot use get to have the article id
		 */
		String stringArticleId = request.getParameter("articleId");
		int intArticleId = Integer.parseInt(stringArticleId);

	    Part filePart_01 = null;
	    Part filePart_02 = null;
	    Part filePart_03 = null;
	    Part filePart_04 = null;

	    String fileName_01 = null;
	    String fileName_02 = null;
	    String fileName_03 = null;
	    String fileName_04 = null;

	    UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
	    SetImageDAO setImageDao = new SetImageDAO();
		Article articleInfo = new Article();
	    StreamProcessor streamProcessor = new StreamProcessor();

	    try {
    		//image（1）
	    	filePart_01 = request.getPart("article_img_01"); // Retrieves <input type="file" name="file">
	    	fileName_01 = Paths.get(filePart_01.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

	    	//if new image is registerd the DB will be updated.
	        if(!(fileName_01.equals(""))) {
	        	InputStream fileContent_01 = filePart_01.getInputStream();
	        	//Get the byte data of the article image 01
		        byte[] byteData01 = streamProcessor.readAll(fileContent_01);

		        //This constant will store the image extension information
		        String articleImg01Type = "";

		        //Get the image extension
				if(fileName_01.matches(".*jpg.*")) {
					articleImg01Type = "jpg";
				}
				else if(fileName_01.matches(".*png.*")) {
					articleImg01Type = "png";
				}
				else if(fileName_01.matches(".*gif.*")) {
					articleImg01Type = "gif";
				}
				else {
					articleImg01Type = "jpg";
				}

		        try {
		        	articleInfo = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
					byte[] is01 = byteData01;
					//Update the new article image 01 as byte stream
					setImageDao.updateArticleImg01(is01, articleInfo.getImg_id());
					//Update the extension of the image
					setImageDao.updateArticleImg01Type(articleImg01Type, articleInfo.getImg_id());
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
		        catch(IllegalArgumentException e) {
				  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/img_format_error.jsp").forward( request, response);
				}
	        }

	        //image（2）
		    filePart_02 = request.getPart("article_img_02"); // Retrieves <input type="file" name="file">
		    fileName_02 = Paths.get(filePart_02.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

		    //if new image is registerd the DB will be updated.
	        if(!(fileName_02.equals(""))) {
	        	InputStream fileContent_02 = filePart_02.getInputStream();
		        byte[] byteData02 = streamProcessor.readAll(fileContent_02);

		        //This constant will store the image extension information
		        String articleImg02Type = "";

		        //Get the image extension
				if(fileName_02.matches(".*jpg.*")) {
					articleImg02Type = "jpg";
				}
				else if(fileName_02.matches(".*png.*")) {
					articleImg02Type = "png";
				}
				else if(fileName_02.matches(".*gif.*")) {
					articleImg02Type = "gif";
				}
				else {
					articleImg02Type = "jpg";
				}

		        try {
		        	articleInfo = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
					byte[] is02 = byteData02;
					//Update the new article image 02 as byte stream
					setImageDao.updateArticleImg02(is02, articleInfo.getImg_id());
					//Update the extension of the image
					setImageDao.updateArticleImg02Type(articleImg02Type, articleInfo.getImg_id());
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

	        //image（3）
		    filePart_03 = request.getPart("article_img_03"); // Retrieves <input type="file" name="file">
		    fileName_03 = Paths.get(filePart_03.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

		    //if new image is registerd the DB will be updated.
	        if(!(fileName_03.equals(""))) {
	        	InputStream fileContent_03 = filePart_03.getInputStream();
		        byte[] byteData03 = streamProcessor.readAll(fileContent_03);

		        //This constant will store the image extension information
		        String articleImg03Type = "";

		        //Get the image extension
				if(fileName_03.matches(".*jpg.*")) {
					articleImg03Type = "jpg";
				}
				else if(fileName_03.matches(".*png.*")) {
					articleImg03Type = "png";
				}
				else if(fileName_03.matches(".*gif.*")) {
					articleImg03Type = "gif";
				}
				else {
					articleImg03Type = "jpg";
				}

		        try {
		        	articleInfo = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
					byte[] is03 = byteData03;
					//Update the new article image 03 as byte stream
					setImageDao.updateArticleImg03(is03, articleInfo.getImg_id());
					//Update the extension of the image
					setImageDao.updateArticleImg03Type(articleImg03Type, articleInfo.getImg_id());
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

	        //image（4）
		    filePart_04 = request.getPart("article_img_04"); // Retrieves <input type="file" name="file">
		    fileName_04 = Paths.get(filePart_04.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

		    //if new image is registerd the DB will be updated.
	        if(!(fileName_04.equals(""))) {
	        	InputStream fileContent_04 = filePart_04.getInputStream();
		        byte[] byteData04 = streamProcessor.readAll(fileContent_04);

		        //This constant will store the image extension information
		        String articleImg04Type = "";

		        //Get the image extension
				if(fileName_04.matches(".*jpg.*")) {
					articleImg04Type = "jpg";
				}
				else if(fileName_04.matches(".*png.*")) {
					articleImg04Type = "png";
				}
				else if(fileName_04.matches(".*gif.*")) {
					articleImg04Type = "gif";
				}
				else {
					articleImg04Type = "jpg";
				}

		        try {
		        	articleInfo = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
					byte[] is04 = byteData04;
					//Update the new article image 04 as byte stream
					setImageDao.updateArticleImg04(is04, articleInfo.getImg_id());
					//Update the extension of the image
					setImageDao.updateArticleImg04Type(articleImg04Type, articleInfo.getImg_id());
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

	  	  response.sendRedirect("https://aws-warehouse58th.com/article/article_info");
	      }
	      catch (NumberFormatException e) {
	        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

	        pe.printStackTrace();
	        session.setAttribute("Except", pe);

	        getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/error.jsp").forward(request, response);
	        return;
	      }
	 }
}

package jp.co.warehouse.controller.article;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jp.co.warehouse.dao.image.SetImageDAO;
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
 * To change the eye-catch image
 */
@WebServlet("/article/modification_02")
@MultipartConfig
public class ModifyArticleEyecatchController extends HttpServlet {

	private static final long serialVersionUID = -6652972191142141120L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;
		User userLogin = null;

		//Have a article id to set the target article needs to be change the eye-catch image
		String stringArticleId = request.getParameter("articleId");
		int intArticleId = Integer.parseInt(stringArticleId);
		Article registeredArticle = new Article();

		//Set the session
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		//Check if session is already set as an admin account
		if(adminLogin != null) {
			try {
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				registeredArticle = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			session.setAttribute("registeredArticle", registeredArticle);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_02.jsp").forward(request, response);
		}
		//Check if session is already set as an admin account
		else if(userLogin != null) {
			try {
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				registeredArticle = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
			}
			catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			session.setAttribute("registeredArticle", registeredArticle);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_02.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		Admin adminLogin = null;
		User userLogin = null;
		request.setCharacterEncoding("UTF-8");
		/*
		 * article id is receive as the input which is set the bottom of the page jsp, name as article Id
		 * Due to this is post method we cannot use get to have the article id
		 */
		String stringArticleId = request.getParameter("articleId");
		int intArticleId = Integer.parseInt(stringArticleId);

		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		else if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		Part filePart = null;
		String fileName = null;
		Article articleInfo = new Article();
		StreamProcessor sp = new StreamProcessor();

		if(adminLogin != null || userLogin != null) {
		//Try to update the eye-catch image
		    try {
		    	filePart = request.getPart("article_eyecatch"); // Retrieves <input type="file" name="file">
		    	fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

		    	//update the DB if new image is added
		        if(!(fileName.equals(""))) {
			        InputStream fileContent = filePart.getInputStream();
			        byte[] byteData = sp.readAll(fileContent);
			        BufferedImage bufferedImage = ImageIO.read(filePart.getInputStream());
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();

			        //Have the image extension
			        String eyecatchType = "";

			        /*
			         * In case of the extension of the image is jpg.
			         */
			        if(fileName.matches(".*jpg.*")) {
						try {
				          ImageIO.write(bufferedImage, "jpg", baos);
						}
						//If the image type is not jpg or png error message will be issued
						catch(IllegalArgumentException e) {
							getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/img_format_error.jsp").forward( request, response);
						}
						//This constant is used to be stored into the DB as a new eye catch image extension image
						eyecatchType = "jpg";
						baos.flush();
						baos.close();
			        }
			        /*
			         * If the image type is png
			         */
			        else if(fileName.matches(".*png.*")){
			        	try {
			        		ImageIO.write(bufferedImage, "png", baos);
						}
							//If the image type is not jpg or png error message will be issued
						catch(IllegalArgumentException e) {
							getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/img_format_error.jsp").forward( request, response);
						}
			        	//This constant is used to be stored into the DB as a new eye catch image extension image
						eyecatchType = "png";
						baos.flush();
						baos.close();
			        }
			        /*
			         * If the image type is gif
			         */
			        else if(fileName.matches(".*gif.*")){
			        	try {
			        		ImageIO.write(bufferedImage, "gif", baos);
						}
							//If the image type is not jpg or png error message will be issued
						catch(IllegalArgumentException e) {
							getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/img_format_error.jsp").forward( request, response);
						}
			        	//This constant is used to be stored into the DB as a new eye catch image extension image
						eyecatchType = "gif";
						baos.flush();
						baos.close();
			        }
			        else {
			        	try {
			        		ImageIO.write(bufferedImage, "jpg", baos);
						}
							//If the image type is not jpg or png error message will be issued
						catch(IllegalArgumentException e) {
							getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/img_format_error.jsp").forward( request, response);
						}
			        	//This constant is used to be stored into the DB as a new eye catch image extension image
						eyecatchType = "jpg";
						baos.flush();
						baos.close();
			        }

					//Update the DB with the new image
					try {
						UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
						articleInfo = userGetArticleDao.getArticleInfoByAritcleId(intArticleId);
				        
						byte[] is2 = byteData;
						SetImageDAO setImageDao = new SetImageDAO();
						
				        //Register the new image byte stream
						setImageDao.updateEyeCatchById(is2, articleInfo.getImg_id());
				        //Set the new image extension into the DB
						setImageDao.updateEyeCatchTypeById(eyecatchType, articleInfo.getImg_id());
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
		        else {
		        	response.sendRedirect("https://aws-warehouse58th.com/article/article_info");
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
		else {
			// No login action is done yet
			response.sendRedirect("https://aws-warehouse58th.com/user/login");
		}
	}
}

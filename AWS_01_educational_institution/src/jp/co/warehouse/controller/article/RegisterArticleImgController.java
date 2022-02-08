package jp.co.warehouse.controller.article;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.ImgAddress;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.stream.StreamProcessor;

/*
 * Register the article images as many as 4
 */
@MultipartConfig
@WebServlet("/article/register_03")
public class RegisterArticleImgController extends HttpServlet {

	private static final long serialVersionUID = 6427058099740575638L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;
		User userLogin = null;

		//Set the session
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}
		/*
		 * "action" is the keyword to tell this class just show the page,
		 * or
		 */
		String action = request.getParameter("action");

		//Login is done already
		if ((adminLogin != null && action == null) || (userLogin != null && action == null)) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/register_03.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	/*
	 * Basically what will be done here is get the image information as many as 4,
	 * and set those data into the session to be ready for sending to the database.
	 *
	 * (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		//Initialize whatever we need to register the images
		request.setCharacterEncoding("UTF-8");
	    String imgHtml_01 = null;
	    String imgHtml_02 = null;
	    String imgHtml_03 = null;
	    String imgHtml_04 = null;

	    ImgAddress imgAddress_01 = new ImgAddress();
	    ImgAddress imgAddress_02 = new ImgAddress();
	    ImgAddress imgAddress_03 = new ImgAddress();
	    ImgAddress imgAddress_04 = new ImgAddress();

	    Part filePart_01 = null;
	    Part filePart_02 = null;
	    Part filePart_03 = null;
	    Part filePart_04 = null;

	    String fileName_01 = null;
	    String fileName_02 = null;
	    String fileName_03 = null;
	    String fileName_04 = null;

	    StreamProcessor streamProcessor = new StreamProcessor();

	    //Try to get the image data from the browser.
	    try {
	    	HttpSession session = request.getSession();

	    	//Processing the image #01
	    	filePart_01 = request.getPart("article_img_01"); // Retrieves <input type="file" name="file">
	        fileName_01 = Paths.get(filePart_01.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

	        InputStream fileContent_01 = null;
	        byte[] byteData01 = null;
	        BufferedImage bufferedImage_01 = null;
	        /*
	         * In case of the back button is selected, there is no file name,
	         * so get the file name from the session and reset the session
	         */
	        if(fileName_01.equals("") && (ImgAddress) session.getAttribute("article_img_path_01") != null) {
	        	ImgAddress imgaddr_recursion_01 = new ImgAddress();
	        	imgaddr_recursion_01 = (ImgAddress) session.getAttribute("article_img_path_01");

	        	fileName_01 = imgaddr_recursion_01.getImgAddress();
	        	filePart_01 = imgaddr_recursion_01.getFilePart();
	        	fileContent_01 = imgaddr_recursion_01.getIs();
	        	byteData01 = imgaddr_recursion_01.getBytedata();
	        	bufferedImage_01 = imgaddr_recursion_01.getImage();
	        }
	        //If there is no image is selected, automatically the default one will be set.
	        else if(fileName_01.equals("")) {
	        	ServletContext context = getServletContext();
	        	fileContent_01 = context.getResourceAsStream("/common/img/img_no_image_02.jpg");

	        	byteData01 = streamProcessor.readAll(fileContent_01);
	        	bufferedImage_01 = ImageIO.read(fileContent_01);
	        }
	        else {
	        	//Image is set and will be taken
	        	fileContent_01 = filePart_01.getInputStream();
	        	byteData01 = streamProcessor.readAll(fileContent_01);
		        bufferedImage_01 = ImageIO.read(filePart_01.getInputStream());
	        }

	        imgAddress_01.setImgHtml(imgHtml_01);
		    imgAddress_01.setImgAddress(fileName_01);
		    imgAddress_01.setFilePart(filePart_01);
		    imgAddress_01.setImage(bufferedImage_01);
		    imgAddress_01.setIs(fileContent_01);
		    imgAddress_01.setBytedata(byteData01);

		    //Processing the image #02
		    filePart_02 = request.getPart("article_img_02"); // Retrieves <input type="file" name="file">
	        fileName_02 = Paths.get(filePart_02.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

	        //Prepare the parts which will be used in processing the image #02
	        InputStream fileContent_02 = null;
	        byte[] byteData02 = null;
	        BufferedImage bufferedImage_02 = null;

	        /*
	         * In case of the back button is selected, there is no file name,
	         * so get the file name from the session and reset the session
	         */
	        if(fileName_02.equals("") && (ImgAddress) session.getAttribute("article_img_path_02") != null) {
	        	ImgAddress imgaddr_recursion_02 = new ImgAddress();
	        	imgaddr_recursion_02 = (ImgAddress) session.getAttribute("article_img_path_02");

	        	fileName_02 = imgaddr_recursion_02.getImgAddress();
	        	filePart_02 = imgaddr_recursion_02.getFilePart();
	        	fileContent_02 = imgaddr_recursion_02.getIs();
	        	byteData02 = imgaddr_recursion_02.getBytedata();
	        	bufferedImage_02 = imgaddr_recursion_02.getImage();
	        }
	        //If there is no image is selected, automatically the default one will be set.
	        else if(fileName_02.equals("")) {
	        	ServletContext context = getServletContext();
	        	fileContent_02 = context.getResourceAsStream("/common/img/img_no_image_02.jpg");

		        byteData02 = streamProcessor.readAll(fileContent_02);
		        bufferedImage_02 = ImageIO.read(fileContent_02);
	        }
	        else {
	        	//Image is set and will be taken
	        	fileContent_02 = filePart_02.getInputStream();
		        byteData02 = streamProcessor.readAll(fileContent_02);
		        bufferedImage_02 = ImageIO.read(filePart_02.getInputStream());
	        }

	        imgAddress_02.setImgHtml(imgHtml_02);
		    imgAddress_02.setImgAddress(fileName_02);
		    imgAddress_02.setFilePart(filePart_02);
		    imgAddress_02.setImage(bufferedImage_02);
		    imgAddress_02.setIs(fileContent_02);
		    imgAddress_02.setBytedata(byteData02);

		    //Processing the image #03
		    filePart_03 = request.getPart("article_img_03"); // Retrieves <input type="file" name="file">
	        fileName_03 = Paths.get(filePart_03.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

	        InputStream fileContent_03 = null;
	        byte[] byteData03 = null;
	        BufferedImage bufferedImage_03 = null;

	        /*
	         * In case of the back button is selected, there is no file name,
	         * so get the file name from the session and reset the session
	         */
	        if(fileName_03.equals("") && (ImgAddress) session.getAttribute("article_img_path_03") != null) {
	        	ImgAddress imgaddr_recursion_03 = new ImgAddress();
	        	imgaddr_recursion_03 = (ImgAddress) session.getAttribute("article_img_path_03");

	        	fileName_03 = imgaddr_recursion_03.getImgAddress();
	        	filePart_03 = imgaddr_recursion_03.getFilePart();
	        	fileContent_03 = imgaddr_recursion_03.getIs();
	        	byteData03 = imgaddr_recursion_03.getBytedata();
	        	bufferedImage_03 = imgaddr_recursion_03.getImage();
	        }
	        //If there is no image is selected, automatically the default one will be set.
	        else if(fileName_03.equals("")) {
	        	ServletContext context = getServletContext();
	        	fileContent_03 = context.getResourceAsStream("/common/img/img_no_image_02.jpg");

	        	byteData03 = streamProcessor.readAll(fileContent_03);
	        	bufferedImage_03 = ImageIO.read(fileContent_03);
	        }
	        else {
	        	//Image is set and will be taken
	        	fileContent_03 = filePart_03.getInputStream();
	        	byteData03 = streamProcessor.readAll(fileContent_03);
		        bufferedImage_03 = ImageIO.read(filePart_03.getInputStream());
	        }

	        imgAddress_03.setImgHtml(imgHtml_03);
		    imgAddress_03.setImgAddress(fileName_03);
		    imgAddress_03.setFilePart(filePart_03);
		    imgAddress_03.setImage(bufferedImage_03);
		    imgAddress_03.setIs(fileContent_03);
		    imgAddress_03.setBytedata(byteData03);

		    //Processing the image #04
		    filePart_04 = request.getPart("article_img_04"); // Retrieves <input type="file" name="file">
	        fileName_04 = Paths.get(filePart_04.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

	        InputStream fileContent_04 = null;
	        byte[] byteData04 = null;
	        BufferedImage bufferedImage_04 = null;

	        /*
	         * In case of the back button is selected, there is no file name,
	         * so get the file name from the session and reset the session
	         */
	        if(fileName_04.equals("") &&(ImgAddress) session.getAttribute("article_img_path_04") != null) {
	        	ImgAddress imgaddr_recursion_04 = new ImgAddress();
	        	imgaddr_recursion_04 = (ImgAddress) session.getAttribute("article_img_path_04");

	        	fileName_04 = imgaddr_recursion_04.getImgAddress();
	        	filePart_04 = imgaddr_recursion_04.getFilePart();
	        	fileContent_04 = imgaddr_recursion_04.getIs();
	        	byteData04 = imgaddr_recursion_04.getBytedata();
	        	bufferedImage_04 = imgaddr_recursion_04.getImage();
	        }
	        //If there is no image is selected, automatically the default one will be set.
	        else if(fileName_04.equals("")) {
	        	ServletContext context = getServletContext();
	        	fileContent_04 = context.getResourceAsStream("/common/img/img_no_image_02.jpg");

	        	byteData04 = streamProcessor.readAll(fileContent_04);
	        	bufferedImage_04 = ImageIO.read(fileContent_04);
	        }
	        else {
	        	//Image is set and will be taken
	        	fileContent_04 = filePart_04.getInputStream();
	        	byteData04 = streamProcessor.readAll(fileContent_04);
		        bufferedImage_04 = ImageIO.read(filePart_04.getInputStream());
	        }

	        imgAddress_04.setImgHtml(imgHtml_04);
		    imgAddress_04.setImgAddress(fileName_04);
		    imgAddress_04.setFilePart(filePart_04);
		    imgAddress_04.setImage(bufferedImage_04);
		    imgAddress_04.setIs(fileContent_04);
		    imgAddress_04.setBytedata(byteData04);

		    //Set the image data into the session
		    session.setAttribute("article_img_path_01", imgAddress_01);
		    session.setAttribute("article_img_path_02", imgAddress_02);
		    session.setAttribute("article_img_path_03", imgAddress_03);
		    session.setAttribute("article_img_path_04", imgAddress_04);

		    //After session gets ready, move on to the tentative article information confirmation page
	  	    response.sendRedirect("https://aws-warehouse58th.com/article/confirm");
	      }
	      catch (NumberFormatException e) {
	        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

	        pe.printStackTrace();
	        HttpSession session = request.getSession();
	        session.setAttribute("Except", pe);

	        getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/error.jsp").forward(request, response);
	        return;
	      }
	 }
}
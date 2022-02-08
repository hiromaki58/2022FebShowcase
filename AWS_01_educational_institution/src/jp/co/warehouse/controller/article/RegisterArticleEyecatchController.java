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

@WebServlet("/article/register_02")
@MultipartConfig
public class RegisterArticleEyecatchController extends HttpServlet {

	private static final long serialVersionUID = 6427058099740575638L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		String action = request.getParameter("action");

		//Session is already set
		if ((adminLogin != null && action == null) || (userLogin != null && action == null)) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/register_02.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		//Create the parts will be used at this post method
		String imgHtml = null;
		ImgAddress imgAddress = new ImgAddress();
		StreamProcessor streamProcessor = new StreamProcessor();
		Part filePart = null;
	    String fileName = null;

	    try {
	    	filePart = request.getPart("article_eyecatch"); // Retrieves <input type="file" name="file">
	        fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

	        InputStream fileContent = null;
	        byte[] byteData = null;
	        BufferedImage bufferedImage = null;
	        /*
	         * In case of the back button is selected, there is no file name,
	         * so get the file name from the session and reset the session
	         */
	        if(fileName.equals("") && (ImgAddress) session.getAttribute("eyecatch_path") != null) {
	        	ImgAddress imgaddr_eyecatch = new ImgAddress();
	        	imgaddr_eyecatch = (ImgAddress) session.getAttribute("eyecatch_path");

	        	fileName = imgaddr_eyecatch.getImgAddress();
	        	filePart = imgaddr_eyecatch.getFilePart();
	        	fileContent = imgaddr_eyecatch.getIs();
	        	byteData = imgaddr_eyecatch.getBytedata();
	        	bufferedImage = imgaddr_eyecatch.getImage();
	        }
	        /*
	         * If no image is selected
	         * add the default image.
	         */
	        else if(fileName.equals("")) {
	        	ServletContext context = getServletContext();
	        	fileContent = context.getResourceAsStream("/common/img/img_no_image_03.jpg");

	        	byteData = streamProcessor.readAll(fileContent);
	        	bufferedImage = ImageIO.read(fileContent);
	        }
	        /*
	         * When making up the bland new eye catch image for the article
	         */
	        else {
	        	fileContent = filePart.getInputStream();
		        byteData = streamProcessor.readAll(fileContent);
		        bufferedImage = ImageIO.read(filePart.getInputStream());
	        }
	        /*
	         * Store the all information what the instance will require to be activated
	         */
	        imgAddress.setImgHtml(imgHtml);
		    imgAddress.setImgAddress(fileName);
		    imgAddress.setFilePart(filePart);
		    imgAddress.setIs(fileContent);
		    imgAddress.setBytedata(byteData);
		    imgAddress.setImage(bufferedImage);

		    //Set the session which is needed at the conformation page
		    session.setAttribute("eyecatch_path", imgAddress);
		    response.sendRedirect("https://aws-warehouse58th.com/article/register_03");
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
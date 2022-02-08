package jp.co.warehouse.controller.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jp.co.warehouse.dao.image.SetImageDAO;
import jp.co.warehouse.entity.ImgAddress;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.stream.StreamProcessor;

/**
 * This class is for registration the user profile image.
 * @author hirog
 * Sep 15, 2021
 *
 */
@WebServlet("/user/register_user_02")
@MultipartConfig
public class UserRegisterImageController extends HttpServlet {

	private static final long serialVersionUID = 8269401208970424028L;

	/**
	 * Show the user image registration page.
	 * @author hirog
	 * Sep 15, 2021
	 *
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		User login = null;
		String action = null;
		String email = null;
		ImgAddress imgaddr = new ImgAddress();

		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			login = (User) session.getAttribute("user");
			action = request.getParameter("action");
			email = login.getEmail();
		}

		//Session is already set
		if (login != null && action == null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/register_user_02.jsp").forward(request, response);
		}
		else if (login != null && action.equals("done")) {

			if((ImgAddress) session.getAttribute("path") != null) {
				imgaddr= (ImgAddress) session.getAttribute("path");
			}

			try {
				SetImageDAO setImageDAO = new SetImageDAO();
		        byte[] is2 = imgaddr.getBytedata();
		        setImageDAO.addUserImgByUser(is2, email);

		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/register_user_complete_02.jsp");
		  	  	dispatcher.forward(request, response);

		  	  	session.removeAttribute("path");
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
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	/**
	 * To register the user profile image
	 * @author hirog
	 * Sep 15, 2021
	 *
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    String imgHtml = null;
	    ImgAddress imgAddress = new ImgAddress();
	    StreamProcessor streamProcessor = new StreamProcessor();
	    Part filePart = null;
	    String fileName = null;

	    try {
	        filePart = request.getPart("user_img"); // Retrieves <input type="file" name="file">
	        fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

	        InputStream fileContent = null;
	        byte[] byteData = null;
	        BufferedImage bufferedImage = null;

	        if(fileName.equals("")) {
	        	ServletContext context = getServletContext();
	        	fileContent = context.getResourceAsStream("/common/img/img_nameless_user.jpg");

	        	byteData = streamProcessor.readAll(fileContent);
	        	bufferedImage = ImageIO.read(fileContent);
	        }
	        else {
	        	fileContent = filePart.getInputStream();
		        byteData = streamProcessor.readAll(fileContent);
		        bufferedImage = ImageIO.read(filePart.getInputStream());
	        }

	        imgAddress.setImgHtml(imgHtml);
		    imgAddress.setImgAddress(fileName);
		    imgAddress.setFilePart(filePart);
		    imgAddress.setImage(bufferedImage);
		    imgAddress.setIs(fileContent);
		    imgAddress.setBytedata(byteData);

		    HttpSession session = request.getSession();
		    session.setAttribute("path", imgAddress);

		    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/register_user_confirm_02.jsp");
	  	  	dispatcher.forward(request, response);
	      }
	      catch (NumberFormatException e) {
	        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

	        pe.printStackTrace();
	        HttpSession session = request.getSession();
	        session.setAttribute("Except", pe);

	        getServletContext().getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
	        return;
	      }
	  }

	/**
	 * Byte stream process for the images
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	inputStream
	 * @return	image byte stream
	 * @throws 	IOException
	 */
	public byte[] readAll(InputStream inputStream) throws IOException {
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    byte [] buffer = new byte[1024];
	    while(true) {
	        int len = inputStream.read(buffer);
	        if(len < 0) {
	            break;
	        }
	        bout.write(buffer, 0, len);
	    }
	    return bout.toByteArray();
	}
}

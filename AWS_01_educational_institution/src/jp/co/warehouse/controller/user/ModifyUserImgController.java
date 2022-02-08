package jp.co.warehouse.controller.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jp.co.warehouse.dao.image.SetImageDAO;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.stream.StreamProcessor;

/*
 * This class is used for updating the 
 * user profile image.
 */
@MultipartConfig
@WebServlet("/user/modification_02")
public class ModifyUserImgController extends HttpServlet {

	private static final long serialVersionUID = -1189551187835006424L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the login info
		User userLogin = null;

		//Set the login info
		HttpSession session = request.getSession();
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

		String mailAddress = "";
		UserRegisterUser retrieveSelfRegisteredUserImgInfo = new UserRegisterUser();

		/*
		 *Session is already set
		 *Login with user account 
		 */
		if (userLogin != null) {
			mailAddress = userLogin.getEmail();
			try {
				UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
				retrieveSelfRegisteredUserImgInfo = userGetUserInfoDao.getSelfRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e1) {
				e1.printStackTrace();
			}

			session.setAttribute("retrieveSelfRegisteredUserInfo", retrieveSelfRegisteredUserImgInfo);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/modification_02.jsp").forward(request, response);
		}
		else {
			// In case of no login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/no_session_user.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialize the login info
		User userLogin = null;

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		//Set the login info
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}

	    StreamProcessor sp = new StreamProcessor();
	    Part filePart = null;
	    String email = userLogin.getEmail();

	    try {
	        filePart = request.getPart("user_img"); // Retrieves <input type="file" name="file">

	        //Have the updated image data as stream
	        //and stored into the buffered image.
	        InputStream fileContent = filePart.getInputStream();
	        byte[] byteData = sp.readAll(fileContent);
	        BufferedImage bufferedImage = ImageIO.read(filePart.getInputStream());
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        
	        try {
	        	ImageIO.write(bufferedImage, "jpg", baos);
	        }
			catch(IllegalArgumentException e) {
			  getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/img_format_error.jsp").forward( request, response);
			}
	        
	        //release the buffered image data 
			baos.flush();
			baos.close();

			try {
				SetImageDAO setImageDAO = new SetImageDAO();
		        byte[] is2 = byteData;
		        //Send the image byte array and email address to update the user profile image
		        setImageDAO.addUserImgByUser(is2, email);

		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user/user_info.jsp");
		  	  	dispatcher.forward(request, response);
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
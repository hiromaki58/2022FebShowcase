package jp.co.warehouse.controller.admin;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;

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

import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.image.SetImageDAO;
import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.stream.StreamProcessor;

/*
 * This class is used for change the user image by the administration authorization.
 */
@WebServlet("/admin/modification_user_02")
@MultipartConfig()
public class AdminModifyUserImgController extends HttpServlet {

	private static final long serialVersionUID = 8213110910117953897L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session information
		Admin adminLogin = null;

		//Receive the session info
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		UserGetUserInfoDAO getUserInfoDao = new UserGetUserInfoDAO();
		AdminRegisterUser registeredUser = new AdminRegisterUser();
		UserRegisterUser retrieveUserImgInfo = new UserRegisterUser();

		/*
		 *Session is already set
		 *Login with admin account 
		 */
		if (adminLogin != null) {
			
			/*
			 *Receive the user id from the user image modification page.
			 *The hidden input html supplies the id. This is the way to provide in the post method.
			 */
			try {
				retrieveUserImgInfo = getUserInfoDao.getSelfRegisteredUserInfo(registeredUser.getUser_mail());
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
			
			session.setAttribute("retrieveSelfRegisteredUserInfo", retrieveUserImgInfo);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/modification_02.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialize the session information
		Admin adminLogin = null;

		//Set the session information
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		if (adminLogin != null) {
			/*
			 * This part is needed to have a parameter coming from when the user profile image is updated.
			 * Because of enctype="multipart/form-data", we cannot use the regular getParameter method.
			 * Instead of that the below part is applied.
			 */
			Collection<Part> parts = request.getParts();
	        parts.stream().forEach(part -> {
	            /*
	             *Get the content type of the parameter
	             *Ex) image/jpeg
	             */
	            String contentType = part.getContentType();
	            if ( contentType == null) {
	                try(InputStream inputStream = part.getInputStream()) {
	                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
	                    log( bufReader.lines().collect(Collectors.joining()));

	                } catch (IOException e) {
	                    throw new RuntimeException(e);
	                }
	            }
	        });

	        /*
			 *Receive the user id from the user image modification page.
			 *The hidden input html supplies the id. This is the way to provide in the post method.
			 */
			String stringuserId = request.getParameter("userId");
			int intId = Integer.parseInt(stringuserId);
			AdminRegisterUser registeredUser = new AdminRegisterUser();
		    StreamProcessor sp = new StreamProcessor();
		    Part filePart = null;
		    String email = "";

		    try {
		    	/*
		    	 * We use same jsp to change the user profile image both of admin account and user account,
		    	 * and at user modification side they only use user instance.
		    	 *
		    	 * This means there is no direct access to the registered_user instance.
		    	 * So, the user table id is the key to get the registered_user instance below.
		    	 */
		    	AdminGetUserInfoDAO adminGetUserInfoDAO = new AdminGetUserInfoDAO();
		    	registeredUser = adminGetUserInfoDAO.getUserByAdminWithUserId(intId);
		    	
		    	//Get the email address as a key to trigger the SQL.
		    	email = registeredUser.getUser_mail();
		        filePart = request.getPart("user_img"); // Retrieves <input type="file" name="file">

		        InputStream fileContent = filePart.getInputStream();
		        byte[] byteData = sp.readAll(fileContent);
		        BufferedImage bufferedImage = ImageIO.read(filePart.getInputStream());

		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "jpg", baos);
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
		      catch (DatabaseException e1) {
				e1.printStackTrace();
			}
		    catch (SystemException e1) {
				e1.printStackTrace();
		    }
		}
	}
}
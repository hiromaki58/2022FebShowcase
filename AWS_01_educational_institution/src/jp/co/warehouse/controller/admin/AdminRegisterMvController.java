package jp.co.warehouse.controller.admin;

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

import org.apache.commons.lang3.StringUtils;

import jp.co.warehouse.dao.image.MvLinkAddressDAO;
import jp.co.warehouse.dao.image.SetImageDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.ImgAddress;
import jp.co.warehouse.entity.MainVisualLinkAddress;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;
import jp.co.warehouse.stream.StreamProcessor;

@WebServlet("/admin/register_mv")
@MultipartConfig
public class AdminRegisterMvController extends HttpServlet {

	private static final long serialVersionUID = 8399831096560489452L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;

		//Set the session
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		//"action" parameter distinguish between just showing up the page or register the new article
		String action = request.getParameter("action");

		//Session is already set
		if ((adminLogin != null && action == null)) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/register_mv.jsp").forward(request, response);
		}
		else if (adminLogin != null && action.equals("done")) {
			ImgAddress imgaddrMainView = new ImgAddress();
			//This constant will store the image extension information
			String eyecatchMainView = "";

			//if there is ImgAdress session scope
			if((ImgAddress) session.getAttribute("mainVisualPath") != null) {
				imgaddrMainView= (ImgAddress) session.getAttribute("mainVisualPath");

				//Get the image extension
				if(imgaddrMainView.getImgAddress().matches(".*jpg.*")) {
					eyecatchMainView = "jpg";
				}
				else if(imgaddrMainView.getImgAddress().matches(".*png.*")) {
					eyecatchMainView = "png";
				}
				else if(imgaddrMainView.getImgAddress().matches(".*gif.*")) {
					eyecatchMainView = "gif";
				}
				else {
					eyecatchMainView = "jpg";
				}
			}

			try {
				//Prepare the accress to the database
		        MvLinkAddressDAO addressDao = new MvLinkAddressDAO();
		        //Get the byte date from the session.
		        byte[] mvByteData = imgaddrMainView.getBytedata();

		        //Get the new url for the top page top image
		        String url = "";
		        if((MainVisualLinkAddress) session.getAttribute("mvlinkAddress") != null) {
		        	MainVisualLinkAddress mvlinkAddress = (MainVisualLinkAddress) session.getAttribute("mvlinkAddress");

		        	if(mvlinkAddress.getAddress() != "") {
		        		url = mvlinkAddress.getAddress();
		        	}
		        	else {
		        		url = "";
		        	}
		        }
		        
		        /*
		         * Register the image at the top of the top front page.
		         */
		        SetImageDAO setImageDao = new SetImageDAO();
		        setImageDao.updateMainView(mvByteData, eyecatchMainView);
		        
		        /*
		         * Set the new link address into the database
		         *
		         * In case of there is no new address which means last address will be used again,
		         * the address is not changed.
		         */
		        if(StringUtils.isEmpty(url)) {
		        	System.out.println("MV link at AdminRegisterMvController when url is empty : " + url);
		        }
		        else {
		        	addressDao.updateMvLinkAddress(url);
		        }

		        //Show the message the updating is done.
		        getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/register_mv_complete.jsp").forward(request, response);
		  	  	//Rid off the session which is not needed.
		  	  	session.removeAttribute("mainVisualPath");
		  	  	session.removeAttribute("mvlinkAddress");
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
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		//Initialize the session
		Admin adminLogin = null;

		//Set the session
		if((Admin) session.getAttribute("admin") != null) {
		  adminLogin = (Admin) session.getAttribute("admin");
		}

		//Create the parts will be used at this post method
		String imgHtml = null;
		ImgAddress imgAddress = new ImgAddress();
		MainVisualLinkAddress mvlinkAddress = new MainVisualLinkAddress();
		StreamProcessor streamProcessor = new StreamProcessor();
		Part filePart = null;
	    String fileName = null;

	    //if login as admin
	  	if(adminLogin != null) {
		    try {
		    	//Have the url which will be used as the target link of the top image
		    	mvlinkAddress.setAddress(request.getParameter("mv_addr_web"));

		    	filePart = request.getPart("main_view"); // Retrieves <input type="file" name="file">
		        fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

		        InputStream fileContent = null;
		        byte[] byteData = null;
		        BufferedImage bufferedImage = null;
		        
		        /*
		         * In case of the back button is selected, there is no file name,
		         * so get the file name from the session and reset the session with the original data
		         */
		        if(fileName.equals("") && (ImgAddress) session.getAttribute("mainVisualPath") != null) {
		        	ImgAddress imgaddr_main_view = new ImgAddress();
		        	imgaddr_main_view = (ImgAddress) session.getAttribute("mainVisualPath");

		        	fileName = imgaddr_main_view.getImgAddress();
		        	filePart = imgaddr_main_view.getFilePart();
		        	fileContent = imgaddr_main_view.getIs();
		        	byteData = imgaddr_main_view.getBytedata();
		        	bufferedImage = imgaddr_main_view.getImage();
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

			    //Set the session which is needed at the confirmation page
			    session.setAttribute("mvlinkAddress", mvlinkAddress);
			    session.setAttribute("mainVisualPath", imgAddress);
			    response.sendRedirect("https://aws-warehouse58th.com/admin/register_mv_confirm");
		      }
		      catch (NumberFormatException e) {
		        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);
		        pe.printStackTrace();
		        session.setAttribute("Except", pe);

		        getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/error.jsp").forward(request, response);
		        return;
		      }
	  	}
	  	else {
	  		// No login action is done yet
	  		getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
	  	}
	}
}
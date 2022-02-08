package jp.co.warehouse.controller.article;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;

import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.image.SetImageDAO;
import jp.co.warehouse.dao.article.AdminSetArticleDAO;
import jp.co.warehouse.dao.article.UserSetArticleDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.ImgAddress;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This class is used to register the new article info and images.
 * 
 * Get method with "done" stores the new article data into DB, and the post method will store the info,
 * which is gathered by the input in HTML, in the session temporary.
 * 
 * The session will be destroyed the get method finishes to register the article info into DB.
 * @author hirog
 * Sep 15, 2021
 *
 */
@WebServlet("/article/register_01")
public class RegisterArticleController extends HttpServlet {

	private static final long serialVersionUID = 5761764047026846415L;

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

		//"action" parameter distinguish between just showing up the page or register the new article
		String action = request.getParameter("action");
		int articleIdNumber = 0;

		ImgAddress imgaddrEyecatch = new ImgAddress();
		ImgAddress imgaddr01 = new ImgAddress();
		ImgAddress imgaddr02 = new ImgAddress();
		ImgAddress imgaddr03 = new ImgAddress();
		ImgAddress imgaddr04 = new ImgAddress();

		@SuppressWarnings("unused")
		BufferedImage bufferedEyecatch = null;
		@SuppressWarnings("unused")
		BufferedImage bufferedImgaddr01 = null;
		@SuppressWarnings("unused")
		BufferedImage bufferedImgaddr02 = null;
		@SuppressWarnings("unused")
		BufferedImage bufferedImgaddr03 = null;
		@SuppressWarnings("unused")
		BufferedImage bufferedImgaddr04 = null;

		/*
		 * Session is already set
		 * Just open the page 
		 */
		if (adminLogin != null && action == null) {
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/register_01.jsp").forward(request, response);
		}
		else if(userLogin != null && action == null){
			String mailAddress = userLogin.getEmail();
			AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
			AdminRegisterUser registeredUser = new AdminRegisterUser();

			try {
				registeredUser = adminGetUserInfoDao.getRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e1) {
				e1.printStackTrace();
			}

			/*
			 * This session will provide the user name.
			 * It will be registered as a part of the article information.
			 */
			session.setAttribute("registeredUserForArticle", registeredUser);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/register_01.jsp").forward(request, response);
		}
		//This part is triggered if login with admin accont and the article registration confirmation process is over.
		else if ((adminLogin != null) && action.equals("done")) {
			//Retrive article info session scope
			Article articleInfo = (Article) session.getAttribute("newArticle");
			AdminRegisterUser userAppointedByAssociation = (AdminRegisterUser)session.getAttribute("userAppointedByAssociation");

			//This constant will store the image extension information
			String eyecatchType = "";
			String articleImg01Type = "";
			String articleImg02Type = "";
			String articleImg03Type = "";
			String articleImg04Type = "";

			//if there is ImgAdress session scope
			//Eyecatch
			if((ImgAddress) session.getAttribute("eyecatch_path") != null) {
				imgaddrEyecatch= (ImgAddress) session.getAttribute("eyecatch_path");
				bufferedEyecatch = imgaddrEyecatch.getImage();

				//Get the image extension
				if(imgaddrEyecatch.getImgAddress().matches(".*jpg.*")) {
					eyecatchType = "jpg";
				}
				else if(imgaddrEyecatch.getImgAddress().matches(".*png.*")) {
					eyecatchType = "png";
				}
				else if(imgaddrEyecatch.getImgAddress().matches(".*gif.*")) {
					eyecatchType = "gif";
				}
				else {
					eyecatchType = "jpg";
				}
			}
			//article img 01
			if((ImgAddress) session.getAttribute("article_img_path_01") != null) {
				imgaddr01= (ImgAddress) session.getAttribute("article_img_path_01");
				bufferedImgaddr01 = imgaddr01.getImage();

				//Get the image extension
				if(imgaddr01.getImgAddress().matches(".*jpg.*")) {
					articleImg01Type = "jpg";
				}
				else if(imgaddr01.getImgAddress().matches(".*png.*")) {
					articleImg01Type = "png";
				}
				else if(imgaddr01.getImgAddress().matches(".*gif.*")) {
					articleImg01Type = "gif";
				}
				else {
					articleImg01Type = "jpg";
				}
			}
			//article img 02
			if((ImgAddress) session.getAttribute("article_img_path_02") != null) {
				imgaddr02= (ImgAddress) session.getAttribute("article_img_path_02");
				bufferedImgaddr02 = imgaddr02.getImage();

				//Get the image extension
				if(imgaddr02.getImgAddress().matches(".*jpg.*")) {
					articleImg02Type = "jpg";
				}
				else if(imgaddr02.getImgAddress().matches(".*png.*")) {
					articleImg02Type = "png";
				}
				else if(imgaddr02.getImgAddress().matches(".*gif.*")) {
					articleImg02Type = "gif";
				}
				else {
					articleImg02Type = "jpg";
				}
			}
			//article img 03
			if((ImgAddress) session.getAttribute("article_img_path_03") != null) {
				imgaddr03= (ImgAddress) session.getAttribute("article_img_path_03");
				bufferedImgaddr03 = imgaddr03.getImage();

				//Get the image extension
				if(imgaddr03.getImgAddress().matches(".*jpg.*")) {
					articleImg03Type = "jpg";
				}
				else if(imgaddr03.getImgAddress().matches(".*png.*")) {
					articleImg03Type = "png";
				}
				else if(imgaddr03.getImgAddress().matches(".*gif.*")) {
					articleImg03Type = "gif";
				}
				else {
					articleImg03Type = "jpg";
				}
			}
			//article img 04
			if((ImgAddress) session.getAttribute("article_img_path_04") != null) {
				imgaddr04= (ImgAddress) session.getAttribute("article_img_path_04");
				bufferedImgaddr04 = imgaddr04.getImage();

				//Get the image extension
				if(imgaddr04.getImgAddress().matches(".*jpg.*")) {
					articleImg04Type = "jpg";
				}
				else if(imgaddr04.getImgAddress().matches(".*png.*")) {
					articleImg04Type = "png";
				}
				else if(imgaddr04.getImgAddress().matches(".*gif.*")) {
					articleImg04Type = "gif";
				}
				else {
					articleImg04Type = "jpg";
				}
			}

			try {
				AdminSetArticleDAO adminSetArticleDAO = new AdminSetArticleDAO(); 
				//Register the new article info with the user name which is appointed by the administration.
				articleIdNumber = adminSetArticleDAO.adminAddArticleInfo(articleInfo, userAppointedByAssociation.getRegisteredUserId());

		        //Get the byte date from the session.
		        byte[] eyecatchByteData = imgaddrEyecatch.getBytedata();
		        byte[] articleImg01ByteData = imgaddr01.getBytedata();
		        byte[] articleImg02ByteData = imgaddr02.getBytedata();
		        byte[] articleImg03ByteData = imgaddr03.getBytedata();
		        byte[] articleImg04ByteData = imgaddr04.getBytedata();

		        /*
		         * Register the article image.
		         * "articleIdNumber" is needed to register the image id into the article table.
		         */
		        SetImageDAO setImageDAO = new SetImageDAO();
		        setImageDAO.addArticleImg(eyecatchByteData, articleImg01ByteData, articleImg02ByteData, articleImg03ByteData, articleImg04ByteData, articleIdNumber,
		        		eyecatchType, articleImg01Type, articleImg02Type, articleImg03Type, articleImg04Type);

		        //Show the message the registration is completed.
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/article/register_complete.jsp");
		  	  	dispatcher.forward(request, response);

		  	  	//Rid off the session which is not needed.
		  	  	session.removeAttribute("newArticle");
			  	session.removeAttribute("eyecatch_path");
			  	session.removeAttribute("article_img_path_01");
			  	session.removeAttribute("article_img_path_02");
			  	session.removeAttribute("article_img_path_03");
			  	session.removeAttribute("article_img_path_04");
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
		//if user wanna create a new article
		else if ((userLogin != null) && action.equals("done")) {
			String mailAddress = userLogin.getEmail();
			AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
			AdminRegisterUser registeredUser = new AdminRegisterUser();
			try {
				registeredUser = adminGetUserInfoDao.getRegisteredUserInfo(mailAddress);
			}
			catch (DatabaseException | SystemException e1) {
				e1.printStackTrace();
			}

			//This constant will store the image extension information
			String eyecatchType = "";
			String articleImg01Type = "";
			String articleImg02Type = "";
			String articleImg03Type = "";
			String articleImg04Type = "";

			//Retrive article info session scope
			Article articleInfo = (Article) session.getAttribute("newArticle");

			//if there is ImgAdress session scope
			//Eyecatch
			if((ImgAddress) session.getAttribute("eyecatch_path") != null) {
				imgaddrEyecatch= (ImgAddress) session.getAttribute("eyecatch_path");
				bufferedEyecatch = imgaddrEyecatch.getImage();

				//Get the image extension
				if(imgaddrEyecatch.getImgAddress().matches(".*jpg.*")) {
					eyecatchType = "jpg";
				}
				else if(imgaddrEyecatch.getImgAddress().matches(".*png.*")) {
					eyecatchType = "png";
				}
				else if(imgaddrEyecatch.getImgAddress().matches(".*gif.*")) {
					eyecatchType = "gif";
				}
				else {
					eyecatchType = "jpg";
				}
			}
			//article img 01
			if((ImgAddress) session.getAttribute("article_img_path_01") != null) {
				imgaddr01= (ImgAddress) session.getAttribute("article_img_path_01");
				bufferedImgaddr01 = imgaddr01.getImage();

				//Get the image extension
				if(imgaddr01.getImgAddress().matches(".*jpg.*")) {
					articleImg01Type = "jpg";
				}
				else if(imgaddr01.getImgAddress().matches(".*png.*")) {
					articleImg01Type = "png";
				}
				else if(imgaddr01.getImgAddress().matches(".*gif.*")) {
					articleImg01Type = "gif";
				}
				else {
					articleImg01Type = "jpg";
				}
			}
			//article img 02
			if((ImgAddress) session.getAttribute("article_img_path_02") != null) {
				imgaddr02= (ImgAddress) session.getAttribute("article_img_path_02");
				bufferedImgaddr02 = imgaddr02.getImage();

				//Get the image extension
				if(imgaddr02.getImgAddress().matches(".*jpg.*")) {
					articleImg02Type = "jpg";
				}
				else if(imgaddr02.getImgAddress().matches(".*png.*")) {
					articleImg02Type = "png";
				}
				else if(imgaddr02.getImgAddress().matches(".*gif.*")) {
					articleImg02Type = "gif";
				}
				else {
					articleImg02Type = "jpg";
				}
			}
			//article img 03
			if((ImgAddress) session.getAttribute("article_img_path_03") != null) {
				imgaddr03= (ImgAddress) session.getAttribute("article_img_path_03");
				bufferedImgaddr03 = imgaddr03.getImage();

				//Get the image extension
				if(imgaddr03.getImgAddress().matches(".*jpg.*")) {
					articleImg03Type = "jpg";
				}
				else if(imgaddr03.getImgAddress().matches(".*png.*")) {
					articleImg03Type = "png";
				}
				else if(imgaddr03.getImgAddress().matches(".*gif.*")) {
					articleImg03Type = "gif";
				}
				else {
					articleImg03Type = "jpg";
				}
			}
			//article img 04
			if((ImgAddress) session.getAttribute("article_img_path_04") != null) {
				imgaddr04= (ImgAddress) session.getAttribute("article_img_path_04");
				bufferedImgaddr04 = imgaddr04.getImage();

				//Get the image extension
				if(imgaddr04.getImgAddress().matches(".*jpg.*")) {
					articleImg04Type = "jpg";
				}
				else if(imgaddr04.getImgAddress().matches(".*png.*")) {
					articleImg04Type = "png";
				}
				else if(imgaddr04.getImgAddress().matches(".*gif.*")) {
					articleImg04Type = "gif";
				}
				else {
					articleImg04Type = "jpg";
				}
			}
			
			try {
				UserSetArticleDAO userSetArticleDAO = new UserSetArticleDAO();
				/*
				 *Register the new article info. String info only here.
				 *User id is required to set the user id and article id into the user_article_match table so that we can tell
				 *which article is created by who.
				 */
				articleIdNumber = userSetArticleDAO.userAddArticleInfo(articleInfo, registeredUser.getRegisteredUserId());

		        byte[] eyecatchByteData = imgaddrEyecatch.getBytedata();
		        byte[] articleImg01ByteData = imgaddr01.getBytedata();
		        byte[] articleImg02ByteData = imgaddr02.getBytedata();
		        byte[] articleImg03ByteData = imgaddr03.getBytedata();
		        byte[] articleImg04ByteData = imgaddr04.getBytedata();

		        /*
		         * Register the article image.
		         * "articleIdNumber" is needed to register the image id into the article table.
		         */
		        SetImageDAO setImageDAO = new SetImageDAO();
		        setImageDAO.addArticleImg(eyecatchByteData, articleImg01ByteData, articleImg02ByteData, articleImg03ByteData, articleImg04ByteData, articleIdNumber,
		        		eyecatchType, articleImg01Type, articleImg02Type, articleImg03Type, articleImg04Type);

		        //Show the message the registration is completed.
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/article/register_complete.jsp");
		  	  	dispatcher.forward(request, response);

		  	    //Rid off the session which is not needed.
		  	  	session.removeAttribute("newArticle");
			  	session.removeAttribute("eyecatch_path");
			  	session.removeAttribute("article_img_path_01");
			  	session.removeAttribute("article_img_path_02");
			  	session.removeAttribute("article_img_path_03");
			  	session.removeAttribute("article_img_path_04");
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialize the session
		Admin adminLogin = null;
		User userLogin = null;

		//Set the session
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
		  adminLogin = (Admin) session.getAttribute("admin");
		}
		else if ((User) session.getAttribute("user") != null) {
		  userLogin = (User) session.getAttribute("user");
		}

		request.setCharacterEncoding("UTF-8");
	    String article_title = null;
	    String category = null;

	    Date opening_day = null;
	    String opening_hour = null;
	    String opening_min = null;
	    Date closing_day = null;
	    String closing_hour = null;
	    String closing_min = null;

	    String userSearchWord = null;
	    StringBuilder user = new StringBuilder();
	    StringBuilder adminUser = new StringBuilder();

	    String company_place = null;
	    String company_addr = null;
	    String company_addr_cl = null;
	    String ceo = null;
	    String ceo_cl = null;
	    String capital = null;
	    String employeeNumber = null;
	    Date establishedDate = null;
	    String company_mail = null;
	    String company_phone = null;
	    String company_url = null;
	    String introduction = null;
	    String introduction_cl = null;

	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		//if login as admin
		if(adminLogin != null) {
			//Gather the String info from the input in HTML
		    try {
		    	article_title = request.getParameter("article_title");
		    	
		    	if(request.getParameter("classification").equals(null)) {
		    		category = "not_specified";
		    	}
		    	else {
		    		category = request.getParameter("classification");
		    	}

		    	if(request.getParameter("opening_day").equals("")) {
		    		opening_day = null;
		    	}
		    	else {
		    		opening_day = df.parse(request.getParameter("opening_day"));
		    	}
		    	opening_hour = request.getParameter("opening_hour");
		    	opening_min = request.getParameter("opening_min");

		    	if(request.getParameter("closing_day").equals("")) {
		    		closing_day = null;
		    	}
		    	else {
		    		closing_day = df.parse(request.getParameter("closing_day"));
		    	}
		    	closing_hour = request.getParameter("closing_hour");
		    	closing_min = request.getParameter("closing_min");

		    	//Receive the search word to find the user who the administration wishes to assign.
		    	userSearchWord = request.getParameter("user_list");
		    	company_url = request.getParameter("company_url");
		    	company_place = request.getParameter("company_place");
		    	company_addr = request.getParameter("company_addr");

			    ceo = request.getParameter("ceo");
			    capital = request.getParameter("capital");
			    employeeNumber = request.getParameter("employeeNumber");

			    /*
				 * Establish date registration is voluntary.
				 * In case of no date is set,
				 * no date info will be regsitered.
				 */
			    if(request.getParameter("establishedDate").equals("")) {
			    	establishedDate = null;
		    	}
		    	else {
		    		establishedDate = df.parse(request.getParameter("establishedDate"));
		    	}

			    company_mail = request.getParameter("company_mail");
			    company_phone = request.getParameter("company_phone");
			    company_url = request.getParameter("company_url");
			    introduction = request.getParameter("article_introduction");

			    /*
			     * Insert "<br>" where the writer set carriage return,
			     * and the first replaceAll ("<br/>", "") prevents to increase the number of <br> as many as editing the article again and again 
			     */
			    if (ceo != null) {
			    	ceo_cl = ceo.replaceAll(" ", "&nbsp;").replaceAll(",", "&nbsp;");
				}
			    if (company_addr != null) {
			    	company_addr_cl = company_addr.replaceAll("\\;", "&#059;").replaceAll("<br/>", "").replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll("\\'", "&#039;").replaceAll("\"", "&#082;").replaceAll("\\(", "&#040;").replaceAll("\\)", "&#041;").replaceAll("\\:", "&#058;").replaceAll("\\'", "&#039;");
				}
			    if (introduction != null) {
					introduction_cl = introduction.replaceAll("\\;", "&#059;").replaceAll("<br/>", "").replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll("\\'", "&#039;").replaceAll("\"", "&#082;").replaceAll("\\(", "&#040;").replaceAll("\\)", "&#041;").replaceAll("\\:", "&#058;").replaceAll("\\'", "&#039;");
				}
		    }
	        catch (NumberFormatException e) {
		        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

		        pe.printStackTrace();
		        session.setAttribute("Except", pe);

		        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/error.jsp").forward(request, response);
		        return;
		    }
		    catch(ParseException e) {
    			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/date_format_error.jsp").forward(request, response);
    		}

		    /*
		     * This part is for find the user when the admin account wish to create the new article,
		     * and appoint one of the user.
		     * 
		     * If the search box is empty,
		     * Warehouse Association is assign as the author.
		     */
		    AdminRegisterUser userAppointedByAssociation = new AdminRegisterUser();
		    if(userSearchWord != "") {
			    try {
			    	AdminGetUserInfoDAO adminGetUserInfoDAO = new AdminGetUserInfoDAO();
			    	userAppointedByAssociation = adminGetUserInfoDAO.getUserByWordSearch(userSearchWord);
			    }
			    catch (DatabaseException | SystemException e) {
			    	e.printStackTrace();
			    	session.setAttribute("Except", e);

			    	getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward( request, response);
			  	}
		    }
		    else {
		    	userSearchWord = "Warehouse Association";
		    }

		    /*
		     * Get the today's date to record the article registration date
		     */
		    Date today = new Date(); //Today's date
			Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
			
		    try {
			    Article articleInfo = new Article();
			    
			    articleInfo.setArticle_name(article_title);
			    articleInfo.setCategory(category);
			    articleInfo.setOpening_day(opening_day);
			    if(opening_hour != null) {
			    	articleInfo.setOpening_hour(Integer.parseInt(opening_hour));
				}
			    if(opening_min != null) {
			    	articleInfo.setOpening_min(Integer.parseInt(opening_min));
				}

			    articleInfo.setClosing_day(closing_day);
			    if(closing_hour != null) {
			    	articleInfo.setClosing_hour(Integer.parseInt(closing_hour));
				}
			    if(closing_min != null) {
			    	articleInfo.setClosing_min(Integer.parseInt(closing_min));
				}

			    articleInfo.setCompany_url(company_url);

			    //Combine the last and first name into one and insert into article table as "user"
			    if(userSearchWord.equals("Warehouse Association")) {
			    	adminUser.append("Warehouse Association");
			    }
			    else {
			    	adminUser.append(userAppointedByAssociation.getUser_last_name());
				    adminUser.append(" ");
				    adminUser.append(userAppointedByAssociation.getUser_first_name());
			    }
			    
			    articleInfo.setUser(adminUser);
			    articleInfo.setCompany_place(company_place);
			    articleInfo.setCompany_addr(company_addr_cl);
			    articleInfo.setCeo(ceo_cl);
			    articleInfo.setCapital(capital);

			    /*
			     * The programming needs the number of the people how many will join the article,
			     * so if the number is assigned, zero will be stored automatically.
			     */
			    if(employeeNumber.equals("")) {
			    	articleInfo.setEmployeeNumber(0);
			    }else {
			    	articleInfo.setEmployeeNumber(Integer.parseInt(employeeNumber));
			    }

			    articleInfo.setEstablishedDate(establishedDate);
			    articleInfo.setCompany_mail(company_mail);
			    articleInfo.setCompany_phone(company_phone);
			    articleInfo.setCompany_url(company_url);
			    articleInfo.setIntroduction(introduction_cl);
			    
			    //Set the date of today to record the article registration date into the article table
			    articleInfo.setRegistration_day(truncToday);

			    session.setAttribute("newArticle", articleInfo);
			    session.setAttribute("userAppointedByAssociation", userAppointedByAssociation);
			    response.sendRedirect("https://aws-warehouse58th.com/article/register_02");
		    }
		    catch (NumberFormatException nfex) {
	    		getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/register_population_error.jsp").forward(request, response);
	    	}
		}
		//if login as user
		else if(userLogin != null) {
			AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
			AdminRegisterUser registeredUser = new AdminRegisterUser();
			String userEmail = userLogin.getEmail();
			try {
				registeredUser = adminGetUserInfoDao.getRegisteredUserInfo(userEmail);
			}
			catch (DatabaseException e1) {
				e1.printStackTrace();
			}
			catch (SystemException e1) {
				e1.printStackTrace();
			}

		    try {
		    	article_title = request.getParameter("article_title");

		    	if(request.getParameter("opening_day").equals("")) {
		    		opening_day = null;
		    	}
		    	else {
		    		opening_day = df.parse(request.getParameter("opening_day"));
		    	}
		    	opening_hour = request.getParameter("opening_hour");
		    	opening_min = request.getParameter("opening_min");

		    	if(request.getParameter("closing_day").equals("")) {
		    		closing_day = null;
		    	}
		    	else {
		    		closing_day = df.parse(request.getParameter("closing_day"));
		    	}
		    	closing_hour = request.getParameter("closing_hour");
		    	closing_min = request.getParameter("closing_min");

		    	company_url = request.getParameter("company_url");
		    	company_place = request.getParameter("company_place");
		    	company_addr = request.getParameter("company_addr");

			    ceo = request.getParameter("ceo");
			    capital = request.getParameter("capital");
			    employeeNumber = request.getParameter("employeeNumber");

			    /*
				 * Establish date registration is voluntary.
				 * In case of no date is set,
				 * no date info will be regsitered.
				 */
			    if(request.getParameter("establishedDate").equals("")) {
			    	establishedDate = null;
		    	}
		    	else {
		    		establishedDate = df.parse(request.getParameter("establishedDate"));
		    	}

			    company_mail = request.getParameter("company_mail");
			    company_phone = request.getParameter("company_phone");
			    company_url = request.getParameter("company_url");
			    introduction = request.getParameter("article_introduction");

			    /*
			     * Insert "<br>" where the writer set carriage return,
			     * and the first replaceAll ("<br/>", "") prevents to increase the number of <br> as many as editing the article again and again
			     */
			    if (ceo != null) {
			    	ceo_cl = ceo.replaceAll(" ", "&nbsp;").replaceAll(",", "&nbsp;");
				}
			    if (company_addr != null) {
			    	company_addr_cl = company_addr.replaceAll("\\;", "&#059;").replaceAll("<br/>", "").replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll("\\'", "&#039;").replaceAll("\"", "&#082;").replaceAll("\\(", "&#040;").replaceAll("\\)", "&#041;").replaceAll("\\:", "&#058;").replaceAll("\\'", "&#039;");
				}
			    if (introduction != null) {
			    	introduction_cl = introduction.replaceAll("\\;", "&#059;").replaceAll("<br/>", "").replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll("\\'", "&#039;").replaceAll("\"", "&#082;").replaceAll("\\(", "&#040;").replaceAll("\\)", "&#041;").replaceAll("\\:", "&#058;").replaceAll("\\'", "&#039;");
				}
		    }
	        catch (NumberFormatException e) {
		        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

		        pe.printStackTrace();
		        session.setAttribute("Except", pe);

		        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/error.jsp").forward(request, response);
		        return;
		    }
		    catch(ParseException e) {
    			getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/date_format_error.jsp").forward(request, response);
    		}

		    /*
		     * Get the today's date to record the article registration date
		     */
		    Date today = new Date(); //Today's date
			Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second

		    try {
			    Article articleInfo = new Article();
			    
			    articleInfo.setArticle_name(article_title);
			    articleInfo.setCategory("not_specified");
			    articleInfo.setOpening_day(opening_day);
			    if(opening_hour != null) {
			    	articleInfo.setOpening_hour(Integer.parseInt(opening_hour));
				}
			    if(opening_min != null) {
			    	articleInfo.setOpening_min(Integer.parseInt(opening_min));
				}

			    articleInfo.setClosing_day(closing_day);
			    if(closing_hour != null) {
			    	articleInfo.setClosing_hour(Integer.parseInt(closing_hour));
				}
			    if(closing_min != null) {
			    	articleInfo.setClosing_min(Integer.parseInt(closing_min));
				}

			    articleInfo.setCompany_url(company_url);
			    
			    //Combine the last and first name into one and insert into article table as "user".
			    user.append(registeredUser.getUser_last_name());
			    user.append(" ");
			    user.append(registeredUser.getUser_first_name());
			    articleInfo.setUser(user);
			    
			    /*
			     * Set the self registered user id(USER_ID at the table) is retrieved.
			     * Therefore testing the authors register the article or not
			     * the id is the key to identify the article.
			     */
			    articleInfo.setUser_id(registeredUser.getSelfRegisteredUserId());
			    articleInfo.setCompany_place(company_place);
			    articleInfo.setCompany_addr(company_addr_cl);
			    articleInfo.setCeo(ceo_cl);
			    articleInfo.setCapital(capital);

			    /*
			     * The programming needs the number of the people how many will join the article,
			     * so if the number is assigned, zero will be stored automatically.
			     */
			    if(employeeNumber.equals("")) {
			    	articleInfo.setEmployeeNumber(0);
			    }else {
			    	articleInfo.setEmployeeNumber(Integer.parseInt(employeeNumber));
			    }
			    
			    articleInfo.setEstablishedDate(establishedDate);
			    articleInfo.setCompany_mail(company_mail);
			    articleInfo.setCompany_phone(company_phone);
			    articleInfo.setCompany_url(company_url);
			    articleInfo.setIntroduction(introduction_cl);

			    //Set the date of today to record the article registration date into the article table
			    articleInfo.setRegistration_day(truncToday);

			    session.setAttribute("newArticle", articleInfo);
			    response.sendRedirect("https://aws-warehouse58th.com/article/register_02");
		    }
		    catch (NumberFormatException nfex) {
	    		getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/register_population_error.jsp").forward(request, response);
	    	}
		}
	}
}
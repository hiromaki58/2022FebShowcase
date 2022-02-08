package jp.co.warehouse.controller.article;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.dao.user.UserGetUserInfoDAO;
import jp.co.warehouse.dao.utility.MatchDAO;
import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.article.AdminGetArticleDAO;
import jp.co.warehouse.dao.article.AdminSetArticleDAO;
import jp.co.warehouse.dao.article.UserGetArticleDAO;
import jp.co.warehouse.dao.article.UserSetArticleDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/*
 * To modify the article information.
 * The difference between if login as an administration and if as a user,
 * the administration can change the recommendation weight and assign the user to the article.
 *
 * On the other hand it is impossible for the user to modify who will be the ceo of the article.
 */
@WebServlet("/article/modification_01")
public class ModifyArticleController extends HttpServlet {

	private static final long serialVersionUID = 6427058099740575638L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		Admin adminLogin = null;
		User userLogin = null;

		AdminRegisterUser registeredUser = new AdminRegisterUser();
		Article registeredArticle = new Article();
		MatchDAO matchDao = new MatchDAO();

		//Make sure the access is authorized by the session.
		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}
		if((User) session.getAttribute("user") != null) {
			userLogin = (User) session.getAttribute("user");
		}
		if (adminLogin != null) {
			//Recieve the article ID from mypage as the page link in the mypage has the id number
			String stringArticleId = request.getParameter("articleId");
			int articleId = Integer.parseInt(stringArticleId);

			try {
				/*
				 * To get the user who register the article,
				 * 1, Find what the user id in the user_article_match table with the article id
				 * 2, Check the user name by the user id
				 * 3, and set the session to show the name into the article modification page
				 */
				AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
				AdminGetArticleDAO adminGetArticleDao = new AdminGetArticleDAO();
				int userId = matchDao.getUserIdByArticleId(articleId);
				registeredUser = adminGetUserInfoDao.getUserByAdminWithUserId(userId);
				registeredArticle = adminGetArticleDao.getArticleInfoByArticleId(articleId);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("registeredUser", registeredUser);
			session.setAttribute("registeredArticle", registeredArticle);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_01.jsp").forward(request, response);
		}
		//Login is already set as user
		else if (userLogin != null) {
			//Recieve the article ID from mypage as the page link in the mypage has the id number
			String stringArticleId = request.getParameter("articleId");
			int articleId = Integer.parseInt(stringArticleId);

			try {
				/*
				 * To get the user who register the article,
				 * 1, Due to login with the user id user login session is set
				 * 2, Find the name by the e-mail address which is received from the user login session
				 */
				UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				int userId = matchDao.getUserIdByArticleId(articleId);
				
				registeredUser = userGetUserInfoDao.getUserByUserWithUserId(userId);
				registeredArticle = userGetArticleDao.getArticleInfoByAritcleId(articleId);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("registeredUser", registeredUser);
			session.setAttribute("registeredArticle", registeredArticle);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_01.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/no_session.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		Admin adminLogin = null;
		User userLogin = null;
		request.setCharacterEncoding("UTF-8");
		/*
		 * Article id is receive as the input which is set the bottom of the page jsp, name as articleId
		 * Due to this is post method we cannot use get to have the article id
		 */
		String stringArticleId = request.getParameter("articleId");
		int articleId = Integer.parseInt(stringArticleId);

		HttpSession session = request.getSession();
		if ((Admin) session.getAttribute("admin") != null) {
			  adminLogin = (Admin) session.getAttribute("admin");
			}
		if ((User) session.getAttribute("user") != null) {
		  userLogin = (User) session.getAttribute("user");
		}

		request.setCharacterEncoding("UTF-8");
	    String article_title = null;

	    String stringWeight = null;
	    String category = null;

	    Date opening_day = null;
	    String opening_hour = null;
	    String opening_min = null;
	    Date closing_day = null;
	    String closing_hour = null;
	    String closing_min = null;
	    
	    StringBuilder user = new StringBuilder();
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

	    /*
	     * Modify the article detail with admin account
	     * category and recommendation weight will be changed here  
	     */
		if(adminLogin != null) {
			//Get the information from the HTML input
		    try {
		    	article_title = request.getParameter("article_title");
		    	stringWeight = request.getParameter("weight");
		    	category = request.getParameter("category");

		    	opening_day = df.parse(request.getParameter("opening_day"));
		    	opening_hour = request.getParameter("opening_hour");
		    	opening_min = request.getParameter("opening_min");
		    	closing_day = df.parse(request.getParameter("closing_day"));
		    	closing_hour = request.getParameter("closing_hour");
		    	closing_min = request.getParameter("closing_min");
		    	
		    	company_place = request.getParameter("company_place");
		    	company_addr = request.getParameter("company_addr");
			    ceo = request.getParameter("ceo");
			    capital = request.getParameter("capital");
			    employeeNumber = request.getParameter("employeeNumber");
			    establishedDate = df.parse(request.getParameter("establishedDate"));
			    company_mail = request.getParameter("company_mail");
			    company_phone = request.getParameter("company_phone");
			    company_url = request.getParameter("company_url");
			    introduction = request.getParameter("article-introduction");

			    /*
			     * Insert "<br>" where the writer set carriage return,
			     * and the first replaceAll ("<br/>", "") prevents to 
			     * increase the number of <br> as many as editing the article again and again
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

		    //Set the info into the article instance
		    Article articleInfo = new Article();
		    articleInfo.setArticle_name(article_title);
		    
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
		    
		    articleInfo.setWeight(Integer.parseInt(stringWeight));
		    articleInfo.setCategory(category);
		    articleInfo.setCompany_place(company_place);
		    articleInfo.setCompany_addr(company_addr_cl);
		    articleInfo.setCeo(ceo_cl);
		    articleInfo.setCapital(capital);

		    //If there is no number of people is set, automatically zero is set
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

		    /*
		     * Update the article information
		     * The article is determined by the article id and update the article.
		     * Meanwhile the user ID in the user article match table is updated as well
		     */
		    try {
				AdminSetArticleDAO adminSetArticleDao = new AdminSetArticleDAO();
				adminSetArticleDao.adminModifyArticleInfo(articleInfo, articleId);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			} catch (NumberFormatException nfex) {
				//If the number of people is set by rather than int, alert will be issued
	    		getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/register_population_error.jsp").forward(request, response);
	    	}
		    response.sendRedirect("https://aws-warehouse58th.com/article/article_info");
		}

		//if login as user
		else if(userLogin != null) {
			AdminRegisterUser userInfoBean = new AdminRegisterUser();
			String userEmail = userLogin.getEmail();
			try {
				//Calling the user's name
				AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
				userInfoBean = adminGetUserInfoDao.getRegisteredUserInfo(userEmail);
			}
			catch (DatabaseException e1) {
				e1.printStackTrace();
			}
			catch (SystemException e1) {
				e1.printStackTrace();
			}

		    try {
		    	article_title = request.getParameter("article_title");

		    	opening_day = df.parse(request.getParameter("opening_day"));
		    	opening_hour = request.getParameter("opening_hour");
		    	opening_min = request.getParameter("opening_min");
		    	closing_day = df.parse(request.getParameter("closing_day"));
		    	closing_hour = request.getParameter("closing_hour");
		    	closing_min = request.getParameter("closing_min");

		    	company_place = request.getParameter("company_place");
		    	company_addr = request.getParameter("company_addr");
			    ceo = request.getParameter("ceo");
			    capital = request.getParameter("capital");
			    employeeNumber = request.getParameter("employeeNumber");
			    establishedDate = df.parse(request.getParameter("establishedDate"));
			    company_mail = request.getParameter("company_mail");
			    company_phone = request.getParameter("company_phone");
			    company_url = request.getParameter("company_url");
			    introduction = request.getParameter("article-introduction");

			    /*
			     * Insert "<br>" where the writer set carriage return,
			     * and the first replaceAll ("<br/>", "") prevents to 
			     * increase the number of <br> as many as editing the article again and again.
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

		    //Set the info into the article instance
		    Article articleInfo = new Article();
		    articleInfo.setArticle_name(article_title);

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

		    //Combine the last and first name into one and insert into article table as "user"
		    user.append(userInfoBean.getUser_last_name());
		    user.append(" ");
		    user.append(userInfoBean.getUser_first_name());
		    articleInfo.setUser(user);

		    articleInfo.setCompany_place(company_place);
		    articleInfo.setCompany_addr(company_addr_cl);
		    articleInfo.setCeo(ceo_cl);
		    articleInfo.setCapital(capital);

		    //If there is no number of employee is set, automatically zero is set
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

		    //If the number of people is set by rather than int, alert will be issued
		    try {
				UserSetArticleDAO userSetArticleDao = new UserSetArticleDAO();
				userSetArticleDao.userModifyArticleInfo(articleInfo, articleId);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			} catch (NumberFormatException nfex) {
	    		getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/register_population_error.jsp").forward(request, response);
	    	}
		    request.setAttribute("articleInfo", articleInfo);
		    response.sendRedirect("https://aws-warehouse58th.com/article/article_info");
		}
	}
}

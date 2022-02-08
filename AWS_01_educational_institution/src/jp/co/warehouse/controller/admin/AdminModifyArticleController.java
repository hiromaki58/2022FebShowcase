package jp.co.warehouse.controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;

import jp.co.warehouse.dao.admin.AdminGetUserInfoDAO;
import jp.co.warehouse.dao.article.AdminSetArticleDAO;
import jp.co.warehouse.dao.article.UserGetArticleDAO;
import jp.co.warehouse.entity.Admin;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.User;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.ParameterException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * 
 * @author hirog
 * Sep 18, 2021
 *
 */
@WebServlet("/admin/modification_article_01")
public class AdminModifyArticleController extends HttpServlet {

	private static final long serialVersionUID = 6427058099740575638L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//Initialization
		Admin adminLogin = null;
		User userLogin = null;

		String stringArticleId = request.getParameter("articleId");
		int articleId = Integer.parseInt(stringArticleId);

		AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
		AdminRegisterUser userName = new AdminRegisterUser();
		Article registeredArticle = new Article();

		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
			adminLogin = (Admin) session.getAttribute("admin");
		}

		//Login is already set as administrator
		if (adminLogin != null) {
			String mailAddress = userLogin.getEmail();

			try {
				userName = adminGetUserInfoDao.getRegisteredUserInfo(mailAddress);
				UserGetArticleDAO userGetArticleDao = new UserGetArticleDAO();
				registeredArticle = userGetArticleDao.getArticleInfoByAritcleId(articleId);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}

			session.setAttribute("userName", userName);
			session.setAttribute("registeredArticle", registeredArticle);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/modification_01.jsp").forward(request, response);
		}
		else {
			// No login action is done yet
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/no_session_admin.jsp").forward( request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		//Initialization
		Admin adminLogin = null;
		User userLogin = null;

		request.setCharacterEncoding("UTF-8");
		String stringArticleId = request.getParameter("articleId");
		int articleId = Integer.parseInt(stringArticleId);

		HttpSession session = request.getSession();
		if((Admin) session.getAttribute("admin") != null) {
		  adminLogin = (Admin) session.getAttribute("admin");
		}

		//if login as admin
		if(adminLogin != null) {
			AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
			AdminRegisterUser userInfoBean = new AdminRegisterUser();
			
			try {
				userInfoBean = adminGetUserInfoDao.getRegisteredUserInfo(userLogin.getEmail());
			}
			catch (DatabaseException e1) {
				e1.printStackTrace();
			}
			catch (SystemException e1) {
				e1.printStackTrace();
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

		    String[] userList = null;
		    StringBuilder user = new StringBuilder();

		    String company_place = null;
		    String company_addr = null;

		    String ceo = null;
		    String capital = null;
		    String employeeNumber = null;
		    Date establishedDate = null;

		    String company_mail = null;
		    String company_phone = null;
		    String company_url = null;
		    String introduction = null;
		    String introduction_cl = null;

		    List<String> listUser = Arrays.asList();
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		    try {
		    	article_title = request.getParameter("article_title");
		    	category = request.getParameter("category");

		    	opening_day = df.parse((String)request.getAttribute("opening_day"));
		    	opening_hour = request.getParameter("opening_hour");
		    	opening_min = request.getParameter("opening_min");
		    	closing_day = df.parse((String)request.getAttribute("closing_day"));
		    	closing_hour = request.getParameter("closing_hour");
		    	closing_min = request.getParameter("closing_min");

		    	userList = request.getParameterValues("user_list");
		    	company_place = request.getParameter("company_place");
			    company_addr = request.getParameter("company_addr");

			    ceo = request.getParameter("ceo");
			    capital = request.getParameter("capital");
			    employeeNumber = request.getParameter("employeeNumber");

			    establishedDate = df.parse((String)request.getAttribute("establishedDate"));

			    company_mail = request.getParameter("company_mail");
			    company_phone = request.getParameter("company_phone");
			    company_url = request.getParameter("company_url");
			    introduction = request.getParameter("article_introduction");

			    if (introduction != null) {
					introduction_cl = introduction.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll(",", "&nbsp;");
				}

			    if (CollectionUtils.sizeIsEmpty(userList)) {
//			    	getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error_null.jsp").forward(request, response);
//			        return;
			    }
			    else {
			    	listUser = Arrays.asList(userList);
			    }
		    }
		      catch (NumberFormatException e) {
		        ParameterException pe = new ParameterException(ExceptionParameters.PARAMETER_FORMAT_EXCEPTION_MESSAGE, e);

		        pe.printStackTrace();
		        session.setAttribute("Except", pe);

		        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article/error.jsp").forward(request, response);
		        return;
		    } catch (ParseException e) {
				e.printStackTrace();
			}
		    Article article = new Article();

		    article.setArticle_name(article_title);
		    article.setCategory(category);
		    
		    article.setOpening_day(opening_day);
		    if(opening_hour != null) {
		    	article.setOpening_hour(Integer.parseInt(opening_hour));
			}
		    if(opening_min != null) {
		    	article.setOpening_min(Integer.parseInt(opening_min));
			}
		    article.setClosing_day(closing_day);
		    if(closing_hour != null) {
		    	article.setClosing_hour(Integer.parseInt(closing_hour));
			}
		    if(closing_min != null) {
		    	article.setClosing_min(Integer.parseInt(closing_min));
			}

		    article.setUserList(listUser);
		    user.append(userInfoBean.getUser_last_name());
		    user.append(" ");
		    user.append(userInfoBean.getUser_first_name());
		    article.setCompany_place(company_place);
		    article.setCompany_addr(company_addr);

		    article.setCeo(ceo);
		    article.setCapital(capital);
		    article.setEmployeeNumber(Integer.parseInt(employeeNumber));
		    article.setEstablishedDate(establishedDate);

		    article.setCompany_mail(company_mail);
		    article.setCompany_phone(company_phone);
		    article.setCompany_url(company_url);
		    article.setIntroduction(introduction_cl);

		    try {
		    	AdminSetArticleDAO adminSetArticleDao = new AdminSetArticleDAO();
		    	adminSetArticleDao.adminModifyArticleInfo(article, articleId);
			} catch (DatabaseException | SystemException e) {
				e.printStackTrace();
			}
		    response.sendRedirect("https://aws-warehouse58th.com/article/article_info");
		}
	}
}
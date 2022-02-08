package jp.co.warehouse.dao.article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

public class PublicGetArticleDAO extends DAOBase {
	
	/*
	 * The order is determined by the weight which is assigned by the administrator.
	 * Due to show recommended article @ index page top, pick up 6 articless
	 */
	public ArticleInfoArray getArticleInfoArrayByWeight()
	throws DatabaseException, SystemException {

		ArticleInfoArray infoArray = new ArticleInfoArray();
		Date today = new Date(); //Today's date
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
		String formattedToday = null;
		String DATE_PATTERN = "yyyy-MM-dd";

		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);
		PreparedStatement pStmt = null;
		this.open();
		try {
			/*
			 * Check the present time is in the period of the article in public so that the article is confirmed in the released period.
			 */
			String sql = "SELECT * FROM article WHERE RELEASED = 'yes'"
					+ " AND OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' ORDER BY WEIGHT ASC, OPENING_DAY DESC LIMIT 6;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				Article articleInfo = new Article();
				StringBuilder str = new StringBuilder();

				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setCategory(rs.getString("CATEGORY"));

				//If the administration account set multiple user in the article.
				if(rs.getString("user_LIST") != null) {
					String users = rs.getString("user_LIST");
				    List<String> listuser = new ArrayList<String>(Arrays.asList(users.split(",")));
				    articleInfo.setUserList(listuser);
				}

				//In case of the administration doesn't assign any weight on the article
				if(rs.getInt("WEIGHT") != 0) {
				    articleInfo.setWeight(rs.getInt("WEIGHT"));
				}
			    articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
				articleInfo.setUser(str.append(rs.getString("AUTHOR")));
				articleInfo.setCompany_place(rs.getString("PLACE"));
				articleInfo.setCompany_addr(rs.getString("ADDRESS"));
				articleInfo.setCeo(rs.getString("CEO"));
				articleInfo.setEstablishedDate(rs.getDate("ESTABLISHED_DATE"));
				articleInfo.setImg_id(rs.getInt("IMG_ID"));

				infoArray.addArticleInfo(articleInfo);
			}
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return infoArray;
	}
	
	/*
	 * Due to show new arrival article @ index page, pick up 3 articles
	 * Ordered by the start day is closer to now.  
	 */
	public ArticleInfoArray getArticleInfoArrayByDate()
	throws DatabaseException, SystemException {

		ArticleInfoArray infoArray = new ArticleInfoArray();
		Date today = new Date(); //Today's date
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
		String formattedToday = null;

		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);
		PreparedStatement pStmt = null;
		this.open();
		try {
			/*
			 * Check the present time is in the period of the article in public,
			 * and the user who registers the article has chosen the release yes
			 */
			String sql = "SELECT * FROM article WHERE RELEASED = 'yes'"
					+ " AND OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' ORDER BY REGISTRATION_DAY DESC LIMIT 3;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				Article articleInfo = new Article();
				StringBuilder str = new StringBuilder();

				//Pick the data as much as will be shown @ top page new arrival
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setCategory(rs.getString("CATEGORY"));

				//If the administration account set multiple user in the article.
				if(rs.getString("user_LIST") != null) {
					String users = rs.getString("user_LIST");
				    List<String> listuser = new ArrayList<String>(Arrays.asList(users.split(",")));
				    articleInfo.setUserList(listuser);
				}

				//In case of the administration doesn't assign any weight on the article
				if(rs.getInt("WEIGHT") != 0) {
				    articleInfo.setWeight(rs.getInt("WEIGHT"));
				}
			    articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
				articleInfo.setUser(str.append(rs.getString("AUTHOR")));
				articleInfo.setCompany_place(rs.getString("PLACE"));
				articleInfo.setCompany_addr(rs.getString("ADDRESS"));
				articleInfo.setCeo(rs.getString("CEO"));
				articleInfo.setEstablishedDate(rs.getDate("ESTABLISHED_DATE"));
				articleInfo.setImg_id(rs.getInt("IMG_ID"));

				infoArray.addArticleInfo(articleInfo);
			}
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return infoArray;
	}
	
	/*
	 * To show new arrival article in the bottom of the index page, pick up 6 articles
	 * Ordered by the start day is closer to now
	 */
	public ArticleInfoArray getSixArticleInfoArrayByDate()
	throws DatabaseException, SystemException {

		ArticleInfoArray infoArray = new ArticleInfoArray();
		Date today = new Date(); //Today's date
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
		String formattedToday = null;

		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);
		PreparedStatement pStmt = null;
		this.open();
		try {
			/*
			 * Check the present time is in the period of the article in public,
			 * and the user who registers the article has chosen the release yes
			 */
			String sql = "SELECT * FROM article WHERE RELEASED = 'yes'"
					+ " AND OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' ORDER BY OPENING_DAY ASC LIMIT 6;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				Article articleInfo = new Article();
				StringBuilder str = new StringBuilder();

				//Pick the data as much as will be shown in the bottom of top page new arrival
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setCategory(rs.getString("CATEGORY"));

				//If the administration account set multiple user in the article.
				if(rs.getString("user_LIST") != null) {
					String users = rs.getString("user_LIST");
				    List<String> listuser = new ArrayList<String>(Arrays.asList(users.split(",")));
				    articleInfo.setUserList(listuser);
				}

				//In case of the administration doesn't assign any weight on the article
				if(rs.getInt("WEIGHT") != 0) {
				    articleInfo.setWeight(rs.getInt("WEIGHT"));
				}
			    articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
				articleInfo.setUser(str.append(rs.getString("AUTHOR")));
				articleInfo.setCompany_place(rs.getString("PLACE"));
				articleInfo.setCompany_addr(rs.getString("ADDRESS"));
				articleInfo.setImg_id(rs.getInt("IMG_ID"));

				infoArray.addArticleInfo(articleInfo);
			}
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return infoArray;
	}
	
	/*
	 * To show new arrival article in the bottom of the index page, pick up 6 articles
	 * Ordered by the start day is closer to now
	 */
	public ArticleInfoArray getComingArticleArray()
	throws DatabaseException, SystemException {

		ArticleInfoArray infoArray = new ArticleInfoArray();
		Date today = new Date(); //Today's date
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
		String formattedToday = null;

		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);
		PreparedStatement pStmt = null;
		this.open();
		try {
			/*
			 * Check the present time is in the period of the article in public,
			 * and the user who registers the article has chosen the release yes
			 */
			String sql = "SELECT * FROM article WHERE RELEASED = 'yes'"
					+ " AND OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' ORDER BY OPENING_DAY ASC;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				Article articleInfo = new Article();
				StringBuilder str = new StringBuilder();

				//Pick the data as much as will be shown in the bottom of top page new arrival
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setCategory(rs.getString("CATEGORY"));

				//If the administration account set multiple user in the article.
				if(rs.getString("user_LIST") != null) {
					String users = rs.getString("user_LIST");
				    List<String> listuser = new ArrayList<String>(Arrays.asList(users.split(",")));
				    articleInfo.setUserList(listuser);
				}

				//In case of the administration doesn't assign any weight on the article
				if(rs.getInt("WEIGHT") != 0) {
				    articleInfo.setWeight(rs.getInt("WEIGHT"));
				}
			    articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
				articleInfo.setUser(str.append(rs.getString("AUTHOR")));
				articleInfo.setCompany_place(rs.getString("PLACE"));
				articleInfo.setCompany_addr(rs.getString("ADDRESS"));

				articleInfo.setCeo(rs.getString("CEO"));
				articleInfo.setEstablishedDate(rs.getDate("ESTABLISHED_DATE"));
				articleInfo.setImg_id(rs.getInt("IMG_ID"));

				infoArray.addArticleInfo(articleInfo);
			}
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return infoArray;
	}
	
	/*
 	 * This method calls the article if the article is released in public.
 	 * It is determined by the date comparison.
 	 */
 	public Article getArticleInfoByPublic(int intArticleId)
 	throws DatabaseException, SystemException {

 		Article articleInfo = new Article();
 		StringBuilder str = new StringBuilder();
 		Date today = new Date(); //Today's date
 		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
 		String formattedToday = null;

 		String DATE_PATTERN = "yyyy-MM-dd";
 		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);
 		
 		PreparedStatement pStmt = null;
 		this.open();
 		try {
 			/*
 			 * Check the present time is in the period of the article in public
 			 */
 			String sql02 = "SELECT * FROM article WHERE ARTICLE_ID = '" + intArticleId
 					+ "' AND OPENING_DAY <= '"
 					+ formattedToday
 					+ "' AND CLOSING_DAY >= '"
 					+ formattedToday
 					+ "' AND RELEASED = 'yes';";
 			pStmt = conn.prepareStatement(sql02);
 			ResultSet rs = pStmt.executeQuery();

 			while (rs.next()) {
 				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
 				articleInfo.setCategory(rs.getString("CATEGORY"));

 				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
 				articleInfo.setOpening_hour(rs.getInt("OPENING_HOUR"));
 				articleInfo.setOpening_min(rs.getInt("OPENING_MIN"));
 				articleInfo.setClosing_day(rs.getDate("CLOSING_DAY"));
 				articleInfo.setClosing_hour(rs.getInt("CLOSING_HOUR"));
 				articleInfo.setClosing_min(rs.getInt("CLOSING_MIN"));

 				if(rs.getString("user_LIST") != null) {
 					String users = rs.getString("user_LIST");
 				    List<String> listuser = new ArrayList<String>(Arrays.asList(users.split(",")));
 				    articleInfo.setUserList(listuser);
 				}

 				//In case of the administration doesn't assign any weight on the article
 				if(rs.getInt("WEIGHT") != 0) {
 				    articleInfo.setWeight(rs.getInt("WEIGHT"));
 				}

 			    articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
 				articleInfo.setUser(str.append(rs.getString("AUTHOR")));
 				articleInfo.setCompany_place(rs.getString("PLACE"));
 				articleInfo.setCompany_addr(rs.getString("ADDRESS"));
 				articleInfo.setCeo(rs.getString("CEO"));
 				articleInfo.setCapital(rs.getString("CAPITAL"));
 				articleInfo.setEmployeeNumber(rs.getInt("EMPLOYEE_NUMBER"));

 				articleInfo.setEstablishedDate(rs.getDate("ESTABLISHED_DATE"));
 				articleInfo.setCompany_mail(rs.getString("COMPANY_EMAIL"));
 				articleInfo.setCompany_phone(rs.getString("COMPANY_PHONE"));
 				articleInfo.setCompany_url(rs.getString("COMPANY_URL"));
 				articleInfo.setIntroduction(rs.getString("INTRODUCTION"));
 				articleInfo.setImg_id(rs.getInt("IMG_ID"));
 			}
 		}
 		catch (SQLException e) {
 	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
 	    }
 	    finally {
 	    	this.close(pStmt);
 	    }
 		return articleInfo;
 	}
 	
 	/*
	 * Show the article card in order by the date in ascent.
	 */
	public ArticleInfoArray searchArticleArrayBySearch(ArrayList<String> searchWordArray)
	throws DatabaseException, SystemException {

		/*
		 * The part below is getting the date of today to compare the registered
		 * opening and closing day
		 */
		ArticleInfoArray infoArray = new ArticleInfoArray();
		Date today = new Date(); //Today's date
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
		String formattedToday = null;
		//Format the date
		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);

		/*
		 *This is used for the search function is accepted the multiple search word
		 *even in the article name and user field will be scanned.
		 */
		String stringSearchWordInArticleName = "";
		String stringSearchWordInArticleName02 = "";
		String stringSearchWordInuserName = "";
		String stringSearchWordInuserName02 = "";
		
		/*
		 * The first word needs to be isolated any other words because
		 * we have to use "AND" to concatenate the words.
		 */
		stringSearchWordInArticleName = "ARTICLE_NAME LIKE '%" + searchWordArray.get(0) + "%'";
		
		/*
		 * The point of there is space at the end of the "searchWordArray.get(0)" is
		 * we wish to keep the space between the first and last name
		 */
		stringSearchWordInuserName = "AUTHOR LIKE '%" + searchWordArray.get(0) + " %'";
		
		//As many as the search words, the for loop will loop
		if(searchWordArray.size() > 1) {
			for(int i = 1; i < searchWordArray.size(); i++) {
				stringSearchWordInArticleName02 = stringSearchWordInArticleName.concat(" AND ").concat("ARTICLE_NAME LIKE '%" + searchWordArray.get(i) + "%' ");
				stringSearchWordInuserName02 = stringSearchWordInuserName.concat(" AND ").concat("AUTHOR LIKE '%" + searchWordArray.get(i) + "%' ");
			}
		}
		
		//If there is only one word is input as a search word
		else {
			stringSearchWordInArticleName02 = stringSearchWordInArticleName;
			stringSearchWordInuserName02 = stringSearchWordInuserName;
		}

		PreparedStatement pStmt = null;
		this.open();
		try {
			/*
			 * Try to find the article which match the article title and the search word even if the part of the article title is matched
			 */
			String sql = "SELECT ARTICLE_NAME, ARTICLE_ID, CATEGORY, AUTHOR, ADDRESS, CEO, ESTABLISHED_DATE, IMG_ID"
					+ " FROM article WHERE (" + stringSearchWordInArticleName02
					+ " OR " + stringSearchWordInuserName02 + ")"
					+ " AND RELEASED = 'yes'"
					+ " AND OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' ORDER BY OPENING_DAY ASC;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				Article articleInfo = new Article();
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
				articleInfo.setCategory(rs.getString("CATEGORY"));
				articleInfo.setStringUser(rs.getString("AUTHOR"));
				articleInfo.setCompany_addr(rs.getString("ADDRESS"));
				articleInfo.setCeo(rs.getString("CEO"));
				articleInfo.setEstablishedDate(rs.getDate("ESTABLISHED_DATE"));
				articleInfo.setImg_id(rs.getInt("IMG_ID"));

				infoArray.addArticleInfo(articleInfo);
			}
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return infoArray;
	}
	
	/*
	 * This method will supply the 3 articles information.
	 * Those data is shown at the practitioner detail page and introduce the instructor's
	 * article which is ordered by the date and selected the closest date article.
	 */
	public ArticleInfoArray getThreeArticleInfoArraySelectedByUser(int userId)
	throws DatabaseException, SystemException {

	PreparedStatement pStmt = null;
	ArticleInfoArray infoArray = new ArticleInfoArray();
	//Today's date
	Date today = new Date();
	Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH); //Roll down the mill second
	String formattedToday = null;

	String DATE_PATTERN = "yyyy-MM-dd";
	formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);

	this.open();
	try {
		/*
		 * Check the present time is in the period of the article in public,
		 * and the instructor who registers the article has chosen the release yes
		 *
		 * The selecting condition is the article start day in ascent
		 */
		String sql = "SELECT * FROM article WHERE USER_ID = '"
				+ userId 
				+ "'AND RELEASED = 'yes' "
				+ "AND OPENING_DAY <= '"
				+ formattedToday
				+ "' AND CLOSING_DAY >= '"
				+ formattedToday
				+ "' ORDER BY OPENING_DAY ASC LIMIT 3;";
		pStmt = conn.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		//Have the article information and set into the article instance
		while (rs.next()) {
			Article articleInfo = new Article();

			//Pick the data as much as will be shown @ top page new arrival
			articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));

			//If the administration account set multiple instructor in the article.
			if(rs.getString("USER_LIST") != null) {
				String instructors = rs.getString("USER_LIST");
			    List<String> listInstructor = new ArrayList<String>(Arrays.asList(instructors.split(",")));
			    articleInfo.setUserList(listInstructor);
			}

			//In case of the administration doesn't assign any weight on the article
			if(rs.getInt("WEIGHT") != 0) {
			    articleInfo.setWeight(rs.getInt("WEIGHT"));
			}
		    articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
		    articleInfo.setCategory(rs.getString("CATEGORY"));
		    articleInfo.setStringUser(rs.getString("AUTHOR"));
		    articleInfo.setCompany_addr(rs.getString("ADDRESS"));
			articleInfo.setImg_id(rs.getInt("IMG_ID"));

			infoArray.addArticleInfo(articleInfo);
		}
	}
	catch (SQLException e) {
        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
    }
    finally {
    	this.close(pStmt);
    }
		return infoArray;
	}
}

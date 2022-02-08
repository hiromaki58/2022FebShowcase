package jp.co.warehouse.dao.article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * To get the article informaion by the user.
 * @author hirog
 * Sep 14, 2021
 *
 */
public class UserGetArticleDAO extends DAOBase {

	/**
	 *Get the article information by the self registered user id
	 * 
	 * @author 	hirog
	 * Sep 14, 2021
	 * @param 	userId
	 * @return	articles which is written by the author who is identified by the user id.
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public ArticleInfoArray getArticleInfoArrayByUserId(String userId)
	throws DatabaseException, SystemException {

		/*
		 * This is the array to collect the selected article information
		 * and return to show in the my page
		 */
		ArticleInfoArray infoArray = new ArticleInfoArray();
		StringBuilder str = new StringBuilder();

		/*
		 * This part is preparation for comparing the period of the article in public and today
		 */
		Date today = new Date();
		//Roll down the mill second
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
		String formattedToday = null;
		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);

		PreparedStatement pStmt = null;
		this.open();
		try {
			
			/*
			 *"UNINON" is the key word to concatenate the 3 arrays.
			 * The arrays' article is selected by when they are in public and ordered by the open day.
			 */
			String sql = "SELECT * FROM "
					//Get the articles which will be released in the near future
					+ "((SELECT * FROM article"
					+ " WHERE OPENING_DAY >= '"
					+ formattedToday
					
					/*
					 * This cause the optimizer to create a temporary table, and use file sort to order the query
					 * the limit number is a 64bit unsigned -1 (2^64-1), this is a big number and can work with 99.999% of queries i know
					 */
					+ "' AND USER_ID = '" + userId + "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which is in public now
					+ "(SELECT * FROM article"
					+ " WHERE OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' AND USER_ID = '" + userId + "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which pass the end date of the released
					+ "(SELECT * FROM article"
					+ " WHERE CLOSING_DAY <= '"
					+ formattedToday
					+ "' AND USER_ID = '" + userId + "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615)) "
					+ "AS table02;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				Article articleInfo = new Article();
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setClosing_day(rs.getDate("CLOSING_DAY"));
				articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
				articleInfo.setUser(str.append(rs.getString("AUTHOR")));

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
	
	/**
	 * Search the article by the author name
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	userName
	 * @param 	searchWordArray
	 * @return
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public ArticleInfoArray getArticleInfoArrayByUserNameWithSearchWord(String userName, ArrayList<String> searchWordArray)
	throws DatabaseException, SystemException {

		/*
		 * This is the array to collect the selected article information
		 * and return to show in the mypage
		 */
		ArticleInfoArray infoArray = new ArticleInfoArray();
		StringBuilder str = new StringBuilder();

		/*
		 * This is used for the search function is accepted the multiple search word
		 * even in the article name and user field will be scanned.
		 */
		String stringSearchWordInArticleName = "";
		String stringSearchWordInArticleName02 = "";
		String stringSearchWordInuserName = "";
		String stringSearchWordInuserName02 = "";
		
		/*
		 * The first word needs to be isolated any other words because
		 * we have to use "AND" to concatenate the words.
		 */
		stringSearchWordInArticleName = "article_NAME LIKE '%" + searchWordArray.get(0) + "%'";
		
		/*
		 * The point of there is space at the end of the "searchWordArray.get(0)" is
		 * we wish to keep the space between the first and last name
		 */
		stringSearchWordInuserName = "user LIKE '%" + searchWordArray.get(0) + " %'";
		
		//As many as the search words, the for loop will loop
		if(searchWordArray.size() > 1) {
			for(int i = 1; i < searchWordArray.size(); i++) {
				stringSearchWordInArticleName02 = stringSearchWordInArticleName.concat(" AND ").concat("article_NAME LIKE '%" + searchWordArray.get(i) + "%' ");
				stringSearchWordInuserName02 = stringSearchWordInuserName.concat(" AND ").concat("user LIKE '%" + searchWordArray.get(i) + "%' ");
			}
		}
		else {
			//In case of the only one search word is input
			stringSearchWordInArticleName02 = stringSearchWordInArticleName;
			stringSearchWordInuserName02 = stringSearchWordInuserName;
		}

		/*
		 * This part is preparation for comparing the period of the article in public and today
		 */
		Date today = new Date();
		//Roll down the mill second
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
		String formattedToday = null;
		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);

		PreparedStatement pStmt = null;
		this.open();
		try {
			/*
			 *"UNINON" is the key word to concatenate the 3 arrays.
			 * The arrays' article is selected by when they are in public and ordered by the opening day.
			 */
			String sql = "SELECT * FROM "
					
					//Get the articles which will be released in the near future
					+ "((SELECT * FROM ARTICLE"
					+ " WHERE OPENING_DAY >= '"
					+ formattedToday
					
					/*
					 * This cause the optimizer to create a temporary table, and use file sort to order the query
					 * the limit number is a 64bit unsigned -1 (2^64-1), this is a big number and can work with 99.999% of queries i know
					 */
					+ "' AND user = '" + userName + "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which is in public now
					+ "(SELECT * FROM ARTICLE"
					+ " WHERE OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' AND user = '" + userName + "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which pass the end date of the released
					+ "(SELECT * FROM ARTICLE"
					+ " WHERE CLOSING_DAY <= '"
					+ formattedToday
					+ "' AND user = '" + userName + "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615)) "
					+ "AS table02 "
					+ "WHERE " + stringSearchWordInArticleName02
					+ "OR " + stringSearchWordInuserName02 + ";";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				Article articleInfo = new Article();
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setClosing_day(rs.getDate("CLOSING_DAY"));
				articleInfo.setArticleId(rs.getInt("ID"));
				articleInfo.setUser(str.append(rs.getString("user")));

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
	
	/**
	 * Receive the article which is identified by the article ID. 
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleId
	 * @return
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public Article getArticleInfoByAritcleId(int articleId)
	throws DatabaseException, SystemException {

		Article articleInfo = new Article();
		StringBuilder str = new StringBuilder();
		
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql02 = "SELECT * FROM article WHERE ARTICLE_ID = '" + articleId + "';";
			pStmt = conn.prepareStatement(sql02);
			ResultSet rs = pStmt.executeQuery();

			//Have the article information and set into the article instance
			while (rs.next()) {
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setCategory(rs.getString("CATEGORY"));
				
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setOpening_hour(rs.getInt("OPENING_HOUR"));
				articleInfo.setOpening_min(rs.getInt("OPENING_MIN"));
				articleInfo.setClosing_day(rs.getDate("CLOSING_DAY"));
				articleInfo.setClosing_hour(rs.getInt("CLOSING_HOUR"));
				articleInfo.setClosing_min(rs.getInt("CLOSING_MIN"));

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
}

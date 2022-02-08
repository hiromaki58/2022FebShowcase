package jp.co.warehouse.dao.utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserArray;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.entity.ArticleInfoArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/*
 * This class is basically supplying the article information to pagination.
 */
public class PaginationDAO extends DAOBase {

	private int noOfRecords;

	/*
	 * 1, The articles are divided into 3 groups
	 * 	a) Not in public yet
	 * 	b) It is in public
	 * 	c) Already be wraped up
	 *
	 * 2, The order of the each group show up does follows as the order a,b and c
	 * 3, And, this method is working with pagination
	 */
	public ArticleInfoArray articlePagination (int offset, int noOfRecords)
	throws DatabaseException, SystemException {

		/*
		 * This is the array to collect the selected article information
		 * and return to show in the mypage
		 */
		ArticleInfoArray articleInfoArray = new ArticleInfoArray();
		PreparedStatement pStmt = null;

		/*
		 * This part is preparation for comparing the period of the article in public and today
		 */
		//Get today
		Date today = new Date();
		//Roll down the mill second
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
		String formattedToday = null;
		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);

		this.open();
		try {
			/*
			 *"UNINON" is the key word to concatenate the 3 arrays.
			 * The arrays' article is selected by when they are in public and ordered by the start day.
			 */
			String sql = "SELECT SQL_CALC_FOUND_ROWS * FROM "
					//Get the articles which will be released in the near future
					+ "((SELECT * FROM article"
					+ " WHERE OPENING_DAY >= '"
					+ formattedToday
					
					/*
					 * This cause the optimizer to create a temporary table, and use filesort to order the query
					 * the limit number is a 64bit unsigned -1 (2^64-1), this is a big number and can work with 99.999% of queries i know
					 */
					+ "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which is in public now
					+ "(SELECT * FROM article"
					+ " WHERE OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which pass the end date of the released
					+ "(SELECT * FROM article"
					+ " WHERE CLOSING_DAY <= '"
					+ formattedToday
					+ "' ORDER BY OPENING_DAY DESC LIMIT 18446744073709551615)) "
					+ "AS table02 LIMIT " + offset + ", " + noOfRecords + ";";

			/*
			 * Get the article information will be shown in the my page
			 */
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Article articleInfo = new Article();
				articleInfo.setArticle_name(rs.getString("article_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setClosing_day(rs.getDate("CLOSING_DAY"));
				articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
				articleInfo.setStringUser(rs.getString("AUTHOR"));

				articleInfoArray.addArticleInfo(articleInfo);
			}
			rs.close();

			/*
			 * Return how many record is already shown in the my page
			 * This number is the number to indicate which article should be shown at top in the second, third page and so on.
			 */
			rs = pStmt.executeQuery("SELECT FOUND_ROWS()");
			if(rs.next()) {
				this.noOfRecords = rs.getInt(1);
			}
			rs.close();
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return articleInfoArray;
	}

	//Return the article info to the pagination function at admin mypage.
	public ArticleInfoArray articleSearchPagination (int offset, int noOfRecords, ArrayList<String> searchWordArray)
	throws DatabaseException, SystemException {
		ArticleInfoArray articleInfoArray = new ArticleInfoArray();
		PreparedStatement pStmt = null;

		/*
		 *This is used for the search function is accepted the multiple search word
		 *even in the article name and user field will be scanned.
		 */
		String stringSearchWordInarticleName = "";
		String stringSearchWordInarticleName02 = "";
		String stringSearchWordInuserName = "";
		String stringSearchWordInuserName02 = "";
		
		/*
		 * The first word needs to be isolated any other words because
		 * we have to use "AND" to concatenate the words.
		 */
		stringSearchWordInarticleName = "article_NAME LIKE '%" + searchWordArray.get(0) + "%'";
		
		/*
		 * The point of there is space at the end of the "searchWordArray.get(0)" is
		 * we wish to keep the space between the first and last name
		 */
		stringSearchWordInuserName = "AUTHOR LIKE '%" + searchWordArray.get(0) + " %'";
		//As many as the search words, the for loop will loop
		if(searchWordArray.size() > 1) {
			for(int i = 1; i < searchWordArray.size(); i++) {
				stringSearchWordInarticleName02 = stringSearchWordInarticleName.concat(" AND ").concat("article_NAME LIKE '%" + searchWordArray.get(i) + "%' ");
				stringSearchWordInuserName02 = stringSearchWordInuserName.concat(" AND ").concat("AUTHOR LIKE '%" + searchWordArray.get(i) + "%' ");
			}
		}
		else {
			//In case of the only one search word is input
			stringSearchWordInarticleName02 = stringSearchWordInarticleName;
			stringSearchWordInuserName02 = stringSearchWordInuserName;
		}

		/*
		 * This part is preparation for comparing the period of the article in public and today
		 */
		//Get today
		Date today = new Date();
		//Roll down the mill second
		Date truncToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
		String formattedToday = null;
		String DATE_PATTERN = "yyyy-MM-dd";
		formattedToday = new SimpleDateFormat(DATE_PATTERN).format(truncToday);

		this.open();
		try {
			/*
			 * "SELECT SQL_CALC_FOUND_ROWS" doesn't take the data which is already selected.
			 */

			String sql02 = "SELECT SQL_CALC_FOUND_ROWS * FROM "
					//Get the articles which will be released in the near future
					+ "((SELECT * FROM article"
					+ " WHERE OPENING_DAY >= '"
					+ formattedToday
					
					/*
					 * This cause the optimizer to create a temporary table, and use filesort to order the query
					 * the limit number is a 64bit unsigned -1 (2^64-1), this is a big number and can work with 99.999% of queries i know
					 */
					+ "' ORDER BY START_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which is in public now
					+ "(SELECT * FROM article"
					+ " WHERE OPENING_DAY <= '"
					+ formattedToday
					+ "' AND CLOSING_DAY >= '"
					+ formattedToday
					+ "' ORDER BY START_DAY DESC LIMIT 18446744073709551615) "
					+ "UNION"
					
					//Get the articles which pass the end date of the released
					+ "(SELECT * FROM article"
					+ " WHERE CLOSING_DAY <= '"
					+ formattedToday
					+ "' ORDER BY START_DAY DESC LIMIT 18446744073709551615)) "
					+ "AS table02 "
					+ "WHERE " + stringSearchWordInarticleName02
					+ "OR " + stringSearchWordInuserName02
					+ "LIMIT " + offset + ", " + noOfRecords + ";";

			pStmt = conn.prepareStatement(sql02);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				Article articleInfo = new Article();
				articleInfo.setArticle_name(rs.getString("article_NAME"));
				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setClosing_day(rs.getDate("CLOSING_DAY"));
				articleInfo.setArticleId(rs.getInt("ID"));
				articleInfo.setStringUser(rs.getString("AUTHOR"));

				articleInfoArray.addArticleInfo(articleInfo);
			}
			rs.close();

			/*
			 * Return how many record is already shown in the my page
			 * This number is the number to indicate which article should be shown at top in the second, third page and so on.
			 */
			rs = pStmt.executeQuery("SELECT FOUND_ROWS()");
			if(rs.next()) {
				this.noOfRecords = rs.getInt(1);
			}
			rs.close();
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return articleInfoArray;
	}

	/*
	 * This method will call the user info array so that my page will show the list of the user.
	 * How many users will be indicated is selected by what number is assigned by the "int noOfRecords".
	 */
	public UserArray userPagination (int offset, int noOfRecords)
	throws DatabaseException, SystemException {
		UserArray userInfoArray = new UserArray();
		PreparedStatement pStmt = null;

		this.open();
		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS * FROM registered_user LIMIT " + offset + ", " + noOfRecords + ";";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				AdminRegisterUser userInfo = new AdminRegisterUser();
				userInfo.setUser_first_name(rs.getString("FIRST_NAME"));
				userInfo.setUser_last_name(rs.getString("LAST_NAME"));
				userInfo.setUser_mail(rs.getString("EMAIL"));
				userInfo.setRegisteredUserId(rs.getInt("ID"));

				userInfoArray.addUserRecord(userInfo);
			}
			rs.close();

			//Return the number of the user record which is stored in the DB
			rs = pStmt.executeQuery("SELECT FOUND_ROWS()");
			if(rs.next()) {
				this.noOfRecords = rs.getInt(1);
			}
			else {
				System.out.println("No noOfRecords @ PaginationDAO");
			}
			rs.close();
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return userInfoArray;
	}

	public UserArray userSearchPagination (int offset, int noOfRecords, String userName)
	throws DatabaseException, SystemException {
		UserArray userInfoArray = new UserArray();
		PreparedStatement pStmt = null;

		this.open();
		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS * FROM registered_user "
					+ " WHERE LAST_NAME LIKE '%" + userName + "%'"
					+ " OR FIRST_NAME LIKE '%" + userName + "%'"
					+ " LIMIT " + offset + ", " + noOfRecords + ";";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				AdminRegisterUser userInfo = new AdminRegisterUser();
				userInfo.setUser_first_name(rs.getString("FIRST_NAME"));
				userInfo.setUser_last_name(rs.getString("LAST_NAME"));
				userInfo.setUser_mail(rs.getString("EMAIL"));
				userInfo.setRegisteredUserId(rs.getInt("ID"));

				userInfoArray.addUserRecord(userInfo);
			}
			rs.close();

			rs = pStmt.executeQuery("SELECT FOUND_ROWS()");
			if(rs.next()) {
				this.noOfRecords = rs.getInt(1);
			}
			else {
				System.out.println("No noOfRecords @ PaginationDAO");
			}
			rs.close();
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return userInfoArray;
	}

	/*
	 * Return how many record is already shown in the my page
	 * This number is the number to indicate which article should be shown at top in the second, third page and so on.
	 */
	public int getNoOfRecords() {
		return noOfRecords;
	}
}
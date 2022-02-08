package jp.co.warehouse.dao.article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.date.DateFormatChanger;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * To create the new article by the admin account
 * @author hirog
 * Sep 15, 2021
 *
 */
public class AdminSetArticleDAO extends DAOBase {
	
	/**
	 * This method will add the new article
	 * 
	 * @author hirog
	 * Sep 15, 2021
	 * @param article
	 * @param userId
	 * @return
	 * @throws DatabaseException
	 * @throws SystemException
	 */
	public int adminAddArticleInfo(Article article, int userId)
	throws DatabaseException, SystemException {
		PreparedStatement pStmt = null;
		PreparedStatement pStmt02 = null;
		PreparedStatement pStmt03 = null;
		int articleIdNumber;

		DateFormatChanger dfc = new DateFormatChanger();
		String openingDay = "";
		String closingDay = "";
		String registrationDay = "";
		String establishedDay = "";
		articleIdNumber = 0;

		this.open();
		try {
			conn.setAutoCommit(false);
			/*
			 * This if the article date is not fixed date "2100-12-31" will be assigned,
			 * and the date will be the key to distinguish to tell fix or open date
			 *
			 */
			//Change the date format data into String and MySQL will accept that
			openingDay = dfc.dateToString(article.getOpening_day());
			closingDay = dfc.dateToString(article.getClosing_day());
			registrationDay = dfc.dateToString(article.getRegistration_day());
			
			/*
			 * Establish date registration is voluntary.
			 * In case of no date is set,
			 * no date info will be regsitered.
			 */
			if(article.getEstablishedDate() == null) {
				establishedDay = dfc.dateToString(new Date());
			}
			else {
				establishedDay = dfc.dateToString(article.getEstablishedDate());
			}

			String sql01 = "INSERT INTO article("
					+ "ARTICLE_NAME, "
					+ "OPENING_HOUR, OPENING_MIN, "
					+ "CLOSING_HOUR, CLOSING_MIN, CATEGORY, "
					+ "AUTHOR, USER_ID, PLACE, ADDRESS, "
					+ "OPENING_DAY, "
					+ "CLOSING_DAY, "
					+ "ESTABLISHED_DATE, "
					+ "CEO, CAPITAL, EMPLOYEE_NUMBER, "
					+ "COMPANY_EMAIL, COMPANY_PHONE, COMPANY_URL, "
					+ "INTRODUCTION, REGISTRATION_DAY) "
					+ "VALUES("
					+ "?, "

					+ "?, "
					+ "?, "
					+ "?, "
					+ "?, "
					+ "?, "

					+ "?, "
					+ "?, "
					+ "?, "
					+ "?, " 
					+ "?, " 
					+ "?, " 
					+ "?, " 

					+ "?, "
					+ "?, "
					+ "?, "

					+ "?, "
					+ "?, "
					+ "?, "
					+ "?, "
					+ "?"
					+ ");";

			pStmt = conn.prepareStatement(sql01);
			pStmt.setString(1, article.getArticle_name());
			
			pStmt.setInt(2, article.getOpening_hour());
			pStmt.setInt(3, article.getOpening_min());
			pStmt.setInt(4, article.getClosing_hour());
			pStmt.setInt(5, article.getClosing_min());
			pStmt.setString(6, article.getCategory());
			
			pStmt.setString(7, article.getUser().toString());
			pStmt.setInt(8, article.getUser_id());
			pStmt.setString(9, article.getCompany_place());
			pStmt.setString(10, article.getCompany_addr());
			pStmt.setString(11, openingDay);
			pStmt.setString(12, closingDay);
			pStmt.setString(13, establishedDay);
			
			pStmt.setString(14, article.getCeo());
			pStmt.setString(15, article.getCapital());
			pStmt.setInt(16, article.getEmployeeNumber());
			
			pStmt.setString(17, article.getCompany_mail());
			pStmt.setString(18, article.getCompany_phone());
			pStmt.setString(19, article.getCompany_url());
			pStmt.setString(20, article.getIntroduction());
			pStmt.setString(21, registrationDay);
			pStmt.executeUpdate();
			/*
			 * 	The point of these second and last SQL is retrieve the article ID and set the ID into the self registered user article match table
			 */
			String sql02 = "SELECT LAST_INSERT_ID() AS LAST;";
			pStmt02 = conn.prepareStatement(sql02);
			ResultSet rs02 = pStmt02.executeQuery();

			if(rs02.next()) {
				articleIdNumber = rs02.getInt("LAST");
			}
			/*
			 * Set the article ID into the self registered user_article_match table
			 */
			String sql03 = "INSERT INTO user_article_match "
								+ "(user_ID, article_ID) "
								+ "VALUES(?, ?);";

			pStmt03 = conn.prepareStatement(sql03);
			pStmt03.setInt(1, userId);
			pStmt03.setInt(2, articleIdNumber);
			pStmt03.executeUpdate();
			
			//Start the transaction
			try {
				conn.commit();
			}
			catch(SQLException e) {
				conn.rollback();
				throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
			}
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    	this.close(pStmt02);
	    	this.close(pStmt03);
	    }
		return articleIdNumber;
	}
	
	/**
	 * Update the article by admin
	 * 
	 * @author 	hirog
	 * Sep 18, 2021
	 * @param 	article
	 * @param 	articleId
	 * @return	modified article number
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public int adminModifyArticleInfo(Article article, int articleId)
	throws DatabaseException, SystemException {

		DateFormatChanger dfc = new DateFormatChanger();
		String openingDay = "";
		String closingDay = "";
		String establishedDate = "";
		int articleIdNumber = 0;

		PreparedStatement pStmt = null;
		this.open();
		try {
			conn.setAutoCommit(false);
			/*
			 * Change the date format data into String and MySQL will accept that
			 * 
			 * This if the article date is not fixed date "2100-12-31" will be assigned,
			 * and the date will be the key to distinguish to tell fix or open date
			 *
			 */
			openingDay = dfc.dateToString(article.getOpening_day());
			closingDay = dfc.dateToString(article.getClosing_day());
			establishedDate = dfc.dateToString(article.getEstablishedDate());

			String sql = "UPDATE article SET "
					+ "ARTICLE_NAME = ?', "

					+ "OPENING_HOUR = ?, "
					+ "OPENING_MIN = ?, "
					+ "CLOSING_HOUR = ?, "
					+ "CLOSING_MIN = ?, "

					+ "CATEGORY = ?, "
					+ "WEIGHT = ?, "
					+ "PLACE = ?, "
					+ "ADDRESS = ?, "

					+ "OPENING_DAY = ?, "
					+ "CLOSING_DAY = ?, "
					+ "ESTABLISHED_DATE = ?, "

					+ "CEO = ?, "
					+ "CAPITAL = ?, "
					+ "EMPLOYEE_NUMBER = ?, "

					+ "COMPANY_EMAIL = ?, "
					+ "COMPANY_PHONE = ?, "
					+ "COMPANY_URL = ?, "
					+ "INTRODUCTION = ? "
					+ "WHERE ARTICLE_ID = ?;";
			
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, article.getArticle_name());
			
			pStmt.setInt(2, article.getOpening_hour());
			pStmt.setInt(3, article.getOpening_min());
			pStmt.setInt(4, article.getClosing_hour());
			pStmt.setInt(5, article.getClosing_min());
			
			pStmt.setString(6, article.getCategory());
			pStmt.setInt(7, article.getWeight());
			pStmt.setString(8, article.getCompany_place());
			pStmt.setString(9, article.getCompany_addr());
			
			pStmt.setString(10, openingDay);
			pStmt.setString(11, closingDay);
			pStmt.setString(12, establishedDate);
			
			pStmt.setString(13, article.getCeo());
			pStmt.setString(14, article.getCapital());
			pStmt.setInt(15, article.getEmployeeNumber());
			
			pStmt.setString(15, article.getCompany_mail());
			pStmt.setString(17, article.getCompany_phone());
			pStmt.setString(18, article.getCompany_url());
			pStmt.setString(19, article.getIntroduction());
			pStmt.setInt(20, articleId);
			
			pStmt.executeUpdate();
			
			//Start the transaction
			try {
				conn.commit();
			}
			catch(SQLException e) {
				conn.rollback();
				throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
			}
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return articleIdNumber;
	}
	
	/**
	 * Delete the article
	 * 
	 * @author 	hirog
	 * Sep 18, 2021
	 * @param 	articleId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void deleteArticleById(int articleId)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			conn.setAutoCommit(false);
			String sql = "DELETE FROM article WHERE ARTICLE_ID = ?;";
			
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, articleId);
			pStmt.executeUpdate();
			
			//Start the transaction
			try {
				conn.commit();
			}
			catch(SQLException e) {
				conn.rollback();
				throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
			}
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	}
	
	/**
	 * This method is used when the user release information is changed.
	 * If the user change the status "no", all of article he creates are not in public anymore.
	 * 
	 * @author 	hirog
	 * Sep 18, 2021
	 * @param 	articleId
	 * @param 	release_or_not
	 * @return	modified article number
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public int adminModifyArticleRelease(Integer articleId, String release_or_not)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		int articleIdNumber = 0;
		this.open();
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE article SET "
					+ "RELEASED = ? "
					+ "WHERE ARTICLE_ID = ?;";

			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, release_or_not);
			pStmt.setInt(2, articleId);
			pStmt.executeUpdate();
			
			//Start the transaction
			try {
				conn.commit();
			}
			catch(SQLException e) {
				conn.rollback();
				throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
			}
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return articleIdNumber;
	}
}

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

public class UserSetArticleDAO extends DAOBase {
	/*
	 * This method will add the new article
	 */
	public int userAddArticleInfo(Article article, int userId)
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
					+ "INTRODUCTION, REGISTRATION_DAY)"
					+ "VALUES('"
					+ article.getArticle_name()

					+ "', " + article.getOpening_hour()
					+ ", " + article.getOpening_min()
					+ ", " + article.getClosing_hour()
					+ ", " + article.getClosing_min()
					+ ", '" + article.getCategory()

					+ "', '" + article.getUser()
					+ "', '" + article.getUser_id()
					+ "', '" + article.getCompany_place()
					+ "', '" + article.getCompany_addr()
					+ "', '" + openingDay
					+ "', '" + closingDay
					+ "', '" + establishedDay

					+ "', '" + article.getCeo()
					+ "', '" + article.getCapital()
					+ "', " + article.getEmployeeNumber()

					+ ", '" + article.getCompany_mail()
					+ "', '" + article.getCompany_phone()
					+ "', '" + article.getCompany_url()
					+ "', '" + article.getIntroduction()
					+ "', '" + registrationDay
					+ "');";

			pStmt = conn.prepareStatement(sql01);
			pStmt.execute();
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
			String sql03 = "INSERT INTO user_article_match("
								+ "user_ID, article_ID)"
								+ "VALUES(" + userId + ", " + articleIdNumber + ");";

			pStmt03 = conn.prepareStatement(sql03);
			pStmt03.execute();
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
	
	public int userModifyArticleInfo(Article article, int articleId)
	throws DatabaseException, SystemException {

		DateFormatChanger dfc = new DateFormatChanger();;
		String openingDay = "";
		String closingDay = "";
		String establishedDate = "";
		int articleIdNumber = 0;

		PreparedStatement pStmt = null;
		this.open();
		try {

			/*
			 * Change the date format data into String and MySQL will accept that
			 * This if the article date is not fixed date "2100-12-31" will be assigned,
			 * and the date will be the key to distinguish to tell fix or open date
			 *
			 */
			openingDay = dfc.dateToString(article.getOpening_day());
			closingDay = dfc.dateToString(article.getClosing_day());
			establishedDate = dfc.dateToString(article.getEstablishedDate());

			String sql = "UPDATE article SET "
					+ "ARTICLE_NAME = '" + article.getArticle_name() + "', "

					+ "OPENING_HOUR = " + article.getOpening_hour() + ", "
					+ "OPENING_MIN = " + article.getOpening_min() + ", "
					+ "CLOSING_HOUR = " + article.getClosing_hour() + ", "
					+ "CLOSING_MIN = " + article.getClosing_min() + ", "


					+ "AUTHOR = '" + article.getUser() + "', "
					+ "PLACE = '" + article.getCompany_place() + "', "
					+ "ADDRESS = '" + article.getCompany_addr() + "', "
					
					+ "OPENING_DAY = '" + openingDay + "', "
					+ "CLOSING_DAY = '" + closingDay + "', "
					+ "ESTABLISHED_DATE = '" + establishedDate + "', "
					
					+ "CEO = '" + article.getCeo() + "', "
					+ "CAPITAL = '" + article.getCapital() + "', "
					+ "EMPLOYEE_NUMBER = " + article.getEmployeeNumber() + ", "

					+ "COMPANY_EMAIL = '" + article.getCompany_mail() + "', "
					+ "COMPANY_PHONE = '" + article.getCompany_phone() + "', "
					+ "COMPANY_URL = '" + article.getCompany_url() + "', "
					+ "INTRODUCTION = '" + article.getIntroduction() + "' "
					+ "WHERE ARTICLE_ID = '"+ articleId + "';";

			pStmt = conn.prepareStatement(sql);
			pStmt.execute();
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return articleIdNumber;
	}
	
	/*
	 * Delete the article
	 */
	public void deleteArticleById(int articleId)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "DELETE FROM article WHERE ARTICLE_ID = "+ articleId + ";";
			pStmt = conn.prepareStatement(sql);
			pStmt.executeUpdate();
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	}
	
	/*
	 * This method is used when the user release information is changed.
	 * If the user change the status "no", all of article he creates are not in public anymore.
	 */
	public int userModifyArticleRelease(Integer articleId, String release_or_not)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		int articleIdNumber = 0;
		this.open();
		try {
			String sql = "UPDATE article SET "
					+ "RELEASED = '" + release_or_not + "'"
					+ "WHERE ID = '"+ articleId + "';";

			pStmt = conn.prepareStatement(sql);
			pStmt.execute();
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

package jp.co.warehouse.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * Check user registered status dao
 * @author hirog
 * Sep 8, 2021
 *
 */
public class CheckUserRegisteredStatusDAO extends DAOBase{
	private PreparedStatement pStmt;
	
	/**
	 * Check the user already writes any article.
	 * 
	 * @author	H.Maki
	 * @param 	userId
	 * @return	true or false
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean isArticleRegistered(String userId)
	throws DatabaseException, SystemException {

		boolean result;
		this.open();
		try {
			String sql = "SELECT ARTICLE_NAME FROM article WHERE USER_ID = '" + userId + "' AND ARTICLE_NAME IS NOT NULL;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(!rs.next()) {
			   result = false;
		   }else {
			   result = true;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return result;
	}
	
	/**
	 * This method checks the catch phrase is registered.
	 * If it is done which means self registered user information is stored into the DB 
	 * because the catch phrase is needed to be in DB.
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	true or false
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkUserInfoRegistered(String email)
	throws DatabaseException, SystemException {

		boolean result;
		this.open();
		try {
			String sql = "SELECT PROFILE FROM selfregistered_user WHERE EMAIL = '" + email + "' AND PROFILE IS NOT unknown;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(!rs.next()) {
			   result = false;
		   }else {
			   result = true;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return result;
	}

	/**
	 * Check user's profile image is registered yet. 
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	true or false
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkUserImgRegistered(String email)
	throws DatabaseException, SystemException {

		boolean result;
		this.open();
		try {
			String sql = "SELECT IMG_PROFILE FROM selfregistered_user WHERE EMAIL = '" + email + "' AND IMG_PROFILE IS NOT NULL;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(!rs.next()) {
			   result = false;
		   }else {
			   result = true;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return result;
	}
	
	/**
	 * Check user account is set as release by admin from selfregistered_user table.
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	true or false
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkUserReleaseRegistered(String email)
	throws DatabaseException, SystemException {

		boolean result = false;
		this.open();
		try {
			String sql = "SELECT RELEASED FROM selfregistered_user WHERE EMAIL = '" + email + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
			   if(rs.getString("RELEASED").equals("none")) {
				   result = false;
			   }
			   else if(rs.getString("RELEASED").equals("yes")) {
				   result = true;
			   }
			   else if(rs.getString("RELEASED").equals("no")) {
				   result = true;
			   }
		   }else{
			   result = false;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return result;
	}
	
	/**
	 * This method will test the user choose the release info whether yes or no.
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	true or false
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkUserReleaseRegisteredAsYes(String email)
	throws DatabaseException, SystemException {

		boolean result;
		this.open();
		try {
			String sql = "SELECT RELEASED FROM selfregistered_user WHERE EMAIL = '" + email + "' ;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(!rs.next()) {
			   result = false;
		   }
		   else if(rs.getString("RELEASED").equals("yes")) {
			   result = true;
		   }
		   else if(rs.getString("RELEASED").equals("no")) {
			   result = true;
		   }
		   else if(rs.getString("RELEASED").equals("none")) {
			   result = false;
		   }
		   else {
			   result = false;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return result;
	}
	
	/**
	 * Check user account is set as release by admin from user_login table.
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @param 	password
	 * @return	true or false
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkNegativeAdminAcknowledgment(String email, String password)
	throws DatabaseException, SystemException {

		boolean result;
		this.open();
		try {
			String sql = "SELECT * from user_login where EMAIL = '" + email
					+ "' AND PASSWORD = '" + password
					+ "' AND ACKNOWLEDGMENT = 'no' ;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(!rs.next()) {
			   result = false;
		   }
		   else {
			   result = true;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return result;
	}
}

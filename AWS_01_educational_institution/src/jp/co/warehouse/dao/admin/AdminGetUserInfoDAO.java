package jp.co.warehouse.dao.admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This class is to get the user informaion by the administration.
 * @author H.Maki
 */
public class AdminGetUserInfoDAO extends DAOBase {
	
	/**
	 * Retrieve the registered user info with email address
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	the user info registered by admin
	 * @throws 	DatabaseException	If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public AdminRegisterUser getRegisteredUserInfo(String email)
	throws DatabaseException, SystemException {

		AdminRegisterUser registeredUser = new AdminRegisterUser();
		PreparedStatement pStmt = null;

		this.open();
		try {
			String sql = "SELECT * FROM registered_user WHERE EMAIL = '" + email + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				registeredUser.setUser_first_name(rs.getString("FIRST_NAME"));
			    registeredUser.setUser_last_name(rs.getString("LAST_NAME"));
			    registeredUser.setUser_mail(rs.getString("EMAIL"));
			    registeredUser.setRegisteredUserId(rs.getInt("ID"));
			    registeredUser.setSelfRegisteredUserId(rs.getInt("USER_ID"));
			}
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return registeredUser;
	}
	
	/**
	 * Get the self registered user table info as using the E-mail address is key.
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	the user info registered by user
	 * @throws 	DatabaseException	If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public UserRegisterUser getSelfRegisteredUserInfo(String email)
	throws DatabaseException, SystemException {

		UserRegisterUser registeredUser = new UserRegisterUser();
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT * FROM selfregistered_user WHERE EMAIL = '" + email + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	registeredUser.setGender_profile(rs.getString("GENDER_PROFILE"));
		    	registeredUser.setOpenMail(rs.getString("OPEN_MAIL"));
		    	
		    	if(rs.getString("PHONE") != null) {
			      registeredUser.setPhone(rs.getString("PHONE"));
		    	}
		    	if(rs.getString("WEB_SITE") != null) {
				      registeredUser.setWeb_site(rs.getString("WEB_SITE"));
			    }

			    registeredUser.setProfile(rs.getString("PROFILE"));
			    registeredUser.setReleased(rs.getString("RELEASED"));
			    registeredUser.setId(rs.getInt("USER_ID"));
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	    return registeredUser;
	}
	
	/**
	 * Get the self registered user table info as using the E-mail address is key.
	 * 
	 * @author	H.Maki
	 * @param 	selfRegisteredUserId
	 * @return	the user info registered by user
	 * @throws 	DatabaseException	If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public UserRegisterUser getSelfRegisteredUserInfoById(int selfRegisteredUserId)
	throws DatabaseException, SystemException {

		UserRegisterUser registeredUser = new UserRegisterUser();
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT * FROM selfregistered_user WHERE USER_ID = '" + selfRegisteredUserId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	registeredUser.setGender_profile(rs.getString("GENDER_PROFILE"));
		    	registeredUser.setOpenMail(rs.getString("OPEN_MAIL"));
		    	
		    	if(rs.getString("PHONE") != null) {
			      registeredUser.setPhone(rs.getString("PHONE"));
		    	}
		    	if(rs.getString("WEB_SITE") != null) {
				      registeredUser.setWeb_site(rs.getString("WEB_SITE"));
			    }

			    registeredUser.setProfile(rs.getString("PROFILE"));
			    registeredUser.setReleased(rs.getString("RELEASED"));
			    registeredUser.setId(rs.getInt("USER_ID"));
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	    return registeredUser;
	}
	
	/**
	 * Checking the acknowledgment status for indicating the color
	 * at the user search page in administration page.
	 * 
	 * @author	H.Maki
	 * @param 	This email is user email address.
	 * @return	The true tells the user is not genuine.
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkAcknowledgmentByEmail(String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		boolean result;
		this.open();
		try {
			String sql = "SELECT * from user_login "
					+ "where EMAIL = '" + email
					+ "' AND ACKNOWLEDGMENT = 'no';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(!rs.next()) {
			   result = false;
		   }
		   else {
			   result = true;
		   }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	    return result;
	}
	
	/**
	 * This method is used for find the user when the admin account wish to 
	 * create the new article and appoint one of the user.
	 * 
	 * @author	H.Maki
	 * @param 	userSearchWord would be the author name.
	 * @return	If the research hits any result, the result would be
	 * 			the registered user info.
	 * @throws 	DatabaseException	 If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public AdminRegisterUser getUserByWordSearch(String userSearchWord)
	throws DatabaseException, SystemException {

		AdminRegisterUser registeredUser = new AdminRegisterUser();
		PreparedStatement pStmt = null;
		this.open();
		try {
			/*
			 * Find the user even the part of search word is matched, the user will be called.
			 */
			String sql = "SELECT * FROM registered_user WHERE CONCAT(LAST_NAME, FIRST_NAME) LIKE '%" + userSearchWord + "%' ;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	registeredUser.setUser_first_name(rs.getString("FIRST_NAME"));
		    	registeredUser.setUser_last_name(rs.getString("LAST_NAME"));
		    	registeredUser.setUser_mail(rs.getString("EMAIL"));
			    registeredUser.setRegisteredUserId(rs.getInt("ID"));
			    registeredUser.setSelfRegisteredUserId(rs.getInt("USER_ID"));
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	    return registeredUser;
	}
	
	/**
	 * This method returns the registered user information.
	 * The parameter is the ID in the registered_instructer table.
	 * 
	 * @author	H.Maki
	 * @param 	userId
	 * @return	the user info registered by admin
	 * @throws 	DatabaseException	If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public AdminRegisterUser getUserByAdminWithUserId(int userId)
	throws DatabaseException, SystemException {

		AdminRegisterUser registeredUser = new AdminRegisterUser();
		PreparedStatement pStmt = null;

		this.open();
		try {
			String sql = "SELECT * FROM registered_user WHERE ID = '" + userId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				registeredUser.setUser_first_name(rs.getString("FIRST_NAME"));
			    registeredUser.setUser_last_name(rs.getString("LAST_NAME"));
			    registeredUser.setUser_mail(rs.getString("EMAIL"));
			    registeredUser.setRegisteredUserId(rs.getInt("ID"));
			    registeredUser.setSelfRegisteredUserId(rs.getInt("USER_ID"));
			}
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return registeredUser;
	}
}
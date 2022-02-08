package jp.co.warehouse.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jp.co.warehouse.DaoInterface.RegisterInfoInterface;
import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This class is assigned to get the user informaion by the user.
 * @author H.Maki
 *
 */
public class UserGetUserInfoDAO extends DAOBase implements RegisterInfoInterface {
	/**
	 * When the new user registers his info this method will be used.
	 * Because the email is already registered at regsitered_user table, the method is "updated".
	 * ${inheritDoc}
	 */
	@Override
	public void updateUserInfo(UserRegisterUser userRegisteruser, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE selfregistered_user SET"
					+ " GENDER_PROFILE = ?,"
					+ " PHONE = ?,"
					+ " WEB_SITE = ?,"
					+ " OPEN_MAIL = ?,"
					+ " PROFILE = ?"
					+ " WHERE EMAIL = ?"
					+ ";";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userRegisteruser.getGender_profile());
			pStmt.setString(2, userRegisteruser.getPhone());
			pStmt.setString(3, userRegisteruser.getWeb_site());
			pStmt.setString(4, userRegisteruser.getOpenMail());
			pStmt.setString(5, userRegisteruser.getProfile());
			pStmt.setString(6, email);
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
	 * Get the self registered user table info as using the E-mail address is key.
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	UserRegisterUser
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public UserRegisterUser getSelfRegisteredUserInfo(String email)
	throws DatabaseException, SystemException {

		UserRegisterUser userRegisterUser = new UserRegisterUser();

		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT * FROM selfregistered_user WHERE EMAIL = '" + email + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	userRegisterUser.setGender_profile(rs.getString("GENDER_PROFILE"));
		    	userRegisterUser.setOpenMail(rs.getString("OPEN_MAIL"));
		    	
		    	if(rs.getString("PHONE") != null) {
			      userRegisterUser.setPhone(rs.getString("PHONE"));
		    	}
		    	if(rs.getString("WEB_SITE") != null) {
			      userRegisterUser.setWeb_site(rs.getString("WEB_SITE"));
			    }
		    	
			    userRegisterUser.setProfile(rs.getString("PROFILE"));
			    userRegisterUser.setReleased(rs.getString("RELEASED"));
			    userRegisterUser.setId(rs.getInt("USER_ID"));
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	    return userRegisterUser;
	}
	
	/**
	 * Retrieve the registered user info with email address
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	AdminRegisterUser
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public AdminRegisterUser getRegisteredUserInfo(String email)
	throws DatabaseException, SystemException {

		AdminRegisterUser egisteredUser = new AdminRegisterUser();
		PreparedStatement pStmt = null;

		this.open();
		try {
			String sql = "SELECT * FROM registered_user WHERE EMAIL = '" + email + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				egisteredUser.setUser_first_name(rs.getString("FIRST_NAME"));
			    egisteredUser.setUser_last_name(rs.getString("LAST_NAME"));
			    egisteredUser.setUser_mail(rs.getString("EMAIL"));
			    egisteredUser.setRegisteredUserId(rs.getInt("ID"));
			    egisteredUser.setSelfRegisteredUserId(rs.getInt("USER_ID"));
			}
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return egisteredUser;
	}
	
	/**
	 * Find the registered user ID from the e-mail address
	 * 
	 * @author	H.Maki
	 * @param 	email
	 * @return	int: Checking existing
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public int getRegisteredUserIdByUserMail(String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		int registereduserId = 0;
		this.open();
		try {
			String sql = "SELECT ID FROM registered_user WHERE EMAIL = '" + email + "' ;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				registereduserId = rs.getInt("ID");
		    }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return registereduserId;
	}
	
	/**
	 * Get the self registered user ID which is determined by the registered user ID
	 * 
	 * @author	H.Maki
	 * @param 	userId
	 * @return	int: Checking existing
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public int getSelfRegisteredUserIdByRegisteredUserId(int userId)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		int selfRegisteredUserId = 0;
		this.open();
		try {
			String sql = "SELECT USER_ID FROM registered_user WHERE ID = '" + userId + "' ;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				selfRegisteredUserId = rs.getInt("USER_ID");
		    }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
	    return selfRegisteredUserId;
	}
	
	/**
	 * Get the registered user info by the user id
	 * 
	 * @author	H.Maki
	 * @param 	id
	 * @return	AdminRegisterUser
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public AdminRegisterUser getUserByUserWithUserId(int id)
	throws DatabaseException, SystemException {

		AdminRegisterUser registeredUser = new AdminRegisterUser();
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT * FROM registered_user WHERE ID = " + id + ";";
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
	 * This is for retrieve user information which is identified by selfregistered_user id.
	 * 
	 * @author	H.Maki
	 * @param 	selfRegisteredUserId
	 * @return	UserRegisterUser
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public UserRegisterUser getSelfRegisteredUserInfoById(int selfRegisteredUserId)
	throws DatabaseException, SystemException {

		UserRegisterUser userRegisterUser = new UserRegisterUser();
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT * FROM selfregistered_user WHERE USER_ID = '" + selfRegisteredUserId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	userRegisterUser.setGender_profile(rs.getString("GENDER_PROFILE"));
		    	if(rs.getString("PHONE") != null) {
			      userRegisterUser.setPhone(rs.getString("PHONE"));
		    	}
		    	if(rs.getString("WEB_SITE") != null) {
			      userRegisterUser.setWeb_site(rs.getString("WEB_SITE"));
			    }

			    userRegisterUser.setProfile(rs.getString("PROFILE"));
			    userRegisterUser.setEmail(rs.getString("EMAIL"));
			    userRegisterUser.setReleased(rs.getString("RELEASED"));
			    userRegisterUser.setId(rs.getInt("USER_ID"));
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	    return userRegisterUser;
	}
}

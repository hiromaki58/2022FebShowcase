package jp.co.warehouse.dao.admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This class is for Administration set or change the user information.
 * @author hirog
 * Sep 18, 2021
 *
 */
public class AdminSetUserInfoDAO extends DAOBase {
	/**
	 * This method is used for register the new user to the DB.
	 *  
	 * @author	H.Maki
	 * @param	registeredUser is the user info which is created by admin.
	 * @return
	 * @throws 	DatabaseException, if DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public int addUserByAdmin(AdminRegisterUser registeredUser)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		PreparedStatement userStmt = null;
		PreparedStatement pStmt02 = null;
		PreparedStatement pStmt03 = null;
		
		int result = 0;
		int registereduserIdNumber = 0;
		this.open();
		
		try {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO registered_user "
					+ "(FIRST_NAME, LAST_NAME, EMAIL) "
					+ "VALUES "
					+ "(?, ?, ?);";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, registeredUser.getUser_first_name());
			pStmt.setString(2, registeredUser.getUser_last_name());
			pStmt.setString(3, registeredUser.getUser_mail());
			pStmt.executeUpdate();

			/*
			 *The reason why inserting the e-mail into the user table is the e-mail will be the key to
			 *identify which cell is inserted with the info which is registered by the new user.
			 */
			String userSql = "INSERT INTO selfregistered_user "
					+ "(EMAIL) "
					+ "VALUES "
					+ "(?);";
			userStmt = conn.prepareStatement(userSql);
			userStmt.setString(1, registeredUser.getUser_mail());
			userStmt.executeUpdate();

			/*
			 *Get the "ID" at user table and set it as a user ID into registered_user table
			 */
			String sql02 = "SELECT LAST_INSERT_ID() AS LAST;";
			pStmt02 = conn.prepareStatement(sql02);
			ResultSet rs02 = pStmt02.executeQuery();

			if(rs02.next()) {
				registereduserIdNumber = rs02.getInt("LAST");
			}
			String sql03 = "UPDATE registered_user "
					+ "SET USER_ID = ? "
					+ "WHERE EMAIL = ? "
					+ ";";
			
			pStmt03 = conn.prepareStatement(sql03);
			pStmt03.setInt(1, registereduserIdNumber);
			pStmt03.setString(2, registeredUser.getUser_mail());
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
	    	this.close(userStmt);
	    	this.close(pStmt02);
	    	this.close(pStmt03);
	    }
		return result;
	}
		
	/**
	 * This method is for the new user chooses the release condition.
	 * 
	 * @author	H.Maki
	 * @param 	released
	 * @param 	email
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void addUserReleasedByAdmin(String released, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE selfregistered_user SET RELEASED = ? "
					+ "WHERE EMAIL = ?"
					+ ";";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, released);
			pStmt.setString(2, email);
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
	 * The administration account change the acknowledgment status if the admin wishes to temporary close the user account.
	 * 
	 * @author	H.Maki
	 * @param 	acknowledgment
	 * @param 	email
	 * @throws 	DatabaseException, If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateUserLoginByAdmin(String acknowledgment, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE user_login SET ACKNOWLEDGMENT = ? "
					+ "WHERE EMAIL = ?"
					+ ";";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, acknowledgment);
			pStmt.setString(2, email);
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
	 * Change the user name by the admin account
	 * 
	 * @author	H.Maki
	 * @param 	registeredUser is the target user.
	 * @param 	email is the way to confirm who is the target.
	 * @return	
	 * @throws 	DatabaseException, If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public int updateUserByAdmin(AdminRegisterUser registeredUser, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		int result = 0;
		this.open();
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE registered_user SET "
					+ "FIRST_NAME = ?,"
					+ "LAST_NAME = ?"
					+ "WHERE EMAIL = ?"
					+ ";";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, registeredUser.getUser_first_name());
			pStmt.setString(2, registeredUser.getUser_last_name());
			pStmt.setString(3, email);
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
		return result;
	}
}

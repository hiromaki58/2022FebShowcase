package jp.co.warehouse.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.warehouse.DaoInterface.LoginStrategy;
import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.Mail;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This class is controlling the database access about setting, updating and adding login information.
 * @author H.Maki
 *
 */
public class UserLoginDAO extends DAOBase implements LoginStrategy {

	private Statement stmt;
	
	/**
	 * Checking the user login ID and password are in the DB.
	 * 
	 * @author	H.Maki
	 * @param	encryptedId_user
	 * @param	password
	 * @throws 	SystemException
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws	SQLException If the DB connection is fail.
	 */
	@Override
	public boolean checkLogin(String encryptedId_user, String password) 
	throws SystemException, DatabaseException {
		
		PreparedStatement pStmt = null;
		boolean result;
		this.open();
		try {
			String sql = "SELECT * from user_login where EMAIL = '" + encryptedId_user
					+ "' AND PASSWORD = '" + password
					+ "' AND ACKNOWLEDGMENT = 'yes' ;";
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
	
	/**
	 * Set the initial password to login by the user.
	 * This is the initial step to use the "user_login" table.
	 * The table is started by the user.
	 *  
	 * @author	H.Maki	
	 * @param 	mailAddress
	 * @param 	password_new
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void addPasswordByUser(Mail mailAddress, String password_new)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "INSERT INTO user_login ("
					+ "EMAIL, PASSWORD)"
					+ "VALUES('"
					+ mailAddress.getMail()
					+ "','" + password_new
					+ "');";
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
	
	/**
	 * After checking the user is genuine because he knows the old password,
	 * the new password is registered by this method.
	 * 
	 * @author	H.Maki
	 * @param 	encryptedId_user is who wish to change the password
	 * @param	password_new is the new password to regiser into DB.
	 * @throws	DatabaseException, if the DB is down.
	 * @throws	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	@Override
	public void updatePassword(String encryptedId_user, String password_new)
	throws DatabaseException, SystemException {
		this.open();
		try {
			stmt = conn.createStatement();
			String sql = "UPDATE user_login SET PASSWORD = '"
					+ password_new
					+ "' WHERE EMAIL = '"
					+ encryptedId_user
					+ "';";
			stmt.executeUpdate(sql);
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(stmt);
	    }
	}
	
	/**
	 * If the user wish to change the password, 
	 * before starting the process the user is tested by typing old password,
	 * and determined he is genuine.
	 * 
	 * @author 	H.Maki
	 * @param 	encryptedId_user is the E-mail address of the user who wish to change the password.
	 * @param 	password_old is the old password which is used before. 
	 * @return	it tells existing password is right.
	 * @throws	DatabaseException If the DB connection is fail.
	 * @throws	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	@Override
	public boolean checkMatchOldPassword(String encryptedId_user, String password_old)
	throws DatabaseException, SystemException {

		boolean result;
		this.open();
		try {
			String sql = "SELECT * FROM user_login WHERE EMAIL = '"
					+ encryptedId_user
					+ "' AND PASSWORD = '"
					+ password_old
					+ "' ;";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);

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
	    	this.close(stmt);
	    }
		return result;
	}
	
	/**
	 * If the user wish to change the E-mail as the ID, before starting the process
	 * the user is testified by typing old mail address and password,
	 * and determined he is genuine.
	 * 
	 * @author 	H.Maki
	 * @param 	encryptedPassword is the password to check the user is genuin.
	 * @param 	id_old is the E-mail address of which the user has used before.
	 * @return	The user is genuin or not.
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkMatchOldIdByUser(String encryptedPassword, String id_old)
	throws DatabaseException, SystemException {

		boolean result;
		this.open();
		try {
			String sql = "SELECT * FROM user_login WHERE PASSWORD = '"
					+ encryptedPassword
					+ "' AND EMAIL = '"
					+ id_old
					+ "' ;";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);

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
	    	this.close(stmt);
	    }
		return result;
	}
	
	/**
	 * After checking the user is genuine because he knows the old password,
	 * the new password is registered by this method.
	 * This method infects to the user_login table in the DB.
	 * 
	 * @author 	H.Maki
	 * @param 	encryptedPassword 
	 * @param 	id_new
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateIdByUser(String encryptedPassword, String id_new)
	throws DatabaseException, SystemException {
		this.open();
		try {
			stmt = conn.createStatement();
			String sql = "UPDATE user_login SET EMAIL = '"
					+ id_new
					+ "' WHERE PASSWORD = '"
					+ encryptedPassword
					+ "';";
			stmt.executeUpdate(sql);
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(stmt);
	    }
	}
	
	/**
	 * This class is used to check the email is registered in the DB.
	 * If so, the user is genuine and can proceed to set the initial password set up page.
	 * 
	 * @author 	H.Maki
	 * @param 	email
	 * @return
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkEmail(String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		boolean result;
		this.open();
		try {
			String sql = "SELECT * from registered_user where EMAIL = '" + email + "';";
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
	 * Get the registered user information by the user ID
	 * 
	 * @author	H.Maki
	 * @param	email
	 * @return	USER_ID at the registered_user table.
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public String getUserIdByUser(String email) 
			throws DatabaseException, SystemException{
		
		PreparedStatement pStmt = null;
		String result = "";
		this.open();
		try {
			String sql = "SELECT USER_ID from registered_user WHERE EMAIL = '"
					+ email
					+ "';";
			
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			result = rs.getString("USER_ID");
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

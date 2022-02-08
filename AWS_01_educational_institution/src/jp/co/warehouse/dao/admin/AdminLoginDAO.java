package jp.co.warehouse.dao.admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jp.co.warehouse.DaoInterface.LoginStrategy;
import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * To login to the page as the administrator.
 * @author hirog
 * Sep 18, 2021
 *
 */
public class AdminLoginDAO extends DAOBase implements LoginStrategy {
	
	/**
	 * ${inheritDoc}
	 */
	@Override
	public boolean checkLogin(String encryptedId_admin, String password) 
	throws SystemException, DatabaseException, SystemException {
		PreparedStatement pStmt = null;
		boolean result;

		this.open();
		try {
			String sql = "SELECT ADMINISTRATORID, PASSWORD from administrator where ADMINISTRATORID = '"
					+ encryptedId_admin
					+ "' AND PASSWORD = '"
					+ password + "';";
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
	 * After making sure the admin account with the old password,
	 * the new password is registered by this method.
	 * 
	 * @author	H.Maki
	 * @param	encryptedId_admin is the admin account.
	 * @param	password_new is the new password which will be registered
	 * 			into the DB.
	 * @throws	DatabaseException, if DB connection is fail.
	 * @throws	SQLException If the DB connection is fail.
	 */
	@Override
	public void updatePassword(String encryptedId_admin, String password_new)
	throws DatabaseException, SystemException {
		PreparedStatement pStmt = null;
		
		this.open();
		try {
			String sql = "UPDATE administrator SET PASSWORD = ? "
//					+ password_new
					+ "WHERE ADMINISTRATORID = ?;";
//					+ encryptedId_admin
//					+ "';";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, password_new);
			pStmt.setString(2, encryptedId_admin);
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
	 * If the ADMIN accounts wish to change the password,
	 * the ADMIN accounts is tested by typing old password.
	 * ${inheritDoc}
	 */
	@Override
	public boolean checkMatchOldPassword(String encryptedId_admin, String password_old)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		boolean result;
		this.open();
		try {
			String sql = "SELECT * FROM administrator WHERE ADMINISTRATORID = '"
					+ encryptedId_admin
					+ "' AND PASSWORD = '"
					+ password_old
					+ "' ;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery(sql);

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
	 * Testing the admin account does exists in the USERID table.
	 * 
	 * @author	H.Maki
	 * @param 	encryptedAdminEmail
	 * @return
	 * @throws 	DatabaseException	If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public boolean checkAdminEmail(String encryptedAdminEmail)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		boolean result;
		this.open();
		try {
			String sql = "SELECT * from users where USERID = '" + encryptedAdminEmail + "';";
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
}

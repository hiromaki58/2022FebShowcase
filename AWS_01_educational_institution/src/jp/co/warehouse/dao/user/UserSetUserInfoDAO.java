package jp.co.warehouse.dao.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import jp.co.warehouse.DaoInterface.RegisterInfoInterface;
import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

public class UserSetUserInfoDAO extends DAOBase implements RegisterInfoInterface {

	/*
	 * When the new user registers his info this method will be used.
	 * Because the email is already registered at regsitered_user table, the method is "updated".
	 */
	@Override
	public void updateUserInfo(UserRegisterUser userRegisterUser, String email)
			throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "UPDATE selfregistered_user SET"
					+ " GENDER_PROFILE = '" + userRegisterUser.getGender_profile() + "', "
					+ " PHONE = '" + userRegisterUser.getPhone() + "', "
					+ " WEB_SITE = '" + userRegisterUser.getWeb_site() + "', "
					+ " OPEN_MAIL = '" + userRegisterUser.getOpenMail() + "', "
					+ " PROFILE = '" + userRegisterUser.getProfile()
					+ "' WHERE EMAIL = '" + email + "';";
			
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
	 * This method is for the new user chooses the release condition.
	 */
	public void addUserReleasedByUser(String released, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "UPDATE selfregistered_user SET RELEASED = "
					+ "('" + released + "')"
					+ "WHERE EMAIL ="
					+ "('" + email + "')"
					+ ";";
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
	 * Update the new E-mail address into the registered_user table
	 * as a new mail address is set.
	 */
	public void updateRegisteredInstrutorMailById(int registereduserId, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "UPDATE registered_user SET EMAIL = '" + email + "' WHERE ID = " + registereduserId + " ;";

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
	 * Update the new E-mail address into the selfregistered_user table
	 * as a new mail address is set.
	 */
	public void updateSelfRegisteredUserMailById(int selfRegisteredUserId, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "UPDATE selfregistered_user SET EMAIL = '" + email + "' WHERE USER_ID = " + selfRegisteredUserId + " ;";

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
	 * @param 	mail identifies the user.
	 * @param 	password_new is the new password which is set by the user.
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 */
	public void updatePasswordByUser(String mail, String password_new)
	throws DatabaseException, SystemException {
		
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "UPDATE user_login SET PASSWORD = '"
					+ password_new
					+ "' WHERE EMAIL = '"
					+ mail
					+ "';";
			
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
}

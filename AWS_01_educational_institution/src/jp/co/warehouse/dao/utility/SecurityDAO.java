package jp.co.warehouse.dao.utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * To identify whom wanna change the password is genuine, this class is used.
 * @author hirog
 *
 */
public class SecurityDAO extends DAOBase {
	/**
	 * Check the genuine user
	 * Used at
	 *  1, Password reissue
	 *  2, Admin new user registration(cross site counter measure)
	 * 
	 * @author	H.Maki
	 * @param 	token
	 * @param 	email
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void addTokenDB(String token, String email)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		//Set the "NULL" to make TIME_STAMP have the current time
		try {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO token(TOKEN, EMAIL) VALUES(?, ?)";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, token);
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
	 * Determine the token is existed in the DB
	 * 
	 * @author	H.Maki
	 * @param 	token
	 * @return	Check the token is registered into DB.
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public String checkTokenExist(String token)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		String result = "";
		this.open();
		try {
			String sql = "SELECT * from token WHERE TOKEN = '" + token + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(!rs.next()) {
			   //The answer is the e-mail address is not registered.
			   result = "notExist";
		   }
		   else {
			   //The address is in the DB.
			   result = "exist";
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
	 * Get the email address which is registered when the token is issued, 
	 * and this address will be used to update the password information of the instructor.
	 * 
	 * @author	H.Maki
	 * @param 	token
	 * @return
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public String getEmailByToken(String token)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		String result = "";
		this.open();
		try {
			String sql = "SELECT EMAIL FROM token WHERE TOKEN = '" + token + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				result = rs.getString("EMAIL");
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

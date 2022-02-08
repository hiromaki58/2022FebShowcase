package jp.co.warehouse.dao.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.DAOParameters;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This class is the DB connection base class.
 * @author H.Maki
 *
 */
public class DAOBase {
	/**
	 * Connection instance
	 */
	protected Connection conn;

	/**
	 * Open the connection to DB
	 * 
	 * @author 	H.Maki
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SystemException
	 * @throws 	ClassNotFoundException
	 * @throws 	SQLException If the DB connection is fail.
	 */
	protected void open() throws DatabaseException, SystemException {
		try {
	      Class.forName(DAOParameters.DRIVER_NAME);
	      conn = DriverManager.getConnection(DAOParameters.CONNECT_STRING, DAOParameters.USERID, DAOParameters.PASSWORD);
	    }
	    catch (ClassNotFoundException e) {
	    	throw new SystemException(ExceptionParameters.SYSTEM_EXCEPTION_MESSAGE, e);
	    }
	    catch (SQLException e) {
	      throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE, e);
	    }
	}

	/**
	 * Close the connection to DB
	 * 
	 * @author	H.Maki
	 * @param 	stmt
	 * @throws 	DatabaseException If the DB connection is fail.
	 * @throws 	SQLException If the DB connection is fail.
	 */
	protected void close(Statement stmt) throws DatabaseException {
	    try {
	      if (stmt != null) {
	        stmt.close();
	      }
	      if (conn != null) {
	        conn.close();
	      }
	    }
	    catch (SQLException e) {
	      throw new DatabaseException(ExceptionParameters.DATABASE_CLOSE_EXCEPTION_MESSAGE, e);
	    }
	}
}

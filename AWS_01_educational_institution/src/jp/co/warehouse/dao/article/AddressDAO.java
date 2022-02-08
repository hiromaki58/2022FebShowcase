package jp.co.warehouse.dao.article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

public class AddressDAO extends DAOBase {

	/*
	 * Update the link address of the image at the top in the top page
	 */
	public void updateMvLinkAddress(String linkAddress)
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			//Due to the main visual should be only one, there is only one ID in the table
			String sql = "UPDATE mv SET MV_WEB_SITE = '" + linkAddress + "' WHERE ID = 1;";

			//This SQL sentence is used at really first time to set the main visual image
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
	 * Get the url for the top page image link
	 */
	public String getMvLinkAddress()
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		String linkAddress = "";

		this.open();
		try {
			//Due to the main visual should be only one, there is only one ID in the table
			String sql = "SELECT MV_WEB_SITE FROM mv WHERE ID = 1;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	linkAddress = rs.getString("MV_WEB_SITE");
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return linkAddress;
	}
}
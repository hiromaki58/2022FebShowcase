package jp.co.warehouse.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.AdminRegisterUser;
import jp.co.warehouse.entity.UserArray;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * Show the all users who are "released" the info at the public page
 * @author hirog
 * Sep 18, 2021
 *
 */
public class PublicGetUserDAO extends DAOBase {
	
	public UserArray getUserInfoOrderedByName()
	throws DatabaseException, SystemException {

		PreparedStatement pStmt = null;
		UserArray userInfo = new UserArray();
		this.open();
		try {
			String sql = "SELECT registered_user.* FROM registered_user "
					+ "INNER JOIN selfregistered_user "
					+ "ON registered_user.USER_ID = selfregistered_user.USER_ID "
					+ "WHERE RELEASED = 'yes' "
					+ "ORDER BY LAST_NAME;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	AdminRegisterUser record = new AdminRegisterUser();
			    record.setUser_first_name(rs.getString("FIRST_NAME"));
			    record.setUser_last_name(rs.getString("LAST_NAME"));
			    record.setUser_mail(rs.getString("EMAIL"));

//			    String certifications = rs.getString("CERTIFICATION_LIST");
//			    List<String> listCertification = new ArrayList<String>(Arrays.asList(certifications.split(",")));
//			    record.setCertifications(listCertification);

			    record.setRegisteredUserId(rs.getInt("ID"));
			    record.setSelfRegisteredUserId(rs.getInt("USER_ID"));
			    userInfo.addUserRecord(record);
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
	    return userInfo;
	}
}

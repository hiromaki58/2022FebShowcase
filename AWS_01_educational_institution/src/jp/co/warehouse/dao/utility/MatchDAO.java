package jp.co.warehouse.dao.utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * This is a bridge between the user id and article id.
 * @author hirog
 * Sep 18, 2021
 *
 */
public class MatchDAO extends DAOBase {

	/**
	 * This SQL will supply the user id who register the article from article ID.
	 * 
	 * @author 	hirog
	 * Sep 18, 2021
	 * @param 	articleId
	 * @return	User id
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public int getUserIdByArticleId(int articleId)
	throws DatabaseException, SystemException {
		PreparedStatement pStmt = null;
		int result = 0;

		this.open();
		try {
			String sql = "SELECT user_ID FROM user_article_match WHERE article_ID = ?;";

			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, articleId);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	result = rs.getInt("user_ID");
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
	 * This SQL will supply thearticle ID with the user id who register the article.
	 * 
	 * @author 	hirog
	 * Sep 18, 2021
	 * @param 	registeredUserId
	 * @return	List of the articles
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public ArrayList<Integer> getArticleIdByUserId(int registeredUserId)
	throws DatabaseException, SystemException {
		PreparedStatement pStmt = null;
		ArrayList<Integer> numList = new ArrayList<Integer>();

		this.open();
		try {
			String sql = "SELECT article_ID FROM user_article_match WHERE user_ID  = ?;";

			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, registeredUserId);
			ResultSet rs = pStmt.executeQuery();

		    while (rs.next()) {
		    	int num = rs.getInt("article_ID");
		    	numList.add(num);
		    }
		}
	    catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return numList;
	}
}
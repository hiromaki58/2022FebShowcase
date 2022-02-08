package jp.co.warehouse.dao.article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.entity.Article;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

public class AdminGetArticleDAO extends DAOBase {
	
	/*
	 * Have the article data with article id
	 */
	public Article getArticleInfoByArticleId(int articleId)
	throws DatabaseException, SystemException {

		Article articleInfo = new Article();
		StringBuilder str = new StringBuilder();
		
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql02 = "SELECT * FROM article WHERE ARTICLE_ID = '" + articleId + "';";
			pStmt = conn.prepareStatement(sql02);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				articleInfo.setArticle_name(rs.getString("ARTICLE_NAME"));
				articleInfo.setCategory(rs.getString("CATEGORY"));

				articleInfo.setOpening_day(rs.getDate("OPENING_DAY"));
				articleInfo.setOpening_hour(rs.getInt("OPENING_HOUR"));
				articleInfo.setOpening_min(rs.getInt("OPENING_MIN"));
				articleInfo.setClosing_day(rs.getDate("CLOSING_DAY"));
				articleInfo.setClosing_hour(rs.getInt("CLOSING_HOUR"));
				articleInfo.setClosing_min(rs.getInt("CLOSING_MIN"));

				if(rs.getString("user_LIST") != null) {
					String users = rs.getString("user_LIST");
				    List<String> listuser = new ArrayList<String>(Arrays.asList(users.split(",")));
				    articleInfo.setUserList(listuser);
				}
				/*
				 * If the priority is set by the admin account, the instance will carry the information.
				 */
				if(rs.getInt("WEIGHT") != 0) {
				    articleInfo.setWeight(rs.getInt("WEIGHT"));
				}
				
				articleInfo.setArticleId(rs.getInt("ARTICLE_ID"));
 				articleInfo.setUser(str.append(rs.getString("AUTHOR")));
 				articleInfo.setCompany_place(rs.getString("PLACE"));
 				articleInfo.setCompany_addr(rs.getString("ADDRESS"));
 				articleInfo.setCeo(rs.getString("CEO"));
 				articleInfo.setCapital(rs.getString("CAPITAL"));
 				articleInfo.setEmployeeNumber(rs.getInt("EMPLOYEE_NUMBER"));
 				
 				articleInfo.setEstablishedDate(rs.getDate("ESTABLISHED_DATE"));
 				articleInfo.setCompany_mail(rs.getString("COMPANY_EMAIL"));
 				articleInfo.setCompany_phone(rs.getString("COMPANY_PHONE"));
 				articleInfo.setCompany_url(rs.getString("COMPANY_URL"));
 				articleInfo.setIntroduction(rs.getString("INTRODUCTION"));
 				articleInfo.setImg_id(rs.getInt("IMG_ID"));
			}
		}
		catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }
	    finally {
	    	this.close(pStmt);
	    }
		return articleInfo;
	}
}

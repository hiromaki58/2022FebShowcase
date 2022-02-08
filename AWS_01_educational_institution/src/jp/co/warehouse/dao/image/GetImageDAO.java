package jp.co.warehouse.dao.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

public class GetImageDAO extends DAOBase {
	
	/*
	 *This method provides the user's profile image.
	 *The image is found by the user_id.
	 */
	public BufferedImage selectImageByUserId(int usrId)
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_PROFILE FROM selfregistered_user WHERE USER_ID = " + usrId + ";";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("IMG_PROFILE");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 *This method provides the user's profile image.
	 *The image is found by the email.
	 */
	public BufferedImage selectImageByEmail(String email)
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_PROFILE FROM selfregistered_user WHERE EMAIL = '" + email + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("IMG_PROFILE");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the eyecatch image
	 */
	public BufferedImage selectEyecatchById(int imgId)
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT EYE_CATCH FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("EYE_CATCH");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * get the article image(1)
	 */
	public BufferedImage selectArticleImg01(int imgId)
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_01 FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("IMG_COMPANY_01");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * get the article image(2)
	 */
	public BufferedImage selectArticleImg02(int imgId)
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_02 FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("IMG_COMPANY_02");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * get the article image(3)
	 */
	public BufferedImage selectArticleImg03(int imgId)
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_03 FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("IMG_COMPANY_03");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * get the article image(4)
	 */
	public BufferedImage selectArticleImg04(int imgId)
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_04 FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("IMG_COMPANY_04");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    } catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the main visual image
	 */
	public BufferedImage selectMainVisual()
	throws DatabaseException, SystemException {

		BufferedImage result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_MV FROM mv WHERE ID = 1;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
				InputStream is = rs.getBinaryStream("IMG_MV");
	            result = ImageIO.read(is);
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the extension of the eye catch image from DB
	 */
	public String selectEyecatchTypeById(int imgId)
	throws DatabaseException, SystemException {

		String result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT EYE_CATCH_TYPE FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
			   result = rs.getString("EYE_CATCH_TYPE");
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the extension of the article image01 from DB
	 */
	public String selectArticleImg01TypeById(int imgId)
	throws DatabaseException, SystemException {

		String result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_01_TYPE FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
			   result = rs.getString("IMG_COMPANY_01_TYPE");
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the extension of the article image02 from DB
	 */
	public String selectArticleImg02TypeById(int imgId)
	throws DatabaseException, SystemException {

		String result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_02_TYPE FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
			   result = rs.getString("IMG_COMPANY_02_TYPE");
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the extension of the article image03 from DB
	 */
	public String selectArticleImg03TypeById(int imgId)
	throws DatabaseException, SystemException {

		String result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_03_TYPE FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
			   result = rs.getString("IMG_COMPANY_03_TYPE");
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the extension of the article image04 from DB
	 */
	public String selectArticleImg04TypeById(int imgId)
	throws DatabaseException, SystemException {

		String result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_COMPANY_04_TYPE FROM article_img WHERE IMG_ID = '" + imgId + "';";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
			   result = rs.getString("IMG_COMPANY_04_TYPE");
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
		return result;
	}
	
	/*
	 * Get the extension of the eye catch image from DB
	 */
	public String selectMvType()
	throws DatabaseException, SystemException {

		String result = null;
		PreparedStatement pStmt = null;
		this.open();
		try {
			String sql = "SELECT IMG_MV_TYPE FROM mv;";
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		   if(rs.next()) {
			   result = rs.getString("IMG_MV_TYPE");
		   }
		   else {
			   return null;
		   }
		}catch (SQLException e) {
	        throw new DatabaseException(ExceptionParameters.DATABASE_CONNECTION_EXCEPTION_MESSAGE,e);
	    }finally {
	    	this.close(pStmt);
	    }
		return result;
	}
}

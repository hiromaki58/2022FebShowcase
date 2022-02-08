package jp.co.warehouse.dao.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.warehouse.dao.utility.DAOBase;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;
import jp.co.warehouse.parameter.ExceptionParameters;

/**
 * Registert the images to DB.
 * @author hirog
 * Sep 15, 2021
 *
 */
public class SetImageDAO extends DAOBase {
	
	/**
	 * Register the article image and return the id at article_img table.
	 * The id will be stored as img_id at article table and will be used as a foreign key to find the images from article id.
	 *
	 * Due to the image extension is needed to show the proper color balance image to the page
	 * the extension will be stored in the DB
	 *
	 * The strings as call as "eyecatch type" are the information to tell the extension.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	eyecatchByteData
	 * @param 	articleImg01ByteData
	 * @param 	articleImg02ByteData
	 * @param 	articleImg03ByteData
	 * @param 	articleImg04ByteData
	 * @param 	articleIdNumber
	 * @param 	eyecatchType
	 * @param 	articleImg01Type
	 * @param 	articleImg02Type
	 * @param 	articleImg03Type
	 * @param 	articleImg04Type
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void addArticleImg(byte[] eyecatchByteData, byte[] articleImg01ByteData, byte[] articleImg02ByteData, byte[] articleImg03ByteData, byte[] articleImg04ByteData, int articleIdNumber,
	String eyecatchType, String articleImg01Type, String articleImg02Type, String articleImg03Type, String articleImg04Type)
	throws DatabaseException, SystemException, IOException {

		PreparedStatement pStmt = null;
		PreparedStatement pStmt02 = null;
		PreparedStatement pStmt03 = null;
		int articleImgIdNumber = 0;
		this.open();
		try {
			conn.setAutoCommit(false);
			
			ByteArrayInputStream eyecatchStream = new ByteArrayInputStream(eyecatchByteData);
			ByteArrayInputStream articleImg01Stream = new ByteArrayInputStream(articleImg01ByteData);
			ByteArrayInputStream articleImg02Stream = new ByteArrayInputStream(articleImg02ByteData);
			ByteArrayInputStream articleImg03Stream = new ByteArrayInputStream(articleImg03ByteData);
			ByteArrayInputStream articleImg04Stream = new ByteArrayInputStream(articleImg04ByteData);
			String sql = "INSERT INTO article_img ("
					+ "EYE_CATCH, IMG_COMPANY_01, IMG_COMPANY_02, IMG_COMPANY_03, IMG_COMPANY_04, "
					+ "EYE_CATCH_TYPE, IMG_COMPANY_01_TYPE, IMG_COMPANY_02_TYPE, IMG_COMPANY_03_TYPE, IMG_COMPANY_04_TYPE)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			pStmt = conn.prepareStatement(sql);

			//Send the binary stream to the DB
			pStmt.setBinaryStream(1,eyecatchStream);
			pStmt.setBinaryStream(2,articleImg01Stream);
			pStmt.setBinaryStream(3,articleImg02Stream);
			pStmt.setBinaryStream(4,articleImg03Stream);
			pStmt.setBinaryStream(5,articleImg04Stream);

			/*
			 *Set up the extension of the image in the table
			 *This parameter will be used when the image is called and shown in the page
			 */
			pStmt.setString(6, eyecatchType);
			pStmt.setString(7, articleImg01Type);
			pStmt.setString(8, articleImg02Type);
			pStmt.setString(9, articleImg03Type);
			pStmt.setString(10, articleImg04Type);
			pStmt.executeUpdate();

			//Get the id of the new registered article image at article_img.
			String sql02 = "SELECT LAST_INSERT_ID() AS LAST;";
			pStmt02 = conn.prepareStatement(sql02);
			ResultSet rs02 = pStmt02.executeQuery();

			if(rs02.next()) {
				articleImgIdNumber = rs02.getInt("LAST");
			}

			//Set the image id in the article table, so article can tell where the article imageds are stored in the article_img table.
			String sql03 = "UPDATE article SET IMG_ID = ? "
					+ "WHERE ARTICLE_ID = ?;";
			pStmt03 = conn.prepareStatement(sql03);
			pStmt03.setInt(1, articleImgIdNumber);
			pStmt03.setInt(2, articleIdNumber);
			pStmt03.executeUpdate();
			
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
	    	this.close(pStmt02);
	    	this.close(pStmt03);
	    }
	}
	
	/**
	 * This method is updating the user profile image with the email address.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	is
	 * @param 	email
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void addUserImgByUser(byte[] is, String email)
	throws DatabaseException, SystemException, IOException {

		PreparedStatement pStmt = null;
		this.open();
		try {
			conn.setAutoCommit(false);
			ByteArrayInputStream bais = new ByteArrayInputStream(is);
			String sql = "UPDATE selfregistered_user SET IMG_PROFILE = ? "
					+ "WHERE EMAIL = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBinaryStream(1,bais);
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
	 * Due to the image extension is needed to show the proper color balance image to the page
	 * the extension will be stored in the DB
	 *
	 * The strings as call as "main view type" are the information to tell the extension.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	eyecatchByteData
	 * @param 	eyecatchType
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateMainView(byte[] eyecatchByteData, String eyecatchType)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			ByteArrayInputStream eyecatchStream = new ByteArrayInputStream(eyecatchByteData);
			//This SQL sentence is used at really first time to set the main visual image
			String sql = "UPDATE mv SET IMG_MV = ?, IMG_MV_TYPE = ? WHERE ID = 1;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBinaryStream(1,eyecatchStream);
			pStmt.setString(2, eyecatchType);
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
	 * This method will be used for registering the new eyecatch image.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	eyecatchByteData
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateEyeCatchById(byte[] eyecatchByteData, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			ByteArrayInputStream eyecatchStream = new ByteArrayInputStream(eyecatchByteData);
			String sql = "UPDATE article_img SET EYE_CATCH = ? WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBinaryStream(1,eyecatchStream);
			pStmt.setInt(2, imgId);
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
	 * To update the article image #01.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg01ByteData
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg01(byte[] articleImg01ByteData, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			ByteArrayInputStream articleImg01Stream = new ByteArrayInputStream(articleImg01ByteData);
			String sql = "UPDATE article_img SET "
					+ "IMG_COMPANY_01 = ? "
					+ "WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBinaryStream(1,articleImg01Stream);
			pStmt.setInt(2, imgId);
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
	 * To update the article image #02.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg02ByteData
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg02(byte[] articleImg02ByteData, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			ByteArrayInputStream articleImg02Stream = new ByteArrayInputStream(articleImg02ByteData);
			String sql = "UPDATE article_img SET "
					+ "IMG_COMPANY_02 = ? "
					+ "WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBinaryStream(1,articleImg02Stream);
			pStmt.setInt(2, imgId);
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
	 * To update the article image #03.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg03ByteData
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg03(byte[] articleImg03ByteData, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			ByteArrayInputStream articleImg03Stream = new ByteArrayInputStream(articleImg03ByteData);
			String sql = "UPDATE article_img SET "
					+ "IMG_COMPANY_03 = ? "
					+ "WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBinaryStream(1,articleImg03Stream);
			pStmt.setInt(2, imgId);
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
	 * To update the article image #04.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg04ByteData
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg04(byte[] articleImg04ByteData, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			ByteArrayInputStream articleImg04Stream = new ByteArrayInputStream(articleImg04ByteData);
			String sql = "UPDATE article_img SET "
					+ "IMG_COMPANY_04 = ? "
					+ "WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBinaryStream(1,articleImg04Stream);
			pStmt.setInt(2, imgId);
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
	 * This method will be used for registering the new article image 01.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	eyeCatchType
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateEyeCatchTypeById(String eyeCatchType, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE article_img SET EYE_CATCH_TYPE = ? WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,eyeCatchType);
			pStmt.setInt(2, imgId);
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
	 * This method will be used for registering the new article image 01.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg01Type
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg01Type(String articleImg01Type, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE article_img SET IMG_COMPANY_01_TYPE = ? WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,articleImg01Type);
			pStmt.setInt(2, imgId);
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
	 * This method will be used for registering the new article image 02.
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg02Type
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg02Type(String articleImg02Type, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE article_img SET IMG_COMPANY_02_TYPE = ? WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,articleImg02Type);
			pStmt.setInt(2, imgId);
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
	 * This method will be used for registering the new article image 03.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg03Type
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg03Type(String articleImg03Type, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE article_img SET IMG_COMPANY_03_TYPE = ? WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,articleImg03Type);
			pStmt.setInt(2, imgId);
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
	 * This method will be used for registering the new article image 04.
	 * 
	 * @author 	hirog
	 * Sep 15, 2021
	 * @param 	articleImg04Type
	 * @param 	imgId
	 * @throws 	DatabaseException
	 * @throws 	SystemException
	 * @throws 	IOException
	 * @throws	SQLException If the DB connection is fail.
	 */
	public void updateArticleImg04Type(String articleImg04Type, int imgId)
	throws DatabaseException, SystemException, IOException {

		this.open();
		PreparedStatement pStmt = null;
		try {
			conn.setAutoCommit(false);
			String sql = "UPDATE article_img SET IMG_COMPANY_04_TYPE = ? WHERE IMG_ID = ?;";
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,articleImg04Type);
			pStmt.setInt(2, imgId);
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
}

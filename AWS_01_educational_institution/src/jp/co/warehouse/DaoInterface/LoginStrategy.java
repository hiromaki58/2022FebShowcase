package jp.co.warehouse.DaoInterface;

import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/**
 * Login controller class revokes this class.
 * This class aims to open close principal.
 * @author hirog
 */
public interface LoginStrategy {
	public boolean checkLogin(String id, String password)
			throws SystemException, DatabaseException, SystemException ;
	
	public void updatePassword(String encryptedId_admin, String password_new)
			throws DatabaseException, SystemException;
	
	public boolean checkMatchOldPassword(String encryptedId_admin, String password_old)
			throws DatabaseException, SystemException;
}
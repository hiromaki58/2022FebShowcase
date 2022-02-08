package jp.co.warehouse.DaoInterface;

import jp.co.warehouse.entity.UserRegisterUser;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

/*
 * Whenever the user info is new registered or modified, this interface is called.
 */
public interface RegisterInfoInterface {
	public void updateUserInfo(UserRegisterUser userRegisteruser, String email)
			throws DatabaseException, SystemException ;
}

package jp.co.warehouse.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class UserArray implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5010092630646764506L;
	private ArrayList<AdminRegisterUser> userRecordArray;

    public UserArray() {
    	userRecordArray = new ArrayList<AdminRegisterUser>();
    }

    public void addUserRecord(AdminRegisterUser obj) {
    	userRecordArray.add(obj);
    }

    public int getArraySize() {
      return userRecordArray.size();
    }

    public ArrayList<AdminRegisterUser> getUserRecordArray() {
      return userRecordArray;
    }

    public void setUserRecordArray(
      ArrayList<AdminRegisterUser> userRecordArray) {
      this.userRecordArray = userRecordArray;
    }
}

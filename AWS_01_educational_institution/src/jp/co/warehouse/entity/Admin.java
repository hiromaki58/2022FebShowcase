package jp.co.warehouse.entity;

/*
 * The purpose of this entity is carrying the information which is needed to login as admin.
 * 1, The id of the admin
 * 2, The password which is combined with the id.
 */
public class Admin {
	private String userid;
	private String password;

	public Admin() {
	}

	public Admin(String userid, String password) {
	  this.userid = userid;
	  this.password = password;
	}

	/**
	 * @return userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid �Z�b�g���� userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password �Z�b�g���� password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (�� Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	/* (�� Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	/* (�� Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Admin [userid=" + userid + ", password=" + password + "]";
	}


}

package jp.co.warehouse.entity;

/*
 * This class carries the admin account id information to modify the password.
 */
public class AdminId {
	private String adminid;

	public AdminId(String adminId) {
		this.adminid = adminId;
	}

	/**
	 * @return adminid
	 */
	public String getAdminid() {
		return adminid;
	}

	/**
	 * @param adminid セットする adminid
	 */
	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminid == null) ? 0 : adminid.hashCode());
		return result;
	}

	/* (非 Javadoc)
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
		AdminId other = (AdminId) obj;
		if (adminid == null) {
			if (other.adminid != null)
				return false;
		} else if (!adminid.equals(other.adminid))
			return false;
		return true;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdminId [adminid=" + adminid + "]";
	}
}
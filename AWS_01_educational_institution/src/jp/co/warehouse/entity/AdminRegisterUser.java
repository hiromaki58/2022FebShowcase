package jp.co.warehouse.entity;

/**
 * This entity capuselize the information of the admin account registration.
 * @author hirog
 * Sep 14, 2021
 *
 */
public class AdminRegisterUser {

	private String user_last_name;
	private String user_first_name;
	private String user_mail;
	private String user_pass_by_admin;
	private int registeredUserId;
	private int selfRegisteredUserId;
	/**
	 * @return the user_last_name
	 */
	public String getUser_last_name() {
		return user_last_name;
	}
	/**
	 * @param user_last_name the user_last_name to set
	 */
	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}
	/**
	 * @return the user_first_name
	 */
	public String getUser_first_name() {
		return user_first_name;
	}
	/**
	 * @param user_first_name the user_first_name to set
	 */
	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}
	/**
	 * @return the user_mail
	 */
	public String getUser_mail() {
		return user_mail;
	}
	/**
	 * @param user_mail the user_mail to set
	 */
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	/**
	 * @return the user_pass_by_admin
	 */
	public String getUser_pass_by_admin() {
		return user_pass_by_admin;
	}
	/**
	 * @param user_pass_by_admin the user_pass_by_admin to set
	 */
	public void setUser_pass_by_admin(String user_pass_by_admin) {
		this.user_pass_by_admin = user_pass_by_admin;
	}
	/**
	 * @return the registeredUserId
	 */
	public int getRegisteredUserId() {
		return registeredUserId;
	}
	/**
	 * @param registeredUserId the registeredUserId to set
	 */
	public void setRegisteredUserId(int registeredUserId) {
		this.registeredUserId = registeredUserId;
	}
	/**
	 * @return the selfRegisteredUserId
	 */
	public int getSelfRegisteredUserId() {
		return selfRegisteredUserId;
	}
	/**
	 * @param selfRegisteredUserId the selfRegisteredUserId to set
	 */
	public void setSelfRegisteredUserId(int selfRegisteredUserId) {
		this.selfRegisteredUserId = selfRegisteredUserId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + registeredUserId;
		result = prime * result + selfRegisteredUserId;
		result = prime * result + ((user_first_name == null) ? 0 : user_first_name.hashCode());
		result = prime * result + ((user_last_name == null) ? 0 : user_last_name.hashCode());
		result = prime * result + ((user_mail == null) ? 0 : user_mail.hashCode());
		result = prime * result + ((user_pass_by_admin == null) ? 0 : user_pass_by_admin.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminRegisterUser other = (AdminRegisterUser) obj;
		if (registeredUserId != other.registeredUserId)
			return false;
		if (selfRegisteredUserId != other.selfRegisteredUserId)
			return false;
		if (user_first_name == null) {
			if (other.user_first_name != null)
				return false;
		} else if (!user_first_name.equals(other.user_first_name))
			return false;
		if (user_last_name == null) {
			if (other.user_last_name != null)
				return false;
		} else if (!user_last_name.equals(other.user_last_name))
			return false;
		if (user_mail == null) {
			if (other.user_mail != null)
				return false;
		} else if (!user_mail.equals(other.user_mail))
			return false;
		if (user_pass_by_admin == null) {
			if (other.user_pass_by_admin != null)
				return false;
		} else if (!user_pass_by_admin.equals(other.user_pass_by_admin))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AdminRegisterUser [user_last_name=" + user_last_name + ", user_first_name=" + user_first_name
				+ ", user_mail=" + user_mail + ", user_pass_by_admin=" + user_pass_by_admin + ", registeredUserId="
				+ registeredUserId + ", selfRegisteredUserId=" + selfRegisteredUserId + "]";
	}
}

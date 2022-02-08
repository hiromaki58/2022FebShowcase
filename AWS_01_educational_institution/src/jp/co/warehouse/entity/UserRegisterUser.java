package jp.co.warehouse.entity;

import java.awt.image.BufferedImage;

/**
 * This entity is for the information of which the user themself register.
 * @author hirog
 * Sep 14, 2021
 *
 */
public class UserRegisterUser {

	private String email;
	private String gender_profile;
	private String phone;
	private String web_site;
	private String profile;
	private BufferedImage img_profile;

	private String released;
	private int id;
	private String openMail;
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the gender_profile
	 */
	public String getGender_profile() {
		return gender_profile;
	}
	/**
	 * @param gender_profile the gender_profile to set
	 */
	public void setGender_profile(String gender_profile) {
		this.gender_profile = gender_profile;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the web_site
	 */
	public String getWeb_site() {
		return web_site;
	}
	/**
	 * @param web_site the web_site to set
	 */
	public void setWeb_site(String web_site) {
		this.web_site = web_site;
	}
	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}
	/**
	 * @return the img_profile
	 */
	public BufferedImage getImg_profile() {
		return img_profile;
	}
	/**
	 * @param img_profile the img_profile to set
	 */
	public void setImg_profile(BufferedImage img_profile) {
		this.img_profile = img_profile;
	}
	/**
	 * @return the released
	 */
	public String getReleased() {
		return released;
	}
	/**
	 * @param released the released to set
	 */
	public void setReleased(String released) {
		this.released = released;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the openMail
	 */
	public String getOpenMail() {
		return openMail;
	}
	/**
	 * @param openMail the openMail to set
	 */
	public void setOpenMail(String openMail) {
		this.openMail = openMail;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender_profile == null) ? 0 : gender_profile.hashCode());
		result = prime * result + id;
		result = prime * result + ((img_profile == null) ? 0 : img_profile.hashCode());
		result = prime * result + ((openMail == null) ? 0 : openMail.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((released == null) ? 0 : released.hashCode());
		result = prime * result + ((web_site == null) ? 0 : web_site.hashCode());
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
		UserRegisterUser other = (UserRegisterUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender_profile == null) {
			if (other.gender_profile != null)
				return false;
		} else if (!gender_profile.equals(other.gender_profile))
			return false;
		if (id != other.id)
			return false;
		if (img_profile == null) {
			if (other.img_profile != null)
				return false;
		} else if (!img_profile.equals(other.img_profile))
			return false;
		if (openMail == null) {
			if (other.openMail != null)
				return false;
		} else if (!openMail.equals(other.openMail))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (released == null) {
			if (other.released != null)
				return false;
		} else if (!released.equals(other.released))
			return false;
		if (web_site == null) {
			if (other.web_site != null)
				return false;
		} else if (!web_site.equals(other.web_site))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserRegisterUser [email=" + email + ", gender_profile=" + gender_profile + ", phone=" + phone
				+ ", web_site=" + web_site + ", profile=" + profile + ", img_profile=" + img_profile + ", released="
				+ released + ", id=" + id + ", openMail=" + openMail + "]";
	}
}
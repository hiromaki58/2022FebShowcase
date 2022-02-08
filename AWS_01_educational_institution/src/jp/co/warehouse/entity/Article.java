package jp.co.warehouse.entity;

import java.util.Date;
import java.util.List;

/**
 * This class will capsulize the article information, 
 * and img_id works as foreign key to access to article image table. 
 * @author hirog
 * Sep 18, 2021
 *
 */
public class Article{
	
	private String article_name;
	private Date registration_day;
	private int weight;
	private String category;

	private Date opening_day;
	private int opening_hour;
	private int opening_min;
	private Date closing_day;
	private int closing_hour;
	private int closing_min;
	
	private List<String> userList;	
	private StringBuilder user;
	private String stringUser;
	private int articleId;			//id
	private String released;

	private String company_place;
	private String company_addr;

	private String ceo;				//article_host
	private String capital;			//article_cost;
	private int employeeNumber;		//article_number;
	private Date establishedDate;	//end_of_applying;
	
	private String company_mail;	//article_mail;
	private String company_phone;	//article_phone;
	private String company_url;		//article_url;
	
	private int user_id;
	private String introduction;	//article_introduction;
	private int img_id;
	
	/**
	 * @return the article_name
	 */
	public String getArticle_name() {
		return article_name;
	}
	/**
	 * @param article_name the article_name to set
	 */
	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}
	/**
	 * @return the registration_day
	 */
	public Date getRegistration_day() {
		return registration_day;
	}
	/**
	 * @param registration_day the registration_day to set
	 */
	public void setRegistration_day(Date registration_day) {
		this.registration_day = registration_day;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the opening_day
	 */
	public Date getOpening_day() {
		return opening_day;
	}
	/**
	 * @param opening_day the opening_day to set
	 */
	public void setOpening_day(Date opening_day) {
		this.opening_day = opening_day;
	}
	/**
	 * @return the opening_hour
	 */
	public int getOpening_hour() {
		return opening_hour;
	}
	/**
	 * @param opening_hour the opening_hour to set
	 */
	public void setOpening_hour(int opening_hour) {
		this.opening_hour = opening_hour;
	}
	/**
	 * @return the opening_min
	 */
	public int getOpening_min() {
		return opening_min;
	}
	/**
	 * @param opening_min the opening_min to set
	 */
	public void setOpening_min(int opening_min) {
		this.opening_min = opening_min;
	}
	/**
	 * @return the closing_day
	 */
	public Date getClosing_day() {
		return closing_day;
	}
	/**
	 * @param closing_day the closing_day to set
	 */
	public void setClosing_day(Date closing_day) {
		this.closing_day = closing_day;
	}
	/**
	 * @return the closing_hour
	 */
	public int getClosing_hour() {
		return closing_hour;
	}
	/**
	 * @param closing_hour the closing_hour to set
	 */
	public void setClosing_hour(int closing_hour) {
		this.closing_hour = closing_hour;
	}
	/**
	 * @return the closing_min
	 */
	public int getClosing_min() {
		return closing_min;
	}
	/**
	 * @param closing_min the closing_min to set
	 */
	public void setClosing_min(int closing_min) {
		this.closing_min = closing_min;
	}
	/**
	 * @return the userList
	 */
	public List<String> getUserList() {
		return userList;
	}
	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
	/**
	 * @return the user
	 */
	public StringBuilder getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(StringBuilder user) {
		this.user = user;
	}
	/**
	 * @return the stringUser
	 */
	public String getStringUser() {
		return stringUser;
	}
	/**
	 * @param stringUser the stringUser to set
	 */
	public void setStringUser(String stringUser) {
		this.stringUser = stringUser;
	}
	/**
	 * @return the articleId
	 */
	public int getArticleId() {
		return articleId;
	}
	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
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
	 * @return the company_place
	 */
	public String getCompany_place() {
		return company_place;
	}
	/**
	 * @param company_place the company_place to set
	 */
	public void setCompany_place(String company_place) {
		this.company_place = company_place;
	}
	/**
	 * @return the company_addr
	 */
	public String getCompany_addr() {
		return company_addr;
	}
	/**
	 * @param company_addr the company_addr to set
	 */
	public void setCompany_addr(String company_addr) {
		this.company_addr = company_addr;
	}
	/**
	 * @return the ceo
	 */
	public String getCeo() {
		return ceo;
	}
	/**
	 * @param ceo the ceo to set
	 */
	public void setCeo(String ceo) {
		this.ceo = ceo;
	}
	/**
	 * @return the capital
	 */
	public String getCapital() {
		return capital;
	}
	/**
	 * @param capital the capital to set
	 */
	public void setCapital(String capital) {
		this.capital = capital;
	}
	/**
	 * @return the employeeNumber
	 */
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	/**
	 * @param employeeNumber the employeeNumber to set
	 */
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	/**
	 * @return the establishedDate
	 */
	public Date getEstablishedDate() {
		return establishedDate;
	}
	/**
	 * @param establishedDate the establishedDate to set
	 */
	public void setEstablishedDate(Date establishedDate) {
		this.establishedDate = establishedDate;
	}
	/**
	 * @return the company_mail
	 */
	public String getCompany_mail() {
		return company_mail;
	}
	/**
	 * @param company_mail the company_mail to set
	 */
	public void setCompany_mail(String company_mail) {
		this.company_mail = company_mail;
	}
	/**
	 * @return the company_phone
	 */
	public String getCompany_phone() {
		return company_phone;
	}
	/**
	 * @param company_phone the company_phone to set
	 */
	public void setCompany_phone(String company_phone) {
		this.company_phone = company_phone;
	}
	/**
	 * @return the company_url
	 */
	public String getCompany_url() {
		return company_url;
	}
	/**
	 * @param company_url the company_url to set
	 */
	public void setCompany_url(String company_url) {
		this.company_url = company_url;
	}
	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * @return the img_id
	 */
	public int getImg_id() {
		return img_id;
	}
	/**
	 * @param img_id the img_id to set
	 */
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	@Override
	public String toString() {
		return "Article [article_name=" + article_name + ", registration_day=" + registration_day + ", weight=" + weight
				+ ", category=" + category + ", opening_day=" + opening_day + ", opening_hour=" + opening_hour
				+ ", opening_min=" + opening_min + ", closing_day=" + closing_day + ", closing_hour=" + closing_hour
				+ ", closing_min=" + closing_min + ", userList=" + userList + ", user=" + user + ", stringUser="
				+ stringUser + ", articleId=" + articleId + ", released=" + released + ", company_place="
				+ company_place + ", company_addr=" + company_addr + ", ceo=" + ceo + ", capital=" + capital
				+ ", employeeNumber=" + employeeNumber + ", establishedDate=" + establishedDate + ", company_mail="
				+ company_mail + ", company_phone=" + company_phone + ", company_url=" + company_url + ", user_id="
				+ user_id + ", introduction=" + introduction + ", img_id=" + img_id + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + articleId;
		result = prime * result + ((company_addr == null) ? 0 : company_addr.hashCode());
		result = prime * result + ((article_name == null) ? 0 : article_name.hashCode());
		result = prime * result + ((company_place == null) ? 0 : company_place.hashCode());
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((ceo == null) ? 0 : ceo.hashCode());
		result = prime * result + ((closing_day == null) ? 0 : closing_day.hashCode());
		result = prime * result + closing_hour;
		result = prime * result + closing_min;
		result = prime * result + ((company_mail == null) ? 0 : company_mail.hashCode());
		result = prime * result + ((company_phone == null) ? 0 : company_phone.hashCode());
		result = prime * result + ((company_url == null) ? 0 : company_url.hashCode());
		result = prime * result + employeeNumber;
		result = prime * result + ((establishedDate == null) ? 0 : establishedDate.hashCode());
		result = prime * result + img_id;
		result = prime * result + ((introduction == null) ? 0 : introduction.hashCode());
		result = prime * result + ((opening_day == null) ? 0 : opening_day.hashCode());
		result = prime * result + opening_hour;
		result = prime * result + opening_min;
		result = prime * result + ((registration_day == null) ? 0 : registration_day.hashCode());
		result = prime * result + ((released == null) ? 0 : released.hashCode());
		result = prime * result + ((stringUser == null) ? 0 : stringUser.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userList == null) ? 0 : userList.hashCode());
		result = prime * result + user_id;
		result = prime * result + weight;
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
		Article other = (Article) obj;
		if (articleId != other.articleId)
			return false;
		if (company_addr == null) {
			if (other.company_addr != null)
				return false;
		} else if (!company_addr.equals(other.company_addr))
			return false;
		if (article_name == null) {
			if (other.article_name != null)
				return false;
		} else if (!article_name.equals(other.article_name))
			return false;
		if (company_place == null) {
			if (other.company_place != null)
				return false;
		} else if (!company_place.equals(other.company_place))
			return false;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (ceo == null) {
			if (other.ceo != null)
				return false;
		} else if (!ceo.equals(other.ceo))
			return false;
		if (closing_day == null) {
			if (other.closing_day != null)
				return false;
		} else if (!closing_day.equals(other.closing_day))
			return false;
		if (closing_hour != other.closing_hour)
			return false;
		if (closing_min != other.closing_min)
			return false;
		if (company_mail == null) {
			if (other.company_mail != null)
				return false;
		} else if (!company_mail.equals(other.company_mail))
			return false;
		if (company_phone == null) {
			if (other.company_phone != null)
				return false;
		} else if (!company_phone.equals(other.company_phone))
			return false;
		if (company_url == null) {
			if (other.company_url != null)
				return false;
		} else if (!company_url.equals(other.company_url))
			return false;
		if (employeeNumber != other.employeeNumber)
			return false;
		if (establishedDate == null) {
			if (other.establishedDate != null)
				return false;
		} else if (!establishedDate.equals(other.establishedDate))
			return false;
		if (img_id != other.img_id)
			return false;
		if (introduction == null) {
			if (other.introduction != null)
				return false;
		} else if (!introduction.equals(other.introduction))
			return false;
		if (opening_day == null) {
			if (other.opening_day != null)
				return false;
		} else if (!opening_day.equals(other.opening_day))
			return false;
		if (opening_hour != other.opening_hour)
			return false;
		if (opening_min != other.opening_min)
			return false;
		if (registration_day == null) {
			if (other.registration_day != null)
				return false;
		} else if (!registration_day.equals(other.registration_day))
			return false;
		if (released == null) {
			if (other.released != null)
				return false;
		} else if (!released.equals(other.released))
			return false;
		if (stringUser == null) {
			if (other.stringUser != null)
				return false;
		} else if (!stringUser.equals(other.stringUser))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userList == null) {
			if (other.userList != null)
				return false;
		} else if (!userList.equals(other.userList))
			return false;
		if (user_id != other.user_id)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
}
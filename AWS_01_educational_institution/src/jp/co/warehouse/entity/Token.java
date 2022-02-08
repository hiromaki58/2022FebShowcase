
package jp.co.warehouse.entity;

public class Token {
	/*
	 * used at
	 *  1,password reissue page
	 *  2,cross site counter measure
	 */
	private String token;

	public Token() {
	}

	public Token(String token) {
	  this.token = token;
	}

	/**
	 * @return token getter
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token setter
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		Token other = (Token) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Token [token=" + token + "]";
	}
}
package jp.co.warehouse.entity;

/*
 * This class capsulizes the information to search the article @ index.jsp
 */
public class SearchWord {

	private String wordSearch;
	/**
	 * @return wordSearch
	 */
	public String getWordSearch() {
		return wordSearch;
	}
	/**
	 * @param wordSearch セットする wordSearch
	 */
	public void setWordSearch(String wordSearch) {
		this.wordSearch = wordSearch;
	}
	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wordSearch == null) ? 0 : wordSearch.hashCode());
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
		SearchWord other = (SearchWord) obj;
		if (wordSearch == null) {
			if (other.wordSearch != null)
				return false;
		} else if (!wordSearch.equals(other.wordSearch))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SearchWord [wordSearch=" + wordSearch + "]";
	}
}
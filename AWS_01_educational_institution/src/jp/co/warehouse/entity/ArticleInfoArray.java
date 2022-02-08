package jp.co.warehouse.entity;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * This class is used for carry the multiple article info at once.
 */
public class ArticleInfoArray implements Serializable {

	private static final long serialVersionUID = -2003288719490876337L;
	private ArrayList<Article> articleInfoArray;

	  public ArticleInfoArray() {
		  articleInfoArray = new ArrayList<Article>();
	  }

	  public void addArticleInfo(Article obj) {
		  articleInfoArray.add(obj);
	  }

	  public int getArraySize() {
	    return articleInfoArray.size();
	  }

	  public ArrayList<Article> getArticleInfoArray() {
	    return articleInfoArray;
	  }

	  public ArrayList<Article> addArticleInfoToArray(ArticleInfoArray array01) {
		  for(int i = 0; i < array01.getArraySize(); i++) {
			  articleInfoArray.add(array01.getArticleInfoArray().get(i));
		  }
	    return articleInfoArray;
	  }

	  public void setArticleInfoArray(ArrayList<Article> articleInfoArray) {
	    this.articleInfoArray = articleInfoArray;
	  }
}
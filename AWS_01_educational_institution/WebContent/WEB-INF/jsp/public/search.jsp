<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.ArticleInfoArray"%>
<%@ page import="jp.co.warehouse.date.SeparateFromDate"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%@ page import="jp.co.warehouse.entity.SearchWord"%>
<%
	ArticleInfoArray articleSearchResult = new ArticleInfoArray();
	if((ArticleInfoArray) session.getAttribute("articleSearchResult") != null){
		articleSearchResult = (ArticleInfoArray) session.getAttribute("articleSearchResult");
	}

	SeparateFromDate separation = new SeparateFromDate();
	SearchWord  searchWord = null;
	if((SearchWord) session.getAttribute("searchWord") != null){
		searchWord = (SearchWord) session.getAttribute("searchWord");
	}

	CompareDate compareDate = new CompareDate();
	String establishedYear = "";
    String establishedMonth = "";
    String establishedDay = "";
	request.setCharacterEncoding("utf-8");
%>
<jsp:include page="/WEB-INF/jsp/header/public_header.jsp">
  <jsp:param name="title" value="Article search" />
  <jsp:param name="type" value="website" />
  <jsp:param name="url" value="/index" />
  <jsp:param name="img" value="" />
</jsp:include>

	<article class="contents">
      <div class="area-search-condition">
      	<section>
          <h2 class="ttl-search-condition only-pc">Search</h2>
          <h2 class="ttl-search-condition only-sp">Find the article</h2>
          <form class="box-search-index-wrap" action="./search" method="get">
          	<input type="text" name="articleSearch"
              <c:if test = "${searchWord.getWordSearch() != null}">
              	value="<%= searchWord.getWordSearch() %>"
              </c:if>
          	  class="form-index-keyword" pattern="[^\x22\x27]*">
          
          	<input type="text" name="word" value="articleSearchIn" class="hidden">
          	<input class="btn-search-index" type="submit" value="search">
          </form><!-- /form -->
        </section>
      </div><!-- /area-search-condition -->

      <div class="area-search-number">
      	<section>
          <h2 class="ttl-search-result">Result : <%= articleSearchResult.getArraySize() %></h2>
        </section>
	  </div><!-- /area-search-number -->

      <div class="area-search-result">
      	<div class="sec-search-result">
          <section>
          	<h2 class="hidden">title</h2>
          	<div class="sec-recommendation-in">
              <ul class="box-recommendation-in"><!-- The list for the sliding 6 articles -->
              <%
               for (Article articleInfo : articleSearchResult.getArticleInfoArray()) {
              %>
				<li class="mod-recommendation-wrap">
			  <%
				if(articleInfo.getCategory().equals("public")){
			  %>
				  <div class="bnr-limited">Public</div>
			  <%
				}
			  %>
                  <a href="./article?articleId=<%=articleInfo.getArticleId()%>">
					<div class="img-recommendation" style="background-image: url(./showEyecatch?imgId=<%=articleInfo.getImg_id()%>)" ></div>
                  </a>
                  <div class="date-recommendation">
			  <%
					establishedYear = separation.SeparateYear(articleInfo.getEstablishedDate());
				    establishedMonth = separation.SeparateMonth(articleInfo.getEstablishedDate());
				    establishedDay = separation.SeparateDay(articleInfo.getEstablishedDate());
			  %>
				  Est.&nbsp;&nbsp;
				  <%= establishedYear %>/
	              <%= establishedMonth %>/
	              <%= establishedDay %>
		  		  </div><!-- /date-recommendation -->
                  <section>
					<a href="./article?articleId=<%=articleInfo.getArticleId()%>">
					  <h4 class="ttl-recommendation-individuality"><%=articleInfo.getArticle_name()%></h4>
                    </a>
                    <div class="txt-recommendation-instructor"><%=articleInfo.getCeo()%></div>
                    <div class="txt-recommendation-address"><%=articleInfo.getCompany_addr()%></div>
                    <div class="btn-recommendation-wrap">
					  <a href="./article?articleId=<%=articleInfo.getArticleId()%>" class="btn-recommendation-02">
						<span class="btn-recommendation-detail">Detail</span>
                      </a>
                    </div><!-- /btn-recommendation-wrap -->
				  </section>
				</li><!-- /mod-recommendation-wrap -->
              <%
               }
              %>
            </ul><!-- /box-recommendation-in -->
          </div><!-- /sec-recommendation-in -->
        </section>
      </div><!-- /sec-search-result -->
    </div><!-- /area-search-result -->

  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/public_footer.jsp"></jsp:include>
</body></html>
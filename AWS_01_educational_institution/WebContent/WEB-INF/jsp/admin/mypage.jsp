<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.ArticleInfoArray"%>
<%@ page import="jp.co.warehouse.entity.SearchWord"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%
  ArticleInfoArray articleInfoArray = new ArticleInfoArray();
  articleInfoArray = (ArticleInfoArray) session.getAttribute("articleInfoArray");

  ArrayList<String> userId = new ArrayList<String>();
  userId = (ArrayList<String>) session.getAttribute("userId");

  SearchWord searchWord = new SearchWord();
  if((SearchWord) session.getAttribute("searchWord") != null){
	  searchWord = (SearchWord) session.getAttribute("searchWord");
  }

  Integer currentPage = 1;
  if((Integer) request.getAttribute("currentPage") != null){
	  currentPage = (Integer) request.getAttribute("currentPage");
	}

  CompareDate compareDate = new CompareDate();
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/admin_header.jsp"><jsp:param name="title" value="Administrator page" /></jsp:include>

  <article class="contents clearfix">
    <div class="contents-ttl-mypage">
      <section>
        <h2 class="sec-ttl-in">Administrator page</h2>
        <a class="link-identity" href="../admin/mypage">
          <div class="box-identity-wrap">
            <div class="box-identity-sentence">
              <div class="txt-user-name">Warehouse Association</div>
              <div class="txt-release">Admin</div>
		    </div><!-- /box-identity-sentence -->
		  </div><!-- /box-identity-wrap -->
        </a>
        <!-- </div> --><!-- /box-identity-wrap -->
        <p>Welcome to administrator page!!</p>
        <p>You can register the new article and user.</p>
	  </section>
	</div><!-- /contents-ttl-mypage -->

    <div class="contents-main">
      <section>
        <h3 class="ttl-search-mypage hidden">SEARCH</h3>
        <form  class="sec-search" action="../admin/mypage" method="get">
          <div class="box-search-keyword">
            <span class="ttl-search-keyword">Full text search</span>
            <input type="text" name="search"
              <c:if test = "${searchWord.getWordSearch() != null}">
                value=<%= searchWord.getWordSearch() %>
              </c:if>
            class="form-input-keyword">
          </div><!-- /box-search-keyword -->
          <input type="text" name="word" value="in" class="hidden">
          <input class="btn-article-search" type="submit" value="Search">
        </form><!-- /sec-search -->

        <ul class="list-search-result">
           <li class="ttl-mypage">
             <span class="ttl-mypage-number">#</span>
             <span class="ttl-mypage-article">article title</span>
             <span class="ttl-mypage-user">user</span>
             <span class="ttl-mypage-date">Date</span>
             <span class="ttl-mypage-edit"></span></li>
           <%
            int noOfOffset = 10 * (currentPage - 1);
            int i = 1 + noOfOffset;
        		int j = 0;
            for (Article articleInfo : articleInfoArray.getArticleInfoArray()) {
	         %>
	         <li class="list-mypage">
	           <% if(i < 10) { %><span class="list-mypage-number-01">
	           <% } else if(i > 9) { %><span class="list-mypage-number-02"><% } %>
	           <%= (i++) %></span>
	           <%
             /*
             *This part is used to indicate the color of the status.
             *Status: 1 Released, 2 Not released and 3 acknowledgment is negative
             */
             boolean before = compareDate.checkBefore(articleInfo.getOpening_day());
             boolean after = compareDate.checkAfter(articleInfo.getClosing_day());

             if(!(before)){
             %>
                <span class="list-mypage-indicator bgc-mypage-indicator-02" release="notYet"></span>
             <%
             }
             else if(!(after)){
             %>
             	<span class="list-mypage-indicator bgc-mypage-indicator-03" release="done"></span>
             <%
             }
             else{
             %>
                <span class="list-mypage-indicator bgc-mypage-indicator-01" release="released"></span>
             <%
               }
             %>
	           <span class="list-mypage-article"><strong><a href="../article/article_info?articleId=<%=articleInfo.getArticleId()%>"><%=articleInfo.getArticle_name()%></a></strong></span>
	           <span class="list-mypage-user">
	             <%
	               if(articleInfo.getStringUser().equals("administrator")){
	             %>
	                 <%=articleInfo.getStringUser()%>
	             <%
	               }
	               else{
	             %>
	                 <strong><a href="../admin/user_info?userId=<%=userId.get(j++)%>"><%=articleInfo.getStringUser()%></a></strong>
	             <%
	               }
	             %>
	           </span>
	           <span class="list-mypage-edit">
	           <span class="list-mypage-selection">
	           		<a href="../article/modification_01?articleId=<%=articleInfo.getArticleId()%>" class="link-mypage-selection">Edit</a>
	           </span>
	   		</span>
	      </li>
         <%
          }
         %>
        </ul><!-- /list-search-result -->
      </section><!-- /sec-search -->

       <div class="btn-pagination-search-wrap">
        <section>
          <h3 class="hidden">Pagination</h3>
          <c:choose>
            <c:when test="${noOfPages == 1}">
              <div class="btn-pagination-search-only-one">
            </c:when>
            <c:when test="${noOfPages != 1}">
              <div class="btn-pagination-search">
            </c:when>
          </c:choose>

            <c:if test="${currentPage != 1}">
              <a href="../admin/mypage?pageNumber=${currentPage - 1}">&laquo;</a>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
              <c:choose>
                <c:when test="${currentPage eq i}">
                  <a href="#">${i}</a>
                </c:when>
                <c:otherwise>
                  <a href="../admin/mypage?pageNumber=${i}">${i}</a>
                </c:otherwise>
              </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
              <a href="../admin/mypage?pageNumber=${currentPage + 1}">&raquo;</a>
            </c:if>
          </div><!-- /btn-pagination-search -->
        </section>
      </div><!-- /btn-pagination-search-wrap -->
    </div><!-- /contents-main -->

    <jsp:include page="/WEB-INF/jsp/side/admin_side_menu.jsp"></jsp:include>
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
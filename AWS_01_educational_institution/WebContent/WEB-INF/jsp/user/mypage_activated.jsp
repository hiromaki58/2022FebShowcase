<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.User"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.ArticleInfoArray"%>
<%@ page import="jp.co.warehouse.entity.SearchWord"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%@ page import="jp.co.warehouse.dao.admin.AdminGetUserInfoDAO"%>
<%@ page import="jp.co.warehouse.dao.user.UserGetUserInfoDAO"%>
<%
	Admin adminLogin = (Admin) session.getAttribute("admin");
	User user = (User) session.getAttribute("user");

	AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
	
	UserGetUserInfoDAO getUserInfoDao = new UserGetUserInfoDAO();
	AdminRegisterUser userInfo = new AdminRegisterUser();
	if((User) session.getAttribute("user") != null){
		userInfo = adminGetUserInfoDao.getRegisteredUserInfo(user.getEmail());
	}

	UserRegisterUser practitionerInfoBean = getUserInfoDao.getSelfRegisteredUserInfo(user.getEmail());

	ArticleInfoArray articleInfoArray = new ArticleInfoArray();
	articleInfoArray = (ArticleInfoArray) session.getAttribute("articleInfoArray");

	SearchWord searchWord = new SearchWord();
	if((SearchWord) session.getAttribute("searchWord") != null){
  		searchWord = (SearchWord) session.getAttribute("searchWord");
	}

	StringBuilder sbi = new StringBuilder();
	String userName ="";
	sbi.append(userInfo.getUser_last_name());
	sbi.append(" ");
	sbi.append(userInfo.getUser_first_name());
	userName = sbi.toString();

	CompareDate compareDate = new CompareDate();
	request.setCharacterEncoding("utf-8");
%>
<jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="User page" /></jsp:include>

	<article class="contents-mypage clearfix">
      <div class="contents-ttl-mypage">
      	<section class="sec-ttl">
          <h2 class="h1-mypage-activated">User page</h2>
       	  <section>
         	<h3 class="hidden">SEARCH</h3>
         	<a href="/user/user_release">
        	  <div class="box-identity-wrap">
          	  	<div class="box-identity-sentence">
              	  <div class="txt-user-name"><%= userInfo.getUser_last_name() %><%= userInfo.getUser_first_name() %></div>
              <%
                  if(practitionerInfoBean.getReleased().equals("yes")){
              %>
              	  <div class="txt-release">Released</div>
              <%
               	  }
                  else {
              %>
              	  <div class="txt-non-release">Private</div>
              <%
                  }
              %>
              	</div><!-- /box-identity-sentence -->
              	<div class="img-user" style="background-image: url(../getProfileImageById?practitionerId=<%= userInfo.getSelfRegisteredUserId() %>)"></div>
           	  </div><!-- /box-identity-wrap -->
		  	</a>
		  </section>

       	  <p class="txt-mypage-top">Welcome to user page <%= userInfo.getUser_last_name() %></p>
       	  <p class="txt-mypage-top">You can register the new article and edit it.</p>
      	</section><!-- /sec-ttl -->
      </div><!-- /contents-ttl -->

   	  <div class="contents-mypage-main">
      	<section class="sec-search">
          <h3 class="hidden">SEARCH</h3>
          <form  class="sec-search" action="../user/mypage" method="get">
          	<div class="box-search-keyword">
              <span class="ttl-search-keyword">Word search</span>
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
              <span class="ttl-mypage-article">Article title</span>
              <span class="ttl-mypage-date">Date</span>
              <span class="ttl-mypage-edit"></span>
            </li>

            <%
             int i = 1;
		     for (Article articleInfo : articleInfoArray.getArticleInfoArray()) {
		    %>
		  	<li class="list-mypage">
 		    <% if(i < 10) { %>
		      <span class="list-mypage-number">
            <% } else if(i > 9) { %>
	          <span class="list-mypage-number-02">
            <% } %>
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
	          <span class="list-mypage-article"><a href="../article/article_info?articleId=<%=articleInfo.getArticleId()%>"><%=articleInfo.getArticle_name()%></a></span>
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
	  </div><!-- /contents-mypage-main -->

    <%
      if(adminLogin != null){
    %>
      <jsp:include page="/WEB-INF/jsp/side/admin_mypage_side_menu.jsp"></jsp:include>
    <%
      }
      else {
    %>
      <jsp:include page="/WEB-INF/jsp/side/mypage_side_menu.jsp"></jsp:include>
    <%
      }
    %>
	</article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserArray"%>
<%@ page import="jp.co.warehouse.entity.SearchWord"%>
<%
	UserArray userInfoArray = new UserArray();
  	userInfoArray = (UserArray) session.getAttribute("userInfoArray");

  	SearchWord  searchWord = new SearchWord();
  	if((SearchWord) request.getAttribute("searchWord") != null){
    	searchWord = (SearchWord) request.getAttribute("searchWord");
  	}

  	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/jsp/header/admin_header.jsp"><jsp:param name="title" value="user search" /></jsp:include>

  <article class="contents clearfix">
    <div class="contents-ttl-mypage">
      <section>
        <h2 class="sec-ttl-in">Administrator page</h2>
        <div class="box-identity-wrap">
          <div class="box-identity-sentence">
            <div class="txt-user-name">Warehouse Association</div>
            <div class="txt-release">Admin</div></div><!-- /box-identity-sentence --></div><!-- /box-identity-wrap -->

        <p>Welcome to administrator page！</p>
        <p>You can register the new article and user.</p></section><!-- /sec-ttl --></div><!-- /contents-ttl -->

    <div class="contents-main">
      <section>
        <h3 class="ttl-search-mypage hidden">SEARCH</h3>
        <form  class="sec-search" action="../admin/user_search" method="get">
          <div class="box-search-keyword">
            <span class="ttl-search-keyword">Full text search</span>
            <input type="text" name="search"
              <c:if test = "${searchWord.getWordSearch() != null}">
                value="<%= searchWord.getWordSearch() %>"
              </c:if>
             class="form-input-keyword"></div><!-- /box-search-keyword -->
            <input type="text" name="word" value="in" class="hidden">
            <input class="btn-article-search" type="submit" value="検索"></form><!-- /sec-search -->

        <ul class="list-search-result">
          <li class="ttl-mypage">
              <span class="ttl-search-number">#</span>
              <span class="ttl-search-user">User</span>
              <span class="ttl-search-mail">E-mail</span></li>
           <%
           	int i = 1;
            int j = 0;
            for (AdminRegisterUser userInfo : userInfoArray.getUserRecordArray()) {
           %>
           <li class="list-mypage">
             <span class="list-search-number"><%= (i++) %></span>
             <span class="list-search-user"><strong><a href="../user/user_info?userId=<%=userInfo.getRegisteredUserId()%>"><%=userInfo.getUser_last_name()%><%=userInfo.getUser_first_name()%></a></strong></span>
             <span class="list-search-mail"><%=userInfo.getUser_mail()%></span>
             <span class="list-search-certification"></span></li>
           <%
            }
           %>
        </ul><!-- /list-search-result -->
      </section>

      <div class="btn-pagination-search-wrap">
        <section>
          <h3 class="hidden">Pagination</h3>
          <div class="btn-pagination-search">
            <a href="#">&laquo;</a>
            <a href="#">1</a>
            <a href="#">2</a>
            <a href="#">3</a>
            <a href="#">&raquo;</a>
          </div><!-- /btn-pagination-search -->
        </section>
      </div><!-- /btn-pagination-search-wrap -->
    </div><!-- /contents-main -->

  　　 <jsp:include page="/WEB-INF/jsp/side/admin_side_menu.jsp"></jsp:include>
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
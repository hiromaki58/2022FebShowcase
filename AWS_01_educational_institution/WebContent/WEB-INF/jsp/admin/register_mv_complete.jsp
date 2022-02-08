<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%
  request.setCharacterEncoding("utf-8");
%>
 	<jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Changing main visual - Completed" /></jsp:include>

  	<article class="contents">
      <h2 class="hidden">contents</h2>
   	  <div class="contents-complete sec-ttl">
     	<section>
          <h2 class="sec-ttl-in">Changing key image - Completed</h2>
        	<p class="sec-ttl-detail">Set the main image up</p>
      	</section>
   	  </div><!-- /contents-ttl sec-ttl-->

      <div class="btn-registration-user">
      	<a href="/admin/mypage">
          <span class="btn-registration-user-01">Back to administrator page</span>
        </a>
      </div><!-- /btn-registration-user -->
  	</article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
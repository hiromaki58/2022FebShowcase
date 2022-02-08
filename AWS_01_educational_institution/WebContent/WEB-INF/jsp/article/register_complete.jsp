<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%
	Admin adminLogin = (Admin) session.getAttribute("admin");
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Article registration - Completed" /></jsp:include>
	  <article class="contents">
	    <h2 class="hidden">contents</h2>
	    <div class="contents-complete sec-ttl">
	      <section>
	        <h2 class="sec-ttl-in">Article registration - Completed</h2>
	        <p class="sec-ttl-detail">The article is registered.</p>
	      </section>
	    </div><!-- /contents-ttl sec-ttl-->

	    <div class="btn-registration-user">
	      <%
	         if(adminLogin != null){
	      %>
	      	   <a href="../admin/mypage">
	      <%
	         }
	         else {
	      %>
	      	   <a href="../user/mypage">
	      <%
	         }
	      %>
         	   	 <span class="btn-registration-user-01">Back to my page</span>
	           </a>
         </div><!-- /btn-registration-user -->
	  </article><!-- /contents -->
	</div><!-- /wrapper -->
  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
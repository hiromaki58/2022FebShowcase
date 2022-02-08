<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
<!-- This page is for admin account holder tries to login. -->
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Administrator login" /></jsp:include>

	  <article class="contents">
	    <div class="area-login">
	      <section class="sec-login">
	        <h2 class="sec-login-in">Administrator login</h2>

	        <form class="form-login" action="../admin/login" method="post">
	          <div class="form-login-in">
	            <div class="form-login-ttl">Mail address</div>
	            <input class="form-login-input" type="text" name="userid" placeholder="Mail address"><br></div><!-- form-login-in -->

	          <div class="form-login-in">
	            <div class="form-login-ttl">Password</div>
	            <input class="form-login-input" type="password" name="password" placeholder="Password"><br></div><!-- form-login-in -->

	          <input class="form-login-button" type="submit" value="Login">
		      </form><!-- /form-login -->

		      <a href="/login/admin_reset">Forgot password？</a>
		    </section><!-- /sec-login --></div><!-- /area-login --></article><!-- /contents -->
	   </div><!-- /wrapper -->

     <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
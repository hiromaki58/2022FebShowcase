<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="User login" /></jsp:include>

	  <article class="contents">
	    <div class="area-login">
	      <section class="sec-login">
	        <h2 class="sec-login-in">User login</h2><!-- /sec-login-in -->

	        <form class="form-login" action="../user/login" method="post">
	          <div class="form-login-in">
	            <div class="form-login-ttl">E-mail address</div>
	            <input class="form-login-input" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="userid" placeholder="E-mail address"><br></div><!-- /form-login -->

	          <div class="form-login-in">
	            <div class="form-login-ttl">Password</div>
	            <input class="form-login-input" type="password" name="password" placeholder="Password"><br></div><!-- /area-login-in -->

	          <input class="form-login-button" type="submit" value="Login">
	      </form>
	      <a href="/login/dismissed_reset">Forget password?</a>
	      </section><!-- /sec-login --></div><!-- /area-login --></article><!-- /contents -->

	</div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
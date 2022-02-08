<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.Mail"%>
<%@ page import="java.util.List"%>
<%
  Mail mailAddress = (Mail) session.getAttribute("mailAddress");
  request.setCharacterEncoding("utf-8");
%>
 	<jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Sign up" /></jsp:include>
    <article class="contents">
      <div class="area-login">
        <section class="sec-login">
          <h2 class="sec-login-in">Sign up</h2>

          <div class="form-reset-in">
            <div class="form-reset-ttl">E-mail address</div>
            <div class="form-reset-mail"><%= mailAddress.getMail() %></div>
          </div><!-- /form-reset-in -->

          <form class="form-login" action="../login/issue" method="post">
            <div class="form-login-in">
              <div class="form-login-ttl">Password</div>
              <input class="form-login-input" type="password" pattern="[^\x22\x27]*" name="password_new" placeholder="Password"><br>
            </div><!-- /form-login-in -->

            <div class="form-login-in">
              <div class="form-login-ttl">Password（Confirm）</div>
              <input class="form-login-input" type="password" pattern="[^\x22\x27]*" name="password_confirm" placeholder="Password（Confirm）"><br>
            </div><!-- /form-login-in -->

            <input class="form-login-button" type="submit" value="next">
          </form><!-- /form-reset-in -->
        </section><!-- /sec-login -->
      </div><!-- /area-login -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
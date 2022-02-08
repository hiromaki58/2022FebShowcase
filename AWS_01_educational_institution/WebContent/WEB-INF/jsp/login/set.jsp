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
      <div class="area-set-password">
        <section class="sec-login">
          <h2 class="sec-login-in">Sign up</h2>

          <form class="form-login" action="../login/set" method="post">
            <div class="form-login-in">
              <div class="form-login-ttl">Please input your E-mail address</div>
              <input class="form-login-input" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="mail" placeholder="mail address"><br></div><!-- /form-login-in -->

            <input class="form-login-button" type="submit" value="next"></form><!-- /form-login --></section><!-- /sec-login -->
      </div><!-- /area-login -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

　　<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
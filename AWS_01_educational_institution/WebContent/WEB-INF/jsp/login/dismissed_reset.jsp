<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Enter e-mail" /></jsp:include>

    <article class="contents">
        <div class="area-login">
          <section>
            <h2 class="sec-login-in">Password reset</h2>
            <form class="form-login" action="../login/dismissed_reset" method="post">
              <div class="form-login-in">
                <div class="form-login-ttl">Please enter your e-mail address.</div>
                <input class="form-login-input" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="dismissed_mail" placeholder="E-mail"><br></div><!-- /form-login-in -->

              <input class="form-login-button" type="submit" value="next"></form><!-- /form-login --></section><!-- /sec-login -->
        </div><!-- /area-login -->
      </article><!-- /contents -->
    </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
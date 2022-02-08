<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Set E-mail Address" /></jsp:include>

    <article class="contents">
      <div class="area-ttl-reissue">
        <section>
          <h2 class="ttl-reissue">Set E-mail Address</h2>
        </section>
      </div><!-- /area-ttl-reissue -->
      <div class="area-reissue">
        <section>
          <h3 class="sec-hidden-reissue">Password Reset</h3>
          <form class="form-login" action="../login/dismissed_reissue" method="post">
            <div class="form-login-in">
              <div class="form-login-ttl">Password</div>
              <input class="form-login-input" type="password" name="dismissed_password_new" placeholder="Password"><br>
            </div><!-- /form-login-in -->

            <div class="form-login-in">
              <div class="form-login-ttl">Password confirmation</div>
              <input class="form-login-input" type="password" name="dismissed_password_confirm" placeholder="Password confirmation"><br>
            </div><!-- /form-login-in -->

            <input class="form-login-button" type="submit" value="submit">
          </form><!-- /form-login -->
        </section>
      </div><!-- /area-reissue -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
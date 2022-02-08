<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  session.invalidate();
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="E-mail address ‐ completed" /></jsp:include>

    <article class="contents">
      <div class="area-reissue">
        <section>
          <h2 class="sec-login-in">Success - E-mail change</h2>

          <div class="form-reset-in">
            <p class="form-reset-ttl">New e-mail address successfully set</p>
            <p class="form-reset-ttl">Please login at login page.</p></div><!-- form-reset-in-->

          <div class="btn-registration-password">
            <a href="../user/login">
              <span class="btn-registration-password-01">Login page</span></a></div><!-- btn-registration-password--></section>

      </div><!-- /area-reissue -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
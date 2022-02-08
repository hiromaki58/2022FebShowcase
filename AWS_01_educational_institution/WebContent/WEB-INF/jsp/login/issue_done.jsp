<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Password - completed" /></jsp:include>

    <article class="contents">
      <div class="area-reissue-02">
        <section>
          <h2 class="sec-login-in">Success - password change</h2>
          <div class="form-reset-in">
            <p class="form-reset-ttl">New password successfully set</p>
            <p class="form-reset-ttl">Please login at login page.</p>
          </div><!-- form-reset-in-->

          <div class="box-registration-password-02">
            <a href="../user/login">
              <span class="btn-registration-password-01">Login page</span>
            </a>
          </div><!-- btn-registration-password-->
        </section>
      </div><!-- /area-reissue -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
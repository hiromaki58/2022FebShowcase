<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Sign up - Error" /></jsp:include>

    <article class="contents">
      <div class="area-login">
        <section>
          <h2 class="sec-login-in">Sign up - Error</h2>
          <p class="sec-logout-detail">Error: Please try again later</p>

          <div class="box-registration-password-01">
            <a href="../user/login">
              <span class="btn-registration-password-01">Back to mypage</span></a></div><!-- btn-registration-password-01 --></section>

      </div><!-- /area-login -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
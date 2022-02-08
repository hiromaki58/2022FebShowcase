<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Password issue - Error" /></jsp:include>

    <article class="contents">
      <div class="area-login">
        <section>
          <h2 class="sec-login-in">Passwords do not match</h2>
          <p class="sec-logout-detail">Error: Please try again</p>

          <div class="box-registration-password-01">
            <a href="#" onclick="javascript:window.history.back(-1);return false;">
              <span class="btn-registration-password-01">Back</span></a></div><!-- btn-registration-password --></section>

      </div><!-- /area-reissue -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  request.setCharacterEncoding("utf-8");
%>
    <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="ご入力いただいた管理者アカウントが存在しません" /></jsp:include>

    <article class="contents">
      <div class="area-login">
        <section>
          <h2 class="sec-login-in">ご入力いただいた管理者アカウントが存在しません</h2>
          <p class="sec-logout-detail">Error: アカウントを今一度ご確認ください</p>

          <div class="box-registration-password-01">
            <a href="../login/set">
              <span class="btn-registration-password-01">戻る</span></a></div><!-- /btn-registration-password --></section>

      </div><!-- /area-login -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
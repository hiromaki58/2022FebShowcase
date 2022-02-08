<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Login error" /></jsp:include>

      <article class="contents">
        <div class="area-logout">
          <section>
            <h2 class="sec-logout-in">Fail to login</h2>
            <p class="sec-logout-detail">Please make sure your ID and password</p>

            <div class="box-logout">
              <a href="../admin/login">Back to login page</a></div></section>
        </div><!-- /area-logout --></article><!-- /contents -->
    </div><!-- /wrapper -->

    <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
  </body></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Account suspended" /></jsp:include>

      <article class="contents">
        <div class="area-logout">
          <section>
            <h2 class="sec-logout-in">Your account is suspended.</h2>
            <p class="sec-logout-detail">Please ask to the association representative.</p>

            <div class="box-logout">
              <a href="../user/login">Back to the login page</a></div><!-- /box-logout --></section>

        </div><!-- /area-logout -->
      </article><!-- /contents -->
    </div><!-- /wrapper -->

    <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
  </body></html>
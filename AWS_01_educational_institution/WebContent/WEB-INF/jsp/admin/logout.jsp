<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
session.invalidate();
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Administrator logout" /></jsp:include>

      <article class="contents">
        <div class="area-logout">
          <section class="sec-logout">
            <h2 class="sec-logout-in">Logged out</h2>
            <p class="sec-logout-detail">Successfully logged out</p>
            <div class="box-logout">
              <a href="/admin/login">Back to login page</a></div><!-- /box-logout -->

          </section><!-- /sec-logout --></div><!-- /area-logout --></article><!-- /contents -->
    </div><!-- /wrapper -->

    <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
  </body></html>
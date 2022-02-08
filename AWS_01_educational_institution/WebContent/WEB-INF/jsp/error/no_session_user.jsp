<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("utf-8");
%>
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="No session - Error" /></jsp:include>

    <article class="contents">
      <div class="area-login">
        <section>
          <h2 class="sec-logout-in">No session anymore</h2>
          <p class="sec-logout-detail-02">Session may be logout, or fail to login.</p>
          <p class="sec-logout-detail">Please login again.</p></section>

          <div class="box-registration-password-01">
            <a href="../user/login">
              <span class="btn-registration-password-01">Back to login page</span></a></div><!-- box-registration-password-01 --></section>

      </div><!-- /area-login -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
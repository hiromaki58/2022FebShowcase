<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("utf-8");
%>
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Date input - Error" /></jsp:include>

    <article class="contents">
      <div class="area-login">
        <section>
          <h2 class="sec-logout-in">Wrong date format</h2>
          <p class="sec-error-detail-02">The date should be "yyyy-MM-dd".</p>
        </section>
        
		<div class="box-registration-password-01">
          <a href="#" onclick="javascript:window.history.back(-1);return false;">
            <span class="btn-registration-password-01">Back</span>
          </a>
        </div><!-- btn-registration-password -->
      </div><!-- /area-login -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
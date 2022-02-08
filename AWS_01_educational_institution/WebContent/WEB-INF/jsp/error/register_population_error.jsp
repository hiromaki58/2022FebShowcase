<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Employee number - Error" /></jsp:include>

    <article class="contents">
      <div class="area-login">
        <section>
          <h2 class="sec-login-in">The number should be just number.</h2>
          <p class="sec-logout-detail">Error: Please do not input "5th" instead just "5".</p>
        </section>
        
		<div class="box-registration-password-01">
          <a href="#" onclick="javascript:window.history.back(-1);return false;">
            <span class="btn-registration-password-01">back</span>
          </a>
        </div><!-- box-registration-password-01 -->
      </div><!-- /area-login -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
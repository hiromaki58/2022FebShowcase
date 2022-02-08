<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Code Sent" /></jsp:include>

    <article class="contents">
        <div class="area-reissue">
          <section>
            <h2 class="sec-login-in">We`ve sent you e-mail.</h2>

            <div class="form-reset-in">
              <p class="form-reset-ttl">We have sent you a confirmation code via e-mail.</p>
              <p class="form-reset-ttl">Enter it to the web site which is connected to the link at the e-mail.</p>
            </div>
          </section>
        </div><!-- /area-reissue -->
      </article><!-- /contents -->
    </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
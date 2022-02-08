<%@ page language="java" contentType="text/html;charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Token Confirmation" /></jsp:include>

    <article class="contents">
        <div class="area-login">
          <section>
            <h2 class="sec-login-in">We have sent you a confirmation code vis E-mail.</h2>
            <p class="sec-logout-detail">Enter it to reset your password.</p>
            <form class="form-login" action="../login/code_check" method="post">
              <div class="form-login-in">
                <input class="form-login-input" type="text" name="dismissed_token" placeholder="Code"><br>
              </div><!-- /form-login-in -->

              <input class="form-login-button" type="submit" value="Next">
            </form><!-- /form-login -->
          </section><!-- /sec-login -->
        </div><!-- /area-login -->
      </article><!-- /contents -->
    </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
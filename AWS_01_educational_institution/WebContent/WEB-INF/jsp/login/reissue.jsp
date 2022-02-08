<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.User"%>
<%@ page import="jp.co.warehouse.entity.Mail"%>
<%@ page import="java.util.List"%>
<%
User user = null;
Mail mailAddress = null;

if((User) session.getAttribute("user") != null){
  user = (User) session.getAttribute("user");
 }
if((Mail) session.getAttribute("mailAddress") != null){
	mailAddress = (Mail) session.getAttribute("mailAddress");
}
request.setCharacterEncoding("utf-8");
%>
 	<jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Password change" /></jsp:include>
    <article class="contents">
      <div class="area-ttl-reissue">
        <section>
          <h2 class="ttl-reissue">Password change</h2>
        </section>
      </div><!-- /area-ttl-reissue -->

      <div class="area-reissue">
        <section>
          <h3 class="sec-hidden-reissue">Password change</h3>
          <form class="form-reissue" action="../login/reissue" method="post">
            <div class="form-login-in">
              <div class="form-reissue-ttl">Old password</div>
              <input class="form-login-input" type="password" pattern="[^\x22\x27]*" name="password_old" placeholder="Old password"><br>
            </div><!-- /form-login-in -->

            <div class="form-login-in">
              <div class="form-login-ttl">New Password</div>
              <input class="form-login-input" type="password" pattern="[^\x22\x27]*" name="password_new" placeholder="New Password"><br>
            </div><!-- /form-login-in -->

            <div class="form-login-in">
              <div class="form-login-ttl">New Password（Confirmation）</div>
              <input class="form-login-input" type="password" pattern="[^\x22\x27]*" name="password_confirm" placeholder="New Password（Confirmation）"><br>
            </div><!-- /form-login-in -->

            <input class="form-login-button" type="submit" value="Change">
          </form><!-- /form-reissue -->
        </section>
      </div><!-- /area-reissue -->
      <jsp:include page="/WEB-INF/jsp/side/login_side_menu.jsp"></jsp:include>
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
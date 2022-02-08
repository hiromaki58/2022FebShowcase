<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%
	AdminRegisterUser adminRegisteruser = (AdminRegisterUser) session.getAttribute("adminRegisteruser");
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
  <jsp:include page="/WEB-INF/jsp/header/admin_header.jsp"><jsp:param name="title" value="New user registration - Completion" /></jsp:include>

  <article class="contents clearfix">
      <div class="contents-ttl">
        <section class="sec-ttl">
          <h2 class="sec-ttl-in">Completion of registration</h2>
          <p>Completed</p>
          <p>Registration confirmation email is sent to the address below.</p></section><!-- /sec-ttl --></div><!-- /contents-ttl -->

      <div class="contents-in">
        <section class="sec-register-user-01">
          <h3 class="ttl-register-user">Fundamental information</h3>
          <ul class="form-cmn-01-wrap">
            <li class="form-cmn-01">
              <p class="form-registrtion-ttl-01">E-mail address</p>
              <p class="form-registrtion-user"><%= adminRegisteruser.getUser_mail() %></p></li><!-- /form-cmn-01 --></ul><!-- /form-cmn-01-wrap -->

        </section><!-- /sec-register-user-01 -->

        <div class="btn-registration-user">
          <a href="/admin/mypage">
            <span class="btn-registration-user-01">Back to Administrator page</span></a></div><!-- /btn-registration-user -->

      </div><!-- /contents-in --></article><!-- /contents -->
  </div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>

</body></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>
<%
  AdminRegisterUser adminRegisteruser = (AdminRegisterUser) session.getAttribute("adminRegisteruser");
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/admin_header.jsp"><jsp:param name="title" value="New user registration" /></jsp:include>

  <article class="contents clearfix">
      <div class="contents-ttl">
        <section class="sec-ttl">
          <h2 class="sec-ttl-in">New user registration</h2></section><!-- /sec-ttl --></div><!-- /contents-ttl -->

      <div class="contents-in">
        <form class="form-admin-register-user" id="register_user" action="/admin/register_user" method="post">
          <section class="sec-register-user-01">
            <h3 class="ttl-register-user">Fundamental information</h3>

            <ul class="form-cmn-01-wrap">
              <li class="form-cmn-01">
                <p class="form-registrtion-ttl-01">Family name</p>
                <label class="form-registrtion-ttl-01"><span class="ttl-register-must" id="user_last_name_warning">Please enter your family name</span></label>
                <input class="form-registrtion-input-01" type="text" pattern="^[0-9A-Za-z]+$" id="user_last_name" name="user_last_name" placeholder="Family name"
	                <c:if test = "${adminRegisteruser.getUser_last_name() != null}">
                    value = <%= adminRegisteruser.getUser_last_name() %>
                  </c:if>
                ></li><!-- /form-cmn-01 -->

              <li class="form-cmn-01">
                <p class="form-registrtion-ttl-01">First Name</p>
                <label class="form-registrtion-ttl-01"><span class="ttl-register-must" id="user_first_name_warning">Please enter your first name</span></label>
                <input class="form-registrtion-input-01" type="text" pattern="^[0-9A-Za-z]+$" id="user_first_name" name="user_first_name" placeholder="First name"
	                <c:if test = "${adminRegisteruser.getUser_first_name() != null}">
                    value = <%= adminRegisteruser.getUser_first_name() %>
                  </c:if>
                ></li><!-- /form-cmn-01 --></ul><!-- /form-cmn-01-wrap -->

            <ul class="form-cmn-01-wrap">
              <li class="form-cmn-01">
                <p class="form-registrtion-ttl-02">E-mail address(Less than 100 characters)</p>
                <label class="form-registrtion-ttl-01"><span class="ttl-register-must" id="user_email_warning">Please enter your E-mail</span></label>
                <input class="form-registrtion-input-04" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" id="user_mail" name="user_mail" placeholder="E-mail"
                  <c:if test = "${adminRegisteruser.getUser_mail() != null}">
                    value = <%= adminRegisteruser.getUser_mail() %>
                  </c:if>
                ></li><!-- /form-cmn-01 --></ul><!-- /form-cmn-01-wrap --></section><!-- /sec-register-user-01 -->

          <div class="btn-registration-user">
            <p class="form-registrtion-ttl-01"><div class="ttl-register-must" id="register_warning">Please fill the required info</div></p>
            <input class="btn-registration-user-01" type="submit" value="Confirm"></div>

        </form><!-- /form-admin-register-user --></div><!-- /contents-in --></article><!-- /contents --></div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
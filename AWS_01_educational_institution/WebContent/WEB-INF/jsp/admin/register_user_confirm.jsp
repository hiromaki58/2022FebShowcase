<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="java.util.List"%>
<%
	AdminRegisterUser adminRegisteruser = (AdminRegisterUser) session.getAttribute("adminRegisteruser");
  request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
  <jsp:include page="/WEB-INF/jsp/header/admin_header.jsp"><jsp:param name="title" value="New user registration - Confirmation" /></jsp:include>

  <article class="contents clearfix">
      <div class="contents-ttl">
        <section class="sec-ttl">
          <h2 class="sec-ttl-in">New user registration - Confirmation</h2>
          <p>Is it fine to register the information below??</p></section><!-- /sec-ttl --></div><!-- /contents-ttl --><div class="contents-in">

        <form class="form-admin-register-user" action="" method="get">
          <section class="sec-register-user-01">
            <h3 class="ttl-register-user">Fundamental information</h3>

            <ul class="form-cmn-01-wrap">
              <li class="form-cmn-01">
                <p class="form-registrtion-ttl-01">Last name</p>
                <p class="form-registrtion-user"><%= adminRegisteruser.getUser_last_name() %></p></li><!-- /form-cmn-01 -->
              <li class="form-cmn-01">
                <p class="form-registrtion-ttl-01">First name</p>
                <p class="form-registrtion-user"><%= adminRegisteruser.getUser_first_name() %></p></li><!-- /form-cmn-01 --></ul><!-- /form-cmn-01-wrap -->

            <ul class="form-cmn-01-wrap">
              <li class="form-cmn-01">
                <p class="form-registrtion-ttl-01">Mail address</p>
                <p class="form-registrtion-user"><%= adminRegisteruser.getUser_mail() %></p></li><!-- /form-cmn-01 --></ul><!-- /form-cmn-01-wrap -->

          </section><!-- /sec-register-user-01 -->

          <div class="btn-registration-user">
            <a href="/admin/register_user">
              <span class="btn-registration-user-02">Back</span></a>
            <!-- "checkedTokenid" is used for cross site counter measure -->
            <a href="/admin/register_user?action=done&checkedTokenid=<%= session.getAttribute("originalTokenID") %>">
              <span class="btn-registration-user-01">Registration</span></a></div>

        </form><!-- /form-admin-register-user --></div><!-- /contents-in --></article><!-- /contents -->
  </div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>

</body></html>
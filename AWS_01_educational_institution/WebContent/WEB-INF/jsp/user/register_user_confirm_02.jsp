<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%@ page import="java.util.List"%>
<%
	UserRegisterUser userRegisterImg = (UserRegisterUser) session.getAttribute("userRegisterImg");
	ImgAddress path = new ImgAddress();
	path = (ImgAddress) session.getAttribute("path");
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Profile image registration - Confirmation" /></jsp:include>

  <article class="contents clearfix">
    <div class="contents-ttl">
      <section class="sec-ttl">
        <h2 class="sec-ttl-in">Confirmation - Profile image registration</h2>
        <p class="sec-ttl-detail">Is it fine to register??</p></section><!-- /sec-ttl --></div><!-- /contents-ttl -->

    <div class="contents-in clearfix">
      <form class="form-admin-register-user" action="/user/register_user_02" method="post">
        <div class="contents-main">
          <section class="sec-registrtion-user-02">
            <h3 class="ttl-register-user">Profile image</h3>

            <div class="img-register-user">
              <div class="form-registration-img">
                <div class="img-preview-01" id="file-preview">
                <img src="../showTentativeProfileImage?imgTitle=${ path.getFilePart() }" alt="Profile image"/>
                </div>
              </div><!-- /form-registration-img -->
            </div><!-- /ttl-register-user-in -->
          </section><!-- /sec-registrtion-user-02 -->
        </div><!-- /contents-main -->
      </div><!-- /contents-in -->

      <div class="btn-registration-user">
      <a href="/user/register_user_02">
          <span class="btn-registration-user-02">Back</span></a>
      <a href="/user/register_user_02?action=done">
        <span class="btn-registration-user-01">Register</span></a></div>

    </form><!-- /form-admin-register-user -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
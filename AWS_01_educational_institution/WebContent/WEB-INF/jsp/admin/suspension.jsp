<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%
	AdminRegisterUser registeredUserInfo = (AdminRegisterUser) session.getAttribute("registeredUserInfo");
  	UserRegisterUser userRegisterUser = (UserRegisterUser) session.getAttribute("userRegisterUser");
  	request.setCharacterEncoding("utf-8");
%>
	<jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="User suspension" /></jsp:include>

  	<article class="contents">
      <div class="contents-ttl">
      	<section class="sec-ttl">
          <h2 class="sec-ttl-in">User suspension</h2>
          <p>It is possible to changet the release status of <%= registeredUserInfo.getUser_last_name() %></p>
        </section><!-- /sec-ttl -->
      </div><!-- /contents-ttl -->

      <div class="contents-in">
      	<form class="form-admin-register-user" id="register_release" action="../admin/suspension" method="get">
          <section class="sec-register-user">
          	<h3 class="ttl-register-user">Release status</h3>

          	<div class="ttl-register-user-in">
          	  <p class="form-registrtion-ttl-04">Release status<span class="ttl-register-must">[required]</span></p>
          	  <span class="ttl-register-warning" id="release_warning">[Please select.]</span>
              <label for="release_label_01"><input type="radio" name="user_released" id="release_label_01" value="no"
                <c:if test="${userRegisterUser.getReleased().equals('no')}">
               	  checked="checked"
              	</c:if>
              />Private</label>
              <label for="release_label_02"><input type="radio" name="user_released" id="release_label_02" value="yes"
              	<c:if test="${userRegisterUser.getReleased().equals('yes')}">
                  checked="checked"
               	</c:if>
              />Released</label>
            </div><!-- /ttl-register-user-in -->
		  </section><!-- /sec-registrtion-user -->

          <div class="btn-registration-user">
          	<p class="form-registrtion-ttl-01">
          	  <div class="ttl-register-warning" id="register_release_warning">[Please select.]</div>
          	</p>
          	<a href="/admin/user_release?execution=done">
              <input class="btn-registration-user-01" type="submit" value="submit">
            </a>
          </div>
      	</form><!-- /form-admin-register-user -->
      </div><!-- /contents-in -->
  	</article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
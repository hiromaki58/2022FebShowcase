<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.User"%>
<%@ page import="jp.co.warehouse.dao.admin.AdminGetUserInfoDAO"%>
<%@ page import="jp.co.warehouse.dao.user.CheckUserRegisteredStatusDAO"%>
<%
	User user = (User) session.getAttribute("user");
	AdminGetUserInfoDAO adminGetUserInfoDao = new AdminGetUserInfoDAO();
	CheckUserRegisteredStatusDAO  cursDao = new CheckUserRegisteredStatusDAO();
	AdminRegisterUser userInfo = adminGetUserInfoDao.getRegisteredUserInfo(user.getEmail());
	request.setCharacterEncoding("utf-8");
%>
<c:set var="info"><%= cursDao.checkUserInfoRegistered(user.getEmail()) %></c:set>
<c:set var="img"><%= cursDao.checkUserImgRegistered(user.getEmail()) %></c:set>
<c:set var="release"><%= cursDao.checkUserReleaseRegistered(user.getEmail()) %></c:set>
<jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="User page" /></jsp:include>

	<article class="contents">
      <div class="ttl-non-activated">
      	<section class="sec-ttl">
          <h2 class="sec-ttl-in">User page</h2>
          <p>Welcome to user page <%= userInfo.getUser_last_name() %></p>
          <p class="txt-non-activated">You can register the new article and edit it.</p>
          <p>Let's start with these 3 items!</p>
        </section><!-- /sec-ttl -->
      </div><!-- /contents-ttl -->

	  <div class="sec-non-activated">
	    <ul class="list-non-activated">
	    <c:choose>
	     <c:when test = "${info}">
	       <li class="img-non-activated-01-done">Edit your profile</li>
	     </c:when>
	     <c:otherwise>
	        <li class="img-non-activated-01"><a href="/user/register_user_01">Register your profile</a></li>
	      </c:otherwise>
	    </c:choose>

	    <c:choose>
	      <c:when test = "${img}">
	        <li class="img-non-activated-02-done">Set your image</li>
	      </c:when>
	      <c:otherwise>
	        <li class="img-non-activated-02"><a href="/user/register_user_02">Set your image</a></li>
	      </c:otherwise>
	    </c:choose>

	    <c:choose>
	      <c:when test = "${release}">
	         <li class="img-non-activated-04-done">Private or Released</li>
	      </c:when>
	       <c:otherwise>
	        <li class="img-non-activated-03"><a href="/user/user_release">Private or Released</a></li>
	       </c:otherwise>
	    </c:choose>
	    </ul><!-- /list-non-activated -->
	  </div><!-- /sec-non-activated -->

      <div class="contents-register-sub">
        <nav class="nav-local">
       	  <ul>
          	<li>
              <p class="nav02">Account manage</p>
              <ul class="nav-local-in">
              	<li>
              	  <a href="../user/logout" class="nav02-01">Logout</a>
              	</li>
              </ul><!-- /nav-local-in -->
            </li>
          </ul>
        </nav><!-- /nav-local -->
      </div><!-- /contents-register-sub -->
	</article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.User"%>
<%@ page import="jp.co.warehouse.dao.admin.AdminGetUserInfoDAO"%>
<%@ page import="jp.co.warehouse.dao.user.UserGetUserInfoDAO"%>
<%
	Admin adminLogin = (Admin) session.getAttribute("admin");
	User user = (User) session.getAttribute("user");

	AdminGetUserInfoDAO adminGetUserInfoDAO = new AdminGetUserInfoDAO();
	UserGetUserInfoDAO getUserInfoDAO = new UserGetUserInfoDAO();
	AdminRegisterUser userInfo = adminGetUserInfoDAO.getRegisteredUserInfo(user.getEmail());
	UserRegisterUser userSelfRegisteredInfo = getUserInfoDAO.getSelfRegisteredUserInfo(user.getEmail());

	request.setCharacterEncoding("utf-8");
%>
<jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="User page" /></jsp:include>

	<article class="contents-mypage clearfix">
      <div class="contents-ttl-mypage">
        <section class="sec-ttl">
          <h2 class="h1-mypage-activated">User page</h2>
          <section>
          	<h3 class="hidden">SEARCH</h3>
          	<a href="/user/user_release">
	          <div class="box-identity-wrap">
	            <div class="box-identity-sentence">
	              <div class="txt-user-name"><%= userInfo.getUser_last_name() %><%= userInfo.getUser_first_name() %></div>
	              <%
	                if(userSelfRegisteredInfo.getReleased().equals("yes")){
	              %>
	              <div class="txt-release">Released</div>
	              <%
	                }
	                else {
	              %>
	              <div class="txt-non-release">Private</div>
	              <%
	                }
	              %>
				</div><!-- /box-identity-sentence -->
			  </div><!-- /box-identity-wrap -->
			</a>
		  </section>

          <p>Welcome to user page <%= userInfo.getUser_last_name() %></p>
          <p class="txt-non-activated">You can register the new article and edit it.</p>
        </section><!-- /sec-ttl -->
	  </div><!-- /contents-ttl -->

      <div class="sec-non-activated">
      	<section>
          <h3 class="ttl-non-article-non-released">You cannot registered your article because you chose private mode.</h3><!-- /ttl-non-article -->
          <ul class="list-non-activated">
            <li class="img-non-article">Registered new article</li>
          </ul><!-- /list-non-activated -->
        </section>
      </div><!-- /sec-non-activated -->

	  <jsp:include page="/WEB-INF/jsp/side/private_mypage_side_menu.jsp"></jsp:include>
      <div class="contents-register-sub">
      	<nav class="nav-local">
          <ul>
          	<li>
              <p class="nav02">Account</p>
              <ul class="nav-local-in">
              	<li><a href="/user/user_info" class="nav02-01">User</a></li>
              	<li><a href="/user/register_user_01" class="nav02-01">Modify the user detail</a></li>
              	<li><a href="/login/reissue.html" class="nav02-01">Password change</a></li>
              	<li><a href="/user/logout" class="nav02-01">Logout</a></li>
			  </ul><!-- /nav-local-in -->
			</li>
		  </ul>
      	</nav><!-- /nav-local -->
      </div><!-- /contents-register-sub -->
	</article><!-- /contents -->
  </div><!-- /wrapper -->
<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
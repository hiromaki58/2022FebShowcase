<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Objects"%>
<%
	AdminRegisterUser adminRegisterUserForNew = (AdminRegisterUser) session.getAttribute("adminRegisterUserForNew");
	UserRegisterUser userRegisterUser = null;

	if((UserRegisterUser) session.getAttribute("userRegisterUser") != null){
		userRegisterUser = (UserRegisterUser) session.getAttribute("userRegisterUser");
	}
	
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Info registration" /></jsp:include>

  <article class="contents clearfix">
    <div class="contents-ttl">
      <section class="sec-ttl">
        <h2 class="sec-ttl-in">Info registration</h2></section><!-- /sec-ttl --></div><!-- /contents-ttl -->

    <div class="contents-in">
      <form class="form-admin-register-user" id="register_user_01" action="/user/register_user_01" method="post" onsubmit="return issueAlert()">
        <section class="sec-register-user-01">
          <h3 class="ttl-register-user">General information</h3>

          <ul class="form-cmn-01-wrap">
            <li class="form-cmn-01">
              <p class="form-registrtion-ttl-01">Family name</p>
              <span class="form-registrtion-input-01"><%= adminRegisterUserForNew.getUser_last_name() %></span></li><!-- /form-cmn-01 -->
            <li class="form-cmn-01">
              <p class="form-registrtion-ttl-01">First name</p>
              <span class="form-registrtion-input-01"><%= adminRegisterUserForNew.getUser_first_name() %></span></li><!-- /form-cmn-01 --></ul><!-- /form-cmn-01-wrap -->

          <ul class="form-cmn-01-wrap">
            <li class="form-cmn-01">
              <p class="form-registrtion-ttl-02">E-mail address</p>
              <span class="form-registrtion-input-01"><%= adminRegisterUserForNew.getUser_mail() %></span></li><!-- /form-cmn-01 --></ul><!-- /form-cmn-01-wrap --></section><!-- /sec-register-user-01 -->

        <section class="sec-register-user">
          <h3 class="ttl-register-user">User profile</h3>

          <div class="ttl-register-user-in-temporary">
            <label class="form-registrtion-ttl-01">Gender<span class="ttl-register-must">[required]</span></label>
            <p class="form-registrtion-ttl-01"><span class="ttl-register-must" id="gender_profile_warning">[Choose your profile]</span></p>
            <select class="form-registration-pulldown" id="gender_profile" name="gender_profile">
              <option value="" selected="selected">Please select one of them.</option>
              <option value="female"
                <%
               	 if(userRegisterUser != null && userRegisterUser.getGender_profile().equals("female")){%>
	                selected="selected"
	            <% } %>
              >Female</option>

              <option value="male"
                <%
                  if(userRegisterUser != null && userRegisterUser.getGender_profile().equals("male")){%>
                    selected="selected"
                <% } %>
              >Male</option>

              <option value="unknown"
                <%
                  if(userRegisterUser != null && userRegisterUser.getGender_profile().equals("unknown")){%>
                    selected="selected"
                <% } %>
              >Unknown</option></select><!-- /form-registrtion-pulldown -->

            <div class="form-cmn-01">
              <p class="form-registrtion-ttl-01">E-mail for public contact</p>
              <input class="form-registrtion-input-01" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="open_mail" placeholder="E-mail for contact"
	              <c:if test = "${userRegisterUser.getOpenMail() != null}">
	                value = <%= userRegisterUser.getOpenMail() %>
	              </c:if>
              >
            </div><!-- /form-cmn-01 -->

            <div class="form-cmn-01">
              <p class="form-registrtion-ttl-01">Phone number</p>
              <input class="form-registrtion-input-01" type="tel" pattern="\d{2,4}-\d{3,4}-\d{3,4}" name="user_phone" placeholder="090-1234-5678"
		          <c:if test = "${userRegisterUser.getPhone() != null}">
		            value = <%= userRegisterUser.getPhone() %>
		          </c:if>
              >
            </div><!-- /form-cmn-01 -->

            <div class="form-cmn-01">
              <p class="form-registrtion-ttl-10">Web site (Less than 100 characters)</p>
              <input class="form-registrtion-input-01" type="text" pattern="^http(|s)://[0-9a-zA-Z/#&?%\.\-\+_=]+$" name="user_website" placeholder="https://www.google.ca/"
                <c:if test = "${userRegisterUser.getWeb_site() != null}">
                  value = <%= userRegisterUser.getWeb_site() %>
                </c:if>
              >
            </div><!-- /form-cmn-01 -->
          </div><!-- /ttl-register-user-in-temporary --></section><!-- /sec-registrtion-check -->

        <section class="sec-register-user">
          <h3 class="ttl-register-user">User information</h3>
          <div class="ttl-register-user-in">
            <p class="form-registrtion-textarea">
              <label class="form-registrtion-ttl-01" id="admin-profile">Profile (Up to 300 characters)<span class="ttl-register-must">[required]</span><br>
              <textarea class="form-user-profile" id="profile" name="profile" onkeyup="indicatingNumberOfCharacterOfUserProfile(value);"><c:if test = "${userRegisterUser.getProfile() != null}"><%= userRegisterUser.getProfile() %></c:if></textarea></p>
              <p class="form-registrtion-ttl-01" id="profile_inputlength">Current number of characters: 0</p>
              <span class="ttl-register-must" id="profile_warning">[Please write your profile]</span></label><br>
              <span class="ttl-register-must" id="profile_charactor_number_warning">[Number of character exceeds the limit.]</span></label><br>
		  </div><!-- /ttl-register-user-in --></section><!-- /sec-register-user -->

        <div class="btn-registration-user">
          <p class="form-registrtion-ttl-01"><div class="ttl-register-must" id="register_user_warning">[Required item is missing, or number of character exceeds the limit.]</div>
          <input class="btn-registration-user-01" type="submit" value="next"></div><!-- /btn-registration-user --></form><!-- /form-admin-register-user -->

    </div><!-- /contents-in -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
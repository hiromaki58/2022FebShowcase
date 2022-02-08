<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Objects"%>
<%
	Admin adminLogin = (Admin) session.getAttribute("admin");
	AdminRegisterUser retrieveRegisterUserInfo = (AdminRegisterUser) session.getAttribute("retrieveRegisterUserInfo");
	UserRegisterUser retrieveSelfRegisteredUserInfo = null;

	if((UserRegisterUser) session.getAttribute("retrieveSelfRegisteredUserInfo") != null){
		retrieveSelfRegisteredUserInfo = (UserRegisterUser) session.getAttribute("retrieveSelfRegisteredUserInfo");
	}

	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Info modification" /></jsp:include>

  <article class="contents clearfix">
    <div class="contents-ttl">
      <section class="sec-ttl">
        <h2 class="sec-ttl-in">Info modification</h2>
      </section><!-- /sec-ttl -->
    </div><!-- /contents-ttl -->

    <div class="contents-in">
      <%
         if(adminLogin != null){
      %>
      <form class="form-admin-register-user" id="register_user_01" action="../admin/modification_user_01" method="post" onsubmit="return issueAlert()">
      <%
        }
        else {
      %>
      <form class="form-admin-register-user" id="register_user_01" action="../user/modification_01" method="post" onsubmit="return issueAlert()">
      <%
        }
      %>
      <section>
        <h3 class="ttl-register-user">General information</h3>
        <div class="sec-register-user-in-01">
        <%
          if(adminLogin != null){
        %>
        <ul class="form-cmn-01-wrap">
		  <li class="form-cmn-01">
			<p class="form-registrtion-ttl-01">Family name *Less than 10 characters.</p>
			<input class="form-registrtion-input-01" type="text" id="user_last_name" name="user_last_name" placeholder="Family name"
			  <c:if test = "${retrieveRegisterUserInfo.getUser_last_name() != null}">
				value = <%= retrieveRegisterUserInfo.getUser_last_name() %>
              </c:if>
            >
		  </li><!-- /form-cmn-01 -->

		  <li class="form-cmn-01">
			<p class="form-registrtion-ttl-01">First name *Less than 10 characters.</p>
			<input class="form-registrtion-input-01" type="text" id="user_first_name" name="user_first_name" placeholder="First name"
			  <c:if test = "${retrieveRegisterUserInfo.getUser_first_name() != null}">
				value = <%= retrieveRegisterUserInfo.getUser_first_name() %>
              </c:if>
            >
          </li><!-- /form-cmn-01 -->
		</ul><!-- /form-cmn-01-wrap -->
		<%
		  }
          else {
        %>
		<ul class="form-cmn-01-wrap">
		  <li class="form-cmn-01">
			<p class="form-registrtion-ttl-01">Family name</p>
            <span class="form-registrtion-input-01"><%= retrieveRegisterUserInfo.getUser_last_name() %></span>
		  </li><!-- /form-cmn-01 -->
          <li class="form-cmn-01">
            <p class="form-registrtion-ttl-01">First name</p>
            <span class="form-registrtion-input-01"><%= retrieveRegisterUserInfo.getUser_first_name() %></span>
          </li><!-- /form-cmn-01 -->
        </ul><!-- /form-cmn-01-wrap -->
        <%
		  }
        %>
        <ul class="form-cmn-01-wrap">
		  <li class="form-cmn-01">
			<p class="form-registrtion-ttl-02">E-mail address</p>
            <span class="form-registrtion-input-01"><%= retrieveRegisterUserInfo.getUser_mail() %></span>
          </li><!-- /form-cmn-01 -->
        </ul><!-- /form-cmn-01-wrap -->
      </div>
	</section>

	<section class="sec-register-user">
	  <h3 class="ttl-register-user">user profile</h3>
	  <div class="sec-register-user-in-02">
		<p class="form-registrtion-ttl-01">Profile<span class="ttl-register-must">[required]</span></p>
		<select class="form-registration-pulldown" id="gender_profile" name="gender_profile">
		<option value="">Please select one of them</option>
		<option value="female"
		<%
		  if(retrieveSelfRegisteredUserInfo != null && retrieveSelfRegisteredUserInfo.getGender_profile().equals("female")){%>
			selected="selected"
        <% } %>
        >Female</option>

        <option value="male"
        <%
		  if(retrieveSelfRegisteredUserInfo != null && retrieveSelfRegisteredUserInfo.getGender_profile().equals("male")){%>
          	selected="selected"
        <% } %>
        >Male</option>

        <option value="unknown"
        <%
          if(retrieveSelfRegisteredUserInfo != null && retrieveSelfRegisteredUserInfo.getGender_profile().equals("unknown")){%>
            selected="selected"
        <% } %>
        >Unknown</option>
		</select><!-- /form-registrtion-pulldown -->

        <div class="form-cmn-01">
		  <p class="form-registrtion-ttl-01">E-mail for contact</p>
          <input class="form-registrtion-input-01" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="open_mail" placeholder="E-mail for contact"
			<c:if test = "${retrieveSelfRegisteredUserInfo.getOpenMail() != null}">
			  value = <%= retrieveSelfRegisteredUserInfo.getOpenMail() %>
            </c:if>
           >
        </div><!-- /form-cmn-01 -->

        <div class="form-cmn-01">
          <p class="form-registrtion-ttl-01">Phone number</p>
           <input class="form-registrtion-input-01" type="tel" pattern="^(+|)\d{2,4}-\d{3,4}-\d{3,4}" name="user_phone" placeholder="090-1234-5678"
          <c:if test = "${retrieveSelfRegisteredUserInfo.getPhone() != null}">
            value = <%= retrieveSelfRegisteredUserInfo.getPhone() %>
          </c:if>
           >
        </div><!-- /form-cmn-01 -->

        <div class="form-cmn-01">
          <p class="form-registrtion-ttl-01">Web site *Less than 100 characters.</p>
          <input class="form-registrtion-input-01" type="text" pattern="^http(|s)://[0-9a-zA-Z/#&?%\.\-\+_=]+$" name="user_website" placeholder="https://www.google.ca/"
            <c:if test = "${retrieveSelfRegisteredUserInfo.getWeb_site() != null}">
              value = <%= retrieveSelfRegisteredUserInfo.getWeb_site() %>
            </c:if>
          >
		</div><!-- /form-cmn-01 -->
	  </div><!-- /ttl-register-user-in -->
	</section><!-- /sec-registrtion-check -->

    <section class="sec-register-user">
      <h3 class="ttl-register-user">user information</h3>

      <div class="ttl-register-user-in">
        <p class="form-registrtion-textarea">
          <label class="form-registrtion-ttl-01">Profile *Up to 300 characters.<span class="ttl-register-must">[required]</span><br></label><br>
          <textarea class="form-user-profile" id="profile" name="profile" onkeyup="indicatingNumberOfCharacterOfuserProfile(value);">
			<c:if test="${retrieveSelfRegisteredUserInfo.getProfile() != null}"><%= retrieveSelfRegisteredUserInfo.getProfile().replaceAll("<br/>", "") %></c:if>
		  </textarea>
          <p class="form-registrtion-ttl-01" id="profile_inputlength">Current number of characters: 0</p>
          <span class="ttl-register-must" id="profile_charactor_number_warning">[Number of character exceeds the limit.]</span></label><br>
        </p>
      </div>
            
      <div class="btn-registration-user">
        <input type="text" class="hidden" name="userId" value=<%= retrieveRegisterUserInfo.getRegisteredUserId() %>>
        <p class="form-registrtion-ttl-01">
          <div class="ttl-register-must" id="register_user_warning">[Required item is missing, or number of character exceeds the limit.]</div>
        <input class="btn-registration-user-01" type="submit" value="Register">
      </div><!-- /btn-registration-user -->
    </form><!-- /form-admin-register-user -->

    </div><!-- /contents-in -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
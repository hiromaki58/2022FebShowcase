<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="java.util.List"%>
<%
	AdminRegisterUser adminRegisterUserForNew = (AdminRegisterUser) session.getAttribute("adminRegisterUserForNew");
	UserRegisterUser userRegisterUser = (UserRegisterUser) session.getAttribute("userRegisterUser");
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Info registration - Confirmation" /></jsp:include>

  <article class="contents clearfix">
    <div class="contents-ttl">
      <section class="sec-ttl">
        <h2 class="sec-ttl-in">Confirmation - Info registration</h2>
      </section><!-- /sec-ttl -->
    </div><!-- /contents-ttl -->

    <div class="contents-in">

      <form class="form-admin-register-user" action="/user/register_user_01" method="post">
        <section class="sec-register-user-01">
          <h3 class="ttl-register-user">General information</h3>

          <ul class="form-cmn-01-wrap">
            <li class="form-cmn-01">
              <p class="form-registrtion-ttl-01">Family name</p>
              <span class="form-registrtion-input-01"><%= adminRegisterUserForNew.getUser_last_name() %></span>
            </li><!-- /form-cmn-01 -->
            <li class="form-cmn-01">
              <p class="form-registrtion-ttl-01">First name</p>
              <span class="form-registrtion-input-01"><%= adminRegisterUserForNew.getUser_first_name() %></span>
            </li><!-- /form-cmn-01 -->
          </ul><!-- /form-cmn-01-wrap -->

          <ul class="form-cmn-01-wrap">
            <li class="form-cmn-01">
              <p class="form-registrtion-ttl-02">E-mail address</p>
              <span class="form-registrtion-input-04"><%= adminRegisterUserForNew.getUser_mail() %></span>
            </li><!-- /form-cmn-01 -->
          </ul><!-- /form-cmn-01-wrap -->
        </section><!-- /sec-register-user-01 -->

        <section class="sec-register-user">
          <h3 class="ttl-register-user">User profile</h3>

          <div class="ttl-register-user-in">
            <p class="form-registrtion-ttl-01">Gender<span class="ttl-register-must">[required]</span></p>
            <%
              if(userRegisterUser.getGender_profile().equals("female")){
            %>
              <label for="certification">Female</label>
            <%
              }
              else if(userRegisterUser.getGender_profile().equals("male")){
            %>
              <label for="certification">Male</label>
            <%
              }
              else if(userRegisterUser.getGender_profile().equals("unknown")){
            %>
              <label for="certification">Unknown</label>
            <%
              }
            %>

            <div class="form-cmn-01">
              <p class="form-registrtion-ttl-01">E-mail for contact</p>
              <span class="form-registrtion-input-01"><%= userRegisterUser.getOpenMail() %></span>
            </div><!-- /form-cmn-01 -->

            <div class="form-cmn-01">
              <p class="form-registrtion-ttl-01">Phone number</p>
              <span class="form-registrtion-input-01"><%= userRegisterUser.getPhone() %></span>
            </div><!-- /form-cmn-01 -->

            <div class="form-cmn-01">
              <p class="form-registrtion-ttl-01">Web site</p>
              <span class="form-registrtion-input-01"><%= userRegisterUser.getWeb_site() %></span>
            </div><!-- /form-cmn-01 -->

          </div><!-- /ttl-register-user-in -->
        </section><!-- /sec-registrtion-user -->

        <section class="sec-register-user">
          <h3 class="ttl-register-user">User information</h3>

          <div class="ttl-register-user-in">
            <p class="form-registrtion-textarea">
              <label class="form-registrtion-ttl-01" for="admin-profile">Profile<span class="ttl-register-must">[required]</span></label><br>
              <span class="form-registrtion-input-01"><%= userRegisterUser.getProfile() %></span>
            </p>
          </div><!-- /ttl-register-user-in -->
        </div><!-- /contents-in -->

        <div class="btn-registration-user">
		  <a href="/user/register_user_01">
			<span class="btn-registration-user-02">Back</span>
		  </a>
		  <a href="/user/register_user_01?action=done">
			<span class="btn-registration-user-01">Register</span>
		  </a>
		</div>
    </form><!-- /form-admin-register-user -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
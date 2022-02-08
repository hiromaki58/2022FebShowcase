<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Profile image registration" /></jsp:include>

  <article class="contents">
    <div class="contents-ttl">
      <section class="sec-ttl">
        <h2 class="sec-ttl-in">Profile image registration</h2></section><!-- /sec-ttl --></div><!-- /contents-ttl -->

    <div class="contents-in">
      <form class="form-admin-register-user" id="form-register-profile" action="/user/register_user_02" method="post" enctype="multipart/form-data">

        <section class="sec-register-user">
          <h3 class="ttl-register-user">Profile image</h3>

          <div class="ttl-register-user-in">
            <div class="form-registration-img">
              <div class="img-preview-01"></div>
              <p class="form-registration-ttl-02">Profile image<span class="ttl-register-must">[Required]</span></p>
              <input type="file" class="btn-peview-img" name="user_img" accept="image/jpeg,image/png">
              <p class="form-registration-detail">
              	Aspect ratio needs to be 1:1.<br>
              	The image size should be less than 100MB,<br>
                and the dimension is more than 300 pixel by 300 pixel.</p></div><!-- /form-registration-img --></div><!-- /ttl-register-user-in --></section><!-- /sec-registrtion-user -->

        <div class="btn-registration-user">
          <div class="ttl-register-warning" id="register_profile_warning">The image size should be less than 100MB.</div>
          <input class="btn-registration-user-01" type="submit" value="next"></div>

      </form><!-- /form-admin-register-user -->
    </div><!-- /contents-in -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
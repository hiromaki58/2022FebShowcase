<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.User"%>
<%@ page import="jp.co.warehouse.entity.Mail"%>
<%@ page import="java.util.List"%>
<%
	User user = null;
	Mail mailAddress = null;

	if((User) session.getAttribute("user") != null){
  		user = (User) session.getAttribute("user");
 	}
	if((Mail) session.getAttribute("mailAddress") != null){
  		mailAddress = (Mail) session.getAttribute("mailAddress");
	}
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Change the e-amil address" /></jsp:include>

    <article class="contents">
        <div class="area-ttl-reissue">
          <section>
            <h2 class="ttl-reissue">E-mail address change</h2></section></div><!-- /area-ttl-reissue -->

        <div class="area-reissue">
          <section>
            <h3 class="sec-hidden-reissue hidden">E-mail address change</h3>
            <form class="form-login-02" action="../login/id_reissue" method="post">
              <div class="form-login-in">
                <div class="form-id-reissue-ttl">Old E-mail address</div>
                <input class="form-id-reissue-input" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="id_old" placeholder="Old e-mail address"><br></div><!-- /form-login-in -->

              <div class="form-login-in">
                <div class="form-id-reissue-ttl">New E-mail address</div>
                <input class="form-id-reissue-input" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="id_new" placeholder="New e-mail address"><br></div><!-- /form-login-in -->

              <div class="form-login-in">
                <div class="form-id-reissue-ttl">New E-mail address（Confirmation）</div>
                <input class="form-id-reissue-input" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="id_confirm" placeholder="New e-mail address（Confirmation）"><br></div><!-- /form-login-in -->

              <input class="form-login-button-02" type="submit" value="Submit"></form><!-- /form-login -->
            </section>
        </div><!-- /area-reissue -->
      <jsp:include page="/WEB-INF/jsp/side/login_side_menu.jsp"></jsp:include>
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
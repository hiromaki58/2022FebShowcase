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
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Change the e-mail address - Confirmation" /></jsp:include>

    <article class="contents-id-reset">
      <div class="area-reset-id">
        <section>
          <h2 class="ttl-id-reset">Would you like to change the email address？？</h2>
          <div class="box-reset-id-01">
            <a href="#" onclick="javascript:window.history.back(-1);return false;">
              <span class="btn-reset-id-01">No</span></a>
            <a href="/login/id_reissue">
              <span class="btn-reset-id-02">Yes</span>
            </a>
          </div><!-- box-reset-id-01 -->
        </section>
      </div><!-- /area-reset-id -->
    </article><!-- /contents-id-reset -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
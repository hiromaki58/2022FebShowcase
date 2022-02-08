<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
	<jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="User suspension - Completed" /></jsp:include>

  	<article class="contents">
      <div class="contents-ttl-user-release-complete">
      	<section class="sec-ttl">
          <h2 class="sec-ttl-in">User suspension - Completed</h2>
          <p>Change the user release situation.</p>
        </section><!-- /sec-ttl -->
      </div><!-- /contents-ttl -->

      <div class="contents-in">
        <div class="btn-registration-user">
          <a href="../admin/mypage">
            <span class="btn-registration-user-01">Back to administrator page</span>
          </a>
        </div>
    </div><!-- /contents-in -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
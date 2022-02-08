<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Profile image registration - Completion" /></jsp:include>

	  <article class="contents clearfix">
	    <div class="contents-complete">
	      <section class="sec-ttl">
	        <h2 class="sec-ttl-in">Completion - Profile image registration</h2>
	        <p class="sec-ttl-detail">It has been registered.</p>
	      </section><!-- /sec-ttl -->
	    </div><!-- /contents-ttl -->

	      <div class="btn-registration-user">
	      <a href="../user/mypage">
	          <span class="btn-registration-user-01">Back to the user page</span></a></div>
	    </form><!-- /form-admin-register-user -->

	  </article><!-- /contents -->
	</div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
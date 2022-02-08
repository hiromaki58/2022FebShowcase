<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Release status - Completion" /></jsp:include>

  <article class="contents">
    <div class="contents-ttl-user-release-complete">
      <section class="sec-ttl">
        <h2 class="sec-ttl-in">Release status - Completion</h2>
        <p>The status change is completed.</p>
        </section><!-- /sec-ttl --></div><!-- /contents-ttl -->

    <div class="contents-in">
        <div class="btn-registration-user">
          <a href="/user/mypage">
            <span class="btn-registration-user-01">Back to the user page</span></a></div>

    </div><!-- /contents-in -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
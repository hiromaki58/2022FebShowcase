<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
 <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Article delete - Error" /></jsp:include>

      <article class="contents">
        <div class="area-logout">
          <section>
            <h2 class="sec-logout-in">Fail to delete the article</h2>
            <p class="sec-logout-detail">Error: Please try again later</p>
            <div class="box-error">
              <a class="error-return-button" href="#" onclick="javascript:window.history.back(-1);return false;">back</a>
            </div><!-- /box-logout -->
          </section>
        </div><!-- /area-logout -->
      </article><!-- /contents -->
    </div><!-- /wrapper -->

    <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
  </body></html>
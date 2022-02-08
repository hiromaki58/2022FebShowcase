<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%
  Article articleInfo = (Article) session.getAttribute("registeredArticle");
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Article modification - Eye catch" /></jsp:include>
  	<article class="contents">
      <h2 class="hidden">contents</h2>
      <div class="sec-ttl">
      	<section>
          <h3 class="sec-ttl-in">Article image modification</h3>
        </section>
      </div><!-- /sec-ttl -->

      <div class="contents-in">
        <form class="form-register-article" id="form-register-eyecatch" action="/article/modification_02" method="post" enctype='multipart/form-data'>
          <div class="sec-register-article-01">
            <section>
              <h3 class="ttl-register-user">Article eye-catching image</h3>
              <div class="ttl-register-user-in">
                <div class="img-preview-02"><img class="img-confirm-eyecatch" src="../showEyecatch?imgId=${ articleInfo.getImg_id() }" alt="Eye-catch image"/></div>
                <label class="form-registration-ttl-02">Eye-catch image</label><br>
                <input type="file" class="btn-peview-img" name="article_eyecatch" accept="image/jpeg,image/png">
                <input type="text" class="hidden" name="articleId" value="${ articleInfo.getArticleId() }">
                <p class="form-registration-detail">
                  The image should be less than 100MB.<br>
                  .jpg or .png only acceptable
                </p>
              </div><!-- /ttl-register-user-in -->
            </section>
          </div><!-- /sec-register-article-01 -->

          <div class="btn-registration-user">
            <div class="ttl-register-warning" id="register_eyecatch_warning">The image should be less than 100MB.</div>
            <input type="text" class="hidden" name="articleId" value=<%= articleInfo.getArticleId() %>>
            <input class="btn-registration-user-01" type="submit" value="register">
          </div>
        </form><!-- /form-register-article -->
      </div><!-- /contents-in -->
    </article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
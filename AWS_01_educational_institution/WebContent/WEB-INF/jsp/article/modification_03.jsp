<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%
	Article articleInfo = (Article) session.getAttribute("registeredArticle");
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Article modification - Step3" /></jsp:include>

	<article class="contents">
      <h2 class="hidden">contents</h2>
      <div class="sec-ttl">
        <section>
          <h2 class="sec-ttl-in">Article image modification</h2>
        </section>
      </div><!-- /sec-ttl -->

      <div class="contents-in">
        <form class="form-admin-register-user" id="form-register-article-img" action="/article/modification_03" method="post" enctype="multipart/form-data">
          <section>
          	<h3 class="ttl-register-user">Images</h3>

            <div class="form-registration-img">
              <div class="img-preview-03"><img class="img-modify-article" src="../showArticleImg01?imgId=${ articleInfo.getImg_id() }" alt="Image" /></div>
              <label class="form-registration-ttl-02" for="article_img_01">Image（1）</label><br>
              <input id="form-register-article-img-01"type="file" class="btn-peview-img" name="article_img_01" accept="image/jpeg,image/png">
              <p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
              <div class="ttl-register-warning" id="register_article_img_01_warning">The image should be less than 50MB.</div>
          	</div><!-- /form-registration-img -->

          	<div class="form-registration-img">
              <div class="img-preview-05"><img class="img-modify-article" src="../showArticleImg02?imgId=${ articleInfo.getImg_id() }" alt="Image" /></div>
              <label class="form-registration-ttl-02" for="article_img_02">Image（2）</label><br>
              <input id="form-register-article-img-02" type="file" class="btn-peview-img" name="article_img_02" accept="image/jpeg,image/png">
              <p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
              <div class="ttl-register-warning" id="register_article_img_02_warning">The image should be less than 50MB.</div>
          	</div><!-- /form-registration-img -->

          	<div class="form-registration-img">
              <div class="img-preview-06"><img class="img-modify-article" src="../showArticleImg03?imgId=${ articleInfo.getImg_id() }" alt="Image" /></div>
              <label class="form-registration-ttl-02" for="article_img_03">Image（3）</label><br>
              <input id="form-register-article-img-03" type="file" class="btn-peview-img" name="article_img_03" accept="image/jpeg,image/png">
              <p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
              <div class="ttl-register-warning" id="register_article_img_03_warning">The image should be less than 50MB.</div>
          	</div><!-- /form-registration-img -->

          	<div class="form-registration-img">
              <div class="img-preview-07"><img class="img-modify-article" src="../showArticleImg04?imgId=${ articleInfo.getImg_id() }" alt="Image" /></div>
              <label class="form-registration-ttl-02" for="article_img_04">Image（4）</label><br>
              <input id="form-register-article-img-04" type="file" class="btn-peview-img" name="article_img_04" accept="image/jpeg,image/png">
              <p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
              <div class="ttl-register-warning" id="register_article_img_04_warning">The image should be less than 50MB.</div>
          	</div>
          </section><!-- /form-registration-img -->

          <div class="btn-registration-user">
            <input type="text" class="hidden" name="articleId" value=<%= articleInfo.getArticleId() %>>
            <div class="ttl-register-warning" id="register_article_img_warning">The image should be less than 100MB.</div>
            <input class="btn-registration-user-01" type="submit" value="register">
          </div><!-- /btn-registration-user -->
        </form><!-- /form-admin-register-user -->
      </div><!-- /contents-in -->
	</article><!-- /contents -->
  </div><!-- /wrapper -->
  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
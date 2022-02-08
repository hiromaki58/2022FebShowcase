<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%
  ImgAddress imgaddr_eyecatch = new ImgAddress();

	ImgAddress imgaddr_01 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_path_01") != null){
	  imgaddr_01 = (ImgAddress) session.getAttribute("article_img_path_01");
	}

	ImgAddress imgaddr_02 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_path_02") != null){
	  imgaddr_02 = (ImgAddress) session.getAttribute("article_img_path_02");
	}

	ImgAddress imgaddr_03 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_path_03") != null){
	  imgaddr_03 = (ImgAddress) session.getAttribute("article_img_path_03");
	}

	ImgAddress imgaddr_04 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_path_04") != null){
	  imgaddr_04 = (ImgAddress) session.getAttribute("article_img_path_04");
	}

  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Article registration - Step3" /></jsp:include>

	  <article class="contents">
      	<h2 class="hidden">contents</h2>
      	<div class="sec-ttl">
          <section>
          	<h2 class="sec-ttl-in">Article registration - Step3</h2>
          	<h4 class="ttl-register-article-01">You can register up to 4 images.</h4>
          </section>
        </div><!-- /sec-ttl -->

      	<div class="contents-in">
          <form class="form-admin-register-user" id="form-register-article-img" action="/article/register_03" method="post" enctype="multipart/form-data">
          	<section>
          	  <h3 class="ttl-register-user">Image</h3>
          	  <div class="form-registration-img">
              <div class="img-preview-03">
            	<%
              	  if((ImgAddress) session.getAttribute("article_img_path_01") != null){
            	%>
                	<img class="img-return-step-03" src="../showTentativeArticleImg01?imgPath_01=${ imgaddr_01.image }" alt="Image - confirmation" />
            	<%
              	  }
            	%>
          	  </div>
          	  <label class="form-registration-ttl-02">Image（1）</label><br>
          	  <input id="form-register-article-img-01" type="file" class="btn-peview-img" name="article_img_01" accept="image/jpeg,image/png">
          	  <p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
          	  <div class="ttl-register-warning" id="register_article_img_01_warning">The image should be less than 50MB.</div>
        	</div><!-- /form-registration-img -->

          	<div class="form-registration-img">
              <div class="img-preview-05">
            	<%
              	  if((ImgAddress) session.getAttribute("article_img_path_02") != null){
            	%>
                	<img class="img-return-step-03" src="../showTentativeArticleImg02?imgPath_02=${ imgaddr_02.image }" alt="Image - confirmation" />
            	<%
              	  }
            	%>
            	</div>
            	<label class="form-registration-ttl-02">Image（2）</label><br>
            	<input id="form-register-article-img-02" type="file" class="btn-peview-img" name="article_img_02" accept="image/jpeg,image/png">
            	<p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
            	<div class="ttl-register-warning" id="register_article_img_02_warning">The image should be less than 50MB.</div>
              </div><!-- /form-registration-img -->

          	  <div class="form-registration-img">
            	<div class="img-preview-06">
            	  <%
              		if((ImgAddress) session.getAttribute("article_img_path_03") != null){
            	  %>
                	  <img class="img-return-step-03" src="../showTentativeArticleImg03?imgPath_03=${ imgaddr_03.image }" alt="Image - confirmation" />
            	  <%
             		}
            	  %>
            	</div> 		
            	<label class="form-registration-ttl-02">Image（3）</label><br>
            	<input id="form-register-article-img-03" type="file" class="btn-peview-img" name="article_img_03" accept="image/jpeg,image/png">
            	<p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
            	<div class="ttl-register-warning" id="register_article_img_03_warning">The image should be less than 50MB.</div>
              </div><!-- /form-registration-img -->

          	  <div class="form-registration-img">
            	<div class="img-preview-07">
            	  <%
              		if((ImgAddress) session.getAttribute("article_img_path_04") != null){
            	  %>
                	  <img class="img-return-step-03" src="../showTentativeArticleImg04?imgPath_04=${ imgaddr_04.image }" alt="Image - confirmation" />
            	  <%
              		}
            	  %>
            	</div>
            	<label class="form-registration-ttl-02">Image（4）</label><br>
            	<input id="form-register-article-img-04" type="file" class="btn-peview-img" name="article_img_04" accept="image/jpeg,image/png">
            	<p class="form-registration-detail">The image should be less than 50MB.<br>.jpg or .png only acceptable</p>
            	<div class="ttl-register-warning" id="register_article_img_04_warning">The image should be less than 50MB.</div>
              </div>
            </section><!-- /form-registration-img -->

            <div class="btn-registration-user">
              <div class="ttl-register-warning" id="register_article_img_warning">The image should be less than 50MB.</div>
              <a href="/article/register_02">
                <span class="btn-registration-user-02">back</span>
              </a>
              <input class="btn-registration-user-01" type="submit" value="next">
            </div><!-- /btn-registration-user -->
          </form><!-- /form-admin-register-user -->
        </div><!-- /contents-in -->
	  </article><!-- /contents -->
	</div><!-- /wrapper -->
  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
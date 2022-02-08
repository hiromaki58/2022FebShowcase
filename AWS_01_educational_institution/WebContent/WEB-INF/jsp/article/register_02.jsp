<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%
  ImgAddress imgaddr_eyecatch = new ImgAddress();
  if((ImgAddress) session.getAttribute("eyecatch_path") != null){
	  imgaddr_eyecatch = (ImgAddress) session.getAttribute("eyecatch_path");
	}
  
  request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Article registration - Step2" /></jsp:include>
	<article class="contents">
	  <h2 class="hidden">contents</h2>
      <div class="sec-ttl">
        <section>
          <h2 class="sec-ttl-in">Article registration - Step2</h2>
          <h4 class="ttl-register-article-01">Please register the eye-catching image.</h4>
        </section>
      </div><!-- /sec-ttl -->
      
      <div class="contents-in">
        <form class="form-register-article" id="form-register-eyecatch" action="/article/register_02" method="post" enctype='multipart/form-data'>
        
          <div class="sec-register-article-01">
            <section>
              <h3 class="ttl-register-user">Article eye-catching image</h3>
              <div class="ttl-register-user-in">
                <div class="img-preview-02">
                <%
                  if((ImgAddress) session.getAttribute("eyecatch_path") != null){
                %>
                    <img class="img-confirm-eyecatch" src="../showTentativeEyecatch?imgPath=${ imgaddr_eyecatch.image}" alt="Eye-catching images - Confirmation" />
                <%
                  }
                %>
                </div>
                <label class="form-registration-ttl-02">Eye-catching images</label><br>
                <input type="file" class="btn-peview-img" name="article_eyecatch" accept="image/jpeg,image/png">
                <p class="form-registration-detail">
                  The image should be less than 100MB.<br>
                  .jpg or .png only acceptable
                </p>
              </div><!-- /ttl-register-user-in -->
            </section>
          </div><!-- /sec-register-article-01 -->
          
          <div class="btn-registration-user">
            <div class="ttl-register-warning" id="register_eyecatch_warning">The image should be less than 100MB.</div>
            <a href="/article/register_01">
              <span class="btn-registration-user-02">back</span>
            </a>
            <input class="btn-registration-user-01" id="register_eyecatch" type="submit" value="next">
          </div><!-- /btn-registration-user -->
          
	    </form><!-- /form-register-article -->
	  </div><!-- /contents-in -->
	</article><!-- /contents -->
  </div><!-- /wrapper -->
  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
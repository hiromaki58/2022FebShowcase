<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%@ page import="jp.co.warehouse.entity.MainVisualLinkAddress"%>
<%
	ImgAddress imgaddr_eyecatch = new ImgAddress();
	if((ImgAddress) session.getAttribute("mainVisualPath") != null){
	  imgaddr_eyecatch = (ImgAddress) session.getAttribute("mainVisualPath");
	}

	MainVisualLinkAddress mvlinkAddress = new MainVisualLinkAddress();
	if((MainVisualLinkAddress) session.getAttribute("mvlinkAddress") != null){
		mvlinkAddress = (MainVisualLinkAddress) session.getAttribute("mvlinkAddress");
	}

	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Changing key visual" /></jsp:include>

	  <article class="contents">
    <h2 class="hidden">contents</h2>
      <div class="sec-ttl">
        <section>
          <h2 class="sec-ttl-in">Changing key visual</h2></section></div><!-- /sec-ttl -->

      <div class="contents-in">
        <form class="form-register-article" id="form-register-mv" action="/admin/register_mv" method="post" enctype='multipart/form-data'>
          <div class="sec-register-article-01">
            <section>
              <h3 class="ttl-register-user">Key visual</h3>
              <div class="ttl-register-user-in-03">
                <div class="img-preview-02">
                <%
                  if((ImgAddress) session.getAttribute("mainVisualPath") != null){
                %>
                    <img class="img-confirm-eyecatch" src="../showTentativeMv?imgPath=${ imgaddr_eyecatch.image}" alt="Key image" />
                <%
                  }
                %>
                </div>
                <p class="form-registration-ttl-02">Key visual</p>
                <input type="file" class="btn-peview-img" name="main_view" accept="image/jpeg,image/png">
                <p class="form-registration-detail">The image should be less than 100MB.<br>File extension needs to be .jpg or .png please.</p></div><!-- /ttl-register-user-in-03 -->

              <div class="area-register-article-05">
                <p class="form-registrtion-ttl-01">Related URL</p>
                <input class="form-registrtion-input-04" type="text" pattern="https?://.+" name="mv_addr_web" placeholder="Related URL"
                 <c:if test = "${mvlinkAddress.getAddress() != null}">
                     value = '<%= mvlinkAddress.getAddress() %>'
                 </c:if>
                >
              </div><!-- /area-register-article-05 --></section></div><!-- /sec-register-article-01 -->

          <div class="btn-registration-user">
            <div class="ttl-register-warning" id="register_mv_warning">The image should be less than 100MB.</div>
            <input class="btn-registration-user-01" id="register_mv" type="submit" value="Comfirmation"></div><!-- /btn-registration-user -->

        </form><!-- /form-register-article --></div><!-- /contents-in --></article><!-- /contents -->
    </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
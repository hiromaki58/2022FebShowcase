<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Changing key visual - Confirmation" /></jsp:include>

  <article class="contents">
    <div class="sec-ttl">
      <section>
        <h2 class="sec-ttl-in">Confirmation - Changing key visual</h2>
        <h4 class="ttl-register-article-01">Are you sure to set the image below？</h4>
      </section>
    </div><!-- /sec-ttl -->

    <div class="contents-in">
      <form class="form-admin-register-user" action="/admin/register_mv" method="post">
        <section>
          <h3 class="ttl-register-article-02">Key visual</h3>
          <div class="area-register-article-01">
            <p class="ttl-article-01">Key visual</p>
            <div class="img-preview-02">
              <img class="img-confirm-eyecatch" src="../showTentativeMv" alt="eye-catching image - Confirmation" />
            </div>
          </div><!-- /area-register-article-01 -->

          <div class="area-register-article-05">
            <p class="form-registrtion-ttl-01">Related URL</p>
            <a href="<%= mvlinkAddress.getAddress() %>" target="blank">
              <strong class="txt-confirm-article-02"><%= mvlinkAddress.getAddress() %></strong>
            </a>
          </div><!-- /area-register-article-05 -->

          <div class="btn-registration-user">
            <a href="/admin/register_mv">
              <span class="btn-registration-user-02">Back</span>
            </a>
            <a href="/admin/register_mv?action=done">
              <span class="btn-registration-user-01">Register</span>
            </a>
		  </div><!-- /btn-registration-user -->
        </section>
      </form><!-- /form-admin-register-user -->
    </div><!-- /contents-in -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

 <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="java.util.List"%>
<%
	Article infoBean = null;
	if((Article)session.getAttribute("articleInfo") != null){
		infoBean = (Article) session.getAttribute("articleInfo");
 	}
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/login_header.jsp"><jsp:param name="title" value="Article Delete - Confirmation" /></jsp:include>

    <article class="contents-id-reset">
      <div class="area-reset-id">
        <section>
          <h2 class="ttl-id-reset">Do you want to delete「<%= infoBean.getArticle_name() %>」？？</h2>
          <div class="box-reset-id-01">
            <a href="#" onclick="javascript:window.history.back(-1);return false;">
              <span class="btn-reset-id-01">No</span></a>
            <a href="./delete?execution=go&articleId=<%= infoBean.getArticleId() %>">
              <span class="btn-reset-id-02">Delete</span>
            </a>
          </div><!-- box-reset-id-01 -->
        </section>
      </div><!-- /area-reset-id -->
    </article><!-- /contents-id-reset -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
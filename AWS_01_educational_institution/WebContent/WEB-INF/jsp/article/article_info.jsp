<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%@ page import="jp.co.warehouse.date.SeparateFromDate"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%@ page import="java.util.List"%>
<%
	Admin adminLogin = (Admin) session.getAttribute("admin");
	Article articleInfo = (Article) session.getAttribute("articleInfo");
	SeparateFromDate separation = new SeparateFromDate();
	CompareDate comparison = new CompareDate();
	
	String stringStartMin = "";
	String stringFinishMin = "";
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Article information" /></jsp:include>

  <article class="contents clearfix">
  	<div class="sec-ttl">
	  <section>
		<h2 class="sec-ttl-in">Article information</h2>
	  </section>
    </div><!-- /sec-ttl -->

    <div class="contents-right">
      <section>
        <h3 class="ttl-register-article-02">Article general information</h3>
        <div class="area-register-article-04">
          <p class="ttl-article-01">Article title</p>
          <span class="txt-confirm-article-01"><%= articleInfo.getArticle_name() %></span>
          	          
          <p class="ttl-article-01">Release status</p>
          <span class="txt-confirm-article-01">
          	<%
				boolean checkReleased = comparison.checkPublicity(articleInfo.getOpening_day(), articleInfo.getClosing_day());
                if(checkReleased) {
            %>
					Released
            <%
                }
                else {
			%>
					Private
			<%
                }
			%>
          </span>
			<p class="ttl-article-01">Post period</p>
	        <span class="txt-confirm-article-01">
	        <%
               String openingYear = separation.SeparateYear(articleInfo.getOpening_day());
               String openingMonth = separation.SeparateMonth(articleInfo.getOpening_day());
               String openingDay = separation.SeparateDay(articleInfo.getOpening_day());
            %>
            <%= openingYear %>/
            <%= openingMonth %>/
            <%= openingDay %>
            &nbsp;&nbsp;〜　
            <%
               String closingYear = separation.SeparateYear(articleInfo.getClosing_day());
               String closingMonth = separation.SeparateMonth(articleInfo.getClosing_day());
               String closingDay = separation.SeparateDay(articleInfo.getClosing_day());
             %>
            <%= closingYear %>/
            <%= closingMonth %>/
            <%= closingDay %>
	        </span>
	      </div><!-- /area-register-article-01 -->
	  </section>

      <section>
        <h3 class="ttl-register-article-02">User information</h3>
        <div class="area-register-article-04">
		  <%
			if(adminLogin != null){
          %>
		  <p class="ttl-article-01">Author information</p>
		  <%
			if(articleInfo.getUserList() != null){
		  %>
			  <span class="txt-confirm-article-01"><%= articleInfo.getUser() %></span>
		  <%
			}
		  %>
          <p class="ttl-article-01">Article registered person</p>
          <span class="form-registrtion-input-01">Warehouse Association</span>
		  <%
            }
            else{
		  %>
          <span class="txt-confirm-article-01"><%= articleInfo.getUser() %></span>
          <% } %>
        </div><!-- /area-register-article-01 -->
      </section>

      <section>
        <h3 class="ttl-register-article-02">Images</h3>
        <div class="area-modify-article-img">
          <p class="ttl-article-01">Eye-catching images</p>
          <div class="img-eyecatch-01" style="background-image: url(../showEyecatch?imgId=<%=articleInfo.getImg_id()%>)"></div>
          <a class="btn-modify-eyecatch-wrap" href="../article/modification_02?articleId=<%=articleInfo.getArticleId()%>">
            <div class="btn-modify-eyecatch">
              <span class="btn-modify-eyecatch-in">Edit Eye-catch</span>
            </div>
          </a>

          <p class="ttl-article-01">Images</p>
          <ul class="list-article-img">
            <li class="img-preview-04" style="background-image: url(../showArticleImg01?imgId=<%=articleInfo.getImg_id()%>)"></li>
            <li class="img-preview-04" style="background-image: url(../showArticleImg02?imgId=<%=articleInfo.getImg_id()%>)"></li>
            <li class="img-preview-04" style="background-image: url(../showArticleImg03?imgId=<%=articleInfo.getImg_id()%>)"></li>
            <li class="img-preview-04" style="background-image: url(../showArticleImg04?imgId=<%=articleInfo.getImg_id()%>)"></li>
          </ul>

          <a class="btn-modify-eyecatch-wrap" href="../article/modification_03?articleId=<%=articleInfo.getArticleId()%>">
            <div class="btn-modify-eyecatch">
              <span class="btn-modify-eyecatch-in">Edit Images</span>
            </div>
          </a>
        </div><!-- /area-register-article-01 -->
      </section>

      <section>
        <h3 class="ttl-register-article-02">Article detail</h3>
        <div class="area-register-article-04">
          <p class="ttl-article-01">CEO</p>
          <span class="txt-confirm-article-01"><%= articleInfo.getCeo() %></span>
          <p class="ttl-article-01">Address</p>
          <span class="txt-confirm-article-01"><%= articleInfo.getCompany_addr() %></span>
          <p class="ttl-article-01">Capital</p>
          <span class="txt-confirm-article-01"><%= articleInfo.getCapital() %></span>
          <p class="ttl-article-01">Number of employee</p>
          <span class="txt-confirm-article-01"><%= articleInfo.getEmployeeNumber() %></span>
          <p class="ttl-article-01">Established Date</p>
          <span class="txt-confirm-article-01">
              <%
                 String applyYear = separation.SeparateYear(articleInfo.getEstablishedDate());
                 String applyMonth = separation.SeparateMonth(articleInfo.getEstablishedDate());
                 String applyDay = separation.SeparateDay(articleInfo.getEstablishedDate());
               %>
               <%= applyYear %>/
               <%= applyMonth %>/
               <%= applyDay %>
          </span>

          <p class="ttl-article-01">E-mail address</p>
          <a href="mailto:<%= articleInfo.getCompany_mail() %>" target="blank">
             <strong class="txt-confirm-article-02"><%= articleInfo.getCompany_mail() %></strong>
          </a>
          
          <p class="ttl-article-01">Phone number</p>
          <span class="txt-confirm-article-01"><%= articleInfo.getCompany_phone() %></span>
          <p class="ttl-article-01">URL</p>
          <a href="<%= articleInfo.getCompany_url() %>" target="blank">
            <strong class="txt-confirm-article-02"><%= articleInfo.getCompany_url() %></strong>
          </a>
          
          <p class="ttl-article-01">Article introduction</p>
          <span class="txt-confirm-article-01"><%= articleInfo.getIntroduction() %></span>
        </div><!-- /area-register-article-01 -->
      </section>

      <section>
        <h3 class="hidden">Article detail</h3>
        <div class="area-register-article-04">
	      <ul class="list-article-info">
	        <li>
              <a href="../delete?articleId=<%=articleInfo.getArticleId()%>">
                <span class="btn-editing-article-01">delete</span>
              </a>
            </li>
	        <li>
	          <a href="../previewarticle?articleId=<%=articleInfo.getArticleId()%>" target="blank">
	            <span class="btn-editing-article-02">preview</span>
	          </a>
	        </li>
	        <li>
	          <a href="../article/modification_01?articleId=<%=articleInfo.getArticleId()%>">
	            <span class="btn-editing-article-03">edit</span>
	          </a>
	        </li>
          </ul><!-- /list-article-info -->
      	</div><!-- /area-register-article-01 -->
      </section>
	</div><!-- /contents-right -->
	<%
      if(adminLogin != null){
    %>
      	<jsp:include page="/WEB-INF/jsp/side/admin_side_menu.jsp"></jsp:include>
    <%
      }
      else {
    %>
      	<jsp:include page="/WEB-INF/jsp/side/side_menu.jsp"></jsp:include>
    <%
      }
    %>
  </article><!-- /contents -->
</div><!-- /wrapper -->

 <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
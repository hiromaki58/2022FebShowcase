<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%@ page import="jp.co.warehouse.date.SeparateFromDate"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%@ page import="java.util.List"%>
<%
	SeparateFromDate separation = new SeparateFromDate();
	CompareDate comparison = new CompareDate();
	StringBuilder sbEstablishedDate = new StringBuilder();
	
	Admin adminLogin = (Admin) session.getAttribute("admin");
	Article newArticle = (Article) session.getAttribute("newArticle");
	
	ImgAddress imgaddr_eyecatch = new ImgAddress();
	if((ImgAddress) session.getAttribute("eyecatch_path") != null){
	  imgaddr_eyecatch = (ImgAddress) session.getAttribute("eyecatch_path");
	}
	
	ImgAddress imgaddr_01 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_01") != null){
	  imgaddr_01 = (ImgAddress) session.getAttribute("article_img_01");
	}
	
	ImgAddress imgaddr_02 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_02") != null){
	  imgaddr_02 = (ImgAddress) session.getAttribute("article_img_02");
	}
	
	ImgAddress imgaddr_03 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_03") != null){
	  imgaddr_03 = (ImgAddress) session.getAttribute("article_img_03");
	}
	
	ImgAddress imgaddr_04 = new ImgAddress();
	if((ImgAddress) session.getAttribute("article_img_04") != null){
	  imgaddr_04 = (ImgAddress) session.getAttribute("article_img_04");
	}
	
	String stringStartMin = "";
	String stringFinishMin = "";
	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header.jsp"><jsp:param name="title" value="Article registration - Confirmation" /></jsp:include>

	<article class="contents">
	    <div class="sec-ttl">
	      <section>
	        <h2 class="sec-ttl-in">Article registration - Confirmation</h2>
	        <h4 class="ttl-register-article-01">Is it fine to register the article??</h4>
	      </section>
	    </div><!-- /sec-ttl -->

	    <div class="contents-in">
	      <form class="form-admin-register-user" action="/article/register_article_01" method="post">
	        <section>
	          <h3 class="ttl-register-article-02">Article general information</h3>
	          <div class="area-register-article-04">
	            <p class="ttl-article-01">Article title</p>
	            <span class="txt-confirm-article-01"><%= newArticle.getArticle_name() %></span>
	            
	            <%
               	  if(adminLogin != null){
              	%>
		            <p class="ttl-article-01">Tag</p>
		            <span class="txt-confirm-article-01">
			    <%
			          if(newArticle.getCategory() != null){
		                if(newArticle.getCategory().equals("private")){
			    %>
			              Private
			    <%
			            }
		                else if(newArticle.getCategory().equals("public")){
				%>
				          Public
				<%
		                }
			            else if(newArticle.getCategory().equals("not_specified")){
			    %>
		                  Not specified
			    <%
			            }
			          }
			    %>
		            </span>
	            <%
                  }
                %>

	            <p class="ttl-article-01">Release status</p>
	            <span class="txt-confirm-article-01">
	              <%
	                boolean checkReleased = comparison.checkPublicity(newArticle.getOpening_day(), newArticle.getClosing_day());
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
                	String openingYear = separation.SeparateYear(newArticle.getOpening_day());
                 	String openingMonth = separation.SeparateMonth(newArticle.getOpening_day());
                 	String openingDay = separation.SeparateDay(newArticle.getOpening_day());
               	%>
	            <%= openingYear %>/
	            <%= openingMonth %>/
	            <%= openingDay %>
              		&nbsp;&nbsp;〜　
	            <%
	                 String closingYear = separation.SeparateYear(newArticle.getClosing_day());
	                 String closingMonth = separation.SeparateMonth(newArticle.getClosing_day());
	                 String closingDay = separation.SeparateDay(newArticle.getClosing_day());
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
				<p class="ttl-article-01">User information</p>
				<span class="txt-confirm-article-01">
				  <%= newArticle.getUser() %>
	           	</span>
	          <%
               	}
              %>
              <p class="ttl-article-01">Article registered person</p>
	          <span class="txt-confirm-article-01">
				<%= newArticle.getUser() %>
	          </span>
			</div><!-- /area-register-article-01 -->
		  </section>  
	      <section>
            <h3 class="ttl-register-article-02">Article detail</h3>
	        <div class="area-register-article-04">
	            <p class="ttl-article-01">CEO</p>
	            <span class="txt-confirm-article-01"><%= newArticle.getCeo() %></span>
	            <p class="ttl-article-01">Address</p>
	            <span class="txt-confirm-article-01"><%= newArticle.getCompany_addr() %></span>
	            <p class="ttl-article-01">Capital</p>
	            <span class="txt-confirm-article-01"><%= newArticle.getCapital() %></span>
	            <p class="ttl-article-01">Number of employee</p>
	            <span class="txt-confirm-article-01"><%= newArticle.getEmployeeNumber() %></span>
	            <p class="ttl-article-01">Date</p>
	            <span class="txt-confirm-article-01">
		            <%
		            	if(newArticle.getEstablishedDate() == null){
		            		sbEstablishedDate.append("Not specified");
		            	}
		            	else {
		            		String applyYear = separation.SeparateYear(newArticle.getEstablishedDate());
			                String applyMonth = separation.SeparateMonth(newArticle.getEstablishedDate());
			                String applyDay = separation.SeparateDay(newArticle.getEstablishedDate());
			                
			                sbEstablishedDate.append(applyYear);
			                sbEstablishedDate.append("/");
			                sbEstablishedDate.append(applyMonth);
			                sbEstablishedDate.append("/");
			                sbEstablishedDate.append(applyDay);
		            	}
	            	%>
		            <%= sbEstablishedDate %>
	            </span>
	            <p class="ttl-article-01">E-mail address</p>
	            <strong class="txt-confirm-article-02"><%= newArticle.getCompany_mail() %></strong>
	            <p class="ttl-article-01">Phone number</p>
	            <span class="txt-confirm-article-01"><%= newArticle.getCompany_phone() %></span>
	            <p class="ttl-article-01">URL</p>
	            <a href="<%= newArticle.getCompany_url() %>" target="blank">
	              <strong class="txt-confirm-article-02"><%= newArticle.getCompany_url() %></strong></a>
	            <p class="ttl-article-01">Article introduction</p>
	            <span class="txt-confirm-article-01"><%= newArticle.getIntroduction() %></span>
	          </div><!-- /area-register-article-01 -->
	        </section>

	        <section>
	          <h3 class="ttl-register-article-02">Images</h3>
	          <div class="area-register-article-03">
	            <p class="ttl-article-01">Eye-catching images</p>
	            <div class="img-preview-02">
	              <img class="img-confirm-eyecatch" src="../showTentativeEyecatch?imgPath=${ imgaddr_eyecatch.image}" alt="Eye-catching images - Confirmation" />
	            </div>
	            <p class="ttl-article-01">Images</p>
	            <ul>
	              <li class="img-preview-04"><img class="img-confirm-article" src="../showTentativeArticleImg01?imgPath_01=${ imgaddr_01.image }" alt="Images - Confirmation" /></li>
	              <li class="img-preview-04"><img class="img-confirm-article" src="../showTentativeArticleImg02?imgPath_02=${ imgaddr_02.image }" alt="Images - Confirmation" /></li>
	              <li class="img-preview-04"><img class="img-confirm-article" src="../showTentativeArticleImg03?imgPath_03=${ imgaddr_03.image }" alt="Images - Confirmation" /></li>
	              <li class="img-preview-04"><img class="img-confirm-article" src="../showTentativeArticleImg04?imgPath_04=${ imgaddr_04.image }" alt="Images - Confirmation" /></li>
	            </ul>
	          </div><!-- /area-register-article-01 -->
	        </section>

	        <div class="btn-registration-user">
	          <a href="/article/register_03">
	            <span class="btn-registration-user-02">back</span>
              </a>
	          <a href="/article/register_01?action=done">
	            <span class="btn-registration-user-01">register</span>
	          </a>
            </div>
	      </form><!-- /form-admin-register-user -->
	    </div><!-- /contents-in -->
	  </article><!-- /contents -->
	</div><!-- /wrapper -->
  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
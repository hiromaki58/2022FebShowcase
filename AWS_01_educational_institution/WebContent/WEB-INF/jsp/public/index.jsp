<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.ArticleInfoArray"%>
<%@ page import="jp.co.warehouse.date.SeparateFromDate"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%
    ArticleInfoArray orderByWeightLimit6 = (ArticleInfoArray) session.getAttribute("orderByWeightLimit6");
	ArticleInfoArray orderByDayLimit3 = (ArticleInfoArray) session.getAttribute("orderByDayLimit3");
    ArticleInfoArray orderByDayLimit6 = (ArticleInfoArray) session.getAttribute("comingArticleArray");
    
    String linkAddress = (String) session.getAttribute("linkAddress");
    SeparateFromDate separation = new SeparateFromDate();
    
    CompareDate compareDate = new CompareDate();
    String establishedYear = "";
    String establishedMonth = "";
    String establishedDay = "";
    request.setCharacterEncoding("utf-8");
%>
<jsp:include page="/WEB-INF/jsp/header/public_header_with_ajax.jsp">
  <jsp:param name="title" value="Top" />
  <jsp:param name="type" value="website" />
  <jsp:param name="url" value="/index" />
  <jsp:param name="img" value="" />
</jsp:include>

  <c:if test="${linkAddress != 'unknown'}">
	<a href="<%= linkAddress %>">
  </c:if>
	  <div class="area-hero clearfix" style="background-image: url(./showMv)"><!-- The area composed by the eye-catch image -->
		<section class="sec-hero">
		  <h2 class="hero-title">Canadian corporations</h2>
	   <!-- <h3 class="hero-date">
			  <span class="hero-date-in-01">3</span>
			  <span class="hero-date-in-02">.11</span>
			  <span class="hero-date-in-03"> mon.</span>
			</h3>
		  <div class="btn-hero">apply</div> -->
		</section><!-- /sec-hero -->
	  </div><!-- /area-hero -->	
  <c:if test="${linkAddress != 'unknown'}">
    </a>
  </c:if>
	<article class="contents">
	  <div class="area-search">
		<section>
	      <h2 class="hidden">SEARCH</h2>
		  <form class="box-search-index-wrap" action="./search" method="get">
          	<input type="text" name="articleSearch" class="form-index-keyword" pattern="[^\x22\x27]*" placeholder="Keyword">
            <input class="btn-search-index" type="submit" value="search">
          </form><!-- /form -->
        </section>
      </div><!-- /area-search -->
	  
	  <div class="area-available">
      	<div class="sec-available-wrap">
          <h2 class="ttl-available-01">Did you know??</h2><!-- /ttl-available-01 -->
          <h3 class="ttl-available-02">Top articles</h3><!-- /ttl-available-02 -->
          <section>
          	<div class="sec-available-in" id="number_list">
              <ul class="box-available-wrap">
              <%
             	for (Article articleInfo : orderByDayLimit6.getArticleInfoArray()) {
              %>
              	<li class="mod-available-wrap">
			  <%
				  if(articleInfo.getCategory().equals("public")){
			  %>
					<div class="bnr-limited">Public</div>
			  <%
				  }
			  %>
					<a href="./article?articleId=<%=articleInfo.getArticleId()%>">
					  <div class="img-recommendation" style="background-image: url(./showEyecatch?imgId=<%=articleInfo.getImg_id()%>)" ></div>
                 	</a>
                 	<div class="date-available">
			  <%
					  establishedYear = separation.SeparateYear(articleInfo.getEstablishedDate());
				      establishedMonth = separation.SeparateMonth(articleInfo.getEstablishedDate());
				      establishedDay = separation.SeparateDay(articleInfo.getEstablishedDate());
			  %>
			  		  Est.&nbsp;&nbsp;
					  <%= establishedYear %>/
			          <%= establishedMonth %>/
			          <%= establishedDay %>
					</div>
                	<div class="mod-available-detail">
					  <section>
						<a href="./article?articleId=<%=articleInfo.getArticleId()%>">
						  <h4 class="ttl-available-individuality"><%=articleInfo.getArticle_name()%></h4>
                    	</a>
                    	<div class="txt-available-user"><%=articleInfo.getCeo()%></div>
                    	<div class="txt-available-address"><%=articleInfo.getCompany_addr()%></div>
                    	<div class="btn-available-wrap">
                      	  <a href="./article?articleId=<%=articleInfo.getArticleId()%>" class="btn-available-02">
                        	<span class="btn-available-detail">detail</span>
                          </a>
                       	</div><!-- /btn-available-wrap -->
                      </section>
                    </div><!-- /mod-available-detail -->
                  </li><!-- /mod-available-wrap -->
			  <%
				}
              %>
          	  </ul><!-- /box-available-warp -->
        	</div><!-- /sec-available-in -->
      	  </section>
    	</div><!-- /sec-available-wrap -->
    	<button class="btn_open_top" id="more_btn" type=button style="width:368px;">More</button>
  	  </div><!-- /area-available -->
  	  
	  <div class="area-new-arrival">
		<section class="sec-new-arrival">
		  <h2 class="ttl-new-arrival-01">New Arrivals</h2>
		  <h3 class="ttl-new-arrival-02">The latest articles are here</h3>
		  <div class="sec-new-arrival-in">
			<ul>
			<%
			  int i = 0;
			  for (Article articleInfo : orderByDayLimit3.getArticleInfoArray()) {
			%>
			  <li class="box-new-arrival"><!-- Show the new arrivals  -->
				<div class="bnr-new-arrival-wrap">
			<%
				if(i < 3){
            %>
				  <div class="bnr-new-arrival-new">New article</div>
			<%
				}
				else if(articleInfo.getCategory().equals("public")){
			%>
				  <div class="bnr-new-arrival-limited">Public</div>
			<%
				}
				i++;
			%>
				  <div class="bnr-new-arrival-date">
			<%
					establishedYear = separation.SeparateYear(articleInfo.getEstablishedDate());
				    establishedMonth = separation.SeparateMonth(articleInfo.getEstablishedDate());
				    establishedDay = separation.SeparateDay(articleInfo.getEstablishedDate());
			%>
					Est.&nbsp;&nbsp;
					<%= establishedYear %>/
		            <%= establishedMonth %>/
		            <%= establishedDay %>
				  </div><!-- /bnr-new-arrival-date -->
				</div><!-- /bnr-new-arrival-wrap -->
				<div class="box-new-arrival-in">
			  	  <div class="txt-new-arrival-wrap">
				  	<section>
					  <a href="./article?articleId=<%=articleInfo.getArticleId()%>">
					  	<h4 class="ttl-new-arrival-03"><%=articleInfo.getArticle_name()%></h4>
					  </a>
                      <div class="txt-new-arrival-detail-wrap">
					  	<span class="txt-new-arrival-user"><%=articleInfo.getCeo()%></span>
					  	<span class="txt-new-arrival-address"><%=articleInfo.getCompany_addr()%></span>
					  </div><!-- /txt-new-arrival-detail-wrap -->
				  	</section>
                  </div><!-- /txt-new-arrival-wrap -->
                  <div class="btn-new-arrival-wrap">
                    <a href="./article?articleId=<%=articleInfo.getArticleId()%>">
					  <div class="btn-new-arrival-detail">Detail</div>
				  	</a>
                  </div><!-- /btn-new-arrival-wrap -->
				</div><!-- /box-new-arrival-in -->
			  </li><!-- /box-new-arrival -->
			<%
			  }
			%>
			</ul>
		  </div><!-- /sec-new-arrival-in -->
		</section><!-- /sec-new-arrival -->
	  </div><!-- /area-new-arrival -->
	</article><!-- /contents -->
  </div><!-- /wrapper -->
  
  <jsp:include page="/WEB-INF/jsp/footer/public_footer.jsp"></jsp:include>
</body></html>
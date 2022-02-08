<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserArray"%>
<%@ page import="jp.co.warehouse.entity.ArticleInfoArray"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.date.SeparateFromDate"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%
	AdminRegisterUser registeredUserInfoAtPublicUserDetail = (AdminRegisterUser) session.getAttribute("registeredUserInfoAtPublicUserDetail");
	UserRegisterUser selfRegisteredUserInfoAtPublicUserDetail = (UserRegisterUser) session.getAttribute("selfRegisteredUserInfoAtPublicUserDetail");
	ArticleInfoArray threeArticleInfoSelectedByUserId = (ArticleInfoArray) session.getAttribute("threeArticleInfoSelectedByUserId");

	CompareDate compareDate = new CompareDate();
	String stringStartMin = "";
	SeparateFromDate separation = new SeparateFromDate();

	request.setCharacterEncoding("utf-8");
%>
<jsp:include page="/WEB-INF/jsp/header/public_header.jsp"><jsp:param name="title" value="User information" /></jsp:include>

 <article class="contents clearfix">
    <h2 class="hidden">User information</h2>
    <div class="self-registered-user-contents">
      <section>
        <h3 class="hidden">General information</h3>
        <div class="self-registered-user-contents-left">
          <nav class="nav-local">
            <img class="img-self-registered-user-02" src="../getProfileImageById?selfRegisteredUserId=<%= selfRegisteredUserInfoAtPublicUserDetail.getId() %>" alt="User image">
            <p class="txt-name-02"><%= registeredUserInfoAtPublicUserDetail.getUser_last_name() %>&nbsp;<%= registeredUserInfoAtPublicUserDetail.getUser_first_name() %></p>
          </nav><!-- /nav-local -->
        </div><!-- /self-registered-user-contents-left -->

        <%
          if(threeArticleInfoSelectedByUserId.getArticleInfoArray().size() > 0){
        %>
            <div class="self-registered-user-contents-right">
        <%
          }
          else {
        %>
            <div class="self-registered-user-contents-right-02">
        <%
          }
        %>
          <section>
            <h4 class="hidden">User profile</h4>
          </section>
          <section>
            <h4 class="ttl-self-registered-user-profile">Profile</h4>
            <p class="txt-self-registered-user-profile-04"><%= selfRegisteredUserInfoAtPublicUserDetail.getProfile() %></p>
          </section>
          <section>
            <h4 class="ttl-register-article-02">Contact</h4>
            <div class="box-self-registered-user-contact">
              <%
                if(selfRegisteredUserInfoAtPublicUserDetail.getOpenMail() != null){
              %>
                  <p class="txt-self-registered-user-mail"><%= selfRegisteredUserInfoAtPublicUserDetail.getOpenMail() %></p>
              <%
                }
              %>
              <p class="txt-self-registered-user-phone"><%= selfRegisteredUserInfoAtPublicUserDetail.getPhone() %></p>
              <p class="txt-self-registered-user-website">
              	<strong>
              	  <a href="<%= selfRegisteredUserInfoAtPublicUserDetail.getWeb_site() %>" target="blank"><%= selfRegisteredUserInfoAtPublicUserDetail.getWeb_site() %></a>
              	</strong>
              </p>
            </div><!-- /area-register-article-01 -->
          </section>
        </div><!-- /self-registered-user-contents-right -->
      </section>
    </div><!-- /self-registered-user-contents -->
  </article><!-- /contents -->

  <article class="coming-contents">
    <%
      if(threeArticleInfoSelectedByUserId.getArticleInfoArray().size() > 0){
    %>
    <div class="sec-available-wrap-02">
      <h2 class="ttl-available-04">The articles of <%= registeredUserInfoAtPublicUserDetail.getUser_last_name() %>&nbsp;<%= registeredUserInfoAtPublicUserDetail.getUser_first_name() %></h2>
      <h3 class="ttl-available-05">The author's articles.</h3>
      <section>
        <h4 class="hidden">available</h4>
        <div class="sec-available-in">
          <ul class="box-available-wrap"><!-- The list of the articles of the auther's. -->
            <%
             for (Article articleInfo : threeArticleInfoSelectedByUserId.getArticleInfoArray()) {
            %>
              <li class="mod-available-wrap-02">
                 <%
                     if(articleInfo.getCategory().equals("limited")){
                 %>
                       <div class="bnr-limited">Limited</div>
                 <%
                     }
                 %>

                 <a href="./article?articleId=<%=articleInfo.getArticleId()%>">
                   <div class="img-recommendation" style="background-image: url(./showEyecatch?imgId=<%=articleInfo.getImg_id()%>)" ></div>
                 </a>
                 
                <div class="mod-available-detail">
                  <section>
                    <a href="./article?articleId=<%=articleInfo.getArticleId()%>">
                      <h4 class="ttl-available-individuality"><%=articleInfo.getArticle_name()%></h4>
                    </a>
                    <div class="txt-available-user"><%=articleInfo.getUser()%></div>
                    <div class="txt-available-address"><%=articleInfo.getCompany_addr()%></div>
                    <div class="btn-available-wrap">
                      <a href="./article?articleId=<%=articleInfo.getArticleId()%>" class="btn-available-02">
                        <span class="btn-available-detail">Detail</span>
                      </a><!-- /btn-available-02 -->
                    </div><!-- /btn-available-wrap -->
                  </section>
                </div><!-- /mod-available-detail -->
              </li><!-- /mod-available-wrap-02 -->
              <%
                }
              %>
          </ul><!-- /box-available-warp -->
        </div><!-- /sec-available-in -->
      </section>
    </div><!-- /sec-available-wrap-02 -->
    <% } %>

    <div class="sec-user-wrap">
      <%
        if(threeArticleInfoSelectedByUserId.getArticleInfoArray().size() > 0){
      %>
      <div class="sec-user">
      <%
        } else {
      %>
      <div class="sec-user-02">
      <%
        }
      %>
        <a href="/user">
          <section>
            <h2 class="btn-user">Authors list</h2>
          </section>
        </a>
      </div><!-- /sec-user -->
    </div><!-- /sec-user-wrap -->

  </article><!-- /coming-contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/public_footer.jsp"></jsp:include>
</body></html>
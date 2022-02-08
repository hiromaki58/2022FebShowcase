<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.date.SeparateFromDate"%>
<%@ page import="jp.co.warehouse.date.CompareDate"%>
<%
    Article articleInfo = (Article) session.getAttribute("articleInfo");
    AdminRegisterUser registeredUserInfo = (AdminRegisterUser) session.getAttribute("registeredUserForArticle");
    UserRegisterUser userRegisterUserInfo = (UserRegisterUser) session.getAttribute("userRegisterUserForArticle");
    
    SeparateFromDate separation = new SeparateFromDate();
    CompareDate compareDate = new CompareDate();
    
    String establishedYear = separation.SeparateYear(articleInfo.getEstablishedDate());
    String establishedMonth = separation.SeparateMonth(articleInfo.getEstablishedDate());
    String establishedDay = separation.SeparateDay(articleInfo.getEstablishedDate());
    
    request.setCharacterEncoding("utf-8");
%>
<jsp:include page="/WEB-INF/jsp/header/public_header.jsp">
  <jsp:param name="title" value="<%= articleInfo.getArticle_name() %>" />
  <jsp:param name="type" value="website" />
  <jsp:param name="url" value="/article?articleId=<%=articleInfo.getArticleId()%>" />
  <jsp:param name="img" value="/showEyecatch?imgId=<%=articleInfo.getImg_id()%>" />
</jsp:include>

<div class="btn-event-application" id="event-application">
  <a href="https://aws-warehouse58th.com/index" target="_blank" rel="noopener noreferrer"><p class="btn-event-application-in">back to top</p></a>
</div><!-- /btn-event-application-->
<div class="area-event-hero"><!-- The area composed by the eye-catch image -->
  <section>
    <h2 class="hidden">top image</h2>
    <div class="sec-clear-img">
      <div class="img-clear-top" style="background-image: url(./showEyecatch?imgId=${ articleInfo.getImg_id() })">

      <%
        if(articleInfo.getCategory() != null && articleInfo.getCategory().equals("limited")){
      %>
          <div class="tag-event-classification">Limited</div>
      <%
        }
      %>
      	  <div class="tag-event-date">
      	  	Est.&nbsp;&nbsp;
            <%= establishedYear %>/
            <%= establishedMonth %>/
            <%= establishedDay %>
	      </div>
        </div>
      </div>
    <div class="img-fuzzy-top" style="background-image: url(./showEyecatch?imgId=${ articleInfo.getImg_id() })"> </div>
  </section>
</div><!-- /area-event-hero -->

<article class="contents">
  <h2 class="ttl-event"><%= articleInfo.getArticle_name() %></h2>
  <div class="userRegisterUser-contents  clearfix">
    <section>
    <h3 class="hidden">Article information</h3>
    <div class="event-contents-left">
      <section>
        <h4 class="hidden">Article general information</h4>
          <a href="./user_detail?userId=<%=registeredUserInfo.getRegisteredUserId()%>" target="blank" rel="noopener noreferrer">
		    <div class="txt-event-detail">
			  <img class="img-event-user" src="./getProfileImageById?selfRegisteredUserId=<%= userRegisterUserInfo.getId() %>" alt="Author profile image">
			  <div class="txt-event-user">
			    <p class="txt-job-title">Author</p>
			    <p class="txt-user-name"><strong><%= articleInfo.getUser() %></strong></p>
              </div><!-- /txt-event-user -->
            </div><!-- /txt-event-detail -->
          </a>
          <ul class="list-event-basic">
            <li>CEO : <%= articleInfo.getCeo() %></li>
            <li>Capital : <%= articleInfo.getCapital() %></li>
            <li>Number of employee : <%= articleInfo.getEmployeeNumber() %></li>
            <li>Established :
              <%= establishedYear %>/
              <%= establishedMonth %>/
              <%= establishedDay %>
            </li>
            <li><strong><a href="mailto:<%= articleInfo.getCompany_mail() %>"><%= articleInfo.getCompany_mail() %></a></strong></li>
            <li>Phone : <%= articleInfo.getCompany_phone() %></li>
            <li>Web site : <strong><a href="<%= articleInfo.getCompany_url() %>" target="blank"><%= articleInfo.getCompany_url() %></a></strong></li>
          </ul><!-- /list-event-basic -->
          <ul class="list-event-img">
            <li style="background-image: url(./showArticleImg01?imgId=${ articleInfo.getImg_id() })"></li>
            <li style="background-image: url(./showArticleImg02?imgId=${ articleInfo.getImg_id() })"></li>
            <li style="background-image: url(./showArticleImg03?imgId=${ articleInfo.getImg_id() })"></li>
            <li style="background-image: url(./showArticleImg04?imgId=${ articleInfo.getImg_id() })"></li>
          </ul><!-- /list-article-img -->
            <h4 class="ttl-event-explanation">Detail</h4>
            <p class="txt-event-explanation open" aria-expanded="false" style="width: 94%;"><%= articleInfo.getIntroduction() %></p>
          </section>
        </div><!-- /event-contents-left -->

        <div class="event-contents-right">
          <nav class="nav-event">
            <p class="txt-event-access">Headquarter address</p>
            <div class="nav-event-in">
              <p class="txt-event-address"><%= articleInfo.getCompany_addr() %></p>

              <!-- Show Google map -->
			  <script type="text/javascript">
			    function initMap() {

			      var target = document.getElementById('map'); //Assign the factor to show the map
				  var address = '<%= articleInfo.getCompany_addr() %>'; //Assign the address
				  var geocoder = new google.maps.Geocoder();

				  geocoder.geocode({ address: address }, function(results, status){
				    if (status === 'OK' && results[0]){
				      console.log("The address is fine.");
				      console.log(results[0].geometry.location);

				       var map = new google.maps.Map(target, {
				         center: results[0].geometry.location,
				         zoom: 13
				       });

				       var marker = new google.maps.Marker({
				         position: results[0].geometry.location,
				         map: map,
				         animation: google.maps.Animation.DROP
				       });
				    }else{
				      //if the address does not exist
				      console.log("The address does not exist.");
				      target.style.display='none';
				    }
				  });
				}
			  </script>
			  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD4yfQbnFiKL06l7BUffsj_E2aBuZw5lts&callback=initMap" async defer></script>

			  <!-- The factor to show the map. -->
              <div class="map-event" id="map"></div>
            </div><!-- /nav-event-in -->
          </nav><!-- /nav-event -->
        </div><!-- /event-contents-right -->
      </section>
    </div><!-- /-contents-left -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/public_footer.jsp"></jsp:include>
</body></html>
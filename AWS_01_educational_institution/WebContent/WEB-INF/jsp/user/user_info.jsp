<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.ImgAddress"%>
<%@ page import="java.util.List"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="javax.imageio.ImageIO"%>
<%
	Admin adminLogin = null;
	if(session.getAttribute("admin") != null){
		adminLogin = (Admin) session.getAttribute("admin");	
	}
	AdminRegisterUser registeredUser = (AdminRegisterUser) session.getAttribute("registeredUser");
	UserRegisterUser selfRegisterUser = (UserRegisterUser) session.getAttribute("selfRegisterUser");
	request.setCharacterEncoding("utf-8");
%>
	<jsp:include page="/WEB-INF/jsp/header/admin_header.jsp"><jsp:param name="title" value="User information" /></jsp:include>
	  <article class="contents clearfix">
		<div class="contents-ttl">
		  <section class="sec-ttl">
			<h2 class="sec-ttl-in">User information</h2>
			<section>
	          <h3 class="hidden"></h3>
	          <a href="/user/user_release">
				<div class="box-identity-wrap">
				  <div class="box-identity-sentence">
					<div class="txt-user-name"><%= registeredUser.getUser_last_name() %><%= registeredUser.getUser_first_name() %></div>
		            <%
		            	if(selfRegisterUser.getReleased().equals("yes")){
		            %>
		                  <div class="txt-release">Released</div>
		            <%
		                }
		                else {
		            %>
		                  <div class="txt-non-release">Private</div>
		            <%
		                }
		            %>
		            </div><!-- /box-identity-sentence -->
		            <div class="img-user" style="background-image: url(../getProfileImageById?selfRegisteredUserId=<%= selfRegisterUser.getId() %>)"></div>
	              </div><!-- /box-identity-wrap -->
	          </a>
	        </section>
		  </section><!-- /sec-ttl -->
		</div><!-- /contents-ttl -->

	    <div class="contents-main">
	      <div class="contents-main-main">
	        <section class="sec-user-info-01">
	          <h3 class="ttl-register-user">General information</h3>

	          <ul class="form-cmn-01-wrap">
	            <li class="list-basic-01">
	              <p class="ttl-user-info">Family name</p>
	              <span class="txt-user-info"><%= registeredUser.getUser_last_name() %></span>
	            </li><!-- /list-basic-01 -->
	            <li class="list-basic-01">
	              <p class="ttl-user-info">First name</p>
	              <span class="txt-user-info"><%= registeredUser.getUser_first_name() %></span>
	            </li><!-- /list-basic-01 -->
	          </ul><!-- /form-cmn-01-wrap -->
	              
	          <ul class="form-cmn-01-wrap">
	            <li class="list-basic-02">
	              <p class="ttl-user-info">E-mail address</p>
	              <span class="txt-user-info"><%= registeredUser.getUser_mail() %></span>
	            </li><!-- /list-basic-02 -->
	          </ul><!-- /form-cmn-01-wrap -->
	        </section><!-- /sec-user-info-01 -->

	        <section>
	          <h3 class="ttl-register-user">User profile</h3>
	          <div class="ttl-register-user-in">
	            <p class="ttl-user-info">Gender</p>
	            <%
	              if(selfRegisterUser.getGender_profile().equals("female")){
	            %>
	              <label class="txt-user-info">Female</label>
	            <%
	              }
	              else if(selfRegisterUser.getGender_profile().equals("male")){
	            %>
	              <label class="txt-user-info">Male</label>
	            <%
	              }
	              else if(selfRegisterUser.getGender_profile().equals("unknown")){
	            %>
	              <label class="txt-user-info">Unknown</label>
	            <%
	              }
	            %>

		        <div class="form-cmn-01">
                  <p class="ttl-user-info">E-mail for public contact</p>
                  <span class="txt-user-info"><%= selfRegisterUser.getOpenMail() %></span>
                </div><!-- /form-cmn-01 -->

	            <div class="form-cmn-01">
	              <p class="ttl-user-info">Phone number</p>
	              <span class="txt-user-info"><%= selfRegisterUser.getPhone() %></span>
	            </div><!-- /form-cmn-01 -->

	            <div class="form-cmn-02">
	              <p class="ttl-user-info">Web site (Less than 100 characters)</p>
	              <a href="<%= selfRegisterUser.getWeb_site() %>"><strong class="txt-user-info-03"><%= selfRegisterUser.getWeb_site() %></strong></a>
	            </div><!-- /form-cmn-01 -->

	          </div><!-- /ttl-register-user-in -->
	        </section>
	      </div><!-- /contents-main-main -->

	      <div class="contents-main-sub">
	        <section>
	          <h3 class="ttl-register-user">User image</h3>
              <div class="form-registration-img">
                <div class="img-preview">
	              <img src="../getProfileImageById?selfRegisteredUserId=<%= selfRegisterUser.getId() %>" alt="User image" /></div>
	              <div class="btn-user-img">
	              <%
                  	if(adminLogin != null){
                  %>
	              		<a href="../admin/modification_user_02?userId=<%=registeredUser.getRegisteredUserId()%>">
	              <%
                    }
                  	else{
                  %>
                     	<a href="../user/modification_02?userId=<%=registeredUser.getRegisteredUserId()%>">
                  <%
                    }
                  %>
                 			<span class="btn-registration-user-01">Image editing</span>
                   		</a>
                  </div><!-- /btn-user-img -->
               </div><!-- /ttl-register-user-in -->
		     </section>
		  </div><!-- /contents-main-sub -->

	      <div class="contents-main-bottom">
	        <section>
	          <h3 class="ttl-register-user">User information</h3>
	          <div class="ttl-register-user-in">
	            <p class="form-registrtion-textarea">
	              <label class="ttl-user-info">Profile</label><br>
	              <span class="txt-user-info"><%= selfRegisterUser.getProfile() %></span>
	            </p>
	          </div><!-- /ttl-register-user-in -->
	        </section>

	        <div class="btn-user-info">
	          <%
		        if(adminLogin != null){
		      %>
					<a href="../admin/suspenssion"><span class="hidden btn-editing-user-01">Suspension</span></a>
	            	<a href="../admin/modification_user_01?userId=<%=registeredUser.getRegisteredUserId()%>">
	          <%
               	}
		        else{
              %>
               		<a href="../previewuser" target="blank"><span class="btn-editing-user-02">Preview</span></a>
               		<a href="../user/modification_01">
              <%
               	}
              %>
	          			<span class="btn-editing-user-03">Edit</span>
	          		</a>
	        </div><!-- /btn-user-info -->
	      </div><!-- /contents-main-bottom -->
		</div><!-- /contents-main -->
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
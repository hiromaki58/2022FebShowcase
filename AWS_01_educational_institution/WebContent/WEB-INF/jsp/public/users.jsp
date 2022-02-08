<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.UserArray"%>
<%@ page import="jp.co.warehouse.dao.user.UserGetUserInfoDAO"%>
<%
	UserArray authorList = new UserArray();
	UserGetUserInfoDAO userGetUserInfoDao = new UserGetUserInfoDAO();
	UserRegisterUser selfRegistereUser = new UserRegisterUser();

	if((UserArray) session.getAttribute("authorList") != null){
		authorList = (UserArray) session.getAttribute("authorList");
	}
	
	request.setCharacterEncoding("utf-8");
%>
	<jsp:include page="/WEB-INF/jsp/header/public_header.jsp"><jsp:param name="title" value="Author list" /></jsp:include>

  	<article class="contents">
      <h2 class="ttl-self-registered-user-top" id="ttl-user-search">Find the authors</h2>
	  <div class="area-self-registered-user">
		<div class="sec-self-registered-user-senior-trainer">
          <section>
			<h3 class="ttl-self-registered-user-sub">List of authors</h3>
			<div class="sec-self-registered-user">
			  <ul class="box-self-registered-user">
              <%
				for (AdminRegisterUser authorInfo : authorList.getUserRecordArray()) {
				selfRegistereUser = userGetUserInfoDao.getSelfRegisteredUserInfo(authorInfo.getUser_mail());
              %>
				<li class="mod-self-registered-user-01">
				  <a href="./user_detail?userId=<%=authorInfo.getRegisteredUserId()%>">
					<img class="img-self-registered-user" src="../getProfileImageById?selfRegisteredUserId=<%= authorInfo.getSelfRegisteredUserId() %>" alt="User image">
					  <div class="box-self-registered-user-detail">
                      	<div class="box-self-registered-user-txt">
                       	  <p class="txt-self-registered-user-name-01">
							<strong><%= authorInfo.getUser_last_name() %>&nbsp;<%= authorInfo.getUser_first_name() %></strong>
                       	  </p>
						</div><!-- /box-self-registered-user-txt -->
                      </div><!-- /box-self-registered-user-detail -->
                   </a>
				 </li><!-- /mod-self-registered-user-01 -->
              <%
				}
              %>
              </ul><!-- /box-self-registered-user -->
          	</div><!-- /sec-self-registered-user -->
       	  </section>
      	</div><!-- /sec-self-registered-user -->
      </div><!-- /area-self-registered-user -->
	</article><!-- /contents -->
  </div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/public_footer.jsp"></jsp:include>
</body></html>
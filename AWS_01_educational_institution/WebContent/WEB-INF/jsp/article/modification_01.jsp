<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%
	Admin adminLogin = (Admin) session.getAttribute("admin");
	AdminRegisterUser userName = (AdminRegisterUser) session.getAttribute("registeredUser");
	Article articleInfo = (Article) session.getAttribute("registeredArticle");

	request.setCharacterEncoding("utf-8");
%>
  <jsp:include page="/WEB-INF/jsp/header/user_header_with_openfixdate.jsp"><jsp:param name="title" value="Article modification" /></jsp:include>
	  <article class="contents">
		<h2 class="hidden">contents</h2>
      	<div class="sec-ttl">
		  <section>
          	<h3 class="sec-ttl-in">Article modification</h3>
		  </section>
		</div><!-- /sec-ttl -->

        <div class="contents-in">
		  <form class="form-user-register-article" id="register_article" name="registerarticle01" action="/article/modification_01" method="post">
        	<div class="sec-register-article-01">
          	  <section>
            	<h3 class="ttl-register-user">Article modification</h3>
            	<div class="area-register-article-01">
              	  <label class="form-registrtion-ttl-01" for="article_title">Article title<span class="ttl-register-must">[required]</span></label><br>
              	  <span class="ttl-register-must" id="article_title_warning">[Please input the article title.]</span>
              	  <input class="form-registrtion-input-07" id="article_title" type="text" pattern="[^\x22\x27]*" name="article_title" placeholder="Article title" style="display: block;"
                  <c:if test = "${articleInfo.getArticle_name() != null}">
                  	value="<%= articleInfo.getArticle_name() %>"
                  </c:if>
              	  >
              
              <%
                if(adminLogin != null){
              %>
	              <div class="form-weight-registration">
	                <p class="ttl-span-general-01">Recommendation</p>
	                <select class="form-weight-pulldown" name="weight">
	                  <option value="7"
	                    <%
	                      if(articleInfo != null && articleInfo.getWeight() == 0 ){%>
	                        selected="selected"
	                    <% } %>
	                  >Please select</option>
	                  <option value="1"
	                    <%
	                      if(articleInfo != null && articleInfo.getWeight() == 1 ){%>
	                        selected="selected"
	                    <% } %>
	                  >1</option>
	                  <option value="2"
	                    <%
	                      if(articleInfo != null && articleInfo.getWeight() == 2 ){%>
	                        selected="selected"
	                    <% } %>
	                  >2</option>
	                  <option value="3"
	                    <%
	                      if(articleInfo != null && articleInfo.getWeight() == 3 ){%>
	                        selected="selected"
	                    <% } %>
	                  >3</option>
	                  <option value="4"
	                    <%
	                      if(articleInfo != null && articleInfo.getWeight() == 4 ){%>
	                        selected="selected"
	                    <% } %>
	                  >4</option>
	                  <option value="5"
	                    <%
	                      if(articleInfo != null && articleInfo.getWeight() == 5 ){%>
	                        selected="selected"
	                    <% } %>
	                  >5</option>
	                  <option value="6"
	                    <%
	                      if(articleInfo != null && articleInfo.getWeight() == 6 ){%>
	                        selected="selected"
	                    <% } %>
	                  >6</option>
					</select><!-- /form-weight-pulldown -->
				  </div><!-- /form-weight-registration -->
	
				  <div class="form-classification-registration-02">
					<p class="ttl-span-general-01">Category</p>
	                <select class="form-classification-pulldown" name="category">
	                  <option value="private"
	                    <c:if test = "${articleInfo.getCategory().equals('private')}">
	                       selected="selected"
	                    </c:if>
	                  >Private</option>
	                  <option value="public"
		                  <c:if test = "${articleInfo.getCategory().equals('public')}">
		                     selected="selected"
		                  </c:if>
	                  >Public</option>
	                  <option value="not_specified"
	                    <c:if test = "${articleInfo.getCategory().equals('not_specified')}">
	                        selected="selected"
	                    </c:if>
	                  >Not specified</option>
					</select><!-- /form-classification-pulldown -->
				  </div><!-- /form-classification-registration-02 -->
              <%
                }
              %>

              <label class="form-registrtion-ttl-01">Viewable period<span class="ttl-register-must">[required]</span></label><br>
              <span class="ttl-register-must" id="opening_day_warning">[Please enter the application date.]</span>

              <div class="form-date-registration-01" style="display: block;">
                <span class="ttl-span-general-01">Open date</span>
                <input class="form-date-calendar" id="opening_day" type="date" min="1900-01-01" max="2100-12-31" name="opening_day"
                  <c:if test = "${articleInfo.getOpening_day() != null}">
                      value = <%= articleInfo.getOpening_day() %>
                  </c:if>
                >
                <select class="form-date-pulldown" name="opening_hour">
                 <option value="0"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 0){%>
                        selected="selected"
                    <% } %>
                  >0時</option>
                  <option value="1"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 1 ){%>
                        selected="selected"
                    <% } %>
                  >1</option>
                  <option value="2"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 2 ){%>
                        selected="selected"
                    <% } %>
                  >2</option>
                  <option value="3"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 3 ){%>
                        selected="selected"
                    <% } %>
                  >3</option>
                  <option value="4"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 4 ){%>
                        selected="selected"
                    <% } %>
                  >4</option>
                  <option value="5"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 5 ){%>
                        selected="selected"
                    <% } %>
                  >5</option>
                  <option value="6"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 6 ){%>
                        selected="selected"
                    <% } %>
                  >6</option>
                  <option value="7"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 7 ){%>
                        selected="selected"
                    <% } %>
                  >7</option>
                  <option value="8"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 8 ){%>
                        selected="selected"
                    <% } %>
                  >8</option>
                  <option value="9"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 9 ){%>
                        selected="selected"
                    <% } %>
                  >9</option>
                  <option value="10"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 10 ){%>
                        selected="selected"
                    <% } %>
                  >10</option>
                  <option value="11"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 11 ){%>
                        selected="selected"
                    <% } %>
                  >11</option>
                  <option value="12"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 12 ){%>
                        selected="selected"
                    <% } %>
                  >12</option>
                  <option value="13"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 13 ){%>
                        selected="selected"
                    <% } %>
                  >13</option>
                  <option value="14"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 14 ){%>
                        selected="selected"
                    <% } %>
                  >14</option>
                  <option value="15"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 15 ){%>
                        selected="selected"
                    <% } %>
                  >15</option>
                  <option value="16"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 16 ){%>
                        selected="selected"
                    <% } %>
                  >16</option>
                  <option value="17"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 17 ){%>
                        selected="selected"
                    <% } %>
                  >17</option>
                  <option value="18"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 18 ){%>
                        selected="selected"
                    <% } %>
                  >18</option>
                  <option value="19"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 19 ){%>
                        selected="selected"
                    <% } %>
                  >19</option>
                  <option value="20"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 20 ){%>
                        selected="selected"
                    <% } %>
                  >20</option>
                  <option value="21"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 21 ){%>
                        selected="selected"
                    <% } %>
                  >21</option>
                  <option value="22"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 22 ){%>
                        selected="selected"
                    <% } %>
                  >22</option>
                  <option value="23"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_hour() == 23 ){%>
                        selected="selected"
                    <% } %>
                  >23</option>
                </select>
                <select class="form-date-pulldown" name="opening_min">
                  <option value="00"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_min() == 00 ){%>
                        selected="selected"
                    <% } %>
                  >0</option>
                  <option value="15"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_min() == 15 ){%>
                        selected="selected"
                    <% } %>
                  >15</option>
                  <option value="30"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_min() == 30 ){%>
                        selected="selected"
                    <% } %>
                  >30</option>
                  <option value="45"
                    <%
                      if(articleInfo != null && articleInfo.getOpening_min() == 45 ){%>
                        selected="selected"
                    <% } %>
                  >45</option>
                </select>
              </div><!-- /form-date-registration-01 -->

              <div class="form-date-registration-01">
                <span class="ttl-span-general-01">Closing date</span>
                <input class="form-date-calendar" type="date" min="1900-01-01" max="2100-12-31" name="closing_day"
                  <c:if test = "${articleInfo.getClosing_day() != null}">
                      value = <%= articleInfo.getClosing_day() %>
                  </c:if>
                >
                <select class="form-date-pulldown" name="closing_hour">
                  <option value="0"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 0){%>
                        selected="selected"
                    <% } %>
                  >0</option>
                  <option value="1"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 1 ){%>
                        selected="selected"
                    <% } %>
                  >1</option>
                  <option value="2"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 2 ){%>
                        selected="selected"
                    <% } %>
                  >2</option>
                  <option value="3"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 3 ){%>
                        selected="selected"
                    <% } %>
                  >3</option>
                  <option value="4"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 4 ){%>
                        selected="selected"
                    <% } %>
                  >4</option>
                  <option value="5"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 5 ){%>
                        selected="selected"
                    <% } %>
                  >5</option>
                  <option value="6"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 6 ){%>
                        selected="selected"
                    <% } %>
                  >6</option>
                  <option value="7"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 7 ){%>
                        selected="selected"
                    <% } %>
                  >7</option>
                  <option value="8"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 8 ){%>
                        selected="selected"
                    <% } %>
                  >8</option>
                  <option value="9"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 9 ){%>
                        selected="selected"
                    <% } %>
                  >9</option>
                  <option value="10"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 10 ){%>
                        selected="selected"
                    <% } %>
                  >10</option>
                  <option value="11"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 11 ){%>
                        selected="selected"
                    <% } %>
                  >11</option>
                  <option value="12"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 12 ){%>
                        selected="selected"
                    <% } %>
                  >12</option>
                  <option value="13"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 13 ){%>
                        selected="selected"
                    <% } %>
                  >13</option>
                  <option value="14"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 14 ){%>
                        selected="selected"
                    <% } %>
                  >14</option>
                  <option value="15"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 15 ){%>
                        selected="selected"
                    <% } %>
                  >15</option>
                  <option value="16"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 16 ){%>
                        selected="selected"
                    <% } %>
                  >16</option>
                  <option value="17"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 17 ){%>
                        selected="selected"
                    <% } %>
                  >17</option>
                  <option value="18"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 18 ){%>
                        selected="selected"
                    <% } %>
                  >18</option>
                  <option value="19"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 19 ){%>
                        selected="selected"
                    <% } %>
                  >19</option>
                  <option value="20"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 20 ){%>
                        selected="selected"
                    <% } %>
                  >20</option>
                  <option value="21"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 21 ){%>
                        selected="selected"
                    <% } %>
                  >21</option>
                  <option value="22"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 22 ){%>
                        selected="selected"
                    <% } %>
                  >22</option>
                  <option value="23"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_hour() == 23 ){%>
                        selected="selected"
                    <% } %>
                  >23</option>
                </select>
                <select class="form-date-pulldown" name="closing_min">
                  <option value="00"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_min() == 00 ){%>
                        selected="selected"
                    <% } %>
                  >0</option>
                  <option value="15"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_min() == 15 ){%>
                        selected="selected"
                    <% } %>
                  >15</option>
                  <option value="30"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_min() == 30 ){%>
                        selected="selected"
                    <% } %>
                  >30</option>
                  <option value="45"
                    <%
                      if(articleInfo != null && articleInfo.getClosing_min() == 45 ){%>
                        selected="selected"
                    <% } %>
                  >45</option>
                </select>
                <br><br>
              </div><!-- /form-date-registration-01 -->
            </div><!-- /area-register-article-01 -->
          </section>
        </div><!-- /sec-register-article-01 -->

        <div class="sec-register-article">
          <section>
            <h3 class="ttl-register-user">User information</h3>
            <div class="area-register-article-01">
			  <span class="form-registrtion-input-01">
                <%= userName.getUser_last_name() %>
                <%= userName.getUser_first_name() %>
              </span>
            </div><!-- /area-register-article-01 -->
          </section>
        </div><!-- /sec-register-article -->

        <div class="sec-register-article">
          <section>
            <h3 class="ttl-register-user">Article detail</h3>
            <div class="area-register-article-01">
              <label class="form-registrtion-ttl-01" id="ceo">CEO</label><br>
              <input class="form-registrtion-input-07" type="text" name="ceo" placeholder="CEO"
                <c:if test = "${articleInfo.getCeo() != null}">
                    value = <%= articleInfo.getCeo() %>
                </c:if>
              ><br>
              <%= articleInfo.getCeo() %>
              <label class="form-registrtion-ttl-01" id="company_addr">Address</label><br>
              <input class="form-registrtion-input-07" type="text" pattern="[^\x22\x27]*" name="company_addr" placeholder="Address"
                <c:if test = "${articleInfo.getCompany_addr() != null}">
                    value = <%= articleInfo.getCompany_addr()%>
                </c:if>
              ><br>
              <label class="form-registrtion-ttl-01" id="capital">Capital</label><br>
              <input class="form-registrtion-input-08" type="text" pattern="[^\x22\x27]*" name="capital" placeholder="example：$8000"
                <c:if test = "${articleInfo.getCapital() != null}">
                    value = <%= articleInfo.getCapital() %>
                </c:if>
              ><br>
              <label class="form-registrtion-ttl-01" id="employeeNumber">Number of employee</label><br>
              <input class="form-registrtion-input-08" type="text" pattern="^[0-9A-Za-z]+$" name="employeeNumber" placeholder="example：10"
                <c:if test = "${articleInfo.getEmployeeNumber() != null}">
                    value = <%= articleInfo.getEmployeeNumber() %>
                </c:if>
              ><br>
              <div class="form-date-registration-02">
                <label class="ttl-span-general-02" for="establishedDate">Established Date</label><br>
                <input class="form-date-calendar" id="establishedDate" type="date" min="1300-01-01" max="2100-12-31" placeholder="yyyy/MM/dd" name="establishedDate" style="display: block;"
                  <c:if test = "${articleInfo.getEstablishedDate() != null}">
                    value = <%= articleInfo.getEstablishedDate() %>
                  </c:if>
                >
              </div><!-- /form-date-registration -->
              <label class="form-registrtion-ttl-01" id="company_mail">E-mail address</label><br>
              <input class="form-registrtion-input-07" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="company_mail" placeholder="E-mail address"
                <c:if test = "${articleInfo.getCompany_mail() != null}">
                  value = <%= articleInfo.getCompany_mail() %>
                </c:if>
              ><br>
              <label class="form-registrtion-ttl-01">Phone number</label><br>
              <input class="form-registrtion-input-07" type="tel" name="company_phone" placeholder="Phone number"
                <c:if test = "${articleInfo.getCompany_phone() != null}">
                  value = <%= articleInfo.getCompany_phone() %>
                </c:if>
              ><br>
              <label class="form-registrtion-ttl-01" id="company_url">URL</label><br>
              <input class="form-registrtion-input-07" type="text" pattern="https?://.+" name="company_url" placeholder="URL"
                <c:if test = "${articleInfo.getCompany_url() != null}">
                  value = <%= articleInfo.getCompany_url() %>
                </c:if>
              ><br>
              <p class="form-registrtion-ttl-01" for="article-introduction">Article introduction<span class="ttl-register-must">[required]</span></p>
              <textarea class="form-article-introduction" id="article-introduction" name="article-introduction" onkeyup="indicatingNumberOfCharacter(value);">
                <c:if test="${articleInfo.getIntroduction() != null}">
                  <%= articleInfo.getIntroduction().replaceAll("<br/>", "\n") %>
                </c:if>
              </textarea>
              <p class="form-registrtion-ttl-01" id="inputlength">The number of character is : </p>
              <span class="ttl-register-warning" id="article-introduction_warning">[Please enter the article introduction.]</span><br>
              <span class="ttl-register-warning" id="article-introduction_charactor_number_warning">[Number of character exceeds the limit.]</span>
            </div><!-- /area-register-article-01 -->
          </section>
        </div><!-- /sec-register-article -->

		<div class="btn-registration-user">
		  <div class="ttl-register-must" id="register_article_warning">[Required item is missing, or number of character exceeds the limit.]</div>
		  <!-- This hidden input passes the article ID to tell which article will be modified. -->
          <input type="text" class="hidden" name="articleId" value="<%= articleInfo.getArticleId() %>">
          <input class="btn-registration-user-01" type="submit" value="Register">
		</div><!-- /btn-registration-user -->
	  </form><!-- /form-admin-register-user -->
    </div><!-- /contents-in -->
  </article><!-- /contents -->
</div><!-- /wrapper -->

<jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
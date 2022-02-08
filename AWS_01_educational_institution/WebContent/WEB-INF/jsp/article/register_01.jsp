<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="jp.co.warehouse.entity.AdminRegisterUser"%>
<%@ page import="jp.co.warehouse.entity.Admin"%>
<%@ page import="jp.co.warehouse.entity.Article"%>
<%@ page import="jp.co.warehouse.date.SeparateFromDate"%>
<%
  AdminRegisterUser registeredUserForArticle = (AdminRegisterUser) session.getAttribute("registeredUserForArticle");
  Article newArticle = new Article();
  newArticle.setOpening_hour(88);
  newArticle.setClosing_hour(88);

  String open_day = "";
  String close_day = "";
  String established_day = "";

  if((Article) session.getAttribute("newArticle") != null){
	  newArticle = (Article) session.getAttribute("newArticle");
	  open_day = new SimpleDateFormat("yyyy-MM-dd").format(newArticle.getOpening_day());
	  close_day = new SimpleDateFormat("yyyy-MM-dd").format(newArticle.getClosing_day());
	  established_day = new SimpleDateFormat("yyyy-MM-dd").format(newArticle.getEstablishedDate());
  }

  request.setCharacterEncoding("utf-8");
%>
    <jsp:include page="/WEB-INF/jsp/header/user_header_with_openfixdate.jsp"><jsp:param name="title" value="Article registration - Step1" /></jsp:include>

    <article class="contents">
      <h2 class="hidden">Contents</h2>
      <div class="sec-ttl">
        <section>
          <h2 class="sec-ttl-in">Article registration - Step1</h2>
          <h4 class="ttl-register-article-01">Required input is needed to register the article.</h4>
        </section>
      </div><!-- /sec-ttl -->

      <div class="contents-in">
        <form class="form-user-register-article" id="register_article" name="registerarticle01" action="/article/register_01" method="post">
          <div class="sec-register-article-01">
            <section>
              <h3 class="ttl-register-user">Article general information</h3>
              <div class="area-register-article-01">
                <label class="form-registrtion-ttl-01" for="article_title">Article title<span class="ttl-register-must">[required]</span></label><br>
                <span class="ttl-register-warning" id="article_title_warning">[Please input the article title.]</span>
                <input class="form-registrtion-input-07" id="article_title" type="text" pattern="[^\x22\x27]*" name="article_title" placeholder="Article title"
                  <c:if test = "${newArticle.getArticle_name() != null}">
                  	value = <%= newArticle.getArticle_name() %>
                  </c:if>
                >
                         
                <%
                if((Admin) session.getAttribute("admin") != null){
                %>
                  <div class="form-classification-registration-02">
                    <p class="ttl-span-general-01">Category</p>
                    <select class="form-classification-pulldown" name="classification">
                      <option value="not_specified" selected="selected">Please select</option>
                      <option value="private"
                        <c:if test = "${newArticle.getCategory().equals('private')}">
                          selected="selected"
                        </c:if>
                      >Private</option>
                      <option value="public"
                        <c:if test = "${newArticle.getCategory().equals('public')}">
                          selected="selected"
                        </c:if>
                      >Public</option>
                      <option value="not_specified"
                        <c:if test = "${newArticle.getCategory().equals('not_specified')}">
                      	  selected="selected"
                  		</c:if>
                      >Not specified</option>
                    </select><!-- /fform-classification-pulldown -->
                  </div><!-- /form-classification-registration-02 -->
                 <%
                 }
                 %>
                <br>                   
                <label class="form-registrtion-ttl-01">Post period<span class="ttl-register-must">[required]</span></label>
                <span class="ttl-register-warning" id="opening_day_warning">[Please select the post period.]</span><br>

                <div class="form-date-registration-01">
                  <span class="ttl-span-general-01">Open date</span>
                  <input class="form-date-calendar" id="opening_day" type="date" name="opening_day" min="1900-01-01" max="2100-12-31" placeholder="yyyy-MM-dd"
                    <c:if test = "${newArticle.getOpening_day() != null}">
                 	  value = '<%= open_day %>'
                   </c:if>
                  >
                  <select class="form-date-pulldown" name="opening_hour">
                    <option value="0"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 0){%>
                    selected="selected"
                <% } %>
                    >0</option>
                    <option value="1"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 1 ){%>
                    selected="selected"
                <% } %>
                    >1</option>
                    <option value="2"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 2 ){%>
                    selected="selected"
                <% } %>
                    >2</option>
                    <option value="3"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 3 ){%>
                    selected="selected"
                <% } %>
                    >3</option>
                    <option value="4"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 4 ){%>
                    selected="selected"
                <% } %>
                    >4</option>
                    <option value="5"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 5 ){%>
                    selected="selected"
                <% } %>
                    >5</option>
                    <option value="6"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 6 ){%>
                    selected="selected"
                <% } %>
                    >6</option>
                    <option value="7"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 7 ){%>
                    selected="selected"
                <% } %>
                    >7</option>
                    <option value="8"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 8 ){%>
                    selected="selected"
                <% } %>
                    >8</option>
                    <option value="9"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 9 ){%>
                    selected="selected"
                <% } %>
                    >9</option>
                    <option value="10"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 10 ){%>
                    selected="selected"
                <% } %>
                    >10</option>
                    <option value="11"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 11 ){%>
                    selected="selected"
                <% } %>
                    >11</option>
                    <option value="12"
                <%
                      if(newArticle != null && newArticle.getOpening_hour() == 88){%>
                       	selected="selected"
				<%
                      }
                      else if(newArticle != null && newArticle.getOpening_hour() == 12 ) {
				%>
						selected="selected"
				<% } %>
                    >12</option>
                    <option value="13"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 13 ){%>
                    selected="selected"
                <% } %>
                    >13</option>
                    <option value="14"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 14 ){%>
                    selected="selected"
                <% } %>
                    >14</option>
                    <option value="15"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 15 ){%>
                    selected="selected"
                <% } %>
                    >15</option>
                    <option value="16"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 16 ){%>
                    selected="selected"
                <% } %>
                    >16</option>
                    <option value="17"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 17 ){%>
                    selected="selected"
                <% } %>
                    >17</option>
                    <option value="18"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 18 ){%>
                    selected="selected"
                <% } %>
                    >18</option>
                    <option value="19"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 19 ){%>
                    selected="selected"
                <% } %>
                    >19</option>
                    <option value="20"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 20 ){%>
                    selected="selected"
                <% } %>
                    >20</option>
                    <option value="21"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 21 ){%>
                    selected="selected"
                <% } %>
                    >21</option>
                    <option value="22"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 22 ){%>
                    selected="selected"
                <% } %>
                    >22</option>
                    <option value="23"
				<%
                  if(newArticle != null && newArticle.getOpening_hour() == 23 ){%>
                    selected="selected"
                <% } %>
                    >23</option>
                  </select>
                  <select class="form-date-pulldown" name="opening_min">
                    <option value="00" selected="selected"
				<%
                  if(newArticle != null && newArticle.getOpening_min() == 00 ){%>
                    selected="selected"
                <% } %>
                    >0</option>
                    <option value="15"
				<%
                  if(newArticle != null && newArticle.getOpening_min() == 15 ){%>
                    selected="selected"
                <% } %>
                    >15</option>
                    <option value="30"
				<%
                  if(newArticle != null && newArticle.getOpening_min() == 30 ){%>
                    selected="selected"
                <% } %>
                    >30</option>
                    <option value="45"
				<%
                  if(newArticle != null && newArticle.getOpening_min() == 45 ){%>
                    selected="selected"
                <% } %>
                    >45</option>
                  </select>
                </div><!-- /form-date-registration-01 -->

                <div class="form-date-registration-01">
                  <span class="ttl-span-general-01">Closing date</span>
                  <input class="form-date-calendar" type="date" name="closing_day" min="1900-01-01" max="2100-12-31" placeholder="yyyy-MM-dd"
                    <c:if test = "${newArticle.getClosing_day() != null}">
                 	value = '<%= close_day %>'
                 </c:if>
                  >
                  <select class="form-date-pulldown" name="closing_hour">
                    <option value="00"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 0){%>
                    selected="selected"
                <% } %>
                    >0</option>
                    <option value="1"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 1 ){%>
                    selected="selected"
                <% } %>
                    >1</option>
                    <option value="2"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 2 ){%>
                    selected="selected"
                <% } %>
                    >2</option>
                    <option value="3"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 3 ){%>
                    selected="selected"
                <% } %>
                    >3</option>
                    <option value="4"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 4 ){%>
                    selected="selected"
                <% } %>
                    >4</option>
                    <option value="5"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 5 ){%>
                    selected="selected"
                <% } %>
                    >5</option>
                    <option value="6"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 6 ){%>
                    selected="selected"
                <% } %>
                    >6</option>
                    <option value="7"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 7 ){%>
                    selected="selected"
                <% } %>
                    >7</option>
                    <option value="8"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 8 ){%>
                    selected="selected"
                <% } %>
                    >8</option>
                    <option value="9"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 9 ){%>
                    selected="selected"
                <% } %>
                    >9</option>
                    <option value="10"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 10 ){%>
                    selected="selected"
                <% } %>
                    >10</option>
                    <option value="11"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 11 ){%>
                    selected="selected"
                <% } %>
                    >11</option>
                    <option value="12"
				<%
                  	  if(newArticle != null && newArticle.getClosing_hour() == 88){%>
                  		selected="selected"
                <%
                  }
                  	  else if(newArticle != null && newArticle.getClosing_hour() == 12 ) {
                %>
                    	selected="selected"
                <% } %>
                    >12</option>
                    <option value="13"
				<%
                  	  if(newArticle != null && newArticle.getClosing_hour() == 13 ){%>
                    	selected="selected"
                <% } %>
                    >13</option>
                    <option value="14"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 14 ){%>
                    selected="selected"
                <% } %>
                    >14</option>
                    <option value="15"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 15 ){%>
                    selected="selected"
                <% } %>
                    >15</option>
                    <option value="16"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 16 ){%>
                    selected="selected"
                <% } %>
                    >16</option>
                    <option value="17"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 17 ){%>
                    selected="selected"
                <% } %>
                    >17</option>
                    <option value="18"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 18 ){%>
                    selected="selected"
                <% } %>
                    >18</option>
                    <option value="19"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 19 ){%>
                    selected="selected"
                <% } %>
                    >19</option>
                    <option value="20"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 20 ){%>
                    selected="selected"
                <% } %>
                    >20</option>
                    <option value="21"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 21 ){%>
                    selected="selected"
                <% } %>
                    >21</option>
                    <option value="22"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 22 ){%>
                    selected="selected"
                <% } %>
                    >22</option>
                    <option value="23"
                      <%
                  if(newArticle != null && newArticle.getClosing_hour() == 23 ){%>
                    selected="selected"
                <% } %>
                    >23</option>
                  </select>
                  <select class="form-date-pulldown" name="closing_min">
                    <option value="00" selected="selected"
                      <%
                  if(newArticle != null && newArticle.getClosing_min() == 00 ){%>
                    selected="selected"
                <% } %>
                    >0</option>
                    <option value="15"
                      <%
                  if(newArticle != null && newArticle.getClosing_min() == 15 ){%>
                    selected="selected"
                <% } %>
                    >15</option>
                    <option value="30"
                      <%
                  if(newArticle != null && newArticle.getClosing_min() == 30 ){%>
                    selected="selected"
                <% } %>
                    >30</option>
                    <option value="45"
                      <%
                  if(newArticle != null && newArticle.getClosing_min() == 45 ){%>
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
              	<%
                  if((Admin) session.getAttribute("admin") != null){
                %>
                   <p class="form-registrtion-ttl-01">User information</p>
                   <input class="form-registrtion-input-06" type="text" name="user_list"
                    <c:if test = "${newArticle.getUser() != null}">
                 	  value = <%= newArticle.getUser() %>
              		</c:if>
                   >
                   <p class="form-registrtion-ttl-01">Article registered person</p>
                <%
                  }
                %>
                <%
                  if(registeredUserForArticle != null){
                %>
                    <span class="form-registrtion-input-01">
                      <%=registeredUserForArticle.getUser_last_name() %>
                      <%=registeredUserForArticle.getUser_first_name() %>
                    </span>
                <%
                  }
                  else {
                %>
					<span class="form-registrtion-input-01">
					  Warehouse Association
					</span>
				<%
                  }
                %>
              </div><!-- /area-register-article-01 -->
            </section>
          </div><!-- /sec-register-article -->

		  <div class="sec-register-article">
            <section>
			  <h3 class="ttl-register-user">Article detail</h3>
                <div class="area-register-article-01">
				  <p class="form-registrtion-ttl-01">CEO</p>
                  <input class="form-registrtion-input-07" type="text" name="ceo" placeholder="CEO"
					<c:if test = "${newArticle.getCeo() != null}">
               		  value = <%= newArticle.getCeo() %>
           		  	</c:if>
				  >
               	  <br>
                  <p class="form-registrtion-ttl-01">Address</p>
             	  <input class="form-registrtion-input-07" type="text" pattern="[^\x22\x27]*" name="company_addr" placeholder="Address"
             		<c:if test = "${newArticle.getCompany_addr() != null}">
					  value = <%= newArticle.getCompany_addr() %>
            		</c:if>
             	  >
             	  <br>
				  <p class="form-registrtion-ttl-01">Capital</p>
                  <input class="form-registrtion-input-08" type="text" pattern="[^\x22\x27]*" name="capital" placeholder="example：$8000"
                   	<c:if test = "${newArticle.getCapital() != null}">
                 	  value = <%= newArticle.getCapital() %>
             	  	</c:if>
                  >
                  <br>
                  <p class="form-registrtion-ttl-01">Number of employee</p>
                  <input class="form-registrtion-input-08" type="text" pattern="^[0-9A-Za-z]+$" name="employeeNumber" placeholder="example：10"
                   	<c:if test = "${newArticle.getEmployeeNumber() != null}">
                  	  value = <%= newArticle.getEmployeeNumber() %>
             		</c:if>
                  >
                  <div class="form-date-registration-02">
                   	<label class="ttl-span-general-02" for="establishedDate">Established Date</label><br>
                   	<input class="form-date-calendar" id="establishedDate" type="date" name="establishedDate" min="1300-01-01" max="2100-12-31" placeholder="yyyy-MM-dd"
                      <c:if test = "${newArticle.getEstablishedDate() != null}">
                		value = '<%= established_day %>'
               		  </c:if>
                    >
                  </div><!-- /form-date-registration -->

                  <p class="form-registrtion-ttl-01">E-mail address</p>
                  <input class="form-registrtion-input-07" type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="company_mail" placeholder="E-mail address"
					<c:if test = "${newArticle.getCompany_mail() != null}">
              		  value = <%= newArticle.getCompany_mail() %>
            		</c:if>
                  >
                  <br>
                  <p class="form-registrtion-ttl-01">Phone number</p>
                  <input class="form-registrtion-input-07" type="text" name="company_phone" placeholder="Phone number"
					<c:if test = "${newArticle.getCompany_phone() != null}">
             		  value = <%= newArticle.getCompany_phone() %>
           		 	</c:if>
                  >
                  <br>
                  <p class="form-registrtion-ttl-01">URL</p>
                  <input class="form-registrtion-input-07" type="text" pattern="https?://.+" name="company_url" placeholder="URL"
					<c:if test = "${newArticle.getCompany_url() != null}">
					  value = <%= newArticle.getCompany_url() %>
            		</c:if>
                  >
                  <br>
                  <p class="form-registrtion-ttl-01">Article introduction<span class="ttl-register-must">[required]</span></p>
                  <textarea class="form-article-introduction" id="article_introduction" name="article_introduction" onkeyup="indicatingNumberOfCharacter(value);"><%if(newArticle.getIntroduction() != null){%><%= newArticle.getIntroduction().replaceAll("<br/>", "\n") %><%}%></textarea>
                  <p class="form-registrtion-ttl-01" id="inputlength">The number of character is : </p>
                  <span class="ttl-register-warning" id="article_introduction_warning">[Please enter the article.]</span><br>
                  <span class="ttl-register-warning" id="article_introduction_charactor_number_warning">[Number of character exceeds the limit.]</span>
				</div><!-- /area-register-article-01 -->
			  </section>
			</div><!-- /sec-register-article -->

            <div class="btn-registration-user">
              <p class="form-registrtion-ttl-01">
				<div class="ttl-register-warning" id="register_article_warning">[Required item is missing, or number of character exceeds the limit.]</div>
              </p>
              <input class="btn-registration-user-01" type="submit" value="next">
            </div><!-- /btn-registration-user -->
		  </form><!-- /form-admin-register-user -->
		</div><!-- /contents-in -->
	  </article><!-- /contents -->
 	</div><!-- /wrapper -->

  <jsp:include page="/WEB-INF/jsp/footer/admin_footer.jsp"></jsp:include>
</body></html>
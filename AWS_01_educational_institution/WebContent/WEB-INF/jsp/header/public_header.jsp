<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String title = (String)request.getParameter("title");
  String type = (String)request.getParameter("type");
  String url = (String)request.getParameter("url");
  String img = (String)request.getParameter("img");
%>
<!DOCTYPE html>
<html lang="ja" prefix="og: http://ogp.me/ns#">
	<head>
	  <!--This is public_header.jsp.-->
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0">
	<meta name="format-detection" content="telephone=no">
	<title><%= title %> | Warehouse58th</title>
	<meta name="robots" content="noindex">
	<meta name="description" content="Warehouse58th">
	<meta name="keywords" content="">

	<meta property="og:title" content="<%= title %> | Warehouse58th" />
	<meta property="og:type" content="<%= type %>" />
	<meta property="og:url" content="<%= url %>" />
	<meta property="og:image" content="<%= img %>" />

	<link href="https://fonts.googleapis.com/css?family=M+PLUS+1p" rel="stylesheet">
	<link rel="stylesheet" href="/common/css/base.css" media="all">
	<link rel="stylesheet" href="/common/css/user-article.css" media="all">
	<script src="./common/js/jquery.js"></script>
	<script src="./common/js/common.js"></script>
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyD4yfQbnFiKL06l7BUffsj_E2aBuZw5lts"></script>
	<link rel="shortcut icon" type="image/x-icon" href="/common/img/warehouse_favicon.ico">
    <script type="text/javascript" src="/common/js/jquery.min.js"></script>
	<script type="text/javascript" src="/common/js/readmore.js"></script>
	<!-- This script is for extension the seminar introduction part -->
	<script>
	jQuery(function () {
	  $(function() {
	    var w = $(window).width();
	    var x = 1200;
	    if (w <= x) {
		    $('.open').readmore({
		        speed: 750,
		        collapsedHeight: 80,
		        moreLink: '<a class="btn_open" href="#" style="width: 80%;margin: 20px 10%;">Read more</a>',
		        lessLink: '<a class="btn_close" href="#" style="width: 80%;margin: 20px 10%;">Close</a>'
		    });
	  	}
	})
	});
	</script>
	<!-- This is for Google Analytics -->
	<!--<script async src="https://www.googletagmanager.com/gtag/js?id=UA-30468100-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());

	  gtag('config', 'UA-30468100-1');
	</script>-->
	</head>
	<body onload="initMap()">
	<div class="wrapper">
	  <header class="header">
	    <div class="header-in only-sp">
	      <section>
	        <h1 class="header-logo logo-company"><!-- Warehouse58th logo -->
	          <a href="https://aws-warehouse58th.com/index"><img src="/common/img/logo_institution_01.jpg" alt="Warehouse58th https://aws-warehouse58th.com/index"></a>
	        </h1><!-- /header-logo-->	        
	      </section>
	    </div><!-- /header-in -->

	    <div class="header-in only-pc clearfix">
	      <section>
	        <h1 class="header-logo logo-company"><!-- Warehouse58th logo -->
	          <a href="https://aws-warehouse58th.com/index"><img src="/common/img/logo_institution_01.jpg" alt="Warehouse58th https://aws-warehouse58th.com/index"></a>
	        </h1><!-- /header-logo-->
	      </section>
	    </div><!-- /header-in only-pc-->
	  </header>
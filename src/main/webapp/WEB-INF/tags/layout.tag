<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="wide" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="rightTeaser" fragment="true" %>
<%@attribute name="breaking" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Balver Kegelstadtmeisterschaft 2016</title>
	<link rel="stylesheet" type="text/css" href="/css/ksm.css" />
	<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<link rel="icon" href="/images/favicon2016.ico" type="image/x-icon">
</head>
<body>

<!-- 
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/de_DE/sdk.js#xfbml=1&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
 -->

	<!-- Start Wrapper -->
	<div class="wrapper">
		<!-- Start Header -->
		<div class="header">
			<div style="float:left;">
				<img src="/images/logo${headTop.recentYear}.jpg" alt="Logo" height="130" />
				<img src="/images/gutHolz3.jpg" alt="Title" height="130" />
			</div>
			<!-- 
			<div class="headTop">
				<div class="heading">Top 3: ${headTop.title}</div>
				<table>
					<tr>
						<th>Platz</th>
						<th style="width: 180px;"></th>
						<th style="width: 65px;">Holz</th>
						<th>9er</th>
					</tr>
					<c:forEach items="${headTop.rows}" var="row" varStatus="rowCounter">
						<tr>
							<td>${rowCounter.count}</td>
							<td style="text-align: left;">${row.name}</td>
							<td>${row.score}</td>
							<td>${row.niner}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			 -->
		</div>
  		<!-- End Header -->
  		
		<!-- Start Navigation Bar -->
		<div class="nav-bar">
			<ul class="nav-links">
		    	<li><a href="/ksm/">Start</a></li>
		    	<li><a href="/ksm/news">News</a></li>
		    	<li><a href="/ksm/${headTop.recentYear}/ausrichter">Ausrichter</a>
		    		<ul>
		    			<li style="width: 230px; margin-top: 3px;" class="sub"><a href="/ksm/2016/ausrichter">&#62; 2016 - KC Schlimma geht’s Nimma </a></li>
		    			<li style="width: 230px;" class="sub"><a href="/ksm/2015/ausrichter">&#62; 2015 - KC After Nine</a></li>
		    		</ul>
		    	</li>
		    	<li style="width: 40px;"><a href="/ksm/${headTop.recentYear}/clubs/herren">Clubs</a>
		    		<ul>
		    			<li style="width: 90px; margin-top: 3px;" class="sub"><a href="/ksm/${headTop.recentYear}/clubs/herren">&#62; Herren</a></li>
		    			<li style="width: 90px;" class="sub"><a href="/ksm/${headTop.recentYear}/clubs/damen">&#62; Damen</a></li>
		    			<li style="width: 90px;" class="sub"><a href="/ksm/${headTop.recentYear}/clubs/mixed">&#62; Mixed</a></li>
		    		</ul>
		    	</li>
		    	<li style="width: 40px;"><a href="/ksm/${headTop.recentYear}/einzel/herren">Einzel</a>
		    		<ul>
		    			<li style="width: 90px; margin-top: 3px;" class="sub"><a href="/ksm/${headTop.recentYear}/einzel/herren">&#62; Herren</a></li>
		    			<li style="width: 90px;" class="sub"><a href="/ksm/${headTop.recentYear}/einzel/damen">&#62; Damen</a></li>
		    		</ul>
		    	</li>
		    	<li style="width: 80px;"><a href="/ksm/${headTop.recentYear}/aufschreiber/herren">Aufschreiber</a>
		    		<ul>
		    			<li class="sub" style="width: 90px; margin-top: 3px;"><a href="/ksm/${headTop.recentYear}/aufschreiber/herren">&#62; Herren</a></li>
		    			<li class="sub" style="width: 90px;"><a href="/ksm/${headTop.recentYear}/aufschreiber/damen">&#62; Damen</a></li>
		    		</ul>
		    	</li>
		    	<li><a href="/ksm/${headTop.recentYear}/termine#week_${currentWeek}">Termine</a></li>
		    	<li><a href="/ksm/2016/kinder">Kinderkegeln</a>
		    		<ul>
		    			<li class="sub" style="width: 135px; margin-top: 3px;"><a href="/ksm/2015/kinder">&#62; Kinderkegeln 2016</a></li>
		    			<li class="sub" style="width: 135px; margin-top: 3px;"><a href="/ksm/2015/kinder">&#62; Kinderkegeln 2015</a></li>
		    			<li class="sub" style="width: 135px; padding-top: 3px;"><a href="/ksm/2014/kinder">&#62; Kinderkegeln 2014</a></li>
		    		</ul>
		    	</li>
		    	<li><a href="/ksm/2016/tandem">Tandemkegeln</a>
		    		<ul>
		    			<li class="sub" style="width: 135px; margin-top: 3px;"><a href="/ksm/2016/tandem">&#62; Tandemkegeln 2016</a></li>
		    			<li class="sub" style="width: 135px; margin-top: 3px;"><a href="/ksm/2015/tandem">&#62; Tandemkegeln 2015</a></li>
		    			<li class="sub" style="width: 135px; padding-top: 3px;"><a href="/ksm/2014/tandem">&#62; Tandemkegeln 2014</a></li>
		    		</ul>
		    	</li>
		    	<li><a href="/ksm/gaestebuch">Gästebuch</a></li>
		    	<li><a href="/ksm/kontakt">Kontakt</a></li>
		  	</ul>
		  	<!-- 
		  	<div id="fb">
			  	<div class="fb-like" data-href="https://www.facebook.com/pages/Kegelstadtmeisterschaft-Balve-2015/1517670148452479?ref=ts&fref=ts" 
			  		data-layout="button_count" data-action="like" data-show-faces="true" data-share="true"></div>
		  	</div>
		  	 -->
		</div>
		<!-- End Navigation Bar -->
		
		<!-- 
		<div class="nav-bar">
			<div class="promo">Achtung: Am 3. Juli findet um 20:00 Uhr die Kegelversammlung bei Padberg statt.</div>
		</div>
		 -->
		 
		 <!-- 
		<div class="adverts">
			<img width="275px" src="/images/luell.jpg" />
		</div>
		  -->
		
		<!-- Start Outer Content -->
		<div id="outercontent">
			<jsp:invoke fragment="breaking"/>			
			<table>
				<tr>
					<td>
						<div id="centercolumn" class="${wide ? 'wide' : 'withRightColumn'}">
							<jsp:doBody/>
						</div>
					</td>
					<td>
						<jsp:invoke fragment="rightTeaser"/>
					</td>
				</tr>
			</table>
	    </div>
		<!-- End Outer Content -->
		
	  	<!-- Start Footer -->
	  	<div id="footer">
	  		<div class="left"> 
		  		<a href="/ksm/impressum">&#60;Impressum&#62;</a>  
		  		<a href="/downloads/KSM_Satzung_${headTop.recentYear}.pdf" target="_blank">&#60;Satzung der KSM&#62;</a>
		  		<a href="/downloads/Wertungsbogen_${headTop.recentYear}.pdf" target="_blank">&#60;Wertungsbogen&#62;</a>
	  		</div>
	  		<div class="right">
		  		Copyright (c) 2014 ksm-balve.de - All Rights Reserved.
	  		</div>
	  	</div>
		<!-- End Footer -->
	</div>
	<!-- End Wrapper -->
</body>
</html>
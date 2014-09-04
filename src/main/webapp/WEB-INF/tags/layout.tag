<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="wide" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="rightTeaser" fragment="true" %>
<%@attribute name="breaking" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Balver Kegelstadtmeisterschaft 2014</title>
	<link rel="stylesheet" type="text/css" href="/css/ksm.css" />
	<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<link rel="icon" href="/images/logo.jpeg" type="image/x-icon">
</head>
<body>
	<!-- Start Wrapper -->
	<div class="wrapper">
		<!-- Start Header -->
		<div class="header">
			<div style="float:left;">
				<img src="/images/logo.jpeg" alt="Logo" width="110" height="130" />
				<img src="/images/title.jpg" alt="Title" width="450" height="130" />
			</div>
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
		</div>
  		<!-- End Header -->
  		
		<!-- Start Navigation Bar -->
		<div class="nav-bar">
			<ul class="nav-links">
		    	<li><a href="/ksm/">Start</a></li>
		    	<li><a href="/ksm/news">News</a></li>
		    	<li style="width: 80px;"><a href="/ksm/clubs/herren">Clubwertung</a>
		    		<ul>
		    			<li style="width: 80px; margin-top: 3px;" class="sub"><a href="/ksm/clubs/herren">&#62; Herren</a></li>
		    			<li style="width: 80px;" class="sub"><a href="/ksm/clubs/damen">&#62; Damen</a></li>
		    			<li style="width: 80px;" class="sub"><a href="/ksm/clubs/mixed">&#62; Mixed</a></li>
		    		</ul>
		    	</li>
		    	<li style="width: 90px;" ><a href="/ksm/einzel/herren">Einzelwertung</a>
		    		<ul>
		    			<li style="width: 90px; margin-top: 3px;" class="sub"><a href="/ksm/einzel/herren">&#62; Herren</a></li>
		    			<li style="width: 90px;" class="sub"><a href="/ksm/einzel/damen">&#62; Damen</a></li>
		    		</ul>
		    	</li>
		    	<li style="width: 130px;"><a href="/ksm/aufschreiber/herren">Aufschreiberwertung</a>
		    		<ul>
		    			<li class="sub" style="width: 130px; margin-top: 3px;"><a href="/ksm/aufschreiber/herren">&#62; Herren</a></li>
		    			<li class="sub" style="width: 130px;"><a href="/ksm/aufschreiber/damen">&#62; Damen</a></li>
		    		</ul>
		    	</li>
		    	<li><a href="/ksm/termine">Termine</a></li>
		    	<!-- <li><a href="/ksm/kinder">Kinderkegeln</a></li> -->
		    	<!-- <li><a href="/ksm/tandem">Tandemkegeln</a></li> -->
		    	<li><a href="/ksm/gaestebuch">Gästebuch</a></li>
		    	<li><a href="/ksm/kontakt">Kontakt</a></li>
		  	</ul>
		</div>
		<!-- End Navigation Bar -->
		
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
		  		<a href="/downloads/KSM_Satzung.pdf" target="_blank">&#60;Satzung der KSM 2014&#62;</a>
		  		<a href="/downloads/Wertungsbogen_KSM14.pdf" target="_blank">&#60;Wertungsbogen&#62;</a>
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
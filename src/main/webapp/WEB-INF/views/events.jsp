<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout>
	<jsp:attribute name="wide">true</jsp:attribute>
    <jsp:body>
    	<h1>Übersicht über die Kegeltermine - ${year}<span class="otherYear"><a href="/ksm/${otherYear}/termine">zu den Terminen von ${otherYear}</a></span></h1>
    	<div class="space"></div>
    	<table class="data">
    		<thead class="head">
    			<tr>
    				<td>Datum</td>
    				<td>Uhrzeit</td>
    				<td>Club</td>
    				<td>Bahn</td>
					<td>Durchgang</td>
					<td>1. Aufschreiber</td>
					<td>2. Aufschreiber</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${data}" var="event" varStatus="rowCounter">
				<tr style="${event.newWeek ? 'height: 30px' :''}" class="${rowCounter.count % 2 == 0 ? 'alt' : ''}">
				<c:choose>
					<c:when test="${event.newWeek}">
						<td style="vertical-align: middle; font-weight: bold; font-size: 15px;">Termine für KW ${event.week}</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</c:when>
					<c:otherwise>
						<td>${event.dateDay}</td>
						<td>${event.time}</td>
						<td><a href="/ksm/club/${event.club.id}">${event.club.name}</a></td>
						<td>${event.location}</td>
						<td>${event.round}</td>
						<td><a href="/ksm/club/${event.checkerClub1.id}">${event.checkerClub1.name}</a></td>
						<td><a href="/ksm/club/${event.checkerClub2.id}">${event.checkerClub2.name}</a></td>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </jsp:body>
</t:layout>
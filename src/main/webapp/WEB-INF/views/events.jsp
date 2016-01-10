<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout>
	<jsp:attribute name="wide">true</jsp:attribute>
    <jsp:body>
    	<h1>Übersicht über die Kegeltermine - ${year}
    		<span class="otherYear">
    			zu den Terminen von |
    			<c:forEach items="${otherYears}" var="otherYear" varStatus="rowCounter">
    				<a href="/ksm/${otherYear}/termine">${otherYear} |</a>
    			</c:forEach>
    		</span>
    	</h1>
    	<div class="space"></div>
    	<table class="data">
    		<thead class="head">
    			<tr>
    				<td width="18%">Datum</td>
    				<td width="7%">Uhrzeit</td>
    				<td width="17%">Club</td>
    				<td width="19%">Bahn</td>
					<td width="5%">Durchgang</td>
					<td width="18%">1. Aufschreiber</td>
					<td width="16%">2. Aufschreiber</td>
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
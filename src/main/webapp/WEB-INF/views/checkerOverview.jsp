<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout>
	<jsp:attribute name="wide">true</jsp:attribute>
    <jsp:body>
    	<h1>Aufschreiberwertung - ${type} - ${year}
    		<span class="otherYear">
    			zur Wertung von |
    			<c:forEach items="${otherYears}" var="otherYear" varStatus="rowCounter">
    				<a href="/ksm/${otherYear}/aufschreiber/${typeLower}">${otherYear} |</a>
    			</c:forEach>
    		</span>
    	</h1>
    	<div class="space"></div>
    	<table class="data">
    		<thead class="head">
    			<tr>
    				<td>Platz</td>
    				<td>Vorname</td>
    				<td>Nachname</td>
    				<td>für</td>
    				<td>bei</td>
    				<td>am</td>
    				<td>DG</td>
    				<td>Bahn</td>
    				<td>Gesamt</td>
    				<td>9er</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${data}" var="checker" varStatus="rowCounter">
				<tr class="${rowCounter.count % 2 == 0 ? 'alt' : ''}">
					<td>${checker.rank}</td>
					<td><a href="/ksm/kegler/${checker.checker.id}">${checker.checker.firstName}</a></td>
					<td><a href="/ksm/kegler/${checker.checker.id}">${checker.checker.lastName}</a></td>
					<td><a href="/ksm/club/${checker.checkerClub.id}">${checker.checkerClub.name}</a></td>
					<td><a href="/ksm/club/${checker.clubToCheck.id}">${checker.clubToCheck.name}</a></td>
					<td>${checker.date}</td>
					<td>${checker.event.round}</td>
					<td>${checker.location}</td>
					<td>${checker.score}</td>
					<td>${checker.ninerCount}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </jsp:body>
</t:layout>
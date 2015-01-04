<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout>
	<jsp:attribute name="wide">true</jsp:attribute>
    <jsp:body>
    	<h1>Clubwertung - ${type} - ${year}<span class="otherYear"><a href="/ksm/${otherYear}/clubs/${typeLower}">zur Wertung von ${otherYear}</a></span></h1>
    	<div class="space"></div>
    	<table class="data">
    		<thead class="head">
    			<tr>
    				<td>Platz</td>
    				<td>Clubname</td>
    				<td>Bahn</td>
					<td class="leftBorder" colspan="2">Durchgang 1</td>
					<td class="leftBorder" colspan="2">Durchgang 2</td>
					<td class="leftBorder" colspan="2">Durchgang 3</td>
					<td class="leftBorder" colspan="2">Durchgang 4</td>
					<td class="leftBorder" colspan="2">Gesamt</td>
				</tr>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th class="leftBorder"><a href="${rootURL}?column=r1_score&order=${column eq 'r1_score' ? order : 'desc'}">Holz</a></th>
					<th><a href="${rootURL}?column=r1_niner&order=${column eq 'r1_niner' ? order : 'desc'}">9er</a></th>
					<th class="leftBorder"><a href="${rootURL}?column=r2_score&order=${column eq 'r2_score' ? order : 'desc'}">Holz</a></th>
					<th><a href="${rootURL}?column=r2_niner&order=${column eq 'r2_niner' ? order : 'desc'}">9er</a></th>
					<th class="leftBorder"><a href="${rootURL}?column=r3_score&order=${column eq 'r3_score' ? order : 'desc'}">Holz</a></th>
					<th><a href="${rootURL}?column=r3_niner&order=${column eq 'r3_niner' ? order : 'desc'}">9er</a></th>
					<th class="leftBorder"><a href="${rootURL}?column=r4_score&order=${column eq 'r4_score' ? order : 'desc'}">Holz</a></th>
					<th><a href="${rootURL}?column=r4_niner&order=${column eq 'r4_niner' ? order : 'desc'}">9er</a></th>
					<th class="leftBorder"><a href="${rootURL}?column=total_score&order=${column eq 'total_score' ? order : 'desc'}">Holz</a></th>
					<th><a href="${rootURL}?column=total_niner&order=${column eq 'total_niner' ? order : 'desc'}">9er</a></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${results}" var="club" varStatus="rowCounter">
				<tr class="${rowCounter.count % 2 == 0 ? 'alt' : ''}">
					<td>${rowCounter.count}</td>
					<td><a href="/ksm/club/${club.club.id}">${club.club.name}</a></td>
					<td>${club.club.location}</td>
					<td class="leftBorder">${club.firstRoundResult.score}</td>
					<td>${club.firstRoundResult.ninerCount}</td>
					<td class="leftBorder">${club.secondRoundResult.score}</td>
					<td>${club.secondRoundResult.ninerCount}</td>
					<td class="leftBorder">${club.thirdRoundResult.score}</td>
					<td>${club.thirdRoundResult.ninerCount}</td>
					<td class="leftBorder">${club.fourthRoundResult.score}</td>
					<td>${club.fourthRoundResult.ninerCount}</td>
					<td class="leftBorder">${club.totalScore}</td>
					<td>${club.totalNinerCount}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </jsp:body>
</t:layout>
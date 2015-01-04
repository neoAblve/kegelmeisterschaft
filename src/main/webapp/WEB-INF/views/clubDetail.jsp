<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:layout>
    <jsp:attribute name="wide">true</jsp:attribute>
    <jsp:body>
    	<h1>Übersicht - ${model.club.name} - ${model.type} - ${model.club.year}</h1>
		<div class="space"></div>
		<table>
			<tr>
				<td>
			    	<table class="dynData">
						<thead class="head">
							<tr>
								<th colspan="5" style="text-decoration:underline;">Wertungstermine:</th>
							</tr>
							<tr>
								<th></th>
								<th class="leftBorder">Datum</th>
								<th class="leftBorder">Aufschreiberclubs</th>
								<th class="leftBorder">Bahn</th>
							</tr>
						</thead>
						<tbody>
						<c:set var="odd" value="false" />
						<c:forEach items="${model.events}" var="event" varStatus="rowCounter">
							<c:set var="odd" value="${rowCounter.count % 2 == 0}" />		
							<tr class="${odd ? 'alt' : ''}">
								<td class="bold">Durchgang ${event.round}</td>
								<td class="leftBorder">${event.dateTime}</td>
								<td class="leftBorder"><a href="/ksm/club/${event.checkerClub1.id}">${event.checkerClub1.name}</a> <br /> <a href="/ksm/club/${event.checkerClub2.id}">${event.checkerClub2.name}</a></td>
								<td class="leftBorder">${event.location}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</td>
				<td style="padding-left: 8px;">
					<c:if test="${fn:length(model.checkerEvents) gt 0}">
					<table class="dynData">
						<thead class="head">
							<tr>
								<th colspan="5" style="text-decoration:underline;">Aufschreibertermine:</th>
							</tr>
							<tr>
								<th>Datum</th>
								<th class="leftBorder">Durchgang</th>
								<th class="leftBorder">Bei</th>
								<th class="leftBorder">Mit</th>
								<th class="leftBorder">Bahn</th>
							</tr>
						</thead>
						<tbody>
						<c:set var="odd" value="false" />
						<c:forEach items="${model.checkerEvents}" var="event" varStatus="rowCounter">
							<c:set var="odd" value="${rowCounter.count % 2 == 0}" />		
							<tr class="${odd ? 'alt' : ''}">
								<td>${event.dateTime}</td>
								<td class="leftBorder">${event.round}</td>
								<td class="leftBorder"><a href="/ksm/club/${event.club.id}">${event.club.name}</a></td>
								<td class="leftBorder"><a href="/ksm/club/${event.checkerClub1.id}">${event.checkerClub1.name}</a></td>
								<td class="leftBorder">${event.location}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</c:if>
				</td>
			</tr>
		</table>
		<div class="space"></div>
		<div class="space"></div>
    
		<table class="data">
			<thead class="head">
				<tr>
					<th>Vorname</th>
					<th>Nachname</th>
					<th class="leftBorder" colspan="2">Durchgang 1</th>
					<th class="leftBorder" colspan="2">Durchgang 2</th>
					<th class="leftBorder" colspan="2">Durchgang 3</th>
					<th class="leftBorder" colspan="2">Durchgang 4</th>
					<th class="leftBorder" colspan="2">Gesamt</th>
				</tr>
				<tr>
					<th></th>
					<th></th>
					<th class="leftBorder">Holz</th>
					<th>9er</th>
					<th class="leftBorder">Holz</th>
					<th>9er</th>
					<th class="leftBorder">Holz</th>
					<th>9er</th>
					<th class="leftBorder">Holz</th>
					<th>9er</th>
					<th class="leftBorder">Holz</th>
					<th>9er</th>
				</tr>
			</thead>
			<tbody>
			<c:set var="odd" value="false" />
			<c:forEach items="${model.playerResults}" var="result" varStatus="rowCounter">
				<c:set var="odd" value="${rowCounter.count % 2 == 0}" />		
				<tr class="${odd ? 'alt' : ''}">
					<td class="${result.singleRelevant ? 'bold' : ''}"><a href="/ksm/kegler/${result.player.id}">${result.player.firstName}</a></td>
					<td class="${result.singleRelevant ? 'bold' : ''}"><a href="/ksm/kegler/${result.player.id}">${result.player.lastName}</a></td>
					<td class="leftBorder ${result.firstRoundResult.top ? 'bold' : ''}">${result.firstRoundResult.score}${result.firstRoundResult.clubRelevant ? '' : '<sup>1</sup>'}</td>
					<td class="${result.firstRoundResult.top ? 'bold' : ''}">${result.firstRoundResult.ninerCount}</td>
					<td class="leftBorder ${result.secondRoundResult.top ? 'bold' : ''}">${result.secondRoundResult.score}${result.secondRoundResult.clubRelevant ? '' : '<sup>1</sup>'}</td>
					<td class="${result.secondRoundResult.top ? 'bold' : ''}">${result.secondRoundResult.ninerCount}</td>
					<td class="leftBorder ${result.thirdRoundResult.top ? 'bold' : ''}">${result.thirdRoundResult.score}${result.thirdRoundResult.clubRelevant ? '' : '<sup>1</sup>'}</td>
					<td class="${result.thirdRoundResult.top ? 'bold' : ''}">${result.thirdRoundResult.ninerCount}</td>
					<td class="leftBorder ${result.fourthRoundResult.top ? 'bold' : ''}">${result.fourthRoundResult.score}${result.fourthRoundResult.clubRelevant ? '' : '<sup>1</sup>'}</td>
					<td class="${result.fourthRoundResult.top ? 'bold' : ''}">${result.fourthRoundResult.ninerCount}</td>
					<td class="leftBorder">${result.totalScore}</td>
					<td>${result.totalNinerCount}</td>
				</tr>
			</c:forEach>
				<tr class="${not odd ? 'alt' : ''}">
					<td class="total" style="border-left: 0px;" colspan="2">Clubwertung</td>
					<td class="total leftBorder">${model.firstRoundResult.score}</td>
					<td class="total">${model.firstRoundResult.ninerCount}</td>
					<td class="total leftBorder">${model.secondRoundResult.score}</td>
					<td class="total">${model.secondRoundResult.ninerCount}</td>
					<td class="total leftBorder">${model.thirdRoundResult.score}</td>
					<td class="total">${model.thirdRoundResult.ninerCount}</td>
					<td class="total leftBorder">${model.fourthRoundResult.score}</td>
					<td class="total">${model.fourthRoundResult.ninerCount}</td>
					<td class="total leftBorder">${model.totalScore}</td>
					<td class="total">${model.totalNinerCount}</td>
				</tr>
				<c:if test="${model.hasNotClubRelevant}">
					<c:set var="odd" value="${not odd}" />		
					<tr class="${not odd ? 'alt' : ''}">
						<td class="legend" colspan="11"><sup>1</sup>: Durchgang wurde vorgelegt bzw. nachgeholt und ist für die Clubwertung nicht relevant</td>
					</tr>
				</c:if>
			</tbody>
		</table>
    </jsp:body>
</t:layout>
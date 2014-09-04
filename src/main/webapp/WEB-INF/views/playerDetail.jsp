<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:layout>
    <jsp:attribute name="wide">true</jsp:attribute>
    <jsp:body>
    	<h1>Keglerübersicht - ${model.player.firstName} ${model.player.lastName} </h1>
		<div class="space"></div>

		<c:if test="${model.hasSingle}">
    	<h2>Einzelwertung bei <a style="color: #E9E9E9;" href="/ksm/club/${model.player.singleLeagueClub.id}">${model.player.singleLeagueClub.name}</a></h2>
		<table class="data">
			<thead class="head">
				<tr>
					<th>Durchgang</th>
					<th>am</th>
					<th>Bahn</th>
					<th class="leftBorder">Volle 1</th>
					<th class="leftBorder">Räumen 1</th>
					<th class="leftBorder">Volle 2</th>
					<th class="leftBorder">Räumen 2</th>
					<th class="leftBorder">Gesamt</th>
					<th>9er</th>
				</tr>
			</thead>
			<tbody>
			<c:set var="odd" value="false" />
			<c:forEach items="${model.single}" var="result" varStatus="rowCounter">
				<c:set var="odd" value="${rowCounter.count % 2 == 0}" />		
				<tr class="${odd ? 'alt' : ''}">
					<td>${result.event.round}</td>
					<td>${result.event.dateTimeInline}</td>
					<td>${result.event.location}</td>
					<td class="leftBorder">${result.result.v1}</td>
					<td class="leftBorder">${result.result.r1}</td>
					<td class="leftBorder">${result.result.v2}</td>
					<td class="leftBorder">${result.result.r2}</td>
					<td class="leftBorder">${result.result.score}</td>
					<td>${result.result.ninerCount}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<div class="space"></div>
		<div class="space"></div>
		</c:if>
		
		<c:if test="${model.hasOther}">
		<c:forEach items="${model.clubs}" var="clubResult">
			<h2>Wertung bei <a style="color: #E9E9E9;" href="/ksm/club/${clubResult.key.id}">${clubResult.key.name}</a></h2>
			<table class="data">
				<thead class="head">
					<tr>
						<th>Durchgang</th>
						<th>am</th>
						<th>Bahn</th>
						<th class="leftBorder">Volle 1</th>
						<th class="leftBorder">Räumen 1</th>
						<th class="leftBorder">Volle 2</th>
						<th class="leftBorder">Räumen 2</th>
						<th class="leftBorder">Gesamt</th>
						<th>9er</th>
					</tr>
				</thead>
				<tbody>
				<c:set var="odd" value="false" />
				<c:forEach items="${clubResult.value}" var="result" varStatus="rowCounter">
					<c:set var="odd" value="${rowCounter.count % 2 == 0}" />		
					<tr class="${odd ? 'alt' : ''}">
						<td>${result.event.round}</td>
						<td>${result.event.dateTimeInline}</td>
						<td>${result.event.location}</td>
						<td class="leftBorder">${result.result.v1}</td>
						<td class="leftBorder">${result.result.r1}</td>
						<td class="leftBorder">${result.result.v2}</td>
						<td class="leftBorder">${result.result.r2}</td>
						<td class="leftBorder">${result.result.score}</td>
						<td>${result.result.ninerCount}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="space"></div>
			<div class="space"></div>
		</c:forEach>
		</c:if>
		
		<c:if test="${model.hasChecker}">
    	<h2>Aufschreiber-Durchgänge</h2>
		<table class="data">
			<thead class="head">
				<tr>
					<th>bei</th>
					<th>Durchgang</th>
					<th>am</th>
					<th>Bahn</th>
					<th class="leftBorder">Volle 1</th>
					<th class="leftBorder">Räumen 1</th>
					<th class="leftBorder">Volle 2</th>
					<th class="leftBorder">Räumen 2</th>
					<th class="leftBorder">Gesamt</th>
					<th>9er</th>
				</tr>
			</thead>
			<tbody>
			<c:set var="odd" value="false" />
			<c:forEach items="${model.checker}" var="result" varStatus="rowCounter">
				<c:set var="odd" value="${rowCounter.count % 2 == 0}" />		
				<tr class="${odd ? 'alt' : ''}">
					<td><a href="/ksm/club/${result.result.club.id}">${result.result.club.name}</a></td>
					<td>${result.event.round}</td>
					<td>${result.event.dateTimeInline}</td>
					<td>${result.event.location}</td>
					<td class="leftBorder">${result.result.v1}</td>
					<td class="leftBorder">${result.result.r1}</td>
					<td class="leftBorder">${result.result.v2}</td>
					<td class="leftBorder">${result.result.r2}</td>
					<td class="leftBorder">${result.result.score}</td>
					<td>${result.result.ninerCount}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</c:if>
    </jsp:body>
</t:layout>
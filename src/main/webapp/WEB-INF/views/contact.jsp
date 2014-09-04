<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:layout>
	<jsp:attribute name="wide">true</jsp:attribute>
    <jsp:body>
    	<h1>Kontakt</h1>
		<p>Bei Fragen, Anmerkungen oder Fehlern, die euch aufgefallen sind, schreibt uns einfach eine Nachricht über das Kontakt-Formular. 
		Wir werden uns bemühen, so schnell wie möglich zu antworten</p>
		<p>${resultMessage}</p>
		
		<c:if test="${not status eq 'true'}">
			<form:form method="post" action="/ksm/kontakt/senden" modelAttribute="contact">
			    <table>
			    	<tr>
				        <td><form:label path="name">Name</form:label></td>
				        <td><form:input size="30" path="name" /></td>
				        <td><form:errors path="name" cssclass="error" /></td>
				    </tr>
				    <tr>
				        <td><form:label path="email">Email</form:label></td>
				        <td><form:input size="30" path="email" /></td>
				        <td><form:errors path="email" cssclass="error" /></td>
				    </tr>
				    <tr>
				        <td><form:label path="message">Nachricht</form:label></td>
				        <td><form:textarea style="width: 350px;" rows="7" path="message" /></td>
				        <td><form:errors path="message" cssclass="error" /></td>
				    </tr>
				    <tr>
				        <td></td>
				        <td><input type="submit" value="Nachricht senden"/></td>
				        <td></td>
				    </tr>
				</table> 
			</form:form>
		</c:if>
    </jsp:body>
</t:layout>
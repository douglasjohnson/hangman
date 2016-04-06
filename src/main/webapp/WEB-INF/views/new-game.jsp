<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Game</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="/css/hangman.css" />" rel="stylesheet">
</head>
<body>
    <div id="lblInstructions"  class="instructions">To start a new game, please enter the answer.</div>
    <form method="post" action="<c:url value="/play-game"/>">
        <input type="text" name="submittedAnswer" id="txtAnswerInput" class="text-answer" autofocus />
        <input type="submit" id="btnAnswer" value="Play!" class="button-answer"/>
    </form>
    <c:if test="${validationMessageList.size() > 0}">
        <ul id="lblValidationMessage" class="error">
            <c:forEach var="messageLine" items="${validationMessageList}">
                <li>${messageLine}</li>
            </c:forEach>
        </ul>
    </c:if>
</body>
</html>
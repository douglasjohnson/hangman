<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Play Game</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/vader/jquery-ui.min.css"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="/resources/css/hangman.css" />" rel="stylesheet">
<script>
$(document).ready(function () {
    var dialogId = "dialog-confirm";
    var dialog = $("#" + dialogId).dialog({
        resizable: false,
        modal: true,
        autoOpen: false,
        buttons: {
            Yes: function() {
                location.href = '<c:url value="/"/>';
            },
            No: function() {
                dialog.close();
            }
        }
    }).dialog("instance");
    $("#btnPlayAgain").click(function() {
        if (${hangman.gameInProgress}) {
            dialog.open();
        } else {
            location.href = '<c:url value="/"/>';
        }
    });

    function answerComplete() {
        var emptyInputs = $("#revealedAnswer > input").filter(function() {
            return $(this).val() === "";
        });
        return emptyInputs.length === 0;
    };
    
    $("#btnGuessAnswer").click(function() {
        var isComplete = answerComplete();
        if (!isComplete) {
            $("#message").text("Guessed answer is incomplete");
        }
        return isComplete;
    });
});
</script>
</head>
<body>
    <div id="lblInstructions" class="instructions">Guess a letter. When you're ready, guess the answer.</div>
    <div id="guessCharacters" class="guessCharacters">
        <c:forEach var="guessCharacter" items="${guessCharacters}">
            <form method="post" action='<c:url value="/guess-character"/>'>
                <c:choose>
                    <c:when test="${guessCharacter.guessed && !guessCharacter.incorrectlyGuessed}">
                        <input id="guessCharacter${guessCharacter.character}" type="button"
                            value="${guessCharacter.character}" disabled class="guessCharacter correctGuessCharacter"/>
                    </c:when>
                    <c:when test="${guessCharacter.incorrectlyGuessed}">
                        <input id="guessCharacter${guessCharacter.character}" type="button"
                            value="${guessCharacter.character}" disabled disabled class="guessCharacter incorrectGuessCharacter" style="text-decoration: line-through" />
                    </c:when>
                    <c:when test="${!hangman.gameInProgress}">
                        <input id="guessCharacter${guessCharacter.character}" type="button"
                            value="${guessCharacter.character}" disabled class="guessCharacter"/>
                    </c:when>
                    <c:otherwise>
                        <input id="guessCharacter${guessCharacter.character}" type="submit" name="guessCharacter"
                            value="${guessCharacter.character}" class="guessCharacter"/>
                    </c:otherwise>
                </c:choose>
            </form>
        </c:forEach>
    </div>
    <div id="gallows" class=gallows"">
        <c:set var="headLine" scope="page" value=" |"/>
        <c:set var="torsoLine" scope="page" value=" |"/>
        <c:set var="legsLine" scope="page" value=" |"/>

        <c:if test="${hangman.livesLost >= 1}">
            <c:set var="headLine" scope="page" value=" |      O"/>
        </c:if>

        <c:if test="${hangman.livesLost == 2}">
            <c:set var="torsoLine" scope="page" value=" |      |"/>
        </c:if>

        <c:if test="${hangman.livesLost == 3}">
            <c:set var="torsoLine" scope="page" value=" |     /|"/>
        </c:if>

        <c:if test="${hangman.livesLost >= 4}">
            <c:set var="torsoLine" scope="page" value=" |     /|\\"/>
        </c:if>

        <c:if test="${hangman.livesLost == 5}">
            <c:set var="legsLine" scope="page" value=" |     /"/>
        </c:if>

        <c:if test="${hangman.livesLost == 6}">
            <c:set var="legsLine" scope="page" value=" |     / \\"/>
        </c:if>
<pre>
  ______
 |      |
<c:out value="${headLine}"/>
<c:out value="${torsoLine}"/>
<c:out value="${legsLine}"/>
/_\</pre>
    </div>
    <form method="post" action='<c:url value="/guess-answer"/>'>
        <div id="revealedAnswer" class="revealedAnswer">
            <c:forEach var="revealedAnswerCharacter" varStatus="revealedAnswerCharacterCount"
                items="${revealedAnswerCharacters}">
                <c:choose>
                    <c:when test="${revealedAnswerCharacter.isRevealed()}">
                        <input name="revealedAnswerCharacter${revealedAnswerCharacterCount.index}" type="text" readonly value="${revealedAnswerCharacter.answerCharacter}" class="revealedAnswerCharacter revealedAnswerCharacterShown"/>
                    </c:when>
                    <c:otherwise>
                        <input name="revealedAnswerCharacter${revealedAnswerCharacterCount.index}" type="text" maxlength="1" class="revealedAnswerCharacter revealedAnswerCharacterHidden"/>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <input id="btnGuessAnswer" class="guessAnswer" type="submit" value="Guess Answer"
            <c:if test="${!hangman.gameInProgress}">disabled</c:if> />
    </form>
    <input id="btnPlayAgain" type="button" value="Play Again" class="playAgain"/>
    <div id="message" class="message">${message}</div>
    <div id="dialog-confirm"><p>Are you sure you want to abandon this game?</p></div>
</body>
</html>
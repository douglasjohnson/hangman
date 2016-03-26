define([ 'jquery', 'jqueryui' ], function($) {
    var hangman = function(options) {
        var dialogId = "dialog-confirm", dialog = $("#" + dialogId).dialog({
            resizable : false,
            modal : true,
            autoOpen : false,
            buttons : {
                Yes : function() {
                    location.href = options.newGameUrl;
                },
                No : function() {
                    dialog.close();
                }
            }
        }).dialog("instance");
        $("#btnPlayAgain").click(function() {
            if (options.gameInProgress) {
                dialog.open();
            } else {
                location.href = options.newGameUrl;
            }
        });
        function answerComplete() {
            var emptyInputs = $("#revealedAnswer > input").filter(function() {
                return $(this).val() === "";
            });
            return emptyInputs.length === 0;
        }
        ;
        $("#btnGuessAnswer").click(function() {
            var isComplete = answerComplete();
            if (!isComplete) {
                $("#message").text("Guessed answer is incomplete");
            }
            return isComplete;
        });
    };
    return hangman;
});
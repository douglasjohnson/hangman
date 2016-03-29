/*global define */
define([ "jquery", "jqueryui" ], function($) {
    "use strict";
    return function(options) {
        var hangman = this;
        this.answerComplete = function(inputs) {
            return inputs.length === 0;
        };
        var dialog = $("#dialog-confirm").dialog({
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
        var getInputs = function() {
            return $("#revealedAnswer > input").filter(function() {
                return $(this).val() === "";
            });
        };
        $("#btnGuessAnswer").click(function() {
            var isComplete = hangman.answerComplete(getInputs());
            if (!isComplete) {
                $("#message").text("Guessed answer is incomplete");
            }
            return isComplete;
        });
    };
});
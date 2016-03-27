/*global define */
define([ "jquery", "sinon", "Hangman" ], function($, sinon, Hangman) {
    describe("Answer complete", function() {
        it("should be true when all revealed answer inputs are not empty",
                function() {
                    var hangman = new Hangman({});
                    expect(hangman.answerComplete([])).toBe(true);
                });
        it("should be false when any revealed answer inputs are empty",
                function() {
                    var hangman = new Hangman({});
                    expect(hangman.answerComplete([ "" ])).toBe(false);
                });
    });
});
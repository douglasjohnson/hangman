var allTestFiles = [];
var TEST_REGEXP = /(spec|test)\.js$/i;

// Get a list of all the test files to include
Object.keys(window.__karma__.files).forEach(function(file) {
  if (TEST_REGEXP.test(file)) {
    // Normalize paths to RequireJS module names.
    // If you require sub-dependencies of test files to be loaded as-is (requiring file extension)
    // then do not normalize the paths
    var normalizedTestModule = file.replace(/^\/base\/|\.js$/g, '');
    allTestFiles.push(normalizedTestModule);
  }
});

require.config({
  // Karma serves files under /base, which is the basePath from your config file
  baseUrl: '/base',

  paths : {
      jquery : "//code.jquery.com/jquery-1.11.3",
      jqueryui : "//code.jquery.com/ui/1.11.4/jquery-ui.min",
      sinon : "//cdnjs.cloudflare.com/ajax/libs/sinon.js/1.15.4/sinon.min",
      Hangman : "src/main/webapp/js/hangman"
  },

// dynamically load all test files
  deps: allTestFiles,

  // we have to kickoff jasmine, as it is asynchronous
  callback: window.__karma__.start
});

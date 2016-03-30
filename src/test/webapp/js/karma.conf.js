// Karma configuration

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '../../../../',

    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine', 'requirejs', 'sinon'],

    // list of files / patterns to load in the browser
    files: [
        'src/test/webapp/js/test-main.js',
        {pattern: 'src/main/webapp/js/**/*.js', included: false},
        {pattern: 'src/test/webapp/js/**/*.js', included: false}
    ],

    // list of files to exclude
    exclude: [
        'src/test/webapp/js/**/karma.*.js'
    ],

    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'junit', 'coverage'],

    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
        'src/main/webapp/js/**/*.js': ['coverage']
    },

    junitReporter: {
        outputDir: 'target/karma/junit/'
    },

    coverageReporter: {
        dir: 'target/karma/coverage/',
        type: 'lcov',
        subdir: 'report-lcov'
    },

    // web server port
    port: 9876,

    // enable / disable colors in the output (reporters and logs)
    colors: true,

    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,

    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],

    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}

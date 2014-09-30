var path = require("path");

module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-gitbook');
    grunt.loadNpmTasks('grunt-gh-pages');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-http-server');

    grunt.initConfig({
        'gitbook': {
            development: {
                output: path.join(__dirname, ".grunt/gitbook"),
                input: "./",
                title: "japp 使用指南",
                description: "japp(jfinal application)框架使用帮助",
                github: "sogyf/japp"
            }
        },
        'gh-pages': {
            options: {
                base: '.grunt/gitbook'
            },
            src: ['**']
        },
        'clean': {
            files: '.grunt'
        },
        'http-server': {
            'dev': {
                root: '.grunt/gitbook',

                port: 4000,
                host: "127.0.0.1",

                showDir: true,
                autoIndex: true,
                defaultExt: "html",

                runInBackground: false
            }
        }
    });

    grunt.registerTask('test', [
        'clean',
        'gitbook',
        'http-server'
    ]);
    grunt.registerTask('publish', [
        'clean',
        'gitbook',
        'gh-pages',
        'clean'
    ]);
    grunt.registerTask('default', 'gitbook');
};
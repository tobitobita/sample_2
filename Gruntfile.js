module.exports = function (grunt) {
    grunt.initConfig({
        connect: {
            server: {
                options: {
                    port: 8000,
                    base: 'dist'
                }
            }
        },
        watch: {
//            options: {
//                livereload: true
//            },
            html: {
                files: [
                    'resources/**',
                    'src/**'
                ],
                tasks: [
                    'clean',
                    'copy'
                ]
            }
        },
        clean: {
            dist: [
                'dist/**'
            ]
        },
        copy: {
            dist: {
                files: [
                    {
                        expand: true, cwd: 'resources', src: '**', dest: 'dist'
                    }, {
                        expand: true, cwd: 'src', src: '**', dest: 'dist'
                    }, {
                        src: 'bower_components/bootstrap/dist/css/bootstrap.min.css', dest: 'dist/css/bootstrap.min.css'
                    }, {
                        src: 'bower_components/bootstrap/dist/js/bootstrap.min.js', dest: 'dist/js/bootstrap.min.js'
                    }, {
                        src: 'bower_components/jquery/dist/jquery.min.js', dest: 'dist/js/jquery.min.js'
                    }
                ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.registerTask('default', [
        'clean',
        'copy',
        'connect',
        'watch'
    ]);
};
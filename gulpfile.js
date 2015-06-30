'use strict';
var gulp = require('gulp');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify');
var plumber = require('gulp-plumber');
var del = require('del');
var express = require('express');
var serveStatic = require('serve-static');
var browserify = require('browserify');
var source = require("vinyl-source-stream");
var reactify = require('reactify');
// 文字色変更
var black = '\u001b[30m';
var red = '\u001b[31m';
var green = '\u001b[32m';
var yellow = '\u001b[33m';
var blue = '\u001b[34m';
var magenta = '\u001b[35m';
var cyan = '\u001b[36m';
var white = '\u001b[37m';
var reset = '\u001b[0m';
// 文字色変更 ここまで

// 変数定義
var SRC_DIR = 'src/';
var DEST_DIR = 'build/';
var TEMP_DIR = 'tmp/';
// フォルダ削除。
gulp.task('clean', del.bind(null, [TEMP_DIR, DEST_DIR]));
// jsxを結合し、TEMPへ出力。
gulp.task('concat', ['clean'], function () {
    return gulp.src(
        [
            SRC_DIR + 'jsx/**/*.jsx',
            SRC_DIR + 'js/**/*.js'
        ])
        .pipe(plumber())
        .pipe(concat('app.jsx'))
        .pipe(gulp.dest(TEMP_DIR + '/jsx'));
});
// jsxをコンパイルしてTEMPへ出力
gulp.task('browserify', ['concat'], function () {
    var b = browserify({
        entries: [TEMP_DIR + 'jsx/app.jsx'],
        transform: [reactify]
    });
    return b.bundle()
        .pipe(source('dev-app.js'))
        .pipe(gulp.dest(TEMP_DIR + 'js'));
});
// jsを最小化。
gulp.task('js-uglify', ['browserify'], function () {
    return gulp.src([TEMP_DIR + 'js/dev-app.js'])
        .pipe(plumber())
//        .pipe(uglify())
        .pipe(rename('dev-app.min.js'))
        .pipe(gulp.dest(TEMP_DIR + 'js'));
});
// jsを結合し出力フォルダへ。
gulp.task('bower-concat', ['js-uglify'], function () {
    return gulp.src(
        // 依存順となるので注意。
        [
            'bower_components/jquery/dist/jquery.min.js',
            'bower_components/react/react.min.js',
            TEMP_DIR + 'js/dev-app.min.js'
        ])
        .pipe(plumber())
        .pipe(concat('app.min.js'))
        .pipe(gulp.dest(DEST_DIR + '/js'));
});
// srcコンテンツのを出力フォルダへ。
gulp.task('src-publish', ['bower-concat'], function () {
    return gulp.src(
        [
            '**/*.html',
            'css/**'
        ], {base: SRC_DIR})
        .pipe(plumber())
        .pipe(gulp.dest(DEST_DIR));
});

// サーバーの起動。
gulp.task('server', ['src-publish'], function () {
    console.log(__dirname + '/' + DEST_DIR);
    console.log();
    console.log(red + 'http://localhost:3000' + reset);
    console.log();
    console.log('で起動します。');
    var app = express();
    app.use(serveStatic(__dirname + '/' + DEST_DIR));
    app.listen(3000);
});

// defaultのタスク。
gulp.task('default', ['server'], function () {
    // ファイル監視
    gulp.watch([
        SRC_DIR + 'js/**/*.js',
        SRC_DIR + 'jsx/**/*.jsx',
        SRC_DIR + '**/*.html'
    ], ['src-publish']);
});
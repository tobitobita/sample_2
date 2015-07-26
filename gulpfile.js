'use strict';
var gulp = require('gulp');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify');
var plumber = require('gulp-plumber');
var jade = require('gulp-jade');
var del = require('del');
var express = require('express');
var serveStatic = require('serve-static');
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
var SRC_DIR = 'src';
var DEST_DIR = 'build';
var TEMP_DIR = 'tmp';
// フォルダ削除。
gulp.task('clean', del.bind(null, [TEMP_DIR, DEST_DIR]));

// jsファイル結合。
gulp.task('concat-js', ['clean'], function () {
	return gulp.src(
		[
			`${SRC_DIR}/js/common.js`,
			`${SRC_DIR}/js/index.js`
		])
		.pipe(plumber())
		.pipe(concat('app.js'))
		.pipe(gulp.dest(`${TEMP_DIR}/js`))
		;
});
// jsを最小化。
gulp.task('uglify-js', ['concat-js'], function () {
	return gulp.src([`${TEMP_DIR}/js/app.js`])
		.pipe(plumber())
//        .pipe(uglify())
		.pipe(rename('app.min.js'))
		.pipe(gulp.dest(`${TEMP_DIR}/js`))
		;
});
// jsをbuildへ出力。
gulp.task('publish-js', ['uglify-js'], function () {
	return gulp.src([`${TEMP_DIR}/js/app.min.js`])
		.pipe(plumber())
		.pipe(gulp.dest(`${DEST_DIR}/js`))
		;
});
// cssファイル結合。
gulp.task('concat-css', ['publish-js'], function () {
	return gulp.src(
		[
			`${SRC_DIR}/css/*.css`
		])
		.pipe(plumber())
		.pipe(concat('app.css'))
		.pipe(gulp.dest(`${TEMP_DIR}/css`))
		;
});
// cssを最小化。
gulp.task('uglify-css', ['concat-css'], function () {
	return gulp.src([`${TEMP_DIR}/css/app.css`])
		.pipe(plumber())
//        .pipe(uglify())
		.pipe(rename('app.min.css'))
		.pipe(gulp.dest(`${TEMP_DIR}/css`))
		;
});
// cssをbuildへ出力。
gulp.task('publish-css', ['uglify-css'], function () {
	return gulp.src([`${TEMP_DIR}/css/app.min.css`])
		.pipe(plumber())
		.pipe(gulp.dest(`${DEST_DIR}/css`))
		;
});
// jade -> html変換。
gulp.task('transfer-jade-html', ['publish-css'], function () {
	return gulp.src(`${SRC_DIR}/**/*.jade`)
		.pipe(plumber())
		.pipe(jade())
		.pipe(gulp.dest(`${TEMP_DIR}`))
		;
});
// htmlをbuildへ出力。
gulp.task('publish-html', ['transfer-jade-html'], function () {
	return gulp.src(`${TEMP_DIR}/**/*.html`)
		.pipe(plumber())
		.pipe(gulp.dest(`${DEST_DIR}`))
		;
});
// サーバーの起動。
gulp.task('server', ['publish-html'], function () {
	console.log(`${__dirname}/${DEST_DIR}`);
	console.log();
	console.log(red + 'http://localhost:3000' + reset);
	console.log();
	console.log('で起動します。');
	var app = express();
	app.use(serveStatic(`${__dirname}/${DEST_DIR}`));
	app.listen(3000);
});

// defaultのタスク。
gulp.task('default', ['server'], function () {
	// ファイル監視
	gulp.watch(
		[
			`${SRC_DIR}/css/**/*.css`,
			`${SRC_DIR}/js/**/*.js`,
			`${SRC_DIR}/**/*.jade`
		],
		['publish-html']
	);
});

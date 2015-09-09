'use strict';
var gulp = require('gulp');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify');
var plumber = require('gulp-plumber');
var jade = require('gulp-jade');
var data = require('gulp-data');
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
var SOURCE_DIR = 'src';
var DEST_DIR = 'build';
var TEMP_DIR = 'tmp';
// フォルダ削除。
gulp.task('clean', del.bind(null, [TEMP_DIR, DEST_DIR]));
// js、jsxをコンパイルしてTEMPへ出力
gulp.task('browserify', ['clean'], function () {
	var b = browserify({
		entries: [
			// js
			`${SOURCE_DIR}/js/common.js`,
			// jsx
			`${SOURCE_DIR}/jsx/index.jsx`,
			`${SOURCE_DIR}/js/index.js`
		],
		transform: [reactify]
	});
	return b.bundle()
		.pipe(source('dev-app.js'))
		.pipe(gulp.dest(`${TEMP_DIR}/js`));
});
// jsを最小化。
gulp.task('uglify-js', ['browserify'], function () {
	return gulp.src([`${TEMP_DIR}/js/dev-app.js`])
		.pipe(plumber())
//        .pipe(uglify())
		.pipe(rename('dev-app.min.js'))
		.pipe(gulp.dest(`${TEMP_DIR}/js`))
		;
});
// bower_components, jsファイル結合。
gulp.task('concat-bower_components-js', ['uglify-js'], function () {
	return gulp.src(
		[
			// bower_components
			// designはdevelopモードなのでminを使わない。
			//`bower_components/jquery/dist/jquery.min.js`,
			//`bower_components/bootstrap/dist/js/bootstrap.min.js`,
			//`bower_components/react/react.min.js`,
			`bower_components/jquery/dist/jquery.js`,
			`bower_components/bootstrap/dist/js/bootstrap.js`,
			`bower_components/react/react.js`,
			// js
			`${TEMP_DIR}/js/dev-app.min.js`
		])
		.pipe(plumber())
		.pipe(concat('app.min.js'))
		.pipe(gulp.dest(`${TEMP_DIR}/js`))
		;
});
// jsをbuildへ出力。
gulp.task('publish-js', ['concat-bower_components-js'], function () {
	return gulp.src([`${TEMP_DIR}/js/app.min.js`])
		.pipe(plumber())
		.pipe(gulp.dest(`${DEST_DIR}/js`))
		;
});
// cssファイル結合前処理。
gulp.task('concat-css-prepare', ['publish-js'], function () {
	return gulp.src(
		[
			`${SOURCE_DIR}/css/common.css`
		])
		.pipe(plumber())
		.pipe(concat('prepare-dev-app.css'))
		.pipe(gulp.dest(`${TEMP_DIR}/css`))
		;
});
// cssファイル結合。
gulp.task('concat-css', ['concat-css-prepare'], function () {
	return gulp.src(
		[
			`${TEMP_DIR}/css/prepare-dev-app.css`,
			`${SOURCE_DIR}/css/**/*.css`,
			`!${SOURCE_DIR}/css/common.css`
		])
		.pipe(plumber())
		.pipe(concat('dev-app.css'))
		.pipe(gulp.dest(`${TEMP_DIR}/css`))
		;
});
// cssを最小化。
gulp.task('uglify-css', ['concat-css'], function () {
	return gulp.src([`${TEMP_DIR}/css/dev-app.css`])
		.pipe(plumber())
//        .pipe(uglify())
		.pipe(rename('dev-app.min.css'))
		.pipe(gulp.dest(`${TEMP_DIR}/css`))
		;
});
// bower_components, cssファイル結合。
gulp.task('concat-bower_components-css', ['uglify-css'], function () {
	return gulp.src(
		[
			`bower_components/bootstrap/dist/css/bootstrap.min.css`,
			`${TEMP_DIR}/css/dev-app.min.css`
		])
		.pipe(plumber())
		.pipe(concat('app.min.css'))
		.pipe(gulp.dest(`${TEMP_DIR}/css`))
		;
});
// cssをbuildへ出力。
gulp.task('publish-css', ['concat-bower_components-css'], function () {
	return gulp.src([`${TEMP_DIR}/css/app.min.css`])
		.pipe(plumber())
		.pipe(gulp.dest(`${DEST_DIR}/css`))
		;
});
// jade -> html変換。
gulp.task('transfer-jade-html', ['publish-css'], function () {
	return gulp.src(
		[
			`${SOURCE_DIR}/**/*.jade`,
			// 除外
			`!${SOURCE_DIR}/_jade_template/**/*.jade`
		])
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
// cssをbuildへ出力。
gulp.task('copy-resources', ['publish-html'], function () {
	return gulp.src(
		[
			`bower_components/bootstrap/dist/fonts/**/*.*`
		])
		.pipe(plumber())
		.pipe(gulp.dest(`${DEST_DIR}/fonts`))
		;
});
// サーバーの起動。
gulp.task('server', ['copy-resources'], function () {
	var localServerHome = `${__dirname}/${DEST_DIR}`;
	console.log();
	console.log(`ローカルサーバーホームディレクトリ: ${localServerHome}`);
	console.log(red + 'http://localhost:3001' + reset);
	console.log('で起動します。');
	console.log();
	var app = express();
	app.use(serveStatic(`${localServerHome}`));
	app.listen(3001);
});

// defaultのタスク。
gulp.task('default', ['server'], function () {
	// ファイル監視
	gulp.watch(
		[
			`${SOURCE_DIR}/css/**/*.css`,
			`${SOURCE_DIR}/js/**/*.js`,
			`${SOURCE_DIR}/jsx/**/*.jsx`,
			`${SOURCE_DIR}/**/*.jade`
		],
		['copy-resources']
	);
});
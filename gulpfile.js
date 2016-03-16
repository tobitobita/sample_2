"use strict";

var fs = require('fs');
var del = require('del');
var gulp = require('gulp');
var concat = require('gulp-concat');
var plumber = require('gulp-plumber');
var browserify = require('browserify');
var babelify = require('babelify');
var source = require('vinyl-source-stream');
var webServer = require('gulp-webserver');
var runSequence = require('run-sequence');

// 定数定義
const SOURCE_DIR = 'src';
const DEST_DIR = 'build';
const TEMP_DIR = 'tmp';

// ローカル関数
// TODO モジュール化すること。
function isMatch(name, includes) {
	for (var i in includes) {
		if (includes[i].test(name)) {
			return true;
		}
	}
	return false;
}

function isDir(dir) {
	return fs.existsSync(dir) && fs.statSync(dir).isDirectory();
}

function getFiles(dir, _includes, _files) {
	if (!isDir(dir)) {
		return [];
	}
	_includes = _includes || [];
	_files = _files || [];
	var files = fs.readdirSync(dir);
	for (var i in files) {
		var name = `${dir}/${files[i]}`;
		if (isDir(name)) {
			getFiles(name, _includes, _files);
		} else {
			if (isMatch(name, _includes)) {
				_files.push(name);
			}
		}
	}
	return _files;
}


// タスク記述開始。

// フォルダ削除。
gulp.task('clean', del.bind(null, [DEST_DIR, TEMP_DIR]));

gulp.task('browserify', () => {
	// reactコンポーネント、es6で書かれたjsのフォルダを指定する。
	var dirs = [
		`./${SOURCE_DIR}/esx`,
		`./${SOURCE_DIR}/jsx`
	];
	// 子フォルダも含めたファイルリストを作成。
	var files = [];
	dirs.forEach((path) => {
		getFiles(path, [/^.*\.js$/]).forEach((file) => {
			files.push({
				path: file,
				expose: file.substring(6, file.length - 3)
			});
		});
	});
	console.log(files);
	// browserifyにファイルを追加する。
	var b = browserify();
	files.forEach((file) => {
		b.add(file.path);
		b.require(file.path, {expose: file.expose});
	});
	// 変換処理＆出力。
	return b
		.transform(babelify, {presets: ["es2015", "react"]})
		.bundle()
		.on("error", (err) => {
			console.log("Error : " + err.message);
		})
		.pipe(source('_temp_app.js'))
		.pipe(gulp.dest(`${TEMP_DIR}/js`));
});

// bower_components, jsファイル結合。
gulp.task('concat-js', () => {
	return gulp.src(
		[
			// bower_components
//			'bower_components/jquery/dist/jquery.min.js',
//			'bower_components/bootstrap/dist/js/bootstrap.min.js',
//			'bower_components/react/react.min.js',
//			'bower_components/react/react-dom.min.js',
			//'bower_components/jquery/dist/jquery.js',
			//'bower_components/bootstrap/dist/js/bootstrap.js',
			'bower_components/react/react.js',
			'bower_components/react/react-dom.js',
//			'bower_components/moment/min/moment.min.js',
//			'bower_components/fullcalendar/dist/fullcalendar.min.js',
//			'bower_components/fullcalendar/dist/lang/ja.js',
//			'bower_components/Chart.js/Chart.min.js',
			// js
//			SOURCE_DIR + '/js/__debug.js',
			`${TEMP_DIR}/js/_temp_app.js`
		])
		.pipe(plumber())
		.pipe(concat('app.js'))
		.pipe(gulp.dest(`${TEMP_DIR}/js`))
		;
});

gulp.task('copy-js', () => {
	return gulp.src(
		[
			`${SOURCE_DIR}/browser_js/index.js`,
			`${TEMP_DIR}/js/app.js`
		])
		.pipe(plumber())
		.pipe(gulp.dest(`${DEST_DIR}/js`));
});

gulp.task('copy-html', () => {
	return gulp.src(
		[
			`${SOURCE_DIR}/index.html`
		])
		.pipe(plumber())
		.pipe(gulp.dest(DEST_DIR));
});

//gulp.task('watch', function () {
//	gulp.watch('./src/*.js', ['browserify'])
//});

gulp.task('localServer', () => {
	gulp.src(DEST_DIR)
		.pipe(webServer({
				host: '127.0.0.1',
				port: 3000
			})
		);
});

gulp.task('default', (callback) => {
	return runSequence(
		'clean',
		'browserify',
		'concat-js',
		[
			'copy-js',
			'copy-html'
		],
		'localServer',
		callback
	);
});
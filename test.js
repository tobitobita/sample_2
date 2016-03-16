"use strict";

var fs = require('fs');

console.log('start');

function isMatch(name, includes) {
	for (var i in includes) {
		if (includes[i].test(name)) {
			return true;
		}
	}
	return false;
}

function getFiles(dir, _includes, _files) {
	_includes = _includes || [];
	_files = _files || [];
	var files = fs.readdirSync(dir);
	for (var i in files) {
		var name = `${dir}/${files[i]}`;
		if (fs.statSync(name).isDirectory()) {
			getFiles(name, _includes, _files);
		} else {
			if (isMatch(name, _includes)) {
				_files.push(name);
			}
		}
	}
	return _files;
}
var files = getFiles('src', [/^.*\.js$/]);

console.log(files);
console.log('end');

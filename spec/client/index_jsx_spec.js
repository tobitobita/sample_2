"use strict";

var React = require('react/addons');
var TestUnit = React.addons.TestUtils;

var Hello = require('../../src/jsx/index.jsx').Hello;

describe('index.jsx', function () {
	describe('index.jsx', function () {
		it('renderが実装されており、正しく表示する。', function () {
			TestUnit.renderIntoDocument(<Hello name="ASDF"/>);
		});
	});
});
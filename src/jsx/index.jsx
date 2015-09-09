"use strict";
/**
 * index.jsx
 */
(function () {
	var React = require('react');

	/**
	 * ハローワールド
	 */
	var Hello = React.createClass({
		/**
		 * インターフェイス仕様。
		 */
		propTypes: {
			name: React.PropTypes.string.isRequired
		},
		/**
		 * 描画する。
		 */
		render: function () {
			return (
				<h1>Hello {this.props.name}!</h1>
			);
		}
	});
	module.exports.Hello = Hello;
})();
/**
 * common.jsx
 */
'use strict';
(function () {
	/**
	 * ハローワールド
	 */
	var Hello = React.createClass({
		/**
		 * 描画する。
		 */
		render: function () {
			return (<h1>Hello {this.props.name}!</h1>);
		}
	});
	module.exports.Hello = Hello;
})();


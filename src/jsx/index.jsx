/**
 * index.jsx
 */

var Hello = require('./common.jsx').Hello;

/**
 * カウントアップ
 */
var CountUp = React.createClass({
	/**
	 * 状態初期化
	 */
	getInitialState: function () {
		return {
			count: 0
		};
	},
	/**
	 * ボタンを押下する。
	 *
	 * @param event イベント
	 */
	onClickAction: function (event) {
		console.log(event);
		console.log('onClick, count:' + this.state.count);
		this.setState({count: this.state.count + 1});
	},
	/**
	 * 描画する。
	 */
	render: function () {
		return (
			<div>
				<p>Count: {this.state.count}</p>

				<p>
					<button className="btn" onClick={this.onClickAction}>Up!</button>
				</p>
			</div>
		);
	}
});

/**
 * アプリ
 */
var App = React.createClass({
	/**
	 * 出力
	 */
	render: function () {
		return (
			<div>
				<Hello name="React"/>
				<CountUp />
			</div>
		);
	}
});

$(document).ready(function () {
	console.log('start');
	React.render(<App />, document.getElementById('app'));
});

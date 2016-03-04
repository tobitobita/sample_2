/**
 * index.jsx
 */
'use strict';

var Test = {
	title: null,
	print: function () {
		console.log(this.title);
	}
};

/**
 * ハローワールド
 */
var Hello = React.createClass({
	/**
	 * 出力
	 */
	render() {
		return (<h1>Hello {this.props.name}!</h1>);
	}
});

/**
 * カウントアップ
 */
var CountUp = React.createClass({
	mixins: [
		Test
	],
	/**
	 * 状態初期化
	 */
	getInitialState() {
		return {
			count: 0
		};
	},
	/**
	 * 初回描画前処理
	 */
	componentWillMount() {
		console.log('componentWillMount');
		this.title = 'あ';
	},
	/**
	 * 初回描画後処理
	 */
	componentDidMount() {
		console.log('componentDidMount');
	},
	/**
	 * 次回描画前処理
	 */
	componentWillUpdate(){
		console.log('componentWillUpdate');
	},
	/**
	 * 次回描画後処理
	 */
	componentDidUpdate(){
		console.log('componentDidUpdate');
	},
	/**
	 * 出力
	 */
	render() {
		return (
			<div>
				<p>Count: {this.state.count}</p>

				<p>
					<button className="btn" onClick={this.onClickAction}>Up!</button>
				</p>
			</div>
		);
	},
	/**
	 * ボタン押下
	 *
	 * @param event イベント
	 */
	onClickAction(event) {
		//console.log(event);
		this.print();
		console.log('onClick, count:' + this.state.count);
		this.setState({count: this.state.count + 1});
	}
});

/**
 * アプリ
 */
var App = React.createClass({
	/**
	 * 出力
	 */
	render() {
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
	ReactDOM.unmountComponentAtNode(document.getElementById('app'));
	ReactDOM.render(<App />, document.getElementById('app'));
	setInterval(function(){
		ReactDOM.unmountComponentAtNode(document.getElementById('app'));
		ReactDOM.render(<App />, document.getElementById('app'));
	},1000);
});

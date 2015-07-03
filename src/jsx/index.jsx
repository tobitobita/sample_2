/**
 * index.jsx
 */
'use strict';

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
})

/**
 * カウントアップ
 */
var CountUp = React.createClass({
    /**
     * 状態初期化
     */
    getInitialState() {
        return {
            count: 0
        };
    },
    /**
     * ボタン押下
     *
     * @param event イベント
     */
    onClickAction(event) {
        console.log(event);
        console.log('onClick, count:' + this.state.count);
        this.setState({count: this.state.count + 1});
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
    React.render(<App />, document.getElementById('app'));
});

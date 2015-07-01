/**
 * index.jsx
 */
'use strict';

var Hello = React.createClass({
    render() {
        return (<div className="container">Hello {this.props.name}!</div>);
    }
})

var CountUp = React.createClass({
    getInitialState() {
        return {
            count: 0
        };
    },
    onClickAction(event) {
        console.log(event);
        console.log('onClick, count:' + this.state.count);
        this.setState({count: this.state.count + 1});
    },
    render() {
        return (
            <div>
                <p>Count: {this.state.count}</p>

                <p>
                    <button onClick={this.onClickAction}>Up!</button>
                </p>
            </div>
        );
    }
});

var App = React.createClass({
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

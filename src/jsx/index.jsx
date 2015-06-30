/**
 * index.jsx
 */
'use strict';

var Hello = React.createClass({
    render: function () {
        return (<div className="container">Hello {this.props.name}!</div>);
    }
})

$(document).ready(function () {
    console.log('start');
    React.render(<Hello name="React"/>, document.getElementById("app"));
});

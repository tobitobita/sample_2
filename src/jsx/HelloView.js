"use strict";

var app = require('esx/app');

export class HelloView extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		var helloObj = new app.Hello();
		return (
			<h1>{helloObj.hello}</h1>
		);
	}
}
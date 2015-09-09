var Hello = require('../jsx/index.jsx').Hello;

$(document).ready(function () {
	console.log('index.jsx start.');
	var hello = React.createFactory(Hello);
	var container = document.getElementById('container');
	React.render(hello({name: 'React test'}), container);
});

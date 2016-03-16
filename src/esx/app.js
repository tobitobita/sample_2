"use strict";

export class Hello {
	constructor() {
		console.log('Hello constructor.');
	}

	printHello() {
		console.log(this.hello);
	}

	get hello() {
		return 'Hello';
	}
}

window.onhelp = function () {
    console.log('onhelp');
    return false;
};
window.onkeydown = function (event) {
    console.log(event.keyCode);
    if (event.keyCode >= 112 && event.keyCode <= 123) {
        return false;
    }
};

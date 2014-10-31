function initFunctions() {
    for (var i = 1; i <= 12; ++i) {
        var key = 'f' + i;
        var funcData = viewFuncsions[key];
        if (funcData.state === 'enabled') {
            var txt = $('#' + key + ' p').text() + "<br>" + funcData.title;
            console.log(txt);
            $('#' + key + ' p').html(txt);
            functionKeyFunctions[key] = funcData.func;
        } else {
            $('#' + key).text('').addClass('functionNone');
            functionKeyFunctions[key] = emptyFunction;
        }
    }
}
$(document).ready(function () {
    initFunctions();
    for (var i = 1; i <= 12; ++i) {
        $('#f' + i).on('click', functionKeyFunctions['f' + i]);
    }
});

$(window).keydown(function (event) {
    var result = false;
    switch (event.keyCode) {
        case 112:
            functionKeyFunctions['f1']();
            break;
        case 113:
            functionKeyFunctions['f2']();
            break;
        case 114:
            functionKeyFunctions['f3']();
            break;
        case 115:
            functionKeyFunctions['f4']();
            break;
        case 116:
            functionKeyFunctions['f5']();
            break;
        case 117:
            functionKeyFunctions['f6']();
            break;
        case 118:
            functionKeyFunctions['f7']();
            break;
        case 119:
            functionKeyFunctions['f8']();
            break;
        case 120:
            functionKeyFunctions['f9']();
            break;
        case 121:
            functionKeyFunctions['f10']();
            break;
        case 122:
            functionKeyFunctions['f11']();
            break;
        case 123:
            functionKeyFunctions['f12']();
            break;
        default:
            result = true;
            break;
    }
    return result;
});

function emptyFunction() {
    console.log('empty function.');
}
var functionKeyFunctions = [];
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

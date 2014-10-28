// --> 共通の仕組み
var functionKeyFunctions = [];
function emptyFunction() {
    console.log('empty function.');
}
for (var i = 1; i <= 12; ++i) {
    functionKeyFunctions['f' + i] = emptyFunction;
}
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
// <-- 共通の仕組み

// --> 各機能を実装した関数
function showCreateView() {
    alert('showCreateView');
}
function showCopyView() {
    alert('showCopyView');
}
function showModifyView() {
    alert('showModifyView');
}
function uploadData() {
    alert('uploadData');
}
function downloadData() {
    alert('downloadData');
}
function printData() {
    alert('printData');
}
function deleteData() {
    alert('deleteData');
}
function addRow() {
    alert('addRow');
}
function copyRow() {
    alert('copyRow');
}
function saveData() {
    alert('saveData');
}
// --> 共通の仕組み

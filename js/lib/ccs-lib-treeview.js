'use strict';
(function () {
    var CcsTreeview = function (value) {
        var self = this;
        self._v = value;
    };
    
    CcsTreeview.staticPrint = function() {
        console.log('qwer');
    };

    CcsTreeview.prototype.print = function () {
        console.log('asdf');
    };

    ccs.Treeview = CcsTreeview;
}());

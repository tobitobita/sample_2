//            var viewFuncsions = {
//                f1: {
//                    state: 'none'
//                },
//                f2: {
//                    state: 'enabled',
//                    title: '新規',
//                    func: showCreateView
//                },
//                f3: {
//                    state: 'enabled',
//                    title: '新規コピー',
//                    func: showCopyView
//                },
//                f4: {
//                    state: 'enabled',
//                    title: '変更',
//                    func: showModifyView
//                },
//                f5: {
//                    state: 'enabled',
//                    title: 'アップロード',
//                    func: uploadData
//                },
//                f6: {
//                    state: 'enabled',
//                    title: 'ダウンロード',
//                    func: downloadData
//                },
//                f7: {
//                    state: 'enabled',
//                    title: '印刷',
//                    func: printData
//                },
//                f8: {
//                    state: 'none'
//                },
//                f9: {
//                    state: 'enabled',
//                    title: '削除',
//                    func: deleteData
//                },
//                f10: {
//                    state: 'none'
//                },
//                f11: {
//                    state: 'none'
//                },
//                f12: {
//                    state: 'none'
//                }
//            };
var viewFuncsions = {
    f1: {
        state: 'none'
    },
    f2: {
        state: 'enabled',
        title: '行追加',
        func: addRow
    },
    f3: {
        state: 'enabled',
        title: '行コピー',
        func: copyRow
    },
    f4: {
        state: 'none'
    },
    f5: {
        state: 'enabled',
        title: 'アップロード',
        func: uploadData
    },
    f6: {
        state: 'enabled',
        title: 'ダウンロード',
        func: downloadData
    },
    f7: {
        state: 'enabled',
        title: '印刷',
        func: printData
    },
    f8: {
        state: 'none'
    },
    f9: {
        state: 'enabled',
        title: '削除',
        func: deleteData
    },
    f10: {
        state: 'none'
    },
    f11: {
        state: 'none'
    },
    f12: {
        state: 'enabled',
        title: '保存',
        func: saveData
    }
};

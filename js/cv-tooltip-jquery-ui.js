$(document).ready(function () {
    $('body').tooltip({
        show: false,
        hide: false,
        items: '[tooltip]',
        content: function () {
            return $(this).attr('tooltip');
        }
    });
});

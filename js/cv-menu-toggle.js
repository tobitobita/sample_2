$(document).ready(function () {
    console.log('menu on-off init start.');
    var menuHide = document.createElement('p');
    $(menuHide).attr({id: 'menuHide'}).text('≪');
    $(menuHide).on('click', function () {
        console.log('click');
        $('#menu').fadeOut(0, function () {
            $('#menuShow').fadeIn(0);
            $('#contents').css('width', '100%');
        });
    });
    $('#menu').append(menuHide);
    var menuShow = document.createElement('p');
    $(menuShow).attr({id: 'menuShow'}).text('≫');
    $(menuShow).on('click', function () {
        console.log('click');
        $('#menuShow').fadeOut(0);
        $('#menu').fadeIn(0, function () {
            $('#contents').css('width', 'calc(100% - 20em)');
        });
    });
    // #contents 依存が悩ましい
    $('#contents').append(menuShow);
});

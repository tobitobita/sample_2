/*
 メニュー内のアコーディオン表示
 ※高速化のため、ID（#menu）を指定している。
 */
$(document).ready(function () {
    console.log('accordion init start.');
    var p = document.createElement('p');
    $(p).addClass('accordionToggle').text('-');
    $(p).on('click', function () {
        if ($(this).attr('state') === 'hide') {
            $(this).text('-').attr({state: 'show'});
            $('.accordionBody', $(this).parent().parent()).fadeIn(0);
        } else {
            $(this).text('+').attr({state: 'hide'});
            $('.accordionBody', $(this).parent().parent()).fadeOut(0);
        }
    });
    $('#menu .accordionHead').append(p);
});

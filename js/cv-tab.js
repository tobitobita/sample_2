/*
 コンテンツ内のタブ表示
 ※高速化のため、ID（#contents）を指定している。
 */
$(document).ready(function () {
    $('#contents .tabs li').on('click', function () {
        var index = $('#contents .tabs li').index(this);
        var items = $('#contents .tabItems .tabItem');
        items.css('display', 'none');
        items.eq(index).css('display', 'block');
        $('#contents .tabs li').removeClass('selectedTab');
        $(this).addClass('selectedTab');
    });
});

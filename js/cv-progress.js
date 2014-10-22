$(document).ready(function () {
    $('#progress .progress-start').on('click', function () {
        var max = 1000;
        $('#progress progress').attr({
            max: max
        });
        var count = 1;
        var id = setInterval(function () {
            count += count;
            var ans = Math.round((count / max) * 100);
            console.log(ans);
            if (100 < ans) {
                ans = 100;
            }
            $('#progress .progress-percnt').text(ans);
            $('#progress progress').val(count);
            if (max <= count) {
                clearInterval(id);
            }
        }, 1000);
    });
});

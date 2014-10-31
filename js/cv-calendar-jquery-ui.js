function paddingRight(format, num) {
    return String((format + num).slice(-1 * format.length));
}

$(document).ready(function () {
    $("#datepicker").datepicker({
        showOn: "button",
        buttonImageOnly: false,
        buttonText: "カレンダーを表示",
        prevText: "≪",
        nextText: "≫",
        minDate: new Date(2014, 9, 1),
        maxDate: new Date(2014, 11, 31),
        beforeShowDay: function (day) {
            var dayStr = paddingRight('0000', day.getFullYear()) + '-'
                    + paddingRight('00', day.getMonth() + 1) + '-'
                    + paddingRight('00', day.getDate());
            return [$.inArray(dayStr, judgeHoliday) < 0];
        }
    });
});

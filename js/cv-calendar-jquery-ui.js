$(document).ready(function () {
    $("#datepicker").datepicker({
        showOn: "button",
        buttonImageOnly: false,
        buttonText: "カレンダーを表示",
        prevText: "≪",
        nextText: "≫",
        beforeShowDay: function (day) {
            var workDay = [true, ""];
            if (String(day.getDate()).indexOf('3') !== -1 || day.getDate() % 3 === 0) {
                workDay = [false, "date-holiday"];
            }
//                        switch (day.getDay()) {
//                            case 0: // 日曜日
//                                workDay = [false, "date-sunday"];
//                                break;
//                            case 6: // 土曜日
//                                workDay = [false, "date-saturday"];
//                                break;
//                            default:
//                                workDay = [true, ""];
//                                break;
//                        }
            return workDay;
        }
    });
});

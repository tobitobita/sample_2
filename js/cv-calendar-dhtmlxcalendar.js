$(document).ready(function () {

    dhtmlxCalendar = new dhtmlXCalendarObject({
        input: "calendar_input",
        button: "calendar_icon"
    });
    dhtmlxCalendar.setWeekStartDay(7);// sunday
    dhtmlxCalendar.hideTime();
    dhtmlxCalendar.disableDays('year', judgeHoliday);
});

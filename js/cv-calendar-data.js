var company1_holiday = [
    '2014-10-04',
    '2014-10-05',
    '2014-10-11',
    '2014-10-12',
    '2014-10-18',
    '2014-10-19',
    '2014-10-25',
    '2014-10-26',
    '2014-11-01',
    '2014-11-02',
    '2014-11-08',
    '2014-11-09',
    '2014-11-15',
    '2014-11-16',
    '2014-11-22',
    '2014-11-23',
    '2014-11-29',
    '2014-11-30',
    '2014-12-06',
    '2014-12-07',
    '2014-12-13',
    '2014-12-14',
    '2014-12-20',
    '2014-12-21',
    '2014-12-27',
    '2014-12-28',
    '2014-12-31'
];

var company2_holiday = [
    '2014-10-01',
    '2014-10-07',
    '2014-10-08',
    '2014-10-14',
    '2014-10-15',
    '2014-10-21',
    '2014-10-22',
    '2014-10-28',
    '2014-10-29',
    '2014-11-04',
    '2014-11-05',
    '2014-11-11',
    '2014-11-12',
    '2014-11-18',
    '2014-11-19',
    '2014-11-25',
    '2014-11-26',
    '2014-12-02',
    '2014-12-03',
    '2014-12-09',
    '2014-12-10',
    '2014-12-16',
    '2014-12-17',
    '2014-12-23',
    '2014-12-24',
    '2014-12-30',
    '2014-12-31'
];
var judgeHoliday = company1_holiday;

var dhtmlxCalendar;
$(document).ready(function () {
    var changeCalendar = function () {
        console.log('company1: ' + $('#company1').prop('checked'));
        console.log('company2: ' + $('#company2').prop('checked'));
        if ($('#company1').prop('checked')) {
            judgeHoliday = company1_holiday;
        } else if ($('#company2').prop('checked')) {
            judgeHoliday = company2_holiday;
        } else {
            console.err('error.');
        }
        dhtmlxCalendar.disableDays('year', judgeHoliday);
    };
    $('#company1').on('click', changeCalendar);
    $('#company2').on('click', changeCalendar);
});

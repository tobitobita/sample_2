var company1_holiday = [
    '20141004',
    '20141005',
    '20141011',
    '20141012',
    '20141018',
    '20141019',
    '20141025',
    '20141026',
    '20141101',
    '20141102',
    '20141108',
    '20141109',
    '20141115',
    '20141116',
    '20141122',
    '20141123',
    '20141129',
    '20141130',
    '20141206',
    '20141207',
    '20141213',
    '20141214',
    '20141220',
    '20141221',
    '20141227',
    '20141228',
    '20141231'
];

var company2_holiday = [
    '20141001',
    '20141007',
    '20141008',
    '20141014',
    '20141015',
    '20141021',
    '20141022',
    '20141028',
    '20141029',
    '20141104',
    '20141105',
    '20141111',
    '20141112',
    '20141118',
    '20141119',
    '20141125',
    '20141126',
    '20141202',
    '20141203',
    '20141209',
    '20141210',
    '20141216',
    '20141217',
    '20141223',
    '20141224',
    '20141230',
    '20141231'
];
var judgeHoliday = company1_holiday;

function paddingRight(format, num) {
    return String((format + num).slice(-1 * format.length));
}

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
    };
    $('#company1').on('click', changeCalendar);
    $('#company2').on('click', changeCalendar);

    $("#datepicker").datepicker({
        showOn: "button",
        buttonImageOnly: false,
        buttonText: "カレンダーを表示",
        prevText: "≪",
        nextText: "≫",
        minDate: new Date(2014, 9, 1),
        maxDate: new Date(2014, 11, 31),
        beforeShowDay: function (day) {
            var dayStr = paddingRight('0000', day.getFullYear())
                    + paddingRight('00', day.getMonth() + 1)
                    + paddingRight('00', day.getDate());
            return [$.inArray(dayStr, judgeHoliday) < 0];
        }
    });
});

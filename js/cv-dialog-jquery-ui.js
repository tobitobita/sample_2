$(document).ready(function () {
    $("#dialog").dialog({
        modal: true,
        autoOpen: false,
        width: 400,
        closeText: 'X',
        buttons: [
            {
                text: "はい",
                click: function () {
                    $(this).dialog("close");
                }
            },
            {
                text: "いいえ",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });
    $("#dialog-link").click(function (event) {
        $("#dialog").dialog("open");
        event.preventDefault();
    });
});

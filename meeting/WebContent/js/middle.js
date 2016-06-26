//user
function userShowSchedule() {
    $('#user_showSchedule', parent.document).show();
    $('#user_showNotice', parent.document).hide();
    $('#user_createMeeting', parent.document).hide();
    $('#user_myMeeting', parent.document).hide();
}

function userShowNotice() {
    $('#user_showSchedule', parent.document).hide();
    $('#user_showNotice', parent.document).show();
    $('#user_createMeeting', parent.document).hide();
    $('#user_myMeeting', parent.document).hide();
}

function userCreateMeeting() {
    $('#user_showSchedule', parent.document).hide();
    $('#user_showNotice', parent.document).hide();
    $('#user_createMeeting', parent.document).show();
    $('#user_myMeeting', parent.document).hide();
}

function userMyMeeting() {
    $('#user_showSchedule', parent.document).hide();
    $('#user_showNotice', parent.document).hide();
    $('#user_createMeeting', parent.document).hide();
    $('#user_myMeeting', parent.document).show();
}
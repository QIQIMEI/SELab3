//提交出差纪录
function toAddRecord(){
    var missionID = document.getElementById("thisMissionID").value;
    var recordTime = document.getElementById("realTime").value;
    var recordDayNumber = document.getElementById("realDayNumber").value;
    var recordContent = document.getElementById("realContent").value;
    addRecord(missionID,recordTime,recordDayNumber,recordContent);
}


function addRecord(missionID, recordTime, recordDayNumber, recordContent){
    $.ajax( {
        url:'/addRecord',
        data:{
            missionID : missionID,
            recordTime : recordTime,
            recordDayNumber : recordDayNumber,
            recordContent : recordContent
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {
            alert("提交纪录成功,任务完成!");
        }
    });
}

function toConfirm(missionID){
    $.ajax( {
        url:'/ConfirmMission',
        data:{
            missionID : missionID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {

        }
    });
}

function queryMissionByTime(startTime, endTime){
    $.ajax( {
        url:'/DeveloperQueryMission',
        data:{
            type : "time",
            startTime : startTime,
            endTime : endTime
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var missionID = data[i].missionID;
                var projectName = data[i].projectName;
                var managerID = data[i].managerID;
                var predictTime = data[i].predictTime;
                var dayNumber = data[i].dayNumber;
                var missionStuffNumber = data[i].missionStuffNumber;
                var missionState = data[i].missionState;
                var workDescription = data[i].workDescription;
                var confirmState = data[i].confirmState;
                // TODO
            }
        }
    });
}

function queryMissionBySearch(text){
    $.ajax( {
        url:'/DeveloperQueryMission',
        data:{
            type : "projectName",
            text : text
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var missionID = data[i].missionID;
                var projectName = data[i].projectName;
                var managerID = data[i].managerID;
                var predictTime = data[i].predictTime;
                var dayNumber = data[i].dayNumber;
                var missionStuffNumber = data[i].missionStuffNumber;
                var missionState = data[i].missionState;
                var workDescription = data[i].workDescription;
                var confirmState = data[i].confirmState;
                // TODO
            }
        }
    });
}

//根据状态查询任务
function toQueryMissionByState(){
    var typevalue = document.getElementById("selectStateForMission").value;
    if (typevalue == 0){
        queryMissionByState("all");
    }else if (typevalue == 1){
        queryMissionByState("confirm");
    }else if (typevalue == 2){
        queryMissionByState("doing");
    }else if (typevalue == 3){
        queryMissionByState("finish");
    }
}


function queryMissionByState(type){ // type :  all, confirm, finish, doing
    $.ajax( {
        url:'/DeveloperQueryMission',
        data:{
            type : "projectName"
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var missionID = data[i].missionID;
                var projectName = data[i].projectName;
                var managerID = data[i].managerID;
                var predictTime = data[i].predictTime;
                var dayNumber = data[i].dayNumber;
                var missionStuffNumber = data[i].missionStuffNumber;
                var missionState = data[i].missionState;
                var workDescription = data[i].workDescription;
                var confirmState = data[i].confirmState;
                // TODO
                toAddQueryedMission(i,missionID,projectName,predictTime,missionStuffNumber,dayNumber,missionState,confirmState);
            }
        }
    });
}

function toAddQueryedMission(i,missionID,projectName,predictTime,missionStuffNumber,dayNumber,missionState,confirmState){
    var content = document.createElement('tr');
    var contain = "";
    contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span id="button'+i+
        '_missionID">'+missionID+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectName+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+predictTime+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+missionStuffNumber+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+dayNumber+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span class="button'+i+
        '_state">'+missionState+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE4"><img src="images/edt.gif" width="16" height="16" /><input class="button'+i+
        '" type=button value="'+confirmState+
        '" style="height:20px;" onclick="editButton(this)">';
    content.innerHTML=contain;
    var missionTable = document.getElementById("missionTable");
    missionTable.appendChild(content);
}




function queryProject(){
    $.ajax( {
        url:'/DeveloperQueryProject',
        data:{

        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var projectID = data[i].projectID;
                var managerID = data[i].managerID;
                var projectName = data[i].projectName;
                var projectDescription = data[i].projectDescription;
                // TODO
                toAddQueryProject(projectID,projectName,projectDescription,managerID);
            }
        }
    });
}

function toAddQueryProject(projectID,projectName,projectDescription,managerID){
    var content = document.createElement('tr');
    var contain = "";
    contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectID+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectName+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectDescription+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+managerID+
        '</span></div></td>';
    content.innerHTML=contain;
    var projectTable = document.getElementById("queryProjectTable");
    projectTable.appendChild(content);
}
//提交出差纪录
function toAddRecord(){
    var missionID = document.getElementById("thisMissionID").value;
    var recordTime = document.getElementById("realTime").value;
    var recordDayNumber = document.getElementById("realDayNumber").value;
    var recordContent = document.getElementById("realContent").value;
    addRecord(missionID,recordTime,recordDayNumber,recordContent);
}


function addRecord(missionID, recordTime, recordDayNumber, recordContent){
    $.ajax( {
        url:'/addRecord',
        data:{
            missionID : missionID,
            recordTime : recordTime,
            recordDayNumber : recordDayNumber,
            recordContent : recordContent
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {
            alert("提交纪录成功,任务完成!");
        }
    });
}

function toConfirm(missionID){
    $.ajax( {
        url:'/ConfirmMission',
        data:{
            missionID : missionID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {

        }
    });
}

function queryMissionByTime(startTime, endTime){
    $.ajax( {
        url:'/DeveloperQueryMission',
        data:{
            type : "time",
            startTime : startTime,
            endTime : endTime
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var missionID = data[i].missionID;
                var projectName = data[i].projectName;
                var managerID = data[i].managerID;
                var predictTime = data[i].predictTime;
                var dayNumber = data[i].dayNumber;
                var missionStuffNumber = data[i].missionStuffNumber;
                var missionState = data[i].missionState;
                var workDescription = data[i].workDescription;
                var confirmState = data[i].confirmState;
                // TODO
            }
        }
    });
}

function queryMissionBySearch(text){
    $.ajax( {
        url:'/DeveloperQueryMission',
        data:{
            type : "projectName",
            text : text
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var missionID = data[i].missionID;
                var projectName = data[i].projectName;
                var managerID = data[i].managerID;
                var predictTime = data[i].predictTime;
                var dayNumber = data[i].dayNumber;
                var missionStuffNumber = data[i].missionStuffNumber;
                var missionState = data[i].missionState;
                var workDescription = data[i].workDescription;
                var confirmState = data[i].confirmState;
                // TODO
            }
        }
    });
}

//根据状态查询任务
function toQueryMissionByState(){
    var typevalue = document.getElementById("selectStateForMission").value;
    if (typevalue == 0){
        queryMissionByState("all");
    }else if (typevalue == 1){
        queryMissionByState("confirm");
    }else if (typevalue == 2){
        queryMissionByState("doing");
    }else if (typevalue == 3){
        queryMissionByState("finish");
    }
}


function queryMissionByState(type){ // type :  all, confirm, finish, doing
    $.ajax( {
        url:'/DeveloperQueryMission',
        data:{
            type : "projectName"
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var missionID = data[i].missionID;
                var projectName = data[i].projectName;
                var managerID = data[i].managerID;
                var predictTime = data[i].predictTime;
                var dayNumber = data[i].dayNumber;
                var missionStuffNumber = data[i].missionStuffNumber;
                var missionState = data[i].missionState;
                var workDescription = data[i].workDescription;
                var confirmState = data[i].confirmState;
                // TODO
                toAddQueryedMission(i,missionID,projectName,predictTime,missionStuffNumber,dayNumber,missionState,confirmState);
            }
        }
    });
}

function toAddQueryedMission(i,missionID,projectName,predictTime,missionStuffNumber,dayNumber,missionState,confirmState){
    var content = document.createElement('tr');
    var contain = "";
    contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span id="button'+i+
        '_missionID">'+missionID+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectName+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+predictTime+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+missionStuffNumber+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+dayNumber+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span class="button'+i+
        '_state">'+missionState+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE4"><img src="images/edt.gif" width="16" height="16" /><input class="button'+i+
        '" type=button value="'+confirmState+
        '" style="height:20px;" onclick="editButton(this)">';
    content.innerHTML=contain;
    var missionTable = document.getElementById("missionTable");
    missionTable.appendChild(content);
}




function queryProject(){
    $.ajax( {
        url:'/DeveloperQueryProject',
        data:{

        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {  // data is a jsonarray
            for(var i=0; i<data.length; i++){
                var projectID = data[i].projectID;
                var managerID = data[i].managerID;
                var projectName = data[i].projectName;
                var projectDescription = data[i].projectDescription;
                // TODO
                toAddQueryProject(projectID,projectName,projectDescription,managerID);
            }
        }
    });
}

function toAddQueryProject(projectID,projectName,projectDescription,managerID){
    var content = document.createElement('tr');
    var contain = "";
    contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectID+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectName+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+projectDescription+
        '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><span>'+managerID+
        '</span></div></td>';
    content.innerHTML=contain;
    var projectTable = document.getElementById("queryProjectTable");
    projectTable.appendChild(content);
}


















//登录按钮点击后调用函数
function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	
$.ajax( {
    url:'LoginServlet',
    data:{
        username : username,
        password : password
    },
    type:'POST',
    cache:false, dataType:'json',
    success:function(data) {//返回JSONObject包括userID、username
        console.log(data);
        if(data.userID == "error"){
            alert("用户名或密码错误");
        }else{
        	window.sessionStorage.userIDStatic = data.userID;
        	alert(window.sessionStorage.userIDStatic);
            window.location.href = "main_user.html?username="+data.username+"";
            
        }
    }
});
}

function a(){
	var url = location.href;
	//console.log(url);
	var tmp = url.split("?")[1];
	var username = tmp.split("=")[1];
	
	document.getElementById("topFrame").src += username;
}

function b(){
	var url = location.href;
	var tmp = url.split("?")[1];
	var username = tmp.split("=")[1];
	var content = document.createElement('div');
    var contain = '<div align="center" class="STYLE1">当前用户：'+username+'</div>';
    content.innerHTML = contain;
    var projectTable = document.getElementById("user-show");
    projectTable.appendChild(content);
}

//查看用户日程
function getSchedule(){
	alert(window.sessionStorage.userIDStatic);
    $.ajax( {
      url:'../GetScheduleServlet',
      data:{
      	userID : window.sessionStorage.userIDStatic
      },
      type:'POST',
      cache:false, dataType:'json',
      success:function(data) {//返回一个JSONArray，每个对象包括meetingID,beginTime,place,content,duration,meetingType
          for(var i=0; i<data.length; i++){
              var meetingID = data[i].meetingID;
              var beginTime = data[i].beginTime;
              var place = data[i].place;
              var content = data[i].content;
              toAddQuerySchedule(meetingID,beginTime,place,content);
          }
      }
    });
    
}

function toAddQuerySchedule(meetingID,beginTime,place,c){
  var content = document.createElement('tr');
  var contain = "";
  contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><div align="center">'+meetingID+
  '</div></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+c+
  '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+beginTime+
  '</span></div></td><td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+place+'</span></div></td>';
  content.innerHTML=contain;
  var projectTable = document.getElementById("queryScheduleTable");
  projectTable.appendChild(content);
}

//查看用户通知
function getNotice(){
    $.ajax( {
        url:'/GetNoticeServlet',
        data:{
        	userID : userID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//返回一个JSONArray，每个对象包括noticeID,content,noticeType,noticeTime

        }
    });
}

//查看用户的会议
function getMyMeeting(){
    $.ajax( {
        url:'/GetMyMeetingServlet',
        data:{
        	userID : userID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//返回一个JSONObject，2代表用户发起的会议列表，1代表用户参加的会议列表，0代表用户未参加的会议列表，每个会议包括meetingID,beginTime,place,content,duration,meetingType(会议状态0：已取消；1：未推送；2：已推送)

        }
    });
}

//点击“我发起的会议”中“取消会议”按钮
function cancelMeeting(){
    $.ajax( {
        url:'/CancelMeetingServlet',
        data:{
        	meetingID : meetingID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//无返回信息

        }
    });
}

//点击“推荐我参与的会议”中“参加会议”按钮
function attendMeeting(){
    $.ajax( {
        url:'/AttendMeetingServlet',
        data:{
        	userID : userID,
        	meetingID : meetingID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//无返回信息

        }
    });
}


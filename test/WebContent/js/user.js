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
        if(data.userID == "error"){
            alert("用户名或密码错误");
        }else{
        	window.sessionStorage.userIDStatic = data.userID;
        	window.sessionStorage.userNameStatic = data.username;
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
    var contain = '<div align="center" class="STYLE1">当前用户：'+window.sessionStorage.userNameStatic+'</div>';
    content.innerHTML = contain;
    var projectTable = document.getElementById("user-show");
    projectTable.appendChild(content);
}

//查看用户日程
function getSchedule(){
    $.ajax( {
      url:'../GetScheduleServlet',
      data:{
      	userID : window.sessionStorage.userIDStatic
      },
      type:'POST',
      cache:false, dataType:'json',
      success:function(data) {//返回一个JSONArray，每个对象包括meetingID,beginTime,place,content,duration,meetingType
    	  var projectTable = document.getElementById("queryScheduleTable");
    	  projectTable.innerHTML='<tr>'+
              '<td width="12%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">日程编号</span></div></td>'+
              '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">内容</span></div></td>'+
              '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">时间</span></div></td>'+
              '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">地点</span></div></td>'+
            '</tr>';
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
        url:'../GetNoticeServlet',
        data:{
        	userID : window.sessionStorage.userIDStatic
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//返回一个JSONArray，每个对象包括noticeID,content,noticeType,noticeTime
           var projectTable = document.getElementById("queryNoticeTable");
      	   projectTable.innerHTML='<tr>'+
            '<td width="12%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">通知编号</span></div></td>' +
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">通知类型</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">通知内容</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">通知时间</span></div></td>'+
          '</tr>';
           for(var i=0; i<data.length; i++){
              var noticeID = data[i].noticeID;
              var noticeTime = data[i].noticeTime;
              var noticeType = data[i].noticeType;
              var content = data[i].content;
              toAddQueryNotice(noticeID,noticeTime,noticeType,content);
           }

        }
    });
}

function toAddQueryNotice(noticeID,noticeTime,noticeType,c){
  var content = document.createElement('tr');
  var contain = "";
  contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><div align="center">'+noticeID+
  '</div></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+noticeType+
  '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+c+
  '</span></div></td><td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+noticeTime+'</span></div></td>';
  content.innerHTML=contain;
  var projectTable = document.getElementById("queryNoticeTable");
  projectTable.appendChild(content);
}

//查看用户的会议
function getMyMeeting(){
    $.ajax( {
        url:'../GetMyMeetingServlet',
        data:{
        	userID : window.sessionStorage.userIDStatic
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//返回一个JSONObject，2代表用户发起的会议列表，1代表用户参加的会议列表，0代表用户未参加的会议列表，每个会议包括meetingID,beginTime,place,content,duration,meetingType(会议状态0：已取消；1：未推送；2：已推送)
            var c = document.createElement('tr');
            var contain = "";
            contain='<td height="30" bgcolor="#FFFFFF" colspan="6"><span font-size="30">我发起的会议</span></td>';
            c.innerHTML=contain;
            var projectTable = document.getElementById("queryMyMeetingTable");
            
       	    projectTable.innerHTML="";
            projectTable.appendChild(c);

            c = document.createElement('tr');
            contain='<td width="12%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">会议编号</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">会议内容</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">时间</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">地点</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">当前状态</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">基本操作</div></td>'
            c.innerHTML=contain;
            projectTable = document.getElementById("queryMyMeetingTable");
            projectTable.appendChild(c);


            for(var j=0; j<data.type0[0].length; j++){
                var meetingID = data.type0[0][j].meetingID;
                var beginTime = data.type0[0][j].beginTime;
                var place = data.type0[0][j].place;
                var content = data.type0[0][j].content;
                var meetingType = data.type0[0][j].meetingType;
                
                c = document.createElement('tr');
                contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><div align="center">'+meetingID+
                '</div></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+content+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+beginTime+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+place+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+meetingType+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE4">';
                if(meetingType != "已取消"){
                    contain+='<img src="images/del.gif" width="16" height="16" /><button onclick="cancelMeeting('+meetingID+')">取消会议</button></span></div></td>';
                }            
                else{
                    contain+='</span></div></td>'
                }
                c.innerHTML=contain;
                projectTable = document.getElementById("queryMyMeetingTable");
                projectTable.appendChild(c);
            }

            c = document.createElement('tr');
            contain='<td height="30" bgcolor="#FFFFFF" colspan="6"><span font-size="30">我参与的会议</span></td>';
            c.innerHTML=contain;
            var projectTable = document.getElementById("queryMyMeetingTable");
            projectTable.appendChild(c);
              
            c = document.createElement('tr');
            contain='<td width="12%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">会议编号</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">会议内容</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">时间</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">地点</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">当前状态</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">基本操作</div></td>'
            c.innerHTML=contain;
            projectTable = document.getElementById("queryMyMeetingTable");
            projectTable.appendChild(c);

            for(var j=0; j<data.type1[0].length; j++){
                var meetingID = data.type1[0][j].meetingID;
                var beginTime = data.type1[0][j].beginTime;
                var place = data.type1[0][j].place;
                var content = data.type1[0][j].content;
                var meetingType = data.type1[0][j].meetingType;
                
                c = document.createElement('tr');
                contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><div align="center">'+meetingID+
                '</div></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+content+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+beginTime+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+place+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+'已确认'+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE4">';
                contain+='</span></div></td>'
                c.innerHTML=contain;
                projectTable = document.getElementById("queryMyMeetingTable");
                projectTable.appendChild(c);
            }
            
            c = document.createElement('tr');
            contain='<td height="30" bgcolor="#FFFFFF" colspan="6"><span font-size="30">推荐我参与的会议</span></td>';
            c.innerHTML=contain;
            var projectTable = document.getElementById("queryMyMeetingTable");
            projectTable.appendChild(c);
              
            c = document.createElement('tr');
            contain='<td width="12%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">会议编号</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">会议内容</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">时间</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">地点</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">当前状态</span></div></td>'+
            '<td width="15%" height="22" background="images/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">基本操作</div></td>'
            c.innerHTML=contain;
            projectTable = document.getElementById("queryMyMeetingTable");
            projectTable.appendChild(c);

            for(var j=0; j<data.type2[0].length; j++){
                var meetingID = data.type2[0][j].meetingID;
                var beginTime = data.type2[0][j].beginTime;
                var place = data.type2[0][j].place;
                var content = data.type2[0][j].content;
                var meetingType = data.type2[0][j].meetingType;
                
                c = document.createElement('tr');
                contain='<td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1"><div align="center">'+meetingID+
                '</div></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+content+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+beginTime+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+place+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">'+'未参加'+
                '</span></div></td><td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE4">';
                contain+='<img src="images/edt.gif" width="16" height="16" /><button onclick="attendMeeting('+meetingID+')">参加会议</button></span></div></td>'
                c.innerHTML=contain;
                projectTable = document.getElementById("queryMyMeetingTable");
                projectTable.appendChild(c);
            }

        }
    });
}
//点击“我发起的会议”中“取消会议”按钮
function cancelMeeting(meetingID){
    $.ajax( {
        url:'../CancelMeetingServlet',
        data:{
        	meetingID : meetingID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//无返回信息
            
        }
    });
    getMyMeeting();
}

//点击“推荐我参与的会议”中“参加会议”按钮
function attendMeeting(meetingID){
    $.ajax( {
        url:'../AttendMeetingServlet',
        data:{
        	userID : window.sessionStorage.userIDStatic,
        	meetingID : meetingID
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//无返回信息
            
        }
    });
    getMyMeeting();
}




//点击“推荐人员”
function recommend(){
    $.ajax( {
        url:'../CreateMeetingServlet',
        data:{
            userID : window.sessionStorage.userIDStatic,
            beginTime : document.getElementById("beginTime").value,
            endTime : document.getElementById("endTime").value,
            duration : document.getElementById("duration").value,
            content : document.getElementById("content").value
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//无返回信息
        	var projectTable = document.getElementById("must-list");
            projectTable.innerHTML='';
            var projectTable2 = document.getElementById("rec-list");
            projectTable2.innerHTML='';
            var c = document.createElement('div');
            var d = document.createElement('div');
            var contain1 = '';
            var contain2 = '';
            for(var i=0; i<data.length; i++){
              var userID = data[i].userID;
              var username = data[i].username;
              
              contain1+='<div width="20%" height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><input type="checkbox" name="checkbox" value="checkbox" onclick="check('+i+','+0+')" id="ch'+i+'-'+0+'"/>'+username+'</span></div></div>';
              contain2+='<div width="20%" height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><input type="checkbox" name="checkbox" value="checkbox" onclick="check('+i+','+1+')" id="ch'+i+'-'+1+'"/>'+username+'</span></div></div>';
              if(4==(i%5)){
            	  contain1+='';
            	  contain2+='';
            	c.innerHTML=contain1;
            	d.innerHTML=contain2;
                projectTable = document.getElementById("must-list");
                projectTable.appendChild(c);
                projectTable2 = document.getElementById("rec-list");
                projectTable2.appendChild(d);
                c = document.createElement('div');
                d = document.createElement('div');
                contain1 = '';
                contain2 = '';
              }
            }
            if(0 != data.length){
                c.innerHTML=contain1;
                d.innerHTML=contain2;
                projectTable = document.getElementById("must-list");
                projectTable.appendChild(c);
                projectTable2 = document.getElementById("rec-list");
                projectTable2.appendChild(d);
            }
            
            //window.sessionStorage.userlist = data;
            window.sessionStorage.userlist= JSON.stringify(data);
            //window.sessionStorage.userlist[0] = data[0].userID;
            //window.sessionStorage.userlist.push({userID: data[0].userID});
            //window.sessionStorage.userlist[0] = data[0].userID;
            //console.log("data: " + data[0].userID);
            //console.log("data2: " + window.sessionStorage.userlist);
            var arr = JSON.parse(window.sessionStorage.userlist);
            //console.log("data3: " + arr[0].userID);
            window.sessionStorage.userlistL = data.length;
        }
    });
}

function check(i, index){
	var selfch = document.getElementById("ch"+i+"-"+index);
	var oppoch = document.getElementById("ch"+i+"-"+((index+1)%2));
	if(selfch.checked){
		oppoch.checked = false;
	}
}

function makeMeeting(){
	var userlist = new Array();
	var userArray = JSON.parse(window.sessionStorage.userlist);
	//console.log(userArray[2].userID);
	for(var i = 0; i < window.sessionStorage.userlistL; i++){
		var tmp = document.getElementById("ch"+i+"-0");
		if(tmp.checked){
			userlist.push({userID:userArray[i].userID, level:1});		
		}
		tmp = document.getElementById("ch"+i+"-1");
		if(tmp.checked){
			userlist.push({userID:userArray[i].userID, level:2});
		}
		console.log(userlist);
	}
	$.ajax( {
        url:'../AddAttendenceServlet',
        data:{
        	userlist: JSON.stringify(userlist),
        	//userID :window.sessionStorage.userlist[i].userID,
        	//level:document.getElementById("ch"+i+"-0").value,
            beginTime : document.getElementById("beginTime").value,
            endTime : document.getElementById("endTime").value,
            duration : document.getElementById("duration").value,
            content : document.getElementById("content").value
        },
        type:'post',
        cache:false, dataType:'text',
        success:function(data) {//无返回信息
            var str = data;
            console.log(str);
            document.getElementById("show-result").innerHTML = str;
        }
    });
}
/**
 * Created by Chuanhao on 2015/12/23.
 */
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
/**
 * Created by Chuanhao on 2015/12/23.
 */
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
    $.ajax( {
        url:'/login',
        data:{
            username : username,
            password : password
        },
        type:'post',
        cache:false, dataType:'json',
        success:function(data) {//返回JSONObject包括userID、username

        }
    });
}
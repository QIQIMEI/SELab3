package dao;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Content;
import bean.Notice;
import bean.Scene;
import bean.User;
import bean.Meeting;

public class Dao {
	private static String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/meetingbase?useUnicode=true&amp;characterEncoding=UTF-8&amp;";
	
	//your username and password
	String dbUsername = "root"; 
	String dbPassword = "033576";
	
	private static Dao dao;
	
	private Dao(){};
	
	public static Dao getInstance(){
		if(dao == null){
			dao = new Dao();
		}
		
		return dao;
	}

	static {
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User login(String username, String password){
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user where user.username='"+username+"'");
			if(results.next()){
				String oldPassword = results.getString("password");
				if(oldPassword.equals(password)){
					User user = new User();
					user.setUserID(results.getInt("userID"));
					user.setUsername(username);
					return user;
				}else{
					return null;
				}
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				if(con != null){
					con.close();	
				}
				if(results != null){
					results.close();
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		}
		return null;
	}

	public ArrayList<Meeting> getSchedule(int userID, int level) {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		ArrayList<Meeting> meetingList = new ArrayList<Meeting>();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			if (level == 2) {
				results = sm.executeQuery("select * from createmeeting where userID='"+userID+"'");
				while(results.next()){
					int meetingID = results.getInt("meetingID");
					Meeting meeting = getMeeting(meetingID);
					if (meeting != null) 
					    meetingList.add(meeting);
				}
				return meetingList;
			} else {
				results = sm.executeQuery("select * from attendence where userID='"+userID+"' and level = '"+level+"'");
				while(results.next()){
					int meetingID = results.getInt("meetingID");
					Meeting meeting = getMeeting(meetingID);
					if (meeting != null) 
				    	meetingList.add(meeting);
				}
				return meetingList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(sm != null){		
			    sm.close();
			}
			if(con != null){
				con.close();	
			}
			if(results != null){
				results.close();
			}
		}
		return null;
	}
	
	public Meeting getMeeting(int meetingID) {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from meeting where meetingID='"+meetingID+"'");
			if(results.next()){
				String beginTime = results.getString("beginTime");
				String place = results.getString("place"); 
				String content = results.getString("content"); 
				int duration = results.getInt("duration"); 
				int meetingType = results.getInt("meetingType"); 
				Meeting meeting = new Meeting(meetingID,beginTime,place,content,duration,meetingType);
				return meeting;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				if(con != null){
					con.close();	
				}
				if(results != null){
					results.close();
				}
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return null;
	}
	
	public ArrayList<Notice> getNotice(int userID) {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		ArrayList<Notice> noticeList = new ArrayList<Notice>();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from notice where userID='"+userID+"'");
			while(results.next()){
				int noticeID = results.getInt("noticeID");
				String content = results.getString("content");
				int noticeType = results.getInt("noticeType");
				String noticeTime = results.getString("noticeTime");
				Notice notice = new Notice(noticeID, content, noticeType, noticeTime);
				noticeList.add(notice);
			}
			return noticeList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(sm != null){		
			    sm.close();
			}
			if(con != null){
				con.close();	
			}
			if(results != null){
				results.close();
			}
		}
		return null;
	}
	
	public void cancelMeeting(int meetingID) {
		Connection con = null;
		Statement sm = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();		
			sm.executeUpdate("update meeting set meetingType = '已取消' where meetingID='"+meetingID+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				if(con != null){
					con.close();	
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		}
	}

	public void attendMeeting(int userID, int meetingID) {
		Connection con = null;
		Statement sm = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();		
			sm.executeUpdate("update attendence set level = '1' where meetingID='"+meetingID+"' and userID='"+userID+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				if(con != null){
					con.close();	
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		}
	}
	
	public void create(String meetingID, String userID) {
		Connection con = null;
		Statement sm = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();		
			sm.executeUpdate("insert into createmeeting(meetingID, userID) values('"+meetingID+"', '"+userID+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				if(con != null){
					con.close();	
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		}
	}


    public Meeting getInfo(String meetingID){
	Connection con = null;
	Statement sm = null;
	ResultSet results = null;
	try {
		con = DriverManager.getConnection(url, dbUsername, dbPassword);
		sm = con.createStatement();
		results = sm.executeQuery("select * from meeting where meeting.meetingID='"+meetingID+"'");
		Meeting meeting = new Meeting();
		if(results.next()){
			meeting.setmeetingID(results.getInt("meetingID"));
			meeting.setbeginTime(results.getString("beginTime"));
			meeting.settimeScope(results.getString("timeScope"));
			meeting.setduration(results.getInt("duration"));
		}
		return meeting;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		try {
			if( sm != null){
				sm.close();
			}
			if(con != null){
				con.close();	
			}
			if(results != null){
				results.close();
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}
	return null;
  }
    
    public User askfree(String timeScope){
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from attendence where attendence.occupytime='"+userID+"'");
			User  meeting = new Meeting();
			//怎么开对象数组来着。。
			if(results.next()){
				meeting.setmeetingID(results.getInt("meetingID"));
			}
			return User;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				if(con != null){
					con.close();	
				}
				if(results != null){
					results.close();
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		}
		return null;
	  }
   
    public void addattendence(String meetingID,String userID,int level,String duration){
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm.executeUpdate("insert into attendence(meetingID, userID,level,duration) values('"+meetingID+"', '"+userID+"','"+level+"','"+duration+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( sm != null){
					sm.close();
				}
				if(con != null){
					con.close();	
				}
				if(results != null){
					results.close();
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
		}
	  }
}

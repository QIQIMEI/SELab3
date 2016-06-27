package dao;

import java.util.Date; 
import java.util.Calendar; 

import java.text.SimpleDateFormat; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Notice;
import bean.User;
import bean.Meeting;

public class Dao {
	private int maxID=0;
	private String finalPlace = null;
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
					User user = new User(results.getInt("userID"),username);
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
		System.out.println(level);
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		ArrayList<Meeting> meetingList = new ArrayList<Meeting>();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			if (level == 0) {
				results = sm.executeQuery("select * from meeting where userID='"+userID+"'");
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
					String meetingType = meeting.getMeetingType();
					if (meeting != null && !meetingType.equals("已取消")) 
				    	meetingList.add(meeting);
				}
				return meetingList;
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
				System.out.println(beginTime);
				String place = results.getString("place"); 
				String content = results.getString("content"); 
				int duration = results.getInt("duration"); 
				String meetingType = results.getString("meetingType"); 
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
				String noticeType = results.getString("noticeType");
				String noticeTime = results.getString("noticeTime");
				Notice notice = new Notice(noticeID, content, noticeType, noticeTime);
				noticeList.add(notice);
			}
			return noticeList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(sm != null){		
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
	
	public void cancelMeeting(int meetingID) {
		Connection con = null;
		Statement sm = null;
		Statement sm2 = null;
		ResultSet results = null;
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String noticeTime = dateFormat.format(now); 
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm2 = con.createStatement();
			sm.executeUpdate("update meeting set meetingType = '已取消' where meetingID='"+meetingID+"'");
			results = sm.executeQuery("select * from attendence where meetingID='"+meetingID+"' and level='1'");
			while(results.next()){
				int userID = results.getInt("userID");
				Meeting meeting = getMeeting(meetingID);
				String content = meeting.getContent();
				sm2.executeUpdate("insert into notice(userID,content,noticeType,noticeTime) values('"+userID+"', '"+content+"','会议取消通知','"+noticeTime+"')");		   
			}
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
	
	public void create(String beginTime,int duration,String content,String place,String meetingType,int userID,String endTime) {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		finalPlace = place;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();		
			sm.executeUpdate("insert into meeting(userID,beginTime,duration,place,content,meetingType,endTime) values('"+userID+"','"+beginTime+"', '"+duration+"','"+place+"','"+content+"','"+meetingType+"','"+endTime+"')");
			results = sm.executeQuery("select * from meeting");
			while (results.next())
			{
				if (maxID<results.getInt("meetingID"))
				    maxID = results.getInt("meetingID");
				System.out.println(maxID);
			}
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

	
	public String addattendence(int userID,int level,String beginTime,String endTime,String content) {
		Connection con = null;
		Statement sm = null;
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String noticeTime = dateFormat.format(now); 
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();		
			sm.executeUpdate("insert into attendence(meetingID,userID,level,beginTime,endTime) values('"+maxID+"','"+userID+"', '"+level+"','"+beginTime+"','"+endTime+"')");
		    if (level == 1) {
		    	sm.executeUpdate("insert into notice(userID,content,noticeType,noticeTime) values('"+userID+"', '"+content+"','会议安排通知','"+noticeTime+"')");		    
		    }
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
		return finalPlace;
	}
	
	public ArrayList<User> askfree(String beginTime,String endTime){
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user");
			ArrayList<User> userList = new ArrayList<User>();
			while(results.next()){
				int userID = results.getInt("userID");
				String userName = results.getString("username");
				User user = new User(userID, userName); 
				userList.add(user);
			}
			results.close();
			results=null;
			results=sm.executeQuery("select * from attendence");
			while (results.next()){
				String bt=results.getString("beginTime");
				String et=results.getString("endTime");
		        int userID=results.getInt("userID");
		        	if  ((bt.compareTo(endTime)>=0)||(et.compareTo(beginTime)<=0))
		        	continue;
		        	else
		        	{
		        	  for (int i=0;i<=userList.size()-1;i++)
		        	  {
		        		if (userList.get(i).getUserID()==userID)
		        		 userList.remove(i);
		        	  }
		        	}
		        }
		return userList;
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



}

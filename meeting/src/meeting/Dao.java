package meeting;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;


public class Dao {
	private static String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/meetingbase?useUnicode=true&amp;characterEncoding=UTF-8&amp;";
	
	//your username and password
	String dbUsername = "root"; 
	String dbPassword = "password";
	
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
			sm.executeUpdate("insert into user(username, password) values('"+username+"', '"+password+"')");
			results.close();
			results = sm.executeQuery("select * from user where user.username='"+username+"'");
			User user = new User();
			if(results.next()){
				user.setUserID(results.getInt("userID"));
				user.setUsername(username);
			}
			return user;
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
	
	public Meeting searchallmeeting(String userID){
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from attendence where attendence.userID='"+userID+"'");
			Meeting  meeting = new Meeting();
			//怎么开对象数组来着。。
			if(results.next()){
				meeting.setmeetingID(results.getInt("meetingID"));
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

package dao;

import static org.junit.Assert.*;
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

import org.junit.Test;

public class DaoTest {

	//private String url = "jdbc:mysql://fdu.nxtsysx.net:3306/meetingbase?useUnicode=true&characterEncoding=UTF-8";
    private String url = "jdbc:mysql://127.0.0.1:3306/meetingbase?useUnicode=true&characterEncoding=UTF-8";

    //your username and password
    //private String dbUsername = "selab3";
    //private String dbPassword = "selab3qqm";
    private String dbUsername = "root";
    private String dbPassword = "qwer1234";
    
    
    static private Connection con=null;
    static private Statement sm = null;
    static private ResultSet results = null;

    static private Dao dao;

    static private String case_Username1="test_Name1", case_Password1="testPass1";
    static private String case_Username2="测试用户_中文用户名测试", case_Password2="testPass1";
    static private boolean testUserAdded=false;

    @org.junit.BeforeClass
    static public void setUp() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();

        //String url = "jdbc:mysql://fdu.nxtsysx.net:3306/meetingbase?useUnicode=true&characterEncoding=UTF-8";
        String url = "jdbc:mysql://127.0.0.1:3306/meetingbase?useUnicode=true&characterEncoding=UTF-8";
        //String dbUsername = "selab3";
        //String dbPassword = "selab3qqm";
        String dbUsername = "root";
        String dbPassword = "qwer1234";
        con = DriverManager.getConnection(url, dbUsername, dbPassword);
        sm = con.createStatement();

        dao = Dao.getInstance();

        //check if there is a same user name
        results=sm.executeQuery("SELECT * FROM `user` WHERE `username` LIKE '"+case_Username1+"'");
        assertFalse(results.next());
        results=sm.executeQuery("SELECT * FROM `user` WHERE `username` LIKE '"+case_Username2+"'");
        assertFalse(results.next());
        sm.execute("INSERT INTO `user` (`userID`, `username`, `password`) VALUES (NULL, '"+case_Username1+"', '"+case_Password1+"');");
        sm.execute("INSERT INTO `user` (`userID`, `username`, `password`) VALUES (NULL, '"+case_Username2+"', '"+case_Password2+"');");
        testUserAdded=true;
    }

    @org.junit.AfterClass
    static public void tearDown() throws Exception {
        if (testUserAdded) {
            sm.execute("DELETE FROM `user` WHERE `user`.`username` LIKE '" + case_Username1 + "'");
            sm.execute("DELETE FROM `user` WHERE `user`.`username` LIKE '" + case_Username2 + "'");
        }
        try {
            if(sm != null)
                sm.close();
            if(con != null)
                con.close();
            if(results != null)
                results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void login() throws Exception {
        //insert and check user does exist
        sm.execute("INSERT INTO `user` (`userID`, `username`, `password`) VALUES (NULL, '"+case_Username1+"', '"+case_Password1+"');");
        results=sm.executeQuery("SELECT * FROM `user` WHERE `username` LIKE '"+case_Username1+"'");
        assertTrue(results.next());
        assertEquals(results.getString("password"), case_Password1);

        //test login case 1
        User testUser=dao.login(case_Username1, case_Password1);
        assertNotNull(testUser);
        testUser=dao.login(case_Username1, case_Password1+"1");
        assertNull(testUser);

        //remove the user and try again
        sm.execute("DELETE FROM `user` WHERE `user`.`username` LIKE '"+case_Username1+"'");
        testUser=dao.login(case_Username1, case_Password1);
        assertNull(testUser);
        sm.execute("INSERT INTO `user` (`userID`, `username`, `password`) VALUES (NULL, '"+case_Username1+"', '"+case_Password1+"');");

        //insert and check user does exist
        sm.execute("INSERT INTO `user` (`userID`, `username`, `password`) VALUES (NULL, '"+case_Username2+"', '"+case_Password2+"');");
        results=sm.executeQuery("SELECT * FROM `user` WHERE `username` LIKE '"+case_Username2+"'");
        assertTrue(results.next());
        assertEquals(results.getString("password"), case_Password2);

        //test login case 2
        testUser=dao.login(case_Username2, case_Password2);
        assertNotNull(testUser);
        testUser=dao.login(case_Username2, case_Password2+"1");
        assertNull(testUser);

        //remove the user and try again
        sm.execute("DELETE FROM `user` WHERE `user`.`username` LIKE '"+case_Username2+"'");
        testUser=dao.login(case_Username2, case_Password2);
        assertNull(testUser);
        sm.execute("INSERT INTO `user` (`userID`, `username`, `password`) VALUES (NULL, '"+case_Username2+"', '"+case_Password2+"');");
    }

    @org.junit.Test
    public void testgetMeeting() throws Exception {
        String testContent="测试内容", testEndTime="2016-5-26 13:40:00";
        int testUserId;
        results=sm.executeQuery("SELECT * FROM `user` WHERE `username` LIKE '"+case_Username1+"'");
        assertTrue(results.next());
        testUserId=results.getInt("userID");

        Meeting testMeeting=new Meeting(-1, "2016-5-26 13:00:00", "testPlace", testContent, 40, "已推送");
        results=sm.executeQuery("SELECT * FROM `meeting` WHERE `content` LIKE '"+testContent+"'");
        assertFalse(results.next());
        sm.execute("INSERT INTO `meeting` (`meetingID`, `beginTime`, `duration`, `place`, `content`, `meetingType`, `userID`, `endTime`) VALUES (NULL, '"
                +testMeeting.getBeginTime()     +"', '"
                +testMeeting.getDuration()      +"', '"
                +testMeeting.getPlace()         +"', '"
                +testMeeting.getContent()       +"', '"
                +testMeeting.getMeetingType()   +"', '"
                +String.valueOf(testUserId)     +"', '"
                +testEndTime+"');");
        results=sm.executeQuery("SELECT * FROM `meeting` WHERE `content` LIKE '"+testContent+"'");
        assertTrue(results.next());
        Meeting retrievedMeeting=dao.getMeeting(results.getInt("meetingID"));
        assertEquals(testMeeting.getBeginTime(), retrievedMeeting.getBeginTime());
        assertEquals(testMeeting.getDuration(), retrievedMeeting.getDuration());
        assertEquals(testMeeting.getPlace(), retrievedMeeting.getPlace());
        assertEquals(testMeeting.getContent(), retrievedMeeting.getContent());
        assertEquals(testMeeting.getMeetingType(), retrievedMeeting.getMeetingType());
        sm.execute("DELETE FROM `meeting` WHERE `meeting`.`meetingID` = "+String.valueOf(results.getInt("meetingID")));
    }

    @org.junit.Test
    public void getNotice() throws Exception {
        results=sm.executeQuery("SELECT * FROM `user` WHERE `username` LIKE '"+case_Username1+"'");
        assertTrue(results.next());
        int testUserId=results.getInt("userID");

        Notice testNotice=new Notice(-1, "testNotice", "testType", "2016-5-26 13:00:00");
        results=sm.executeQuery("SELECT * FROM `notice` WHERE `content` LIKE '"+testNotice.getContent()+"' ");
        assertFalse(results.next());
        results=sm.executeQuery("SELECT * FROM `notice` WHERE `userID` = "+String.valueOf(testUserId));
        assertFalse(results.next());
        sm.execute("INSERT INTO `notice` (`noticeID`, `userID`, `content`, `noticeType`, `noticeTime`) VALUES (NULL, "
                +String.valueOf(testUserId)+", '"
                +testNotice.getContent()+"', '"
                +testNotice.getNoticeType()+"', '"
                +testNotice.getNoticeTime()+"');");
        results=sm.executeQuery("SELECT * FROM `notice` WHERE `content` LIKE '"+testNotice.getContent()+"'");
        assertTrue(results.next());
        ArrayList<Notice> retrievedNotices=dao.getNotice(testUserId);
        assertTrue(retrievedNotices.size()==1);
        assertEquals(retrievedNotices.get(0).getContent(), testNotice.getContent());
        assertEquals(retrievedNotices.get(0).getNoticeType(), testNotice.getNoticeType());
        assertEquals(retrievedNotices.get(0).getNoticeTime(), testNotice.getNoticeTime());
        sm.execute("DELETE FROM `notice` WHERE `notice`.`noticeID` = "+String.valueOf(results.getInt("noticeID")));
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
	@Test
	public void testCancelMeeting() {
		int meetingID=14;
		int usrID=1;
		int duration=40;
		String place="会议室88";
		String cnt="23333";
		String meetingType="已推送";
		String beginTime=("2016-06-28 00:00:00");
        String endTime=("2016-06-28 01:00:00");
		Connection con = null;
		Statement sm = null;
		Statement sm2 = null;
		ResultSet results = null;
		ResultSet resultsaft = null;
		ResultSet resultsaft2 = null;
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String noticeTime = dateFormat.format(now);
		String tpafter=null;
		String noticetype=null;
		String noticecon=null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			sm2 = con.createStatement();
			sm.executeUpdate("insert into meeting(meetingID,userID,beginTime,duration,place,content,meetingType,endTime) values('"+meetingID+"','"+usrID+"','"+beginTime+"', '"+duration+"','"+place+"','"+cnt+"','"+meetingType+"','"+endTime+"')");
			sm.executeUpdate("insert into attendence(meetingID,userID,level,beginTime,endTime) values('"+meetingID+"','"+usrID+"', '"+1+"','"+beginTime+"','"+endTime+"')");
			sm.executeUpdate("update meeting set meetingType = '已取消' where meetingID='"+meetingID+"'");
			results = sm.executeQuery("select * from attendence where meetingID='"+meetingID+"' and level='1'");
			//System.out.println(meetingID);
			while(results.next()){
				int userID = results.getInt("userID");				
				Meeting meeting = getMeeting(meetingID);
				String content = meeting.getContent();
				sm2.executeUpdate("insert into notice(userID,content,noticeType,noticeTime) values('"+userID+"', '"+content+"','会议取消通知','"+noticeTime+"')");		   
			}
			resultsaft = sm.executeQuery("select * from meeting where meeting.userID='"+usrID+"' AND meeting.meetingID='"+meetingID+"'");
			while(resultsaft.next()){
				tpafter = resultsaft.getString("meetingType");
			}
			resultsaft2 = sm.executeQuery("select * from notice where notice.userID='"+usrID+"'");
			while(resultsaft2.next()){
				noticetype = resultsaft2.getString("noticeType");
				noticecon = resultsaft2.getString("content");
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
		//System.out.println(tpafter);
		//System.out.println(noticecon);
		//System.out.println(noticetype);
		assertEquals("type wrong","已取消",tpafter);
        assertEquals("content wrong",cnt,noticecon);
        assertEquals("noticetype wrong","会议取消通知",noticetype);
	}

	@Test
	public void testAttendMeeting() {
		int userID=1;
		int meetingID=15;
		int level=0;
		String beginTime=("2016-06-28 00:00:00");
        String endTime=("2016-06-28 01:00:00");
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		int levelaft=0;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();		
			sm.executeUpdate("insert into attendence(meetingID,userID,level,beginTime,endTime) values('"+meetingID+"','"+userID+"', '"+level+"','"+beginTime+"','"+endTime+"')");
			sm.executeUpdate("update attendence set level = '1' where meetingID='"+meetingID+"' and userID='"+userID+"'");
			results = sm.executeQuery("select * from attendence where attendence.userID='"+userID+"' AND attendence.meetingID='"+meetingID+"'");
			while(results.next()){
				levelaft = results.getInt("level");
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
		//System.out.println(levelaft);
		assertEquals("level wrong",1,levelaft);
	}

	@Test
	public void testCreate() {
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		String place = "会议室88";
		int maxID=12;
		int userID=1;
		String beginTime=("2016-06-28 00:00:00");
        String endTime=("2016-06-28 01:00:00");
        int duration=40;
        String content="23333";
        String meetingType="已推送";
        String bgTime=null;
        String edTime=null;
        int dur=0;
        String pl=null;
        String ct=null;
        String mtype=null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();		
			sm.executeUpdate("insert into meeting(meetingID,userID,beginTime,duration,place,content,meetingType,endTime) values('"+maxID+"','"+userID+"','"+beginTime+"', '"+duration+"','"+place+"','"+content+"','"+meetingType+"','"+endTime+"')");
			results = sm.executeQuery("select * from meeting where meeting.userID='"+userID+"' AND meeting.meetingID='"+maxID+"'");
			while (results.next())
			{
				bgTime = results.getString("beginTime");
				//System.out.println(bgTime);
				edTime = results.getString("endTime");
				//System.out.println(edTime);
				dur = results.getInt("duration");
				//System.out.println(dur);
				pl = results.getString("place");
				//System.out.println(pl);
				ct = results.getString("content");
				//System.out.println(ct);
				mtype = results.getString("meetingType");
				//System.out.println(mtype);
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
		assertEquals("begintime wrong",beginTime,bgTime);
		assertEquals("duration wrong",duration,dur);
        assertEquals("place wrong",place,pl);
        assertEquals("content wrong",content,ct);
        assertEquals("type wrong",meetingType,mtype);
        assertEquals("endtime wrong",endTime,edTime);
		
	}

	@Test
	public void testAddattendence(){
        int userID=1;
        int level=1;
        int maxID=10;
        String beginTime=("2016-06-28 00:00:00");
        String endTime=("2016-06-28 01:00:00");
        String content="23333";
        String bgTime = null;
        String edTime = null;
        String ct = null;
        Connection con = null;
        Statement sm = null;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String noticeTime = dateFormat.format(now);
        ResultSet resultsatt = null;
        ResultSet resultsnotice = null;
        try {
            con = DriverManager.getConnection(url, dbUsername, dbPassword);
            sm = con.createStatement();
            sm.executeUpdate("insert into attendence(meetingID,userID,level,beginTime,endTime) values('"+maxID+"','"+userID+"', '"+level+"','"+beginTime+"','"+endTime+"')");
            if (level == 1) {
                sm.executeUpdate("insert into notice(userID,content,noticeType,noticeTime) values('"+userID+"', '"+content+"','会议安排通知','"+noticeTime+"')");
            }
            resultsatt = sm.executeQuery("select * from attendence where attendence.userID='"+userID+"' AND attendence.meetingID='"+maxID+"'");
            if(resultsatt.next()){
            	bgTime = resultsatt.getString("beginTime");
				//System.out.println(bgTime);
				edTime = resultsatt.getString("endTime");
				//System.out.println(edTime);
            }
            resultsnotice = sm.executeQuery("select * from notice where notice.userID='"+userID+"' AND notice.noticeTime='"+noticeTime+"'");
            if(resultsnotice.next()){
            	ct = resultsnotice.getString("content");
				//System.out.println(ct);
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
        assertEquals("begintime wrong",beginTime,bgTime);
        assertEquals("endtime wrong",endTime,edTime);
        assertEquals("content wrong",content,ct);
	}

	@Test
	public void testAskfree() {
		String beginTime=("2016-06-28 00:00:00");
        String endTime=("2016-06-28 01:00:00");
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		ResultSet resultsuser = null;
		int usernum=0;
		int maxID=9;
		int level=1;
		ArrayList<User> userList = new ArrayList<User>();
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			resultsuser = sm.executeQuery("select * from user");
			while(resultsuser.next()){
				usernum++;
			}
			
			sm.executeUpdate("insert into attendence(meetingID,userID,level,beginTime,endTime) values('"+maxID+"','"+1+"', '"+level+"','"+beginTime+"','"+endTime+"')");
			sm.executeUpdate("insert into attendence(meetingID,userID,level,beginTime,endTime) values('"+maxID+"','"+2+"', '"+level+"','"+beginTime+"','"+endTime+"')");
			sm.executeUpdate("insert into attendence(meetingID,userID,level,beginTime,endTime) values('"+maxID+"','"+3+"', '"+level+"','"+beginTime+"','"+endTime+"')");
			results = sm.executeQuery("select * from user");
			
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
			//System.out.println(userList.size());
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
		//System.out.println(usernum);
		assertEquals("length wrong",userList.size(),usernum-3);
	  }

}

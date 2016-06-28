package dao;

import bean.Meeting;
import bean.Notice;
import bean.User;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DaoTest {
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

        String url = "jdbc:mysql://fdu.nxtsysx.net:3306/meetingbase?useUnicode=true&characterEncoding=UTF-8";
        String dbUsername = "selab3";
        String dbPassword = "selab3qqm";
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
    public void getMeeting() throws Exception {
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

    @org.junit.Test
    public void cancelMeeting() throws Exception {

    }

    @org.junit.Test
    public void attendMeeting() throws Exception {

    }

    @org.junit.Test
    public void create() throws Exception {

    }

    @org.junit.Test
    public void addattendence() throws Exception {

    }

    @org.junit.Test
    public void askfree() throws Exception {

    }

}
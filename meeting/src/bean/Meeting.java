package bean;

public class Meeting {
	private int meetingID;
	private String beginTime;
	private String place;
	private String content;
	private int duration;
		
	public Meeting(int meetingID, String beginTime, String place, String content, int duration) {
		this.meetingID = meetingID;
		this.beginTime = beginTime;
		this.place = place;
		this.content = content;
		this.duration = duration;
	}
	
	public int getMeetingID() {
		return meetingID;
	}
	public void setMeetingID(int meetingID) {
		this.meetingID = meetingID;
	}
		
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}	
	
	public void setPlace(String place) {
		this.place = place;
	}		
	public String getPlace() {
		return place;
	}
	
	public void setContent(String content) {
		this.content = content;
	}		
	public String getContent() {
		return content;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getDuration() {
		return duration;
	}	
}
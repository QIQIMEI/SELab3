package bean;

public class Notice {
	private int noticeID;
	private String content;
	private int noticeType;
	private String noticeTime;
		
	public Notice(int noticeID, String content, int noticeType, String noticeTime) {
		this.noticeID = noticeID;
		this.content = content;
		this.noticeType = noticeType;
		this.noticeTime = noticeTime;
	}
	
	public int getNoticeID() {
		return noticeID;
	}
	public void setNoticeID(int noticeID) {
		this.noticeID = noticeID;
	}
	
	public void setContent(String content) {
		this.content = content;
	}		
	public String getContent() {
		return content;
	}
	
	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}
	public int getNoticeType() {
		return noticeType;
	}	
	
	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
	public String getNoticeTime() {
		return noticeTime;
	}	

}

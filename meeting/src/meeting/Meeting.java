package meeting;

public class Meeting {
		private int meetingID;
		private int userID;
		private String beginTime;
		private String timeScope;
		private int duration;
		public int getUserID() {
			return userID;
		}
		public void setUserID(int userID) {
			this.userID = userID;
		}
		public int getmeetingID() {
			return meetingID;
		}
		public void setmeetingID(int meetingID) {
			this.meetingID = meetingID;
		}
		public String getbeginTime() {
			return beginTime;
		}
		public void setbeginTime(String beginTime) {
			this.beginTime = beginTime;
		}	
		public void settimeScope(String timeScope) {
			this.timeScope = timeScope;
		}		
		public String gettimeScope() {
			return timeScope;
		}
		public void setduration(int duration) {
			this.duration = duration;
		}
		public int getduration() {
			return duration;
		}	
	}
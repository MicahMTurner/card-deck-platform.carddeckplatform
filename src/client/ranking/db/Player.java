package client.ranking.db;

public class Player {
	long score;
	String userName;
	long userId;
	
	public Player(String userName,int score) {
		// TODO Auto-generated constructor stub
		this.score=score;
		this.userName=userName;
	}
	
	
	public Player(long id, int score) {
		this.userId=id;
		this.score=score;
	}


	public long getScore() {
		return score;
	}
	public void setScore(Long long1) {
		this.score = long1;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long l) {
		this.userId = l;
	}
	
}

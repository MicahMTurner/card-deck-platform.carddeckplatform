package client.ranking.db;

public class Player {
	long score;
	String userName;
	long userId;
	long userGameID=1;
	
	
	public Player(String userName,int score,long userGameID) {
		this.score=score;
		this.userName=userName;
		this.userGameID=userGameID;
	}
	public Player(String userName,int score) {
		this.score=score;
		this.userName=userName;
	}
	public long getUserGameID() {
		return userGameID;
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

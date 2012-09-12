package client.ranking.db;

import java.util.ArrayList;

public class Game {
	long gameId;
	long lastRoundId;
	ArrayList<Player> players;
	String date;
	String gameType;
	
//	public Game(long gameId,long lastRoundId,ArrayList<Player> players) {
//		// TODO Auto-generated constructor stub
//		this.lastRoundId=lastRoundId;
//		this.gameId=gameId;
//		this.players=players;
//	}
//	public Game(long gameId,String date) {
//		this.gameId=gameId;
//		this.date=date;
//	
//	}
	public Game(long gameId, long roundId, ArrayList<Player> players,
			String date,String gameType) {
		this.lastRoundId=roundId;
		this.gameId=gameId;
		this.players=players;
		this.date=date;
		this.gameType=gameType;
	}
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public long getLastRoundId() {
		return lastRoundId;
	}
	public void setLastRoundId(long lastRoundId) {
		this.lastRoundId = lastRoundId;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public boolean add(Player object) {
		return players.add(object);
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}

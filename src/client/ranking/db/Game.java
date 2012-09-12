package client.ranking.db;

import java.util.ArrayList;

public class Game {
	long gameId;
	long lastRoundId;
	ArrayList<Player> players;
	String date;
	String gameType;
	
public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
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
	public String getPlayersInfo() {
		StringBuilder stringBuilder= new StringBuilder();
		if(players.size()!=0){
			stringBuilder.append(players.get(0).getUserName());
			stringBuilder.append("(");
			stringBuilder.append(players.get(0).getScore());
			stringBuilder.append(")");
		}
		for(int i=1;i<players.size();i++){
			Player player = players.get(i);
			stringBuilder.append(",");
			stringBuilder.append(player.getUserName());
			stringBuilder.append("(");
			stringBuilder.append(player.getScore());
			stringBuilder.append(")");
			
		}
		return stringBuilder.toString();
	}
	
}

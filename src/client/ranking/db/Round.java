package client.ranking.db;

import java.util.ArrayList;

public class Round {
	ArrayList<Player> roundResult ;
	String date;

	public Round(String date) {
		roundResult = new ArrayList<Player>();
		this.date=date;
	}

	public ArrayList<Player> getRoundResult() {
		return roundResult;
	}


	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void addUserResult(Player player) {
		roundResult.add(player);

	}

}
package client.ranking.db;

import java.util.ArrayList;

import android.database.SQLException;
import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.GameActivity;
import carddeckplatform.game.ScoringActivity;

public class ScoringSystem {
	Game game=null;
	private ScoringManager scoringManager;
	public static class Holder{
		public static ScoringSystem instance= new ScoringSystem();
	}
	public static ScoringSystem getInstance(){
		return Holder.instance;
	}
	
	public boolean addPointsToPlayer(long id, long score) throws Exception {
		return game.addPointsToPlayer(id, score);
	}
	public boolean setPointsOfPlayer(long id, long score) throws Exception {
		return game.setPointsOfPlayer(id, score);
	}
	public Long getPointsOfPlayer(long id) throws Exception {
		return game.getPointsOfPlayer(id);
	}
	public boolean makeTransaction() throws Exception {
		return game.makeTransaction(scoringManager);
	}
	
	private ScoringSystem() {
		scoringManager = new ScoringManager(CarddeckplatformActivity.getContext(), ScoringActivity.DBNAME);
	}
	
	public void open() throws SQLException {
		scoringManager.open();
	}
	public void close() {
		scoringManager.close();
	}
	public Game createNewGame(ArrayList<Player> players, String gameType) {
		return scoringManager.createNewGame(players, gameType);
	}
	public void makeNewRound(ArrayList<Long> newScores)
			throws Exception {
		if(this.game!=null)
			scoringManager.makeNewRound(game, newScores);
	}
	public Round[] showAllRoundsRounds() {
		if(this.game!=null)
			return scoringManager.showAllRoundsRounds(game);
		return null;
	}
	
}

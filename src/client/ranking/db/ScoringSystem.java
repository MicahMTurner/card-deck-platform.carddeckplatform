package client.ranking.db;

import java.util.ArrayList;

import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.ScoringActivity;
import client.ranking.db.exception.SqlOpenException;

public class ScoringSystem {
	Game game=null;
	private ScoringManager scoringManager;
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
	public static class Holder{
		public static ScoringSystem instance= new ScoringSystem();
	}
	private ScoringSystem() {
		scoringManager = new ScoringManager(CarddeckplatformActivity.getContext(), ScoringActivity.DBNAME);
	}
	public static ScoringSystem getInstance(){
		return Holder.instance;
	}
	public void open() throws SqlOpenException {
		try {
			scoringManager.open();

		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlOpenException(e.getMessage());
		}
		
	}
	public void close() {
		scoringManager.close();
	}
	public void createNewGame(ArrayList<Player> players, String gameType) {
		this.game= scoringManager.createNewGame(players, gameType);
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

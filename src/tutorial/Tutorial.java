package tutorial;

import java.util.Queue;

import utils.Deck;
import utils.Position.Player;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import logic.client.Game;

public class Tutorial extends Game {

	@Override
	public Deck getDeck() {
		return null;
	}

	@Override
	public Integer onRoundEnd() {

		return null;
	}

	@Override
	protected Queue<Player> setTurns() {

		return null;
	}

	@Override
	public int minPlayers() {

		return 1;
	}

	@Override
	public void dealCards() {

		
	}

	@Override
	public void setLayouts() {
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public utils.Player getPlayerInstance(PlayerInfo playerInfo,
			Player position, int uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String instructions() {
		// TODO Auto-generated method stub
		return null;
	}

}

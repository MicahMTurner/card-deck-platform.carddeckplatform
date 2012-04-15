package freeplay;

import java.util.ArrayList;

import logic.client.GamePrefs;
import logic.client.GamePrefs.Player;

public class FreePlayPrefs implements GamePrefs  {

	@Override
	public int getMinPlayers() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int cardsForEachPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Player startingPlayer() {
		// TODO Auto-generated method stub
		return Player.HOST;
	}

	@Override
	public ArrayList<Player> turnsMethod() {
		// TODO Auto-generated method stub
		return null;
	}

}

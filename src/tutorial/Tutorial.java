package tutorial;

import java.util.Queue;

import logic.client.Game;
import utils.Deck;
import utils.Position.Player;
import carddeckplatform.game.gameEnvironment.PlayerInfo;

public class Tutorial extends Game {
	public static Stages currentStage=Stages.WELCOME;

	public static Boolean isBadJob=null;
	public enum Stages{
		WELCOME,PLAYERS,AUTOHIDE,CANCELAUTOHIDE,DROPPABLES,DECK,HUGEPUBLIC,ROTATE,FLIP,FLIPAGAIN,END;
	}
	public Tutorial() {
		isBadJob=null;
		currentStage=Stages.WELCOME;
	}
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

	public static void nextStage() {
		currentStage=Stages.values()[Tutorial.currentStage.ordinal()+1];
		switch(Tutorial.currentStage){
		case PLAYERS:{
			
		}
		}
	}
	@Override
	public int maxPlayers() {
		// TODO Auto-generated method stub
		return 1;
	}

}

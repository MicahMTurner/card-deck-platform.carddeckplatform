package blackJack;

import java.util.ArrayList;

import android.content.Context;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;
import logic.client.Deck;
import logic.client.Game;
import logic.client.GamePrefs;
import logic.client.Player;
import logic.client.Player.Position;

public class blackJack extends Game{

	@Override
	protected void setNewTools() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTurns() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GamePrefs getPrefs() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildLayout(Context context, TableView tv, Position position) {
		int width = GameStatus.screenWidth;
		int height = GameStatus.screenHeight;
		switch(position){
		case TOP:{
			break;
		}
		case BOTTOM:{
			break;
		}
			
		case LEFT:{
			break;
		}
		case RIGHT:{
			break;
		}
			
			
		}
	}

	@Override
	public void dealCards(Deck deck, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		
	}

}

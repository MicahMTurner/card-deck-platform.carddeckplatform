package freeplay;

import freeplay.droppables.DeckAreaLogic;
import freeplay.droppables.MyPlayerAreaLogic;
import freeplay.droppables.PlayerAreaLogic;
import freeplay.droppables.PublicAreaLogic;
import freeplay.gui.DeckArea;
import freeplay.gui.PlayerArea;
import freeplay.gui.PublicArea;
import android.content.Context;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;
import logic.client.Game;
import logic.client.GameLogic;
import logic.client.GamePrefs;
import logic.client.LogicDroppable;
import logic.client.Player;
import logic.client.Player.Position;

public class FreePlay extends Game {

	private GamePrefs prefs = new FreePlayPrefs();
	private GameLogic logic = new FreePlayLogic();
	
	@Override
	protected void setNewTools() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTurns() {
		// TODO Auto-generated method stub
		super.turnsQueue.add(Player.Position.BOTTOM);
		super.turnsQueue.add(Player.Position.TOP);
	}

	@Override
	public GamePrefs getPrefs() {
		// TODO Auto-generated method stub
		return prefs;
	}

	@Override
	public GameLogic getLogic() {
		// TODO Auto-generated method stub
		return logic;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "free play";
	}

	@Override
	public void buildLayout(Context context, TableView tv, Position position) {
		// TODO Auto-generated method stub
		int width = GameStatus.screenWidth;
		int height = GameStatus.screenHeight;
		
		tv.setUiEnabled(true);
		
		if (position.equals(Player.Position.BOTTOM)){
			droppables.add(new MyPlayerAreaLogic(2,LogicDroppable.Type.PUBLIC));
			droppables.add(new PlayerAreaLogic(3,LogicDroppable.Type.PUBLIC));
			droppables.add(new DeckAreaLogic(4,LogicDroppable.Type.PUBLIC));
			
			tv.addDroppable(new DeckArea(context, 60, height/2, droppables.get(2)));
		}
		else if (position.equals(Player.Position.TOP)){
			droppables.add(new MyPlayerAreaLogic(3,LogicDroppable.Type.PUBLIC));
			droppables.add(new PlayerAreaLogic(2,LogicDroppable.Type.PUBLIC));
			droppables.add(new DeckAreaLogic(4,LogicDroppable.Type.PUBLIC));
			
			tv.addDroppable(new DeckArea(context, width-60, height/2, droppables.get(2)));
		}
		droppables.add(new PublicAreaLogic(1,LogicDroppable.Type.PUBLIC));
		
		
		
		tv.addDroppable(new PlayerArea(context,  width/2, height-100, droppables.get(0))); // places my area in the gui.
		tv.addDroppable(new PlayerArea(context,  width/2, 60, droppables.get(1))); // places opponent area in the gui.
		tv.addDroppable(new PublicArea(context, width/2, height/2, droppables.get(3)));	// places the public area in the gui.
		
	}

}

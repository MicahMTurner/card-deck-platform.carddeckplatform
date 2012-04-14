package war;

import client.gui.entities.Droppable;
import android.content.Context;
import war.droppables.MyPlayerAreaLogic;
import war.droppables.PlayerAreaLogic;
import war.droppables.PublicAreaLogic;
import war.gui.PlayerArea;
import war.gui.PublicPlace;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;
import logic.client.Game;
import logic.client.LogicDroppable;
import logic.client.Player;



public class War extends Game{
	private WarPrefs prefs=new WarPrefs();
	private WarLogic logic=new WarLogic();
	private static boolean tie=false;	
	public War() {
		// TODO Auto-generated constructor stub
		
	}
	public WarLogic getLogic() {
		return logic;
	}
	public WarPrefs getPrefs() {
		return prefs;
	}
	@Override
	protected void setNewTools() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void buildLayout(Context context, TableView tv, Player.Position position) {
		int width = GameStatus.screenWidth;
		int height = GameStatus.screenHeight;
		
		if (position.equals(Player.Position.BOTTOM)){
			droppables.add(new PublicAreaLogic(1,LogicDroppable.Type.PUBLIC));
			droppables.add(new PublicAreaLogic(2,LogicDroppable.Type.PUBLIC));
			droppables.add(new PlayerAreaLogic(3,LogicDroppable.Type.PLAYER));
			droppables.add(new MyPlayerAreaLogic(4,LogicDroppable.Type.PLAYER));
		}
		else if (position.equals(Player.Position.TOP)){
			droppables.add(new PublicAreaLogic(2,LogicDroppable.Type.PUBLIC));
			droppables.add(new PublicAreaLogic(1,LogicDroppable.Type.PUBLIC));
			droppables.add(new PlayerAreaLogic(4,LogicDroppable.Type.PLAYER));
			droppables.add(new MyPlayerAreaLogic(3,LogicDroppable.Type.PLAYER));
		}
		
		tv.addDroppable(new PublicPlace(context, width/3, height/2, droppables.get(0)));
		tv.addDroppable(new PublicPlace(context, 2*(width/3), height/2,droppables.get(1)));
		tv.addDroppable(new PlayerArea(context, width/2, 60, droppables.get(2)));
		tv.addDroppable(new PlayerArea(context, width/2, height-100, droppables.get(3)));
		
		
		
	}
	@Override
	public String toString() {
		return "war";
	}
	@Override
	public void setTurns() {		
		super.turnsQueue.add(Player.Position.BOTTOM);
		super.turnsQueue.add(Player.Position.TOP);
		
	}
	public static void setTie(boolean isTie) {
		tie=isTie;
		
	}
	public static boolean isTie() {
		return tie;
	}
}

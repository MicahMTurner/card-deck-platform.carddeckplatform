package war;

import android.content.Context;
import war.droppables.PlayerAreaLogic;
import war.droppables.PublicAreaLogic;
import war.gui.PlayerArea;
import war.gui.PublicPlace;
import carddeckplatform.game.TableView;
import logic.client.Game;



public class War extends Game{
	private WarPrefs prefs=new WarPrefs();
	private WarLogic logic=new WarLogic();
	
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
	public void buildLayout(Context context, TableView tv) {
		 int width = tv.getxDimention();
		 int hight = tv.getyDimention();
		
		
		droppables.add(new PublicAreaLogic(1));
		droppables.add(new PublicAreaLogic(2));
		droppables.add(new PlayerAreaLogic(3));
		droppables.add(new PlayerAreaLogic(4));
		// TODO Auto-generated method stub
		tv.addDroppable(new PublicPlace(context, width/3, hight/2, droppables.get(0)));
		tv.addDroppable(new PublicPlace(context, 2*(width/3), hight/2,droppables.get(1)));
		tv.addDroppable(new PlayerArea(context, width/2, 20, droppables.get(2)));
		tv.addDroppable(new PlayerArea(context, width/2, hight-20, droppables.get(3)));
		
		
		
	}
}

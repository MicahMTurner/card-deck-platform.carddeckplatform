package freeplay.customization;

import handlers.Handler;
import utils.Card;
import utils.DeckArea;
import utils.Player;
import utils.Point;
import utils.Position.Button;
import utils.StandartCard;
import client.gui.entities.MetricsConvertion;

public class CustomizationDeck extends DeckArea implements CustomizationItem {	
	boolean hasDomColor=true;
	
	private StandartCard sc1 = new StandartCard(null, "h14", "backdeck", 2, null);
	private StandartCard sc2 = new StandartCard(null, "h14", "back", 2, null);
	
	public CustomizationDeck(Button position) {
		super(position);
		sc1.setRevealed(false);
		sc2.setRevealed(true);
		
		sc1.setLocation(getX(), getY());
		
		Point offset = MetricsConvertion.pointRelativeToPx(new Point(5,0));
		
		
		sc2.setLocation(getX() - offset.getX() , getY());
		sc2.setAngle(270);
		
		addCard(null, sc1);
	}
	
	@Override
	public boolean addCard(Player player, Card card){
		getMyCards().add(card);
		return true;
	}
	@Override
	public boolean removeCard(Player player, Card card){
		getMyCards().remove(card);
		return true;
	}

	@Override
	public void onClick() {
//		if(hasDomColor){
//			hasDomColor=false;
//			removeCard(null,sc2);
//		}else{
//			hasDomColor=true;
//			addCard(null,sc2);
//		}
		
	}

	@Override
	public void longClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Handler createHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.DECK;
	}
	
}

package freeplay.customization;

import handlers.PublicEventsHandler;
import utils.Card;
import utils.Player;
import utils.Point;
import utils.Public;
import utils.StandartCard;
import utils.droppableLayouts.DroppableLayout.LayoutType;

public class CustomizationPublic extends Public implements CustomizationItem {

	private boolean isSelected=false;
	private StandartCard sc = new StandartCard(null, "h14", "back", 2, null);
	
	State state = State.NOT_SELECTED;
	public CustomizationPublic(PublicEventsHandler handler, utils.Position.Public position, LayoutType layoutType, Point scale) {
		super(handler, position, layoutType, scale);
		sc.setLocation(this.getX(), this.getY());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean addCard(Player player, Card card){
		cards.add(card);
		return true;
	}
	@Override
	public boolean removeCard(Player player, Card card){
		cards.remove(card);
		return true;
	}
	
	
	@Override
	public void onClick(){
		
		switch (state) {
		case NOT_SELECTED:{
			state = State.SELECTED_REVEALED;	
			sc.setRevealed(true);
			addCard(null, sc);
			break;}
		case SELECTED_REVEALED:{
			state = State.SELECTED_HIDDEN;
			sc.setRevealed(false);		
			break;}
		case SELECTED_HIDDEN:{
			state = State.NOT_SELECTED;
			this.removeCard(null, sc);
			
		break;}
		default:
			break;
		}
		
			
	}

	@Override
	public void longClick() {
		// TODO Auto-generated method stub
		
	}

}

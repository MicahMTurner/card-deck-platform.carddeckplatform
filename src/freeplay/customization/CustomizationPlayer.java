package freeplay.customization;

import freeplay.HiddenPlayerHandler;
import freeplay.PrivatePlayerHandler;
import freeplay.RevealedPlayerHandler;
import handlers.Handler;
import utils.Card;
import utils.Player;
import utils.Position;
import utils.StandartCard;
import utils.droppableLayouts.DroppableLayout.LayoutType;

public class CustomizationPlayer extends Player implements CustomizationItem {
	enum State{NOT_SELECTED , SELECTED_REVEALED_TO_PLAYER ,SELECTED_REVEALED_TO_ALL,  SELECTED_HIDDEN}
	
	private StandartCard sc1 = new StandartCard(null, "h14", "back", 2, null);
	private StandartCard sc2 = new StandartCard(null, "h14eye", "back", 2, null);
	State state = State.NOT_SELECTED;
	public CustomizationPlayer(utils.Position.Player position) {
		super(null, Position.Player.BOTTOM, 0,null,  LayoutType.LINE);
		setRelativePosition(position);
		sc1.setLocation(getX(), getY());
		sc2.setLocation(getX(), getY());
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
	public void setRelativePosition(utils.Position.Player devicePlayerGlobalPos) {
		this.position = devicePlayerGlobalPos;
	}
	
	@Override
	public void onClick() {
		switch (state) {
		case NOT_SELECTED:{
			state = State.SELECTED_REVEALED_TO_PLAYER;	
			sc1.setRevealed(true);
			addCard(null, sc1);
			break;
		}
		case SELECTED_REVEALED_TO_PLAYER:{
			state = State.SELECTED_REVEALED_TO_ALL;
			removeCard(null, sc1);
			sc2.setRevealed(true);
			addCard(null, sc2);		
			break;
		}
		case SELECTED_REVEALED_TO_ALL:{
			state = State.SELECTED_HIDDEN;
			sc2.setRevealed(false);
			break;
		}
		case SELECTED_HIDDEN:{
			state = State.NOT_SELECTED;	
			removeCard(null, sc2);
			break;
		}
		default:
			break;
		}
		
	}

	@Override
	public void longClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Handler createHandler() {
		Handler res=null;
		switch (state) {
		case NOT_SELECTED:{
			break;}
		case SELECTED_REVEALED_TO_PLAYER:{
			res = new PrivatePlayerHandler();
			break;}
		case SELECTED_REVEALED_TO_ALL:{
			res = new RevealedPlayerHandler();
			break;
		}
		case SELECTED_HIDDEN:{
			res = new HiddenPlayerHandler();
			break;
		}
		default:
			break;
		}
		
		return res;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.PLAYER;
	}

}

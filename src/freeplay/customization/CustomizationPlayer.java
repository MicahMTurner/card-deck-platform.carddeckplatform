package freeplay.customization;

import handlers.PlayerEventsHandler;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import utils.Player;
import utils.droppableLayouts.DroppableLayout.LayoutType;

public class CustomizationPlayer extends Player implements CustomizationItem {

	public CustomizationPlayer(utils.Position.Player globalPosition) {
		super(null, globalPosition, 0,null,  LayoutType.LINE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void longClick() {
		// TODO Auto-generated method stub
		
	}

}

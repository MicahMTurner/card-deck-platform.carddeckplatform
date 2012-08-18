package durak;

import client.controller.ClientController;
import utils.Button;
import utils.Point;
import utils.Position;
import utils.Public;
import utils.droppableLayouts.DroppableLayout;
import handlers.ButtonEventsHandler;

public class ButtonHandler implements ButtonEventsHandler {
	
	@Override
	public void onClick() {
		// if I am the attacked player then I may end the round
		// TODO: add enable timer - enable the end round button only after several seconds after the last card was put in public.
		if(Durak.isAttacked(ClientController.get().getMe()))	
			ClientController.get().sendAPI().endRound();
		
	}

}

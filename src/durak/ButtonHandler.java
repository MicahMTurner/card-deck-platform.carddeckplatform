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
		System.out.println("bla bla");
		if(Durak.isAttacked(ClientController.get().getMe()))	
			ClientController.get().endRound();
		
	}

}

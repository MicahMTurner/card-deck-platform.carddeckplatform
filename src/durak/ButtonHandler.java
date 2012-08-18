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

			
		ClientController.get().endRound();
		
	}

}

package durak;

import communication.actions.EndTurnAction;
import communication.link.ServerConnection;
import communication.messages.EndTurnMessage;
import communication.messages.RequestCardMessage;

import client.controller.ClientController;
import utils.Button;
import utils.DeckArea;
import utils.Point;
import utils.Position;
import utils.Public;
import utils.droppableLayouts.DroppableLayout;
import handlers.ButtonEventsHandler;

public class ButtonHandler implements ButtonEventsHandler {
	
	@Override
	public void onClick() {
		//ClientController.get().requestCard(Durak.deckId, 1);
		
		//ClientController.get().getZone(Durak.deckId).peek().moveTo(ClientController.get().getZone(Durak.deckId), ClientController.get().getMe());
		
		
				// if I am the attacked player then I may end the round
		// TODO: add enable timer - enable the end round button only after several seconds after the last card was put in public.
		if(Durak.isAttacked(ClientController.get().getMe())){
			ClientController.get().endRound();
			ClientController.get().sendAPI().endRound();
		}
			
		
	}

}

package war.actions;

import communication.link.ServerConnection;
import communication.messages.Message;

import client.controller.ClientController;
import client.controller.actions.ClientAction;
import client.controller.actions.EndTurnAction;
import logic.client.GameLogic;
import logic.client.Player;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;

public class TurnAction extends ClientAction{
	Player.Position position;
	public TurnAction(Player.Position position) {
		this.position=position;
		
	}

	@Override
	/**
	 * start turn
	 */
	public void incoming() {
		if (GameStatus.me.getHand().size()==0){
			ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
		}
		ClientController.getController().playerTurn(position);
		
	}

	@Override
	/**
	 * end turn
	 */
	public void outgoing() {
		//only incoming from host
	}

}

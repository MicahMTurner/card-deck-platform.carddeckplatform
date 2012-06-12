package president;

import java.util.ArrayList;
import java.util.Queue;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;

import utils.Button;
import utils.Card;
import utils.Deck;
import utils.Point;
import utils.Position;
import utils.Public;
import utils.Position.Player;
import utils.StandartCard;
import utils.droppableLayouts.DroppableLayout;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.gui.entities.Droppable;
import logic.client.Game;

public class President extends Game{
	static public boolean passed;
	private Position.Player startingPlayer=null;

	@Override
	public Deck getDeck() {		
		return new Deck(new CardHandler(), true);
	}

	@Override
	public Integer onRoundEnd() {
		passed=false;
		Public publicZone=(Public) ClientController.get().getZone(Position.Public.MID);
		int nextRoundStartingPlayer=publicZone.peek().getOwner().getId();
		publicZone.clear();
		for (Droppable droppable : droppables){
			droppable.onRoundEnd(null);
		}

		return nextRoundStartingPlayer;
	}
	public President() {
		passed=false;
	}
	@Override
	public Queue<Player> setTurns() {		
		return utils.Turns.counterClockWise(startingPlayer);
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int cardsForEachPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void dealCards() {
		int deckSize=deck.getSize();
		
		int numOfPlayers=players.size();
		
		ArrayList<ArrayList<Card>> playersCards= new ArrayList<ArrayList<Card>>();
		for (int i=0;i<numOfPlayers;i++){
			playersCards.add(new ArrayList<Card>());
		}		
		for (int i=0;i<deckSize;i++){			
			Card card=deck.drawCard();			
			playersCards.get(i%players.size()).add(card);
			
			//starting player is the one with 3 club in his hand
			if (((StandartCard)card).getValue()==3 && ((StandartCard)card).getColor().equals(StandartCard.Color.CLUB)){
				int startingPlayerId=players.get(i%numOfPlayers).getId();				
				for (Position.Player pos : Position.Player.values()){
					if (startingPlayerId==pos.getId()){
						startingPlayer=pos;
						break;
					}
				}
								
			}
			
		}
		
		for (int i=0;i<players.size();i++){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(playersCards.get(i),players.get(i).getId())));
		}
		
	}

	@Override
	public void setLayouts(ArrayList<Droppable> publics, ArrayList<Button> buttons) {
		publics.add(new Public(new PublicAndButtonHandler(), Position.Public.MID, DroppableLayout.LayoutType.HEAP, new Point(10,13)));
		buttons.add(new Button(new ButtonHandler(),Position.Button.BOTLEFT));
	}

	@Override
	public String toString() {		
		return "President";
	}

	@Override
	public utils.Player getPlayerInstance(PlayerInfo playerInfo,
			Player position, int uniqueId) {		
		return new utils.Player(playerInfo, position, uniqueId, new PlayerHandler(), DroppableLayout.LayoutType.LINE);
	}
	

}

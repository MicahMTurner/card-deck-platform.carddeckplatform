package war;

import client.controller.ClientController;
import carddeckplatform.game.GameStatus;
import utils.Card;
import utils.Player;
import utils.Public;
import handlers.PublicEventsHandler;

public class PublicHandler implements PublicEventsHandler{
	private int cardsPlacedWhileTie=0;
	private void getCards(Public publicArea, Player player){
		for (Card card : publicArea.getCards()){
			player.addCard(null,card);
		}
		publicArea.clear();
	}
	@Override
	public boolean onCardAdded(Public publicArea,Player player, Card card) {
		
		if (War.tie && cardsPlacedWhileTie<2){
			card.hide();
			cardsPlacedWhileTie++;
		}else{
			
			card.reveal();
		if (ClientController.getController().isMyTurn()){
			publicArea.addCard(player,card);
			Public otherPublic=(Public) ClientController.getController().getZone("public2");	
			Player otherPlayer=(Player) ClientController.getController().getZone("otherPlayer");
			if (otherPublic.cardsHolding()==publicArea.cardsHolding()){
				
				War.tie=false;
				if (otherPublic.peek().getValue()==card.getValue()){
					//tie
					War.tie=true;

				}
				else if (otherPublic.peek().getValue()>card.getValue()){
					//	lost
					
					ClientController.guiAPI().moveCards(publicArea.getCards(),otherPlayer.getMyId(), true, false);
					getCards(publicArea,otherPlayer);
					ClientController.guiAPI().moveCards(otherPublic.getCards(),otherPlayer.getMyId(), true, false);					
					getCards(otherPublic,otherPlayer);
					player.endTurn();
				}
				else{
					//	won
					
					ClientController.guiAPI().moveCards(publicArea.getCards(),player.getMyId(), true, false);
					getCards(publicArea,player);
					ClientController.guiAPI().moveCards(otherPublic.getCards(),player.getMyId(), true, false);					
					getCards(otherPublic,player);
					if (player.getUsername().equals(GameStatus.username)){
					
					}
				
				}
			}
		}
		}
		return true;
	}

	
	@Override
	public boolean onCardRemoved(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onCardRevealed(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onRoundEnd(Public publicZone, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

}

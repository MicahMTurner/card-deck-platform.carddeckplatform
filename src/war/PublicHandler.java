package war;

import java.util.ArrayList;
import client.controller.ClientController;
import client.gui.entities.GuiPlayer;
import carddeckplatform.game.GameStatus;
import utils.Card;
import utils.Player;
import utils.Position;
import utils.Public;
import utils.StandartCard;
import handlers.PublicEventsHandler;

public class PublicHandler implements PublicEventsHandler{
	private int cardsPlacedWhileTie=0;
	private void getCards(Public publicArea, Player player){
		for (StandartCard card : ((ArrayList<StandartCard>)((ArrayList)publicArea.getCards()))){
			player.addCard(card);
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
		if (ClientController.getController().getMe().isMyTurn()){			
			Public otherPublic=getOtherPublic(publicArea);	
			Player otherPlayer=((GuiPlayer) ClientController.getController().getZone(card.getOwner())).getPlayer();
			if (otherPublic.cardsHolding()==publicArea.cardsHolding()){
				
				War.tie=false;
				if (((StandartCard)otherPublic.peek()).getValue()==((StandartCard)card).getValue()){
					//tie
					War.tie=true;

				}
				else if (((StandartCard)otherPublic.peek()).getValue()>((StandartCard)card).getValue()){
					//	lost
					
					ClientController.guiAPI().moveCards(publicArea.getCards(),otherPlayer.getId(), true, false);
					getCards(publicArea,otherPlayer);
					ClientController.guiAPI().moveCards(otherPublic.getCards(),otherPlayer.getId(), true, false);					
					getCards(otherPublic,otherPlayer);
					if (player.isMyTurn()){
						player.endTurn();
					}
				}
				else{
					//	won
					
					ClientController.guiAPI().moveCards(publicArea.getCards(),player.getId(), true, false);
					getCards(publicArea,player);
					ClientController.guiAPI().moveCards(otherPublic.getCards(),player.getId(), true, false);					
					getCards(otherPublic,player);
					if (player.getUserName().equals(GameStatus.username)){
					
					}
				
				}
			}
		}
		}
		return true;
	}

	
	private Player getOtherPlayer(Player player) {
		// TODO Auto-generated method stub
		return null;
	}
	private Public getOtherPublic(Public publicArea) {
		Public answer=null;
		if (publicArea.getPosition().equals(Position.Public.MIDLEFT)){
			answer=(Public) ClientController.getController().getZone(Position.Public.MIDRIGHT);
		}else{
			answer=(Public) ClientController.getController().getZone(Position.Public.MIDLEFT);
		}
		return answer;
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

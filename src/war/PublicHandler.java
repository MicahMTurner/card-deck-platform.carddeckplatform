package war;

import java.util.ArrayList;

import communication.link.ServerConnection;
import communication.messages.LoseMessage;

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
	private boolean guiLocked;
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
			cardsPlacedWhileTie=0;
			Player me=ClientController.get().getMe();
			
			Public otherPublic=getOtherPublic(publicArea);	
			Player otherPlayer=((GuiPlayer) ClientController.get().getZone(card.getOwner())).getPlayer();
			
			if (publicArea.cardsHolding()!=otherPublic.cardsHolding()){
				if( me.isMyTurn() && !War.tie){
					me.endTurn();
				}
			}
			else{
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
					if (me.equals(player) && me.isMyTurn()){
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
				if (me.isEmpty()){
					ClientController.get().declareLoser();
					ClientController.get().disableUi();
				}else{
					ClientController.get().declareWinner();
					ClientController.get().disableUi();
					
				}
			}
			
			
		}
		
		return true;
	}

	
	
	private Public getOtherPublic(Public publicArea) {
		Public answer=null;
		if (publicArea.getPosition().equals(Position.Public.MIDLEFT)){
			answer=(Public) ClientController.get().getZone(Position.Public.MIDRIGHT);
		}else{
			answer=(Public) ClientController.get().getZone(Position.Public.MIDLEFT);
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

package war;

import handlers.PublicEventsHandler;

import java.util.ArrayList;

import utils.Card;
import utils.Player;
import utils.Position;
import utils.Public;
import utils.StandartCard;
import client.controller.ClientController;

public class PublicHandler implements PublicEventsHandler{
	private int cardsPlacedWhileTie=0;
	private boolean guiLocked=false;
	
	@Override
	public boolean onCardAdded(Public publicArea,Player byWhom, Card card) {
		card.setCoord(publicArea.getX(), publicArea.getY());
		if (War.tie && cardsPlacedWhileTie<2){
			card.hide();
			cardsPlacedWhileTie++;
		}else{
			
		
			//just placed last card for tie state,disable gui if wasn't my turn
			if (cardsPlacedWhileTie==2 && this.guiLocked){
				ClientController.get().disableUi();
			}
			
			cardsPlacedWhileTie=0;
			card.reveal();		
			//get other player/public
			Public otherPublic=getOtherPublic(publicArea);
			
			//check if both public places don't have equal number of cards
			if (publicArea.cardsHolding()!=otherPublic.cardsHolding()){
				//if my turn and not tie state, end turn
				if( byWhom.equals(ClientController.get().getMe()) && !War.tie){
					byWhom.endTurn();
				}
			}else{
				Player otherPlayer=((Player) ClientController.get().getZone(otherPublic.peek().getOwner()));
				calculateRoundWinner(publicArea,otherPublic,byWhom,otherPlayer,card);
				checkAndDeclareGameWinner(byWhom,otherPlayer);
			}
		}
		return true;
	}
	
	private void checkAndDeclareGameWinner(Player byWhom, Player otherPlayer) {
		if (!War.tie){
			if (ClientController.get().getMe().isEmpty()){
				ClientController.get().declareLoser();
				ClientController.get().disableUi();
			}else{
				if (otherPlayer.isEmpty() || byWhom.isEmpty()){
					ClientController.get().declareWinner();
					ClientController.get().disableUi();
				}
			}
		}
		
	}	

	private void getCards(Public publicArea, Player player){
		for (StandartCard card : ((ArrayList<StandartCard>)((ArrayList)publicArea.getCards()))){
			card.moveTo(publicArea,player,true,false);
		}
		//publicArea.clear();
	}
	
	
	private void calculateRoundWinner(Public publicArea,Public otherPublic, Player byWhom,Player otherPlayer, Card card) {
		Player me=ClientController.get().getMe();
			//disable UI after tie state is over, if it isn't my turn 
			if (War.tie && !me.isMyTurn()){
				ClientController.get().disableUi();
			}
			War.tie=false;		
			
			if (((StandartCard)otherPublic.peek()).getValue()==((StandartCard)card).getValue()){
				//tie
				War.tie=true;
				if (!me.isMyTurn()){					
					ClientController.get().enableUi();
				}				
			}
			
			else{
				Player winner=getWinner(otherPublic, otherPlayer, byWhom, card);
				//move cards from public areas to winner
				//ClientController.guiAPI().moveCards(publicArea.getCards(),winner.getId(), true, false);
				getCards(publicArea,winner);
				//ClientController.guiAPI().moveCards(otherPublic.getCards(),winner.getId(), true, false);					
				getCards(otherPublic,winner);
				if (winner.equals(otherPlayer) && me.isMyTurn()){
					me.endTurn();
				}
			}

	}

	private Player getWinner(Public otherPublic,Player otherPlayer,Player byWhom, Card card) {
		
		if (((StandartCard)otherPublic.peek()).getValue()>((StandartCard)card).getValue()){					
			//player who didn't move the card won
			return otherPlayer;			
		}
		else{
			//player who moved the card won
			return byWhom;
		}
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

//package war;
//
//
//import handlers.PublicEventsHandler;
//
//import java.util.ArrayList;
//
//import utils.Card;
//import utils.Player;
//import utils.Position;
//import utils.Public;
//import utils.StandartCard;
//import client.controller.ClientController;
//
//public class PublicHandler implements PublicEventsHandler{
//	private int cardsPlacedWhileTie=0;
//	//private boolean guiLocked=false;
//	
//	@Override
//	public boolean onCardAdded(Public publicArea,Player byWhom, Card card) {
//		
//		boolean answer=false;
//		Card cardInPublic=publicArea.peek();
//
//		if (card.getOwner()==(byWhom.getId())){	
//			if (card.getOwner()==(cardInPublic.getOwner())){				
//				if (War.tie){
//					if (cardsPlacedWhileTie<2){				
//						card.hide();
//						++cardsPlacedWhileTie;
//					}else{
//						if(cardsPlacedWhileTie==3){
//							answer=false;
//						}else{
//							//already placed 2 cards upside-down
//							card.reveal();
//							Public midright=(Public) ClientController.get().getZone(Position.Public.MIDRIGHT);
//							Public midleft=(Public) ClientController.get().getZone(Position.Public.MIDLEFT);
//							
//							++cardsPlacedWhileTie;
//							if (midright.cardsHolding()==midleft.cardsHolding()){
//								War.tie=false;
//								ClientController.get().endRound();
//								//ClientController.get().getMe().endTurn();	
//							}
//						}
//					}
//				}else{					
//					cardsPlacedWhileTie=0;
//					card.reveal();
//					if (checkForEndRound()){
//						ClientController.get().endRound();
//					}else{
//						ClientController.get().getMe().endTurn();
//					}
//				}
//				answer=true;
//			}
//		}
//		return answer;
//	}
//	
//	
//	private boolean checkForEndRound() {
//		Public midRightPublic=(Public) (ClientController.get().getZone(Position.Public.MIDRIGHT));	// add methods.
//		Public midLeftPublic=(Public) (ClientController.get().getZone(Position.Public.MIDLEFT));		
//		if (!midRightPublic.isEmpty() && !midLeftPublic.isEmpty()){			
//			if (midRightPublic.cardsHolding()==midLeftPublic.cardsHolding() && !War.tie){
//				return true;
//			}
//		}
//		return false;
//	}
//
//
//	@Override
//	public boolean onCardRemoved(Public publicZone, Player player, Card card) {
//		
//		return false;
//	}
//	@Override
//	public boolean onCardRevealed(Public publicZone, Player player, Card card) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//	@Override
//	public boolean onRoundEnd(Public publicZone, Player player) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//	@Override
//	public boolean onFlipCard(Card card) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//}

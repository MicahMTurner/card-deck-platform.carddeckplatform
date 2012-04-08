//package war;
//
//import java.util.ArrayList;
//
//import client.controller.Controller;
//
//import logic.actions.CardsActions;
//import logic.actions.PublicActions;
//import logic.card.CardLogic;
//import logic.client.Deck;
//import logic.client.GameLogic;
//import logic.client.Player;
//
//public class Client extends GameLogic implements CardsActions,PublicActions{
//
//	@Override
//	public String lost() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void myTurn() {		
//		if (client.getClient().cardsInHand()==0){
//			Controller.outgoingAPI().lose();
//		}
//		
//	}
//
//	/**
//	 * no deck in war game. each has his own cards
//	 */
//	@Override
//	public CardLogic drawCard() {		
//		return null;
//	}
//
//	@Override
//	public CardLogic gotCardFromDeck() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void showCard(Player player,CardLogic card) {
//		
//		//nothing to do when card is revealed
//	}
//
//	@Override
//	public void hideCard(Player player,CardLogic card) {
//		//nothing to do when card i showed
//	}
//
//	@Override
//	public void putInPublic(Player player,CardLogic card) {
//		//nothing to do
//		
//	}
//
//	@Override
//	public void removeFromPublic(Player player,CardLogic card) {
//		//nothing to do
//	}
//
//	@Override
//	public void cardGiven(Player player,CardLogic card) {		
//		client.getClient().addCard(card);
//	}
//
//	@Override
//	public void giveCard(Player from,Player to,CardLogic card) {
//		client.getClient().removeCard(card);
//		
//	}
//
//	@Override
//	public void placedInPublic(Player player, CardLogic card) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void dealCards(Deck deck, ArrayList<Player> players) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}

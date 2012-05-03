//package client.controller.actions;
//
//import java.util.ArrayList;
//
//import utils.Card;
//
//import communication.actions.Action;
//
//import logic.card.CardLogic;
//import client.controller.ClientController;
//
//
//public class DealCardAction implements Action{
//	private ArrayList<Card> cards = new ArrayList<Card>();
//	private String areaName;
//	private boolean revealWhileMoving;
//	private boolean revealAtEnd;
//	
//	public DealCardAction(ArrayList<Card> cards, String areaName,boolean revealwhileMoving,boolean revealAtEnd){		
//		this.areaName = areaName;
//		for(Card card : cards){
//			this.cards.add(card);
//		}
//		this.revealAtEnd=revealAtEnd;
//		this.revealWhileMoving=revealwhileMoving;
//	}
//
//
//	@Override
//	public void execute() {
//		ClientController.getController().cardAdded(cards, null, areaName, revealWhileMoving, revealAtEnd);
//
//		//update gui
//		//ClientController.getController().getGui().addDraggable(cardLogics, );
//		
//	}
//}

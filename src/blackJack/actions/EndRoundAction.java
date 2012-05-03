//package blackJack.actions;
//
//import java.util.ArrayList;
//import java.util.Stack;
//
//import blackJack.BlackJack;
//import logic.card.CardLogic;
//import logic.client.LogicDroppable;
//import communication.link.ServerConnection;
//import communication.messages.Message;
//
//import client.controller.ClientController;
//import client.controller.actions.ClientAction;
//import client.gui.entities.Table;
//
//public class EndRoundAction extends ClientAction{
//	
//	private void clearCards(LogicDroppable player){
//		ArrayList<CardLogic>logiccards=new ArrayList<CardLogic>();
//		for (CardLogic logicCard : player.getCards()){
//			logiccards.add(logicCard);
//		}
//		for (CardLogic cardLogic : logiccards){
//			ClientController.getController().runCardAnimation(cardLogic, -100,-100, 1000, 10, true, false, Table.GetMethod.PutInBack);
//		}
//		//ClientController.getController().runCardAnimation((Stack<CardLogic>)player.getCards().clone(), -100,-100, 1000, 10, true, false, Table.GetMethod.PutInBack);
//		
//		
//	}
//	private int sumCardsInHand(LogicDroppable player){
//		int sum=0;
//		for (CardLogic card : player.getCards()){
//			int value=card.getValue();
//			if (value>10 ){
//				sum+=10;
//			}
//			else if (value==14){
//				sum+=11;
//			}else{
//				sum+=value;
//			}
//		}
//		return sum;
//	}
//	
//	@Override
//	public void incoming() {
//		ClientController.getController().disableUi();
//		LogicDroppable me= BlackJack.getDroppables().get(0);
//		LogicDroppable other= BlackJack.getDroppables().get(1);
//		int mySum=sumCardsInHand(me);
//		int otherSum=sumCardsInHand(other);
//		
////		for (LogicDroppable droppable : BlackJack.getDroppables()){
////			if (droppable.getType().equals(LogicDroppable.Type.PLAYER)){
////				int othersSum=0;
////				for (CardLogic card : droppable.getCards()){
////					othersSum+=card.getValue();
////				}
//		if (mySum>21 || (otherSum<21 && mySum<otherSum)){		
//			//lose
//			ClientController.getController().declareLoser();	
//		}else {
//			ClientController.getController().declareWinner();
//		}
//		clearCards(me);
//		clearCards(other);
//		me.getCards().clear();
//		other.getCards().clear();
////			}
////		}
//		
//	}
//
//	@Override
//	public void outgoing() {
//		ServerConnection.getConnection().getMessageSender().send(new Message(this));
//		
//	}
//
//}

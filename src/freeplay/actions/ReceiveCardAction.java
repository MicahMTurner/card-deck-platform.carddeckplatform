//package freeplay.actions;
//
//import java.util.ArrayList;
//
//import logic.card.CardLogic;
//import communication.link.ServerConnection;
//import communication.messages.Message;
//
//import client.controller.ClientController;
//import client.controller.actions.ClientAction;
//
//public class ReceiveCardAction extends ClientAction {
//
//	private ArrayList<CardLogic> cardLogics = new ArrayList<CardLogic>();
//	private int fromDroppableId;
//	private int toDroppableId;
//	
//	public ReceiveCardAction(ArrayList<CardLogic> cardLogics, int fromDroppableId,int toDroppableId){
//		this.cardLogics = cardLogics;
//		this.fromDroppableId = fromDroppableId;
//		this.toDroppableId=toDroppableId;
//	}
//	
//	@Override
//	public void incoming() {
//	
//		for(CardLogic cardLogic : cardLogics){
//			ClientController.getController().addCard(cardLogic);			
//		}
//		
//		//update gui		
//		ClientController.getController().getGui().addDraggable(cardLogics, ClientController.getController().getGui().getDroppableById(fromDroppableId)
//				,ClientController.getController().getGui().getDroppableById(toDroppableId));
//		
//	}
//
//	@Override
//	public void outgoing() {
//		System.out.println("ReceiveCardAction: outgoing");
//		ServerConnection.getConnection().getMessageSender().send(new Message(this));
//		
//	}
//
//}

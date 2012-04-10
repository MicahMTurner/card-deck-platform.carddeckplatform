//package communication.messages;
//
////import logic.host.Host;
//import carddeckplatform.game.TableView;
//import client.controller.ClientController;
//
////import com.google.gson.annotations.SerializedName;
//import communication.client.ClientMessageHandler;
//
//
//
//public class EndCardMotionMessage extends Message {
//	
//	public EndCardMotionMessage(){
//		messageType = "EndCardMotionMessage";
//	}
//	
//	public EndCardMotionMessage(int cardId){
//		messageType = "EndCardMotionMessage";
//		
//		
//		this.cardId = cardId;
//	
//	}
//	
//	public int cardId;
//
//	@Override
//	public void serverAction(String connectionId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void clientAction() {
//		// TODO Auto-generated method stub
//		//controller.endDraggableMotion(cardId);
//		ClientController.getController().incomingAPI().endCardMotion(cardId);
//	}
//
//
//}

//package communication.server;
//
//import communication.link.Sender;
//import communication.messages.Message;
//
//public class ServerMessageSender {
//	
//	/**
//	 * sendToAll - sends the message to every user.
//	 * @param msg
//	 */
//	public void sendToAll(Message msg){
//		for(Connection serverTask : ConnectionsManager.getAllConections()){
//			serverTask.send(msg);
//		}
//	}
//	
//	/**
//	 * sendToAllExcptMe - sends the message to every user except for the user 'id'.
//	 * @param msg
//	 * @param id
//	 */
//	public void sendToAllExcptMe(Message msg , String id){
//		for(Connection serverTask : ConnectionsManager.getAllConections()){
//			if(!serverTask.getId().equals(id))
//				serverTask.send(msg);
//		}
//	}
//	
//	/**
//	 * sendTo - sends the message to user 'id'.
//	 * @param msg
//	 * @param id
//	 */
//	public void sendTo(Message msg, String id){
//		for(Connection serverTask : ConnectionsManager.getAllConections()){
//			if(serverTask.getId().equals(id))
//				serverTask.send(msg);
//		}
//	}
//	
//}

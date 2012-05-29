//package server.controller.actions;
//
//
//
//import utils.Position;
//import communication.messages.Message;
//import communication.server.ConnectionsManager;
//
//public class SendToAll extends ServerAction {
//	private Message msg;
//	
//	public SendToAll(Message msg){
//		this.msg = msg;
//	}
//	
//	@Override
//	public void execute(int id) {
//		ConnectionsManager.getConnectionsManager().sendToAll(msg);
//	}
//
//}

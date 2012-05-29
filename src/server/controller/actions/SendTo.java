//package server.controller.actions;
//
//
//
//import utils.Position;
//import communication.messages.Message;
//import communication.server.ConnectionsManager;
//
//public class SendTo extends ServerAction {
//
//	private Message msg;
//	
//	public SendTo(Message msg){
//		this.msg = msg;
//		
//	}
//	
//	@Override
//	public void execute(int id) {		
//		ConnectionsManager.getConnectionsManager().sendTo(msg, id);
//	}
//
//}

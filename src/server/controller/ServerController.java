package server.controller;

import communication.server.ConnectionsManager;

public class ServerController {


	
	//-------Singleton implementation--------//
		private static class ControllerHolder
		{
			private final static ServerController serverController=new ServerController();
		}
		
				
		/**
		 * get Controller instance
		 */
		public static ServerController getServerController(){
			return ControllerHolder.serverController;
		}

	
	
	
	//constructor
	private ServerController(){
	}
	
	public void endTurnCommand(){
		//ConnectionsManager.getConnectionsManager().sendTo(msg, id);
		
	}
	public void putInPublicCommand(String connectionId){
		
	}
	public void ReceiveCardCommand(){
		
	}
	public void turnCommand(){
		
	}
	
	public void RoundWinner(String connectionId, String winner) {
		// TODO Auto-generated method stub
		
	}

	public void recieveCardCommand(String connectionId) {
		//ConnectionsManager.getConnectionsManager().sendToAllExcptMe( connectionId);
		
	}
	
	
	
	

}

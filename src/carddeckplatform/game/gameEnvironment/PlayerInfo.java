package carddeckplatform.game.gameEnvironment;

import java.io.Serializable;

import android.view.Surface;

import utils.Player;
import utils.Position;

import client.controller.ClientController;
import client.controller.LivePosition;

import communication.actions.LivePositionChangedAction;
import communication.link.ServerConnection;
import communication.messages.Message;
import communication.messages.SwapRequestMessage;

public class PlayerInfo implements Serializable{	
		private boolean isServer;
		private String username;
		private Double azimut=null;
		boolean flag=false;
		public PlayerInfo() {
			isServer=false;
			azimut=null;
		}
		
		public boolean isServer() {
			return isServer;
		}
		public void setServer(boolean isServer) {
			this.isServer = isServer;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public void setAzimute(Double newAzimut) {
			
			if (azimut!=null){
				Player me=ClientController.get().getMe();
				if (me!=null){
					if (!(me.getGlobalPosition().equals(LivePosition.translatePositionByAzimute(newAzimut-LivePosition.SAFETYDISTANCE)))
							&& !(me.getGlobalPosition().equals(LivePosition.translatePositionByAzimute(newAzimut+LivePosition.SAFETYDISTANCE)))){
					
						//get new position player want to swap to
						Position.Player newPosition=LivePosition.translatePositionByAzimute(newAzimut);					
						
						//send swap-position request message		
						ServerConnection.getConnection().send(new SwapRequestMessage(ClientController.get().getMe().getGlobalPosition(),newPosition));
					}
				}
			}
			this.azimut=newAzimut;
		
		}
		public Double getAzimute() {
			return azimut;
		}
		
		
	

}

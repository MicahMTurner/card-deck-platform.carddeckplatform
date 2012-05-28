package carddeckplatform.game.gameEnvironment;

import java.io.Serializable;

import android.view.Surface;

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
		private Double azimut;
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
		public void setAzimute(double newAzimut) {
			
			if (azimut!=null){
				if (!(LivePosition.translatePositionByAzimute(azimut).equals(LivePosition.translatePositionByAzimute(newAzimut-LivePosition.SAFETYDISTANCE)))
						&& !(LivePosition.translatePositionByAzimute(azimut).
						equals(LivePosition.translatePositionByAzimute(newAzimut+LivePosition.SAFETYDISTANCE)))){
					
				//if (Math.abs(azimut-newAzimute)>55){
					//get new position player want to swap to
					Position.Player newPosition=LivePosition.translatePositionByAzimute(newAzimut);
					//check is position is clear
					if (ClientController.get().getZone(newPosition)==null){
						//newPosition=null;
						//move to position
						
						//ClientController.get().positionUpdate(ClientController.get().getMe().getId(), newPosition);
						//ServerConnection.getConnection().send(new Message(new LivePositionChangedAction(ClientController.get().getMe().getId(), newPosition)));

					}//else{
						//position is not clear
						
						//send swap-position request message
					if (GameEnvironment.get().getDeviceInfo().getRotationAngle()==Surface.ROTATION_90){
						if(!flag){
						ServerConnection.getConnection().send(new SwapRequestMessage(ClientController.get().getMe().getGlobalPosition(),Position.Player.LEFT));
						flag=true;
						}else{
							ServerConnection.getConnection().send(new SwapRequestMessage(ClientController.get().getMe().getGlobalPosition(),ClientController.get().getMe().getGlobalPosition()));
							
						}
					}else{
						ServerConnection.getConnection().send(new SwapRequestMessage(ClientController.get().getMe().getGlobalPosition(),Position.Player.RIGHT));
					}
//					if (GameEnvironment.get().getDeviceInfo().getRotationAngle()==Surface.ROTATION_90){
//						ServerConnection.getConnection().send(new SwapRequestMessage(ClientController.get().getMe().getGlobalPosition(),Position.Player.LEFT));
//					}else{
//						ServerConnection.getConnection().send(new SwapRequestMessage(ClientController.get().getMe().getGlobalPosition(),Position.Player.TOP));
//					}
					//ServerConnection.getConnection().send(new SwapRequestMessage(ClientController.get().getMe().getGlobalPosition(),newPosition));
						//ServerConnection.getConnection().send(new SwapRequestMessage(
						//		new SwapRequestAction(ClientController.get().getMe().getGlobalPosition()),ClientController.get().getZone(newPosition).getId()));
						//update live position system about my request
						//LivePosition.get().waitForAgreement(ClientController.get().getZone(newPosition).getId(),newPosition);
					//}
					
				}			
			}
			this.azimut=newAzimut;
		
		}
		public double getAzimute() {
			return azimut;
		}
		
		
	

}

package communication.messages;

import logic.client.Player;
import logic.host.Host;



public class LoseMessage extends Message{
	@Override
	public void actionOnServer(Player.Position id){
		Host.playerLost(id);
	}
	
	@Override
	public void actionOnClient(){
		//only inform the server
	}

}

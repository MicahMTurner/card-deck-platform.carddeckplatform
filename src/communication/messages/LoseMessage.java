package communication.messages;

import utils.Position;
import logic.host.Host;



public class LoseMessage extends Message{
	@Override
	public void actionOnServer(Position.Player id){
		Host.playerLost(id);
	}
	
	@Override
	public void actionOnClient(){
		//only informs the server
	}

}

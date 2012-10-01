package communication.messages;

import logic.host.Host;



public class LoseMessage extends Message{
	@Override
	public void actionOnServer(int id){
		Host.playerLost(id);
	}
	
	@Override
	public void actionOnClient(){
		//only informs the server
	}

}

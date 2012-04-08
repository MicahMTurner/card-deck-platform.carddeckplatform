package war.messages;

import communication.messages.Message;

public class RoundWinnerMessage extends Message {

	String winner;
	
	public RoundWinnerMessage(String winner){
		this.winner = winner;
	}
	
	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		
	}

}

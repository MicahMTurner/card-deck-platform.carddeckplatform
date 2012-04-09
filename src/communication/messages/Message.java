package communication.messages;

import java.io.Serializable;

//import logic.host.Host;
import carddeckplatform.game.GameStatus;

import carddeckplatform.game.TableView;


public abstract class Message implements Serializable {
	public String messageType;
	
	public Object sender=GameStatus.localIp;
	
	public abstract void serverAction(String connectionId);
	
	public abstract void clientAction();

}

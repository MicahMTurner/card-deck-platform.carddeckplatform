package communication.messages;

import java.io.Serializable;

//import logic.host.Host;
import carddeckplatform.game.GameStatus;

import carddeckplatform.game.TableView;
import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public abstract class Message implements Serializable {
	public String messageType;
	
	public Object sender=GameStatus.localIp;
	
	public abstract void serverAction();
	
	public abstract void clientAction();

}

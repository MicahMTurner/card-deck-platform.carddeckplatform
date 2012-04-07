package communication.messages;

import java.io.Serializable;

import logic.host.Host;
import carddeckplatform.game.GameStatus;

import carddeckplatform.game.TableView;
import com.google.gson.annotations.SerializedName;
import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public abstract class Message implements Serializable {
	@SerializedName("messageType")
	public String messageType;
	
	public Object sender=GameStatus.localIp;
	
	public abstract void serverAction(ServerMessageHandler serverMessageHandler, Host host, ServerTask serverTask);
	
	public abstract void clientAction();

}

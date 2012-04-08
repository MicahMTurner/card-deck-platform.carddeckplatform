package communication.messages;

import carddeckplatform.game.GameStatus;

import carddeckplatform.game.TableView;
import com.google.gson.annotations.SerializedName;
import communication.server.ServerMessageHandler;

public abstract class Message {
	@SerializedName("messageType")
	public String messageType;
	
	public Object sender=GameStatus.localIp;
	
	public abstract void serverAction(ServerMessageHandler serverMessageHandler);
	
	public abstract void clientAction(TableView tableView);

}

package communication.messages;

import java.io.Serializable;

public interface MessageInterface extends Serializable {
	public void actionOnServer(int id);
	public void actionOnClient();
}

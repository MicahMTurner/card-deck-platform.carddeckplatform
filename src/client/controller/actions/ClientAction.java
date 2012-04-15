package client.controller.actions;

import java.io.Serializable;

import carddeckplatform.game.TableView;

public abstract class  ClientAction implements Serializable {
	
	
	public ClientAction() {
	}
	
	public abstract void incoming();
	public abstract void outgoing();

}

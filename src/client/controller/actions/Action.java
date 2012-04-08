package client.controller.actions;

import logic.client.GameLogic;
import carddeckplatform.game.TableView;

public abstract class  Action {
	protected TableView gui;
	protected GameLogic logic;
	public Action() {
	}
	public abstract void incoming();
	public abstract void outgoing();
	public void setGui(TableView gui) {
		this.gui = gui;
	}
	public void setLogic(GameLogic logic) {
		this.logic = logic;
	}
}

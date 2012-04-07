package client.controller.actions;

import logic.client.GameLogic;
import carddeckplatform.game.TableView;

public abstract class  Action {
	protected TableView gui;
	protected GameLogic logic;
	public Action(TableView gui,GameLogic logic) {
		this.gui=gui;
		this.logic=logic;
	}
	public abstract void incoming();
	public abstract void outgoing();
}

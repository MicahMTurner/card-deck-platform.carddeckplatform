package server.controller.actions;

import logic.client.Player;

public abstract class ServerAction {
	public abstract void execute(Player.Position id);
}

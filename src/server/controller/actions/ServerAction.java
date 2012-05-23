package server.controller.actions;

import utils.Position;

public abstract class ServerAction {
	public abstract void execute(Position.Player id);
}

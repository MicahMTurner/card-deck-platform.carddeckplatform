package server.controller.actions;

import java.io.Serializable;

public abstract class ServerAction implements Serializable {
	public abstract void execute(String id);
}

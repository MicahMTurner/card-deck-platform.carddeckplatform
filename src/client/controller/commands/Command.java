package client.controller.commands;

import client.controller.actions.Action;

public abstract class Command {
	
	protected Action action;
	public Command(Action action) {
		this.action=action;
	}
	public abstract void execute();	
}

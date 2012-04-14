package controller.commands;

import client.controller.actions.ClientAction;

public abstract class Command {
	
	protected ClientAction action;
	public Command(ClientAction action) {
		this.action=action;
	}
	public abstract void execute();	
}

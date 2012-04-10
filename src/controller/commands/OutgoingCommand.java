package controller.commands;

import client.controller.actions.ClientAction;

public class OutgoingCommand extends Command{

	public OutgoingCommand(ClientAction action) {
		super(action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		action.outgoing();
		
	}

}

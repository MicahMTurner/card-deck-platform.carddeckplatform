package controller.commands;

import client.controller.actions.ClientAction;

public class IncomingCommand extends Command{

	public IncomingCommand(ClientAction action) {
		super(action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		action.incoming();
		
	}

}

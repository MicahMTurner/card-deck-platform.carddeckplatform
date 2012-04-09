package controller.commands;

import client.controller.actions.Action;

public class IncomingCommand extends Command{

	public IncomingCommand(Action action) {
		super(action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		action.incoming();
		
	}

}

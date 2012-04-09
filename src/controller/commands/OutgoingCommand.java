package controller.commands;

import client.controller.actions.Action;

public class OutgoingCommand extends Command{

	public OutgoingCommand(Action action) {
		super(action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		action.outgoing();
		
	}

}

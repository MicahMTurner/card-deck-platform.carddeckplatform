package freeplay.customization;

import carddeckplatform.game.EditView;
import handlers.ButtonEventsHandler;
import utils.Button;

public class ToggleModeButton extends Button {
	private EditView editView;
	public ToggleModeButton(utils.Position.Button position, EditView editView) {
		super(null, position, "Mode");
		this.editView = editView;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(){
		editView.toggleMode();
	}
	
}

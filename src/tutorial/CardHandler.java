package tutorial;

import handlers.CardEventsHandler;
import utils.Card;
import client.controller.AutoHide;

public class CardHandler implements CardEventsHandler{

	@Override
	public void onReveal(Card card) {
		if (Tutorial.Stages.CANCELAUTOHIDE==Tutorial.currentStage){
			Tutorial.isBadJob=false;
			AutoHide.get().stop();
		}
	}

	@Override
	public void OnHide(Card card) {
		if (Tutorial.Stages.AUTOHIDE==Tutorial.currentStage){
			Tutorial.nextStage();
		}		
	}

	@Override
	public boolean onFlipCard(Card card) {
		return true;
	}
}

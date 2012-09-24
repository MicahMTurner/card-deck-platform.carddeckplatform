package tutorial;

import client.controller.AutoHide;
import utils.Card;
import handlers.CardEventsHandler;

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

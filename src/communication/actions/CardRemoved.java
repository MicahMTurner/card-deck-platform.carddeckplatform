package communication.actions;

import java.util.ArrayList;

import client.controller.ClientController;

import utils.Card;

public class CardRemoved implements Action{
	private ArrayList<Card> cards;
	private String from;
	
	public CardRemoved(ArrayList<Card> cards,String from) {
		this.cards=cards;
		this.from=from;
	}

	@Override
	public void execute() {
		ClientController.getController().removeCards(cards, from);
		
	}
}

package handlers;

import java.io.Serializable;

import utils.Card;

public interface Handler extends Serializable{
	public boolean onFlipCard(Card card);

}

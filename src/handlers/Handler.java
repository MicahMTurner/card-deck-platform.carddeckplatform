package handlers;

import java.io.Serializable;

import utils.Card;
/**
 * all handler interfaces extends this interface
 *
 */
public interface Handler extends Serializable{
	public boolean onFlipCard(Card card);

}

package logic.client;

import utils.DeckArea;

/**
 * abstract factory to create all tools needed for card games
 * (cards,table etc...)
 * 
 * @author Yoav
 *
 */
public interface ToolsFactory {
	
	public DeckArea createCards();
	//public Table createTable();
	
}

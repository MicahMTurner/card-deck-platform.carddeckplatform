package logic.client;

/**
 * abstract factory to create all tools needed for card games
 * (cards,table etc...)
 * 
 * @author Yoav
 *
 */
public interface ToolsFactory {
	
	public AbstractDeck createCards();
	//public Table createTable();
	
}

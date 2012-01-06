package communication.client;
import communication.link.Sender;
import communication.messages.*;


public class MessageSender {
	// sender - sends messages to the server.
	private Sender sender;
	
	/**
	 * setSender = sender setter.
	 * @param sender
	 */
	public void setSender(Sender sender){
		this.sender = sender;
	}
	
	/**
	 * MessageSender - default c'tor.
	 */
	public MessageSender(){}
	
	/**
	 * MessageSender - c'tor with Sender specification.
	 * @param sender
	 */
	public MessageSender(Sender sender){
		this.sender = sender;
	}
	
	/**
	 * endTurn - declares end of turn.
	 * @return
	 */
	public boolean endTurn(){
		return true;
	}
	
	/**
	 * takeCardFromDeck - take a card from deck.
	 * @return
	 */
	public boolean takeCardFromDeck(){
		return true;
	}
	
	/**
	 * putCard - put a card on some destination.
	 * @param card - a card
	 * @param destination - an object where we want to put the card. could be public area or other player.
	 * @param xCoordinate - x
	 * @param yCoordinate - y
	 * @return
	 */
	public boolean putCard(Object card , Object destination , float xCoordinate , float yCoordinate){
		return true;
	}
	
	/**
	 * showCard - flip over a card.
	 * @param card
	 * @return
	 */
	public boolean showCard(Object card){
		return true;
	}
	
	/**
	 * declare resignation.
	 * @return
	 */
	public boolean resign(){
		return true;
	}
}

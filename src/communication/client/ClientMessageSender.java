package communication.client;
import communication.entities.Client;
import communication.link.Sender;
import communication.messages.*;


public class ClientMessageSender {
	// sender - sends messages to the server.
	private Sender sender;
	// a client id in the network.
	private Client client;
	
	/**
	 * setClient - client setter.
	 * @param client
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * setSender = sender setter.
	 * @param sender
	 */
	public void setSender(Sender sender, Client client){
		this.sender = sender;
		this.client = client;
	}
	
	/**
	 * MessageSender - default c'tor.
	 */
	public ClientMessageSender(){}
	
	/**
	 * MessageSender - c'tor with Sender specification.
	 * @param sender
	 */
	public ClientMessageSender(Sender sender){
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
	 * moveCard - put a card on some destination.
	 * @param card - a card
	 * @param destination - an object where we want to put the card. could be public area or other player.
	 * @param xCoordinate - x
	 * @param yCoordinate - y
	 * @return
	 */
	public boolean moveCardCard(Object from, Object to, Object card, float xCoordinate , float yCoordinate){
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
	
	public boolean sendMessage(Message msg){
		sender.send(msg);
		return true;
	}
}

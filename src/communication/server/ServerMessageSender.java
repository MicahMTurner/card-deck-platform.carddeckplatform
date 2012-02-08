package communication.server;

import communication.entities.Client;
import communication.link.Sender;
import communication.messages.CardMotionMessage;

public class ServerMessageSender {
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
	public ServerMessageSender(){}
	
	/**
	 * MessageSender - c'tor with Sender specification.
	 * @param sender
	 */
	public ServerMessageSender(Sender sender){
		this.sender = sender;
	}
	
	/**
	 * giveTurn - give a turn to a player (client).
	 * @param target
	 * @return
	 */
	public boolean giveTurn(Client target){
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
	public boolean moveCard(Object from, Object to, Object card, float xCoordinate , float yCoordinate){
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
	 * declareWinner - declare a winner.
	 * @param client
	 * @return
	 */
	public boolean declareWinner(Client client){
		return true;
	}
	
	/** 
	 * cardMotion - sends card notion message.
	 * @param card
	 * @param xCoordinate
	 * @param yCoordinate
	 * @return
	 */
	public boolean cardMotion(Client c , int card , int xCoordinate , int yCoordinate){
		CardMotionMessage msg = new CardMotionMessage();
		msg.cardId = card;
		msg.X = xCoordinate;
		msg.Y = yCoordinate;
		
		
		
		//sender.sendTo((String)c.getId() , msg.messageType, msg);
		return true;
	}
}

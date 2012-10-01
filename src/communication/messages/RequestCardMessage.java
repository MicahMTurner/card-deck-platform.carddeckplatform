package communication.messages;

import java.util.ArrayList;

import utils.Card;
import utils.DeckArea;
import client.controller.ClientController;
import client.gui.entities.Droppable;

import communication.server.ConnectionsManager;

public class RequestCardMessage extends Message {
	
	private utils.Player applicant;
	private Integer targetID;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private int amount;
	
	private static Object lock = new Object();
	
	
	public RequestCardMessage(utils.Player applicant, Integer targetID, int amount){
		this.applicant = applicant;
		this.targetID = targetID;
		this.amount = amount;
		
	}
	
	public RequestCardMessage(utils.Player applicant, Integer targetID , Card card){
		this.applicant = applicant;
		this.targetID = targetID;
		cards.add(card);
	}
	
	@Override
	public void actionOnServer(int id){
		utils.Player player = (utils.Player)ClientController.get().getZone(applicant.getId());
		Droppable target = ClientController.get().getZone(targetID);
		synchronized (lock) {
			// gets the card from the target.
			for(int i=0; i<Math.min(amount, target.getCards().size()) ; i++){	
				Card card = target.peek();
				cards.add(card);
				
				System.out.println("GOT card: " + card.getId());
				((DeckArea)target).getMyCards().remove(card); // remove in target.
				player.getMyCards().add(card);
				
			}
			actionOnClient();
			ConnectionsManager.getConnectionsManager().sendToAllExcptHost(this);
			
		}
		
		
	}
	
	@Override
	public void actionOnClient(){
		utils.Player player = (utils.Player)ClientController.get().getZone(applicant.getId());
		Droppable target = ClientController.get().getZone(targetID);
		for(Card c : cards){
			ClientController.get().getCard(c.getId()).moveTo(target, player);
		}
	}

}

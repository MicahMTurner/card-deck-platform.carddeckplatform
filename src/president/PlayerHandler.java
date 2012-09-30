package president;

import client.controller.ClientController;
import utils.Card;
import utils.Player;
import utils.Position;
import utils.Public;
import utils.StandartCard;
import handlers.PlayerEventsHandler;

public class PlayerHandler implements PlayerEventsHandler{
	
	
	@Override
	public boolean onMyTurn(Player player) {
//		if (President.passed){
//			player.endTurn();
//		}else{
			Card topCard=(ClientController.get().getZone(Position.Public.MID)).peek();
			//check if no one placed any cards during the entire round
			if (topCard!=null && topCard.getOwner()==(ClientController.get().getMe().getId())){
//				Integer nextPlayerId=ClientController.get().endRound();
//				ClientController.sendAPI().endRound(nextPlayerId);
				
				ClientController.get().endRound();
				
			}
			
		//}
		return false;
	}

	@Override
	public boolean onTurnEnd(Player player) {
		return false;
	}

	@Override
	public boolean onCardAdded(Player target, Player player, Card card) {
		boolean answer=true;
		if (player.equals(ClientController.get().getMe())){
			card.reveal();
			answer=true;
		}
		
		return answer;
	}

	@Override
	public boolean onCardRemoved(Player player, Card card) {
		
		if (card.getOwner()==player.getId()){
			for (Card standartCard : player.getCards()){
				if (((StandartCard)standartCard).getValue()==3 
						&& ((StandartCard)standartCard).getColor().equals(StandartCard.Color.CLUB) && !card.equals(standartCard)){
					return false;	
				}
			}
			return true;
		}
		return true;
	}

	@Override
	public boolean onCardRevealed(Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onRoundEnd(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFlipCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

}

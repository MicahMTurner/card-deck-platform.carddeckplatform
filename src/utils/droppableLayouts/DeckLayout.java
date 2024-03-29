package utils.droppableLayouts;

import java.util.AbstractList;

import utils.Card;
import utils.Point;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

/**
 * 
 *	Arranges the cards in a heap formation and take into a count the ruler card option.
 *
 */

public class DeckLayout extends DroppableLayout  {

	private Card rulerCard=null;
	
	public DeckLayout(Droppable droppable) {
		super(droppable);
	}
	
	@Override
	public void rearrange(int index, float width, float height) {
		int numberOfCards = droppable.cardsHolding();
		if (numberOfCards == 0)
			return;
		
		// check if the ruler card steel belongs to the deck area.
		if(rulerCard!=null && !droppable.getCards().contains(rulerCard))
			rulerCard=null;
		
		
		if(rulerCard!=null)
			numberOfCards--;
		
		Point location = new Point(droppable.getX(), droppable.getY());
		float[][] animationArgs = new float[3][numberOfCards];
		
		for (int i = 0; i < numberOfCards; i++) {
			animationArgs[0][i] = i+1;
			animationArgs[1][i] = i+1;
			animationArgs[2][i] = 0;	
		}
		
		if(rulerCard==null){
			animate(droppable.getCards(),
					shift(normalizePosition(animationArgs, width/4, height/4),
							location.getX(), location.getY()), 1000);
		}else{
			AbstractList<Card> cards = droppable.getCards();
			cards.remove(rulerCard);
			
			Point offset = MetricsConvertion.pointRelativeToPx(new Point(5,0));
			
			
			rulerCard.reveal();
			rulerCard.setLocation(droppable.getX() - offset.getX() , droppable.getY());
			rulerCard.setAngle(270);
			
			animate(cards,
					shift(normalizePosition(animationArgs, width/4, height/4),
							location.getX(), location.getY()), 1000);
		}
	}

	@Override
	public LayoutType getType() {		
		return LayoutType.HEAP;
	}

	public boolean setRulerCard(Card card){
		if(this.rulerCard != null)
			return false;
		
		this.rulerCard = card;
		return true;
	}

	public boolean hasRulerCard() {
		if(this.rulerCard!=null)
			return true;
		return false;
	}
	
	public Card getRulerCard(){
		return rulerCard;
	}
}

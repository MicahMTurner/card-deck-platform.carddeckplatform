
package freeplay.customization;
import java.util.AbstractList;

import utils.Card;
import utils.Player;
import utils.Point;
import utils.Position;
import utils.droppableLayouts.DroppableLayout.LayoutType;
import client.gui.entities.Droppable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CustomizationDroppable extends Droppable {

	public CustomizationDroppable(int id, Position position, Point scale,
			LayoutType layoutType) {
		super(id, position, layoutType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Card peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRoundEnd(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deltCard(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AbstractList<Card> getMyCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCardAdded(Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCardRemoved(Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int cardsHolding() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void AddInPlace(Card card, int place) {
		// TODO Auto-generated method stub
		
	}
	
}

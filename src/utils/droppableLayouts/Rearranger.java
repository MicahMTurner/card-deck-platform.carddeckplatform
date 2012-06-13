package utils.droppableLayouts;

import java.util.AbstractList;

import utils.Card;

public interface Rearranger {
	public void rearrange(int index, float width, float height, AbstractList<Card> cards);
}

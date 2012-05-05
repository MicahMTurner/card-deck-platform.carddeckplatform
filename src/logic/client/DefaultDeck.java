//package logic.client;
//
//import handlers.CardEventsHandler;
//import utils.Card;
//
//public class DefaultDeck extends AbstractDeck{
//	private final int timesToShuffle=2;
//	public DefaultDeck(CardEventsHandler handler) {
//		int id=1;
//		for (int i=2;i<=14;i++){
//			
//			cards.add(new Card(i,Card.Color.DIAMOND,handler));
//			cards.add(new Card(i,Card.Color.HEART,handler));
//			cards.add(new Card(i,Card.Color.CLUB,handler));
//			cards.add(new Card(i,Card.Color.SPADE,handler));
//			
//			
//		}
//		super.shuffle(timesToShuffle);
//	}
//
//}

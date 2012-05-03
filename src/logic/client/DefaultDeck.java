//package logic.client;
//
//import logic.builtIn.defaultCards.Club;
//import logic.builtIn.defaultCards.Diamond;
//import logic.builtIn.defaultCards.Heart;
//import logic.builtIn.defaultCards.Spade;
//
//public class DefaultDeck extends AbstractDeck{
//	private final int timesToShuffle=2;
//	public DefaultDeck() {
//		int id=1;
//		for (int i=2;i<=14;i++){
//			
//			cards.add(new Heart(i,id));
//			cards.add(new Club(i,id+1));
//			cards.add(new Diamond(i,id+2));
//			cards.add(new Spade(i,id+3));
//			id+=4;
//		}
//		super.shuffle(timesToShuffle);
//	}
//
//}

//package builtIn.defaultCards;
//
//import card.Card;
//
//public class Diamond extends Card{
//	private final String myType="diamond";
//	
//	public Diamond(int value) {		
//		setType(myType);
//		setValue(value);
//	}
//	
//	@Override
//	protected void setValue(int value) {		
//		super.value=value;		
//	}
//
//	@Override
//	protected void setType(String type) {
//		super.type=type;		
//	}
//}
///*
//	private final String type="diamond";
//	private int value;
//	
//	public Diamond(int value) {
//		this.value=value;
//	}
//	@Override
//	public int compareTo(Card oCard) {
//	 int answer=0;
//	 if (this.value<oCard.getValue()){
//	  answer=-1;
//	 }
//	 else if (this.value>oCard.getValue()){
//	  answer=1;
//	 }
//	 return answer;
//	}
//	
//	@Override
//	public String getType() {		
//		return type;
//	}
//	@Override
//	public int getValue() { 
//		return value;
//	}
//}
//*/
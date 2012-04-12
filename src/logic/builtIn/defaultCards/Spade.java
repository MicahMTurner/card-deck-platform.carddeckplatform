package logic.builtIn.defaultCards;

import logic.card.CardLogic;

public class Spade extends CardLogic{
	private final String myType="spade";
	
	public Spade(int value,int id) {
		super(id);
		setType(myType);
		setValue(value);
	}
	
	@Override
	protected void setValue(int value) {		
		super.value=value;		
	}

	@Override
	protected void setType(String type) {
		super.type=type;		
	}

	@Override
	protected void getType(String type) {
		// TODO Auto-generated method stub
		
	}
}
	/*
	private final String type="spade";
	private int value;
	@Override
	public int compareTo(Card oCard) {
	 int answer=0;
	 if (this.value<oCard.getValue()){
	  answer=-1;
	 }
	 else if (this.value>oCard.getValue()){
	  answer=1;
	 }
	 return answer;
	}
	
	@Override
	public String getType() {		
		return type;
	}
	@Override
	public int getValue() { 
		return value;
	}*/


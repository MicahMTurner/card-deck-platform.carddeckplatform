package logic.builtIn.defaultCards;

import logic.card.CardLogic;

public class Heart extends CardLogic{
	private final String myType="heart";
	
	public Heart(int value) {		
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

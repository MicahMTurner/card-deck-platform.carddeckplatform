package utils;

import handlers.CardEventsHandler;

import java.io.Serializable;


public  class StandartCard extends Card {
	public  enum Color implements Serializable{
		HEART("h"),
		DIAMOND("d"),
		SPADE("s"),
		CLUB("c");
		
		final String code;
		
		Color( String code){
			this.code = code;
		}
		public String getCode(){
			return code;
		}
		
	}
	private final Color color;
	private final int value;
	
	public StandartCard(CardEventsHandler handler,String frontImg,String backImg, int value,Color color) {		
		super(handler,frontImg,backImg);
		this.value=value;	
		this.color=color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getValue() {
		return value;
	}
	
}

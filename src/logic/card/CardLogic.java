//package logic.card;
//
//import java.io.Serializable;
//
//
//
//
//
//public abstract class CardLogic implements Comparable<CardLogic>, Serializable{
//	protected String type;
//	private String owner;
//	protected int value;
//	protected int id;
//	protected boolean revealed;
//	protected boolean moveable;
//
//	public CardLogic(int id){
//		this.id=id;
//	}
//	
//	protected void setValue(int value){}
//	protected void setType(String type){}
//	
//	public boolean isRevealed() {
//		return revealed;
//	}
//	
//	public boolean isMoveable() {
//		return moveable;
//	}
//	
//	public String getOwner() {
//		return owner;
//	}
//	public void setOwner(String owner) {
//		this.owner = owner;
//	}
//	
//	public void setMoveable(boolean moveable) {
//		this.moveable = moveable;
//	}
//	
//	public void setRevealed(boolean revealed) {
//		this.revealed = revealed;
//	}
//	
//	public String getType(){
//		return type;
//	}
//	public int getValue(){
//		return value;
//	}
//	
//	protected abstract void getType(String type);
//	@Override
//	public int compareTo(CardLogic oCard) {
//	 int answer=0;
//	 if (this.value<oCard.getValue()){
//	  answer=-1;
//	 }
//	 else if (this.value>oCard.getValue()){
//	  answer=1;
//	 }
//	 return answer;
//	}
//	@Override
//	public String toString(){
//		 return this.getType()+String.valueOf(this.getValue());
//	 }
//	public int getId() {
//		// TODO Auto-generated method stub
//		return id;
//	}
//
//}

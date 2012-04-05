package logic.card;





public abstract class Card implements Comparable<Card>{
	protected String type;
	protected int value;
	protected int id;
	
	public String getType(){
		return type;
	}
	public int getValue(){
		return value;
	}
	protected void setValue(int value){}
	protected void setType(String type){}
	protected abstract void getType(String type);
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
	public String toString(){
		 return this.getType()+String.valueOf(this.getValue());
	 }
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
}

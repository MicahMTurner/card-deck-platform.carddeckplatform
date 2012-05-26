package utils;

import java.io.Serializable;
import java.util.Collections;

import IDmaker.IDMaker;

public interface Position extends Serializable{
	public int getX();
	public int getY();
	public Position getRelativePosition(Player playerPos);
	public Point getPoint();
	
	public enum Player implements Position{   
		
	    BOTTOM(new Point(50,90)),LEFT(new Point(10,50)),TOP(new Point(50,10)),RIGHT(new Point(90,50));
	    private final Point point;
		private int id;		
		
		public int getId(){
			return id;
		}
		private Player(Point point){
			this.point=point;
			this.id=IDMaker.getMaker().getId(this);
		}
		public int getX(){
			return point.getX();
		}
		public int getY(){
			return point.getY();
		}
		
	    public Player getRelativePosition(Player myPos){
	    	Player answer=this;
	    	switch(myPos){
	    		case TOP:{
	    			answer=getTopRelative(this);
	    			break;
	    		}
	    		case RIGHT:{
	    			answer=getRightRelative(this);
	    			break;
	    		}
	    		case LEFT:{
	    			answer=getLeftRelative(this);
	    			break;
	    		}
	    		default:{}
	    	}
			return answer;
	    }

		private Player getLeftRelative(Player playerPos) {
			Player answer=playerPos;
			switch(playerPos){
				
			}
			return null;
		}

		private Player getRightRelative(Player playerPos) {
			// TODO Auto-generated method stub
			return null;
		}

		private Player getTopRelative(Player playerPos) {
			Player answer=playerPos;
			switch(playerPos){
				case BOTTOM:{
					
					//swap(playerPos,TOP);
					answer=TOP;
					break;
				}
				case LEFT:{
					//swap(playerPos,RIGHT);					
					answer=RIGHT;
				}
				case RIGHT:{
					//swap(playerPos,LEFT);					
					answer=LEFT;
				}
				case TOP:{
					answer=BOTTOM;
				}
			}
			return answer;
		}
		private void swap (Player a,Player b){
			int temp=a.id;
			a.id=b.id;
			b.id=temp;
		}
		public utils.Position.Player sitMe(utils.Position.Player globalPosition) {
			//swap(globalPosition,BOTTOM);
			return BOTTOM;
		}
		@Override
		public Point getPoint() {
			return point;
		}
	}  
	  
	public enum Public implements Position{
		TOP(new Point(50,30)),TOPMID(new Point(50,40)),MID(new Point(50,50)),TOPMIDLEFT(new Point(40,40)),
		TOPMIDRIGHT(new Point(60,40)),RIGHT(new Point(70,50)),MIDRIGHT(new Point(60,50)),MIDLEFT(new Point(40,50))
		,LEFT(new Point(30,50)),
		BOTMIDLEFT(new Point(40,60)),BOTMID(new Point(50,60)),BOTMIDRIGHT(new Point(60,60)),BOT(new Point(50,70));
		private final Point point;
		private int id;		

		public int getId(){
			return id;
		}
		private Public(Point point){
			this.point=point;
			this.id=IDMaker.getMaker().getId(this);
		}
		public int getX(){
			return point.getX();
		}
		public int getY(){
			return point.getY();
		}
		public Position.Public getRelativePosition(Position.Player playerPos){
			Public answer=this;
			switch(playerPos){
				case TOP:{
					answer=getTopRelative(this);
					break;
				}
				case RIGHT:{
					answer=getRightRelative(this);
					break;
				}
				case LEFT:{
					answer=getLeftRelative(this);
					break;
				}
				default:{}
			}
			return answer;
		}

		private Public getLeftRelative(Public publicPos) {
			// TODO Auto-generated method stub
			return null;
		}

		private Public getRightRelative(Public publicPos) {
			// TODO Auto-generated method stub
			return null;
		}

		private Public getTopRelative(Public publicPos) {
			Public answer=publicPos;
			switch(publicPos){
				
				case TOPMIDRIGHT:{	
					//swap(publicPos,TOPMIDRIGHT);
					answer=BOTMIDLEFT;
					break;
				}
				case TOPMIDLEFT:{
					//swap(publicPos,TOPMIDLEFT);
					answer=BOTMIDRIGHT;
					break;
				}
				case RIGHT:{
					//swap(publicPos,RIGHT);
					answer=LEFT;
					break;
				}
				case MIDRIGHT:{
					//swap(publicPos,MIDLEFT);					
					answer=MIDLEFT;
					break;					
				}
				case MIDLEFT:{
					//swap(publicPos,MIDRIGHT);					
					answer=MIDRIGHT;
					break;					
				}
				case BOTMIDRIGHT:{
					//swap(publicPos,BOTMIDRIGHT);
					answer=TOPMIDLEFT;
					break;
				}
				case BOTMID:{
					//swap(publicPos,BOTMID);
					answer=TOPMID;
					break;
				}
				case BOTMIDLEFT:{
					//swap(publicPos,BOTMIDLEFT);
					answer=TOPMIDRIGHT;
					break;
				}
				default:{}
				
			}
			return answer;			
		}
		@Override
		public Point getPoint() {
			return point;
		}
	 }  
	  
	public enum Button implements Position{
		  TOPRIGHT(new Point(90,10)),TOPLEFT(new Point(10,10)),BOTLEFT(new Point(10,90)),BOTRIGHT(new Point(90,90));
		  private final Point point;
		  private final int id;		
			
		  public int getId(){
			  return id;
		  }
		  private Button(Point point){
				this.point=point;
				this.id=IDMaker.getMaker().getId(this);
			}
			public int getX(){
				return point.getX();
			}
			public int getY(){
				return point.getY();
			}
			
		  public Button getRelativePosition(Player myPos){
			  Button answer=this;
			  switch(myPos){
				case TOP:{
					answer=getTopRelative(this);
					break;
				}
				case RIGHT:{
					answer=getRightRelative(this);
					break;
				}
				case LEFT:{
					answer=getLeftRelative(this);
					break;
				}
				default:{}
			}
			return answer;
		  }

		private Button getLeftRelative(Button buttonPos) {
			// TODO Auto-generated method stub
			return null;
		}

		private Button getRightRelative(Button buttonPos) {
			// TODO Auto-generated method stub
			return null;
		}

		private Button getTopRelative(Button buttonPos) {
			 Button answer=buttonPos;
			 switch(buttonPos){
			 	case TOPRIGHT:{
			 		answer=BOTLEFT;
			 		break;
			 	}
			 	case TOPLEFT:{
			 		answer=BOTRIGHT;
			 		break;
			 	}
			 	case BOTRIGHT:{
			 		answer=TOPLEFT;
			 		break;
			 	}
			 	case BOTLEFT:{
			 		answer=TOPRIGHT;
			 		break;
			 	}
			 	default:{}
			 }
			return answer;
		}
		@Override
		public Point getPoint() {
			return point;
		}
	 }
	

}  

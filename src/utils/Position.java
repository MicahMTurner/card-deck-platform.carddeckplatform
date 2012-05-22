package utils;

import java.io.Serializable;
import java.util.Collections;

import IDmaker.IDMaker;

public interface Position extends Serializable{
	public int getX();
	public int getY();
	public Position getRelativePosition(Player playerPos);
	
	public enum Player implements Position{   
		
	    BOTTOM(300,400),LEFT(6,6),TOP(200,50),RIGHT(6,6);
	    private final int x;
		private final int y;
		private int id;		
		
		public int getId(){
			return id;
		}
		private Player(int x,int y){
			this.x=x;
			this.y=y;
			this.id=IDMaker.getMaker().getId(this);
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
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
	}  
	  
	public enum Public implements Position{
		TOPRIGHT(6,6),TOPMIDRIGHT(6,6),TOPMID(6,6),TOPMIDLEFT(6,6),TOPLEFT(6,6),
		RIGHT(6,6),MIDRIGHT(400,200),MID(200,300),MIDLEFT(270,200),LEFT(6,6),
		BOTLEFT(6,6),BOTMIDLEFT(6,6),BOTMID(6,6),BOTMIDRIGHT(6,6),BOTRIGHT(6,6);
		private final int x;
		private final int y;
		private int id;		
		private void swap (Public a,Public b){
			int temp=a.id;
			a.id=b.id;
			b.id=temp;
		}
		public int getId(){
			return id;
		}
		private Public(int x,int y){
			this.x=x;
			this.y=y;
			this.id=IDMaker.getMaker().getId(this);
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
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
				case TOPRIGHT:{
					//swap(publicPos,TOPRIGHT);
					answer=BOTLEFT;
					break;
				}
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
				case TOPLEFT:{
					//swap(publicPos,TOPLEFT);
					answer=BOTRIGHT;
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
				case BOTRIGHT:{
					//swap(publicPos,BOTRIGHT);
					answer=TOPLEFT;
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
				case BOTLEFT:{
					//swap(publicPos,BOTLEFT);
					answer=TOPRIGHT;
					break;
				}
				default:{}
				
			}
			return answer;			
		}
	 }  
	  
	public enum Button implements Position{
		  TOPRIGHT(6,6),TOPLEFT(6,6),BOTLEFT(50,400),BOTRIGHT(6,6);
		  private final int x;
		  private final int y;
		  private final int id;		
			
		  public int getId(){
			  return id;
		  }
		  private Button(int x,int y){
				this.x=x;
				this.y=y;
				this.id=IDMaker.getMaker().getId(this);
			}
			public int getX(){
				return x;
			}
			public int getY(){
				return y;
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
	 }
	

}  

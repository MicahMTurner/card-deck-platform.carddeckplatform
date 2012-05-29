package utils;

import java.io.Serializable;
import java.util.Collections;

import IDmaker.IDMaker;

public interface Position extends Serializable{
	final int ELEMENTSINCYCLE=4;
	final int TOPDEVIATION=2;
	final int RIGHTDEVIATION=1;
	final int LEFTDEVIATION=3;
	public float getX();
	public float getY();
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
		public float getX(){
			return point.getX();
		}
		public float getY(){
			return point.getY();
		}
		
	    public Player getRelativePosition(Player myPos){
	    	Player answer=this;
	    	switch(myPos){
	    		case TOP:{
	    			answer=Player.values()[(this.ordinal()+TOPDEVIATION)%ELEMENTSINCYCLE];
	    			break;
	    		}
	    		case RIGHT:{
	    			answer=Player.values()[(this.ordinal()+RIGHTDEVIATION)%ELEMENTSINCYCLE];
	    			break;
	    		}
	    		case LEFT:{
	    			answer=Player.values()[(this.ordinal()+LEFTDEVIATION)%ELEMENTSINCYCLE];
	    			break;
	    		}
	    		default:{}
	    	}
			return answer;
	    }

		
//		private Player getTopRelative(Player playerPos) {
//			Player answer=playerPos;
//			switch(playerPos){
//				case BOTTOM:{
//					
//					//swap(playerPos,TOP);
//					answer=TOP;
//					break;
//				}
//				case LEFT:{
//					//swap(playerPos,RIGHT);					
//					answer=RIGHT;
//				}
//				case RIGHT:{
//					//swap(playerPos,LEFT);					
//					answer=LEFT;
//				}
//				case TOP:{
//					answer=BOTTOM;
//				}
//			}
//			return answer;
//		}

//		public utils.Position.Player sitMe(utils.Position.Player globalPosition) {
//			//swap(globalPosition,BOTTOM);
//			return BOTTOM;
//		}
		@Override
		public Point getPoint() {
			return point;
		}
	}  
	  
	public enum Public implements Position{
		MIDRIGHT(new Point(60,50)),BOTMID(new Point(50,60)),MIDLEFT(new Point(40,50)),TOPMID(new Point(50,40)),
		MID(new Point(50,50)),
		TOPMIDRIGHT(new Point(60,40)),BOTMIDRIGHT(new Point(60,60)),BOTMIDLEFT(new Point(40,60)),TOPMIDLEFT(new Point(40,40)),		
		BOT(new Point(50,70)),LEFT(new Point(30,50)),TOP(new Point(50,30)),RIGHT(new Point(70,50));
		private final Point point;
		private final int ROWS=4;
		private int id;		

		public int getId(){
			return id;
		}
		private Public(Point point){
			this.point=point;
			this.id=IDMaker.getMaker().getId(this);
		}
		public float getX(){
			return point.getX();
		}
		public float getY(){
			return point.getY();
		}
		public Position.Public getRelativePosition(Position.Player playerPos){
			Public answer=this;
			if (!answer.equals(MID)){
				switch(playerPos){
					case TOP:{
						answer=Public.values()[((this.ordinal()+TOPDEVIATION)%
								ELEMENTSINCYCLE)+(ELEMENTSINCYCLE*(int)(this.ordinal()/ROWS))];
						break;
					}
					case RIGHT:{
						answer=Public.values()[((this.ordinal()+RIGHTDEVIATION)%
								ELEMENTSINCYCLE)+(ELEMENTSINCYCLE*(int)(this.ordinal()/ROWS))];
						break;
					}
					case LEFT:{
						answer=Public.values()[((this.ordinal()+LEFTDEVIATION)%
								ELEMENTSINCYCLE)+(ELEMENTSINCYCLE*(int)(this.ordinal()/ROWS))];
						break;
					}
					default:{}
				}
			}
			return answer;
		}
	
//
//		private Public getTopRelative(Public publicPos) {
//			Public answer=publicPos;
//			switch(publicPos){
//				
//				case TOPMIDRIGHT:{	
//					//swap(publicPos,TOPMIDRIGHT);
//					answer=BOTMIDLEFT;
//					break;
//				}
//				case TOPMIDLEFT:{
//					//swap(publicPos,TOPMIDLEFT);
//					answer=BOTMIDRIGHT;
//					break;
//				}
//				case RIGHT:{
//					//swap(publicPos,RIGHT);
//					answer=LEFT;
//					break;
//				}
//				case MIDRIGHT:{
//					//swap(publicPos,MIDLEFT);					
//					answer=MIDLEFT;
//					break;					
//				}
//				case MIDLEFT:{
//					//swap(publicPos,MIDRIGHT);					
//					answer=MIDRIGHT;
//					break;					
//				}
//				case BOTMIDRIGHT:{
//					//swap(publicPos,BOTMIDRIGHT);
//					answer=TOPMIDLEFT;
//					break;
//				}
//				case BOTMID:{
//					//swap(publicPos,BOTMID);
//					answer=TOPMID;
//					break;
//				}
//				case BOTMIDLEFT:{
//					//swap(publicPos,BOTMIDLEFT);
//					answer=TOPMIDRIGHT;
//					break;
//				}
//				default:{}
//				
//			}
//			return answer;			
//		}
		@Override
		public Point getPoint() {
			return point;
		}
	 }  
	  
	public enum Button implements Position{
		  TOPRIGHT(new Point(90,10)),BOTRIGHT(new Point(90,90)),BOTLEFT(new Point(10,90)),TOPLEFT(new Point(10,10));
		  private final Point point;
		  private final int id;		
			
		  public int getId(){
			  return id;
		  }
		  private Button(Point point){
				this.point=point;
				this.id=IDMaker.getMaker().getId(this);
			}
			public float getX(){
				return point.getX();
			}
			public float getY(){
				return point.getY();
			}
			
		  public Button getRelativePosition(Player myPos){
			  Button answer=this;
			  switch(myPos){
				case TOP:{
					answer=Button.values()[(this.ordinal()+TOPDEVIATION)%ELEMENTSINCYCLE];
					break;
				}
				case RIGHT:{
					answer=Button.values()[(this.ordinal()+RIGHTDEVIATION)%ELEMENTSINCYCLE];
					break;
				}
				case LEFT:{
					answer=Button.values()[(this.ordinal()+LEFTDEVIATION)%ELEMENTSINCYCLE];
					break;
				}
				default:{}
			}
			return answer;
		  }

//		private Button getTopRelative(Button buttonPos) {
//			 Button answer=buttonPos;
//			 switch(buttonPos){
//			 	case TOPRIGHT:{
//			 		answer=BOTLEFT;
//			 		break;
//			 	}
//			 	case TOPLEFT:{
//			 		answer=BOTRIGHT;
//			 		break;
//			 	}
//			 	case BOTRIGHT:{
//			 		answer=TOPLEFT;
//			 		break;
//			 	}
//			 	case BOTLEFT:{
//			 		answer=TOPRIGHT;
//			 		break;
//			 	}
//			 	default:{}
//			 }
//			return answer;
//		}
		@Override
		public Point getPoint() {
			return point;
		}
	 }
	

}  

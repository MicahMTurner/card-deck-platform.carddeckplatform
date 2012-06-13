package utils;

import java.io.Serializable;
import java.util.Collections;

import IDmaker.IDMaker;

public interface Position extends Serializable{
	
	final public int ELEMENTSINCYCLE=4;
	final public int TOPDEVIATION=2;
	final public int RIGHTDEVIATION=1;
	final public int LEFTDEVIATION=3;	
	
	public int getId();
	public float getX();
	public float getY();
	public Position getRelativePosition(Player playerPos);
	public Position reArrangeRelativePosition(Player oldPlayerPos, Player newPlayerPos);
	public Point getPoint();
	
	public enum Player implements Position{   
		
	    BOTTOM(new Point(50,90)),LEFT(new Point(10,50)),TOP(new Point(50,10)),RIGHT(new Point(90,50));
	    private final Point point;
		private int id;		
		final public int ELEMENTSINCYCLE=4;
		final public int TOPDEVIATION=2;
		final public int RIGHTDEVIATION=1;
		final public int LEFTDEVIATION=3;
		
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

		@Override
		public Point getPoint() {
			return point;
		}
		@Override
		/**
		 * can't rearrange player position
		 */
		public Position reArrangeRelativePosition(Player oldPlayerPos, Player newPlayerPos) {
			return this;
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
		final public int ELEMENTSINCYCLE=4;		
		final public int TOPDEVIATION=2;
		final public int RIGHTDEVIATION=1;
		final public int LEFTDEVIATION=3;
		final public int ELEMENTS=13;
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

		@Override
		public Point getPoint() {
			return point;
		}
		public Position reArrangeRelativePosition(Player oldPlayerPos,	Player newPlayerPos) {
			if (!this.equals(MID)){
				int different= this.ordinal()-getDeviation(oldPlayerPos);
				if (different<0){
					different=ELEMENTSINCYCLE+different;
				}			
				return Public.values()[(different%ELEMENTSINCYCLE)].getRelativePosition(newPlayerPos);
			}else{
				return this;
			}
		}
		private int getDeviation(Player oldPlayerPos) {
			int deviation=0;
			switch (oldPlayerPos){
				case TOP:{
					deviation=TOPDEVIATION;
					break;
				}
				case RIGHT:{
					deviation=RIGHTDEVIATION;
					break;
				}
				case LEFT:{
					deviation=LEFTDEVIATION;
					break;
				}
				default:{}
			}
			return deviation;
		}
	 }  
	  
	public enum Button implements Position{
		TOPRIGHT(new Point(90,10)),BOTRIGHT(new Point(90,90)),BOTLEFT(new Point(10,90)),TOPLEFT(new Point(10,10));
		private final Point point;
		private final int id;		
		final public int ELEMENTSINCYCLE=4;
		final public int TOPDEVIATION=2;
		final public int RIGHTDEVIATION=1;
		final public int LEFTDEVIATION=3;
		
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

		@Override
		public Position reArrangeRelativePosition(Player oldPlayerPos,	Player newPlayerPos) {
			int different= this.ordinal()+getDeviation(oldPlayerPos);
			if (different<0){
				different=ELEMENTSINCYCLE-different;
			}
			return Button.values()[(different%ELEMENTSINCYCLE)].getRelativePosition(newPlayerPos);
		}
		private int getDeviation(Player oldPlayerPos) {
			int deviation=0;
			switch (oldPlayerPos){
				case TOP:{
					deviation=TOPDEVIATION;
					break;
				}
				case RIGHT:{
					deviation=RIGHTDEVIATION;
					break;
				}
				case LEFT:{
					deviation=LEFTDEVIATION;
					break;
				}
				default:{}
			}
			return deviation;
		}
		@Override
		public Point getPoint() {
			return point;
		}
	 }
}  

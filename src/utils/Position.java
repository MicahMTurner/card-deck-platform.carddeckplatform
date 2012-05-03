package utils;

public interface Position {
	public int getX();
	public int getY();
	
	public enum Player implements Position{   
	    BOTTOM(6,6),TOP(6,6),RIGHT(6,6),LEFT(6,6);
	    private final int x;
		private final int y;
		
		private Player(int x,int y){
			this.x=x;
			this.y=y;
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
					answer=TOP;
					break;
				}
				case LEFT:{
					answer=RIGHT;
				}
				case RIGHT:{
					answer=LEFT;
				}
			}
			return answer;
		}
	}  
	  
	public enum Public{
		TOPRIGHT(6,6),TOPMIDRIGHT(6,6),TOPMID(6,6),TOPMIDLEFT(6,6),TOPLEFT(6,6),
		RIGHT(6,6),MIDRIGHT(6,6),MID(6,6),MIDLEFT(6,6),LEFT(6,6),
		BOTLEFT(6,6),BOTMIDLEFT(6,6),BOTMID(6,6),BOTMIDRIGHT(6,6),BOTRIGHT(6,6);
		private final int x;
		private final int y;
		
		private Public(int x,int y){
			this.x=x;
			this.y=y;
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
					answer=BOTLEFT;
					break;
				}
				case TOPMIDRIGHT:{
					answer=BOTMIDLEFT;
					break;
				}
				case TOPMIDLEFT:{
					answer=BOTMIDRIGHT;
					break;
				}
				case TOPLEFT:{
					answer=BOTRIGHT;
					break;
				}
				case RIGHT:{
					answer=LEFT;
					break;
				}
				case MIDRIGHT:{
					answer=MIDLEFT;
					break;					
				}
				case MIDLEFT:{
					answer=MIDRIGHT;
					break;					
				}
				case BOTRIGHT:{
					answer=TOPLEFT;
					break;
				}
				case BOTMIDRIGHT:{
					answer=TOPMIDLEFT;
					break;
				}
				case BOTMID:{
					answer=TOPMID;
					break;
				}
				case BOTMIDLEFT:{
					answer=TOPMIDRIGHT;
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
	  
	public enum Button{
		  TOPRIGHT(6,6),TOPLEFT(6,6),BOTLEFT(6,6),BOTRIGHT(6,6);
		  private final int x;
		  private final int y;
		  private Button(int x,int y){
				this.x=x;
				this.y=y;
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

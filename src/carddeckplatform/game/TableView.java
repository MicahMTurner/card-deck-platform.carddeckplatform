package carddeckplatform.game;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import utils.Card;
import utils.Player;
import IDmaker.IDMaker;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;
import client.controller.ClientController;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.GuiCard;
import client.gui.entities.GuiPlayer;
import client.gui.entities.Table;
import client.gui.entities.Table.Focus;


public class TableView extends SurfaceView {
	private Table table;
	private Context cont; 
	private Matrix translate;
	private Draggable draggableInHand=null;
	private Droppable from=null;
	private int xDimention;
	private int yDimention;
	private AnimationTask animationTask;
	private boolean uiEnabled=false;
	
	private class AnimationTask extends AsyncTask<Integer, Integer, Long>{
		private BlockingQueue<String> calls = new ArrayBlockingQueue<String>(10000);
		public void redraw(){
			calls.add("xyz");
		}
		
		public void stopDrawing(){
			calls.clear();
		}
		
		@Override
		protected Long doInBackground(Integer... arg0) {
			while(true){
				try {
					calls.take();
					onProgressUpdate(0);
				} catch (InterruptedException ie) {				
					ie.printStackTrace();
				} catch (IllegalStateException ise){
					stopDrawing();
					redraw();
				}
				
			}
		}
		@Override
		protected void onProgressUpdate(Integer... Integ) {
			CarddeckplatformActivity.h.post(new Runnable() {
				
				@Override
				public void run() {					
					invalidate();
				}
			});
		}	
	}
	
	public TableView(Context context,AttributeSet attrs) {
		super(context, attrs);
		translate=new Matrix();
		animationTask = new AnimationTask();
		animationTask.execute(0);
		this.xDimention =  getMeasuredWidth();
		this.yDimention = getMeasuredHeight();
		cont = context;		
		
		table = new Table(context);
		table.setTableImage(R.drawable.boardtest);
	    setFocusable(true); //necessary for getting the touch events.
	}
	
	public void onMove(float dx,float dy){
		translate.postTranslate(dx, dy);
	}
	
	
	public void draggableMotion(String username, int id , int x , int y){
		Draggable draggable = table.getDraggableById(id);
		table.setFrontOrRear(draggable,Focus.FRONT);
		
		draggable.setCarrier(username);
		draggable.setLocation(780-x, 460-y);
		animationTask.redraw();
	}
	
	public void endDraggableMotion(int id){
		Draggable draggable = table.getDraggableById(id);
		table.setFrontOrRear(draggable,Focus.FRONT);
		draggable.setCarrier("");
		animationTask.redraw();
	}

	
	
	
	
	public void drawMovement(final ArrayList<GuiCard> cards, final int toId, final long initialDelay, final long delay, final boolean revealedWhileMoving, final boolean revealedAtEnd){
		ArrayList<Thread> drawingThreads = new ArrayList<Thread>();
		final CountDownLatch cdl=new CountDownLatch(cards.size());
		final Droppable destination=getDroppableById(toId);
		for (final GuiCard guiCard : cards){
			drawingThreads.add(	new Thread(new Runnable() {	
						@Override
						public void run() {
							guiCard.getCard().setRevealed(revealedWhileMoving);
							int x = guiCard.getX();
							int y = guiCard.getY();
							final ArrayList<Point> vector = StaticFunctions.midLine(x, y, destination.getX(), destination.getY());
							try {
			        			Thread.sleep(initialDelay);
			        		} catch (InterruptedException e) {			        			
			        			e.printStackTrace();
			        		}
			            	for(int i=0; i<vector.size(); i++){
			            		guiCard.getCard().setRevealed(revealedWhileMoving);
			            		final int index = i;
					
			            		try {
			            			Thread.sleep(delay);
			            		} catch (InterruptedException e) {			            			
			            			e.printStackTrace();
			            		}					
			            		
			            		guiCard.setLocation(vector.get(index).x, vector.get(index).y);
			            		guiCard.setAngle(i*10);
			            		animationTask.redraw();
			            	}
			            	guiCard.setAngle(0);
			            	guiCard.getCard().setRevealed(revealedAtEnd);
			            	cdl.countDown();
							
						}
					}));
		
		}
		for (Thread drawingThread : drawingThreads){
			drawingThread.start();
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}
		//for (Thread drawingThread : drawingThreads){
		//	try {
		//		drawingThread.join();
		//	} catch (InterruptedException e) {
		//		e.printStackTrace();
		//	}
		//}
		
		
	}
//	public void moveDraggable(final Draggable draggable, final int newX, final int newY, final long initialDelay, final long delay, final boolean revealedWhileMoving, final boolean revealedAtEnd){
//		Thread drawingThread=
//		new Thread(new Runnable() {	
//			@Override
//			public void run() {
//				draggable.getCardLogic().setRevealed(revealedWhileMoving);
//				
//				int x = draggable.getX();
//				int y = draggable.getY();
//				final ArrayList<Point> vector = StaticFunctions.midLine(x, y, newX, newY);
//				try {
//        			Thread.sleep(initialDelay);
//        		} catch (InterruptedException e) {
//        			
//        			e.printStackTrace();
//        		}
//            	for(int i=0; i<vector.size(); i++){
//            		draggable.getCardLogic().setRevealed(revealedWhileMoving);
//            		final int index = i;
//		
//            		try {
//            			Thread.sleep(delay);
//            		} catch (InterruptedException e) {
//            			
//            			e.printStackTrace();
//            		}
//		
//            		
//            		draggable.setLocation(vector.get(index).x, vector.get(index).y);
//            		draggable.setAngle(i*10);
//            		animationTask.redraw();
//            	}
//            	draggable.setAngle(0);
//            	draggable.getCardLogic().setRevealed(revealedAtEnd);
//				
//			}
//		});
//		drawingThread.start();
//		
//	}
//	
//	
//	
//	
//	public void moveDraggable(Draggable draggable, Droppable droppable, final long initialDelay, final long delay, final boolean revealedWhileMoving, final boolean revealedAtEnd){
//		moveDraggable(draggable, droppable.getX(), droppable.getY(), initialDelay, delay, revealedWhileMoving, revealedAtEnd);
//	}
	
	public void addDroppable(Droppable droppable){
		table.addDroppable(droppable);		
	}
	
//	public void addDraggable(ArrayList<CardLogic> cardLogics, Droppable target){
//		animationTask.stopDrawing();
//		// get the first letter of the type and concatenates it with the value.
//		for(CardLogic cardLogic : cardLogics){
//			String key = cardLogic.getType().subSequence(0, 1) + String.valueOf(cardLogic.getValue());
//			System.out.println("the key is: " + key + " the tardet is: " + target.getLogic().getId());
//			int resourceId = getResources().getIdentifier("drawable/" + key, "drawable", "carddeckplatform.game");		
//			Card card = new Card(getContext(),resourceId,0,0); 
//			card.setCardLogic(cardLogic);
//			table.addDraggable(card);
//			target.addDraggable(card);
//		}
//		//invalidate();
//		
//		animationTask.redraw();
//	}
	

	private void addNewDraggable(Card card,Droppable destination){
		animationTask.stopDrawing();
						
			destination.deltCard(card);			
			GuiCard guiCard=new GuiCard(card);
			
			guiCard.setLocation(destination.getX(), destination.getY());		
			table.addDraggable(guiCard);
		
		animationTask.redraw();
	}
	
//	public void addDraggable(ArrayList<CardLogic> cardLogics,
//			Droppable from, Droppable to) {
//		for(CardLogic cardLogic : cardLogics){
//			Draggable card=table.getDraggableById(cardLogic.getId(), GetMethod.PutInFront);
//			from.removeDraggable(card);
//			to.addDraggable(card);
//		}
//		
//	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec,
	     int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
	    table.setxDimention(getMeasuredWidth());
	    table.setyDimention(getMeasuredHeight());
	}
	
	// the method that draws
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);//if you want another background color
    	canvas.drawColor(Color.TRANSPARENT);    	  
        canvas.scale(1, 1);
        // draws the table.
        table.draw(canvas);
    }
    
    
    public void setUiEnabled(boolean uiEnabled) {
		this.uiEnabled = uiEnabled;
	}
    public boolean isUiEnabled() {
		return uiEnabled;
	}
 // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {    	
    		int X = (int)event.getX(); 
            int Y = (int)event.getY(); 
    		int eventAction = event.getAction();    		
    		if(uiEnabled){
	    		switch (eventAction ) { 
	    		
		    		case MotionEvent.ACTION_DOWN:{
		    			draggableInHand = table.getNearestDraggable(X, Y);
		    			
		    			if (draggableInHand!=null){
		    				table.setFrontOrRear(draggableInHand, Focus.FRONT);
		    				if(draggableInHand.isMoveable()){
		    					from=table.getNearestDroppable(X, Y);
		    					draggableInHand.onClick();
		    				}else{		    					
		    					popToast("You cannot move this card");
		    					draggableInHand=null;		    					
		    				}
		    			}		    			
		    			break;
		    		}
		    		case MotionEvent.ACTION_MOVE:{
		    			if(draggableInHand!=null){		    		
		    				draggableInHand.setLocation(X, Y);
		    				draggableInHand.onDrag();
		    			}
		    			break;
		    		}
		    		case MotionEvent.ACTION_UP:{
		    			if(draggableInHand!=null){
		    				draggableInHand.setLocation(X, Y);
		    				draggableInHand.onRelease();		    				
		    				
							Droppable droppable=table.getNearestDroppable(X, Y);
							if (droppable!=null && from!=null){									
								droppable.onDrop(ClientController.get().getMe(),from,((GuiCard)draggableInHand).getCard());
								
							}
							else{
								draggableInHand.invalidMove();
							}							
		    				draggableInHand = null;
		    			}
		    			
		    			//Droppable droppable2=table.getNearestDroppable(X, Y);
						//if (droppable2!=null){									
						//	droppable2.onClick();							
							
						//}		    			
		    			break;
		    		}
	    		}//end if enabdles
    		}
    		else{
    			//GUI is not enabled
    		
    			popToast("It's not your turn now!!");
  
    		}   		
		
    	animationTask.redraw();
		return true;
    }
    
    public void popToast(final String displayMessage){
    	this.post(new Runnable() {
			
			@Override
			public void run() {
				Toast toast = Toast.makeText(cont, displayMessage, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
    	
    }
	public int getxDimention() {
		return xDimention;
	}

	public void setxDimention(int xDimention) {
		this.xDimention = xDimention;
	}

	public int getyDimention() {
		return yDimention;
	}

	public void setyDimention(int yDimention) {
		this.yDimention = yDimention;
	}

	public void removeCards(ArrayList<utils.Card> cards, String from) {
		for (utils.Card card : cards){
			table.getDroppableById(IDMaker.getMaker().getId(from)).removeCard(card);
			
		}
		
	}
	
	public Droppable getDroppableById(Integer id) {
		return table.getDroppableById(id);
	}

	public void moveCard(Card card,int from,int to,Player byWhom){
		ArrayList<Card> cards=new ArrayList<Card>();
		System.out.println("card moved: "+card.getId());
		cards.add(((GuiCard)(table.getDraggableById(card.getId()))).getCard());
		moveCards(cards,from,to,byWhom);
	}
	
	public void moveCards(ArrayList<Card> cards, int from, int to, Player byWhom) {		
		Droppable destination=table.getDroppableById(to);
		Droppable source=table.getDroppableById(from);
		for (Card card : cards){
			if (from==-1){
				//new card, create it
				System.out.println(GameStatus.username+": card id: "+card.getId());
				addNewDraggable(card, destination);
				
			}else{
				//move card from one zone to another
					source.removeCard(byWhom,card);					
					destination.onCardAdded(byWhom, card);
					animationTask.redraw();
					//drawMovement(cards, destination.getPoint(), 1000, 10, revealWhileMoving, revealAtEnd);
				}
		}
	}
	//public void revealedDraggable(int id){
	//	GuiCard revealed=(GuiCard)table.getDraggableById(id, GetMethod.PutInFront);
	//	revealed.getCard().setRevealed(true);
	//	animationTask.redraw();
		
	//}
	public void addPlayer(Player newPlayer) {
		table.addDroppable(new GuiPlayer(newPlayer));
		animationTask.redraw();
		
	}

	public void dealCards(ArrayList<Card> cards, int to) {
		Droppable destination=table.getDroppableById(to);
		for (Card card : cards){			
			//new card, create it
			System.out.println(GameStatus.username+": card id: "+card.getId());
			addNewDraggable(card, destination);
		}			
	}
	
	
	
	
}

package carddeckplatform.game.tutorial;

import tutorial.CardHandler;
import tutorial.GuideCard;
import tutorial.PlayerHandler;
import tutorial.PublicHandler;
import tutorial.Tutorial;
import tutorial.Tutorial.Stages;
import utils.Card;
import utils.DeckArea;
import utils.Player;
import utils.Point;
import utils.Position;
import utils.Public;
import utils.droppableLayouts.DroppableLayout;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import carddeckplatform.game.DrawThread;
import carddeckplatform.game.R;
import carddeckplatform.game.TableView;
import client.controller.AutoHide;
import client.controller.ClientController;
import client.gui.animations.GlowAnimation;
import client.gui.animations.OvershootAnimation;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.Table;
import client.gui.entities.TouchManager;
import freeplay.customization.CustomizationPublic;

public class TutorialView extends TableView  {

	private carddeckplatform.game.DrawThread drawThread;
	Table table;
	private TouchManager touchmanager;
	private Card guideCard;
	private Draggable draggableInHand;
	private Droppable from;
	private Context context;
	private Player me;
	private Tutorial game;
	Public p;
	
	
	public void addManySmall(){
		for (Position.Public position : Position.Public.values()){
			if (!position.equals(Position.Public.LEFT)){
				Public publicZone=new Public(new BigPublicHandler(), position,DroppableLayout.LayoutType.HEAP);
				table.addDroppable(publicZone);
				new GlowAnimation(publicZone, 2000, Color.argb(255, 255, 255, 255)).execute();
			}else{
				new GlowAnimation(p, 2000, Color.argb(255, 255, 255, 255)).execute();
			}
		}		
	}
	
	public void addOneBig(){
		CustomizationPublic bigPublic=new CustomizationPublic(new PublicHandler(), Position.Public.MID,DroppableLayout.LayoutType.NONE , new Point(65,65));
		table.addDroppable(bigPublic);
		new GlowAnimation(bigPublic, 2000, Color.argb(255, 255, 255, 255)).execute();
	}
	
	
	
	public TutorialView(Context context) {
		super(context, null);
		
		this.context = context;
		this.guideCard= new GuideCard(new CardHandler(),true);
		draggableInHand=null;
		from=null;
		table = new Table(context);
		table.setTableImage(R.drawable.boardtest);
		me=new Player(null, Position.Player.BOTTOM, Position.Player.BOTTOM.getId()
				, new PlayerHandler(), DroppableLayout.LayoutType.HEAP);
		
		touchmanager = new TouchManager(context, this, 2);	
		game=new Tutorial();
		setFocusable(true); // necessary for getting the touch events.
		// making the UI thread
		getHolder().addCallback(this);
		
		p = new Public(new PublicHandler(), Position.Public.LEFT,DroppableLayout.LayoutType.HEAP);
		ClientController.get().setGame(game);
		game.addPlayer(me);
		
		table.addDroppable(p);
		p.addCard(null,guideCard);
	}

	@Override
	public boolean onDoubleTap(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		if ((Tutorial.Stages.FLIP==Tutorial.currentStage || Tutorial.Stages.FLIPAGAIN==Tutorial.currentStage) 
				&& !(Tutorial.isBadJob!=null && Tutorial.isBadJob==false)){
			float X = event.getX();
			float Y = event.getY();
			draggableInHand = table.getNearestDraggable(X, Y);
			if (draggableInHand != null) {
				((Card)draggableInHand).flip();
				Tutorial.isBadJob=false;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onDown(MotionEvent event) {
		float X = event.getX();
		float Y = event.getY();
		if ((Tutorial.currentStage==Stages.DECK || Tutorial.currentStage==Stages.HUGEPUBLIC
				|| Tutorial.currentStage==Stages.ROTATE || Tutorial.currentStage==Stages.PLAYERS) && !(Tutorial.isBadJob!=null && Tutorial.isBadJob==false)) {
			draggableInHand = table.getNearestDraggable(X, Y);//check if its a card
			if (draggableInHand != null) {
				if (draggableInHand.isMoveable()) {
					from = table.getNearestDroppable(X, Y);
					
					draggableInHand.onClick(from);
				} else {
					popToast("You cannot move this card");
					draggableInHand = null;
				}
			}
//			else{//check if its a button
//				this.buttonInHand=table.getNearestButton(X, Y);//check if its a card
//				
//				
//			}
		} else
			return false;
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (Tutorial.currentStage==Stages.DECK 
				|| Tutorial.currentStage==Stages.HUGEPUBLIC 
				|| Tutorial.currentStage==Stages.PLAYERS) {
			if (draggableInHand != null) {
				touchmanager.resetAngle();
				touchmanager.resetScale();
				float x = e2.getX();
				float y = e2.getY();
				final float distanceTimeFactor = 0.4f;
				final float totalDx = (distanceTimeFactor * velocityX / 2);
				final float totalDy = (distanceTimeFactor * velocityY / 2);
				draggableInHand.setLocation(x, y);
				//draggableInHand.onRelease();

				Droppable droppable = table.getNearestDroppable(
						draggableInHand.getX(), draggableInHand.getY(), x
								+ totalDx, y + totalDy);

				if (droppable != null && from != null && !from.equals(droppable)) {
					if (droppable.getPosition()==Position.Button.TOPRIGHT ||
							(droppable.getPosition()==Position.Public.MID && Tutorial.currentStage==Stages.HUGEPUBLIC)
							|| (droppable.getPosition()==Position.Player.BOTTOM && Tutorial.currentStage==Stages.PLAYERS)) {
						float totalAnimDx = droppable.getX()
								- draggableInHand.getX();
						float totalAnimDy = droppable.getY()
								- draggableInHand.getY();
						System.out.println("animating");
						new OvershootAnimation(from, droppable,
								(Card) draggableInHand, (long) 1000,
								totalAnimDx, totalAnimDy, false).execute();
						from.removeCard(null, (Card) draggableInHand);
						droppable.addCard(null, (Card) draggableInHand);
						Tutorial.isBadJob=false;
					}
					else if (from.equals(droppable)) {
						// dont do anything cause rearrange is made at onSingleTapUp
						// method

					}else{
						draggableInHand.invalidMove();
						Tutorial.isBadJob=true;
					//	draggableInHand.setLocation(x, y);
					//	draggableInHand.onRelease();
					//	if (!(droppable.getId()==Position.Button.TOPRIGHT.ordinal())){
					//		draggableInHand.invalidMove();
					//	}else{
					//		droppable.addCard(null, (Card) draggableInHand);
					//	}
					}
					this.from = null;
				} else {
					if((droppable!=null && droppable.isFlingabble())|| droppable==null){
						draggableInHand.invalidMove();
						Tutorial.isBadJob=true;
						//ServerConnection.getConnection().send(new Message(new InvalidMoveAction(draggableInHand.getId())));
					}
					else{
						draggableInHand.onRelease();
					}
						
				}
				
				draggableInHand = null;
			}
			
		} else{
			return false;
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		AutoHide.get().stop();
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		float x = e2.getX();
		float y = e2.getY();
		System.out.println("TableView.onScroll()");
		if ((Tutorial.currentStage==Stages.DECK || Tutorial.currentStage==Stages.HUGEPUBLIC || Tutorial.currentStage==Stages.PLAYERS)
				&& !(Tutorial.isBadJob!=null && Tutorial.isBadJob==false)) {
			if (draggableInHand != null) {
				draggableInHand.setLocation(x, y);
			}
		} else{
			return false;
		}
		return true;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		boolean answer=false;
		
		if (Tutorial.currentStage==Stages.END){
			((Activity)context).finish();
		}else if ((Tutorial.currentStage==Stages.WELCOME
				|| Tutorial.currentStage==Stages.DROPPABLES)
				|| (Tutorial.isBadJob!=null && !Tutorial.isBadJob)){
			
			Tutorial.isBadJob=null;
			Tutorial.nextStage();
			switch (Tutorial.currentStage){
				
				case PLAYERS:{
					//add all players
					AutoHide.get().start(context);
					addAllPlayers(true);
									
					break;
				}
				case DROPPABLES:{ 
					removeCardsFromAllDroppables();
					addManySmall();
					break;
				}
				case HUGEPUBLIC:{
					removeSmallPublics();

					addOneBig();
					break;
				}
				case DECK:{
					DeckArea deckArea=new DeckArea(Position.Button.TOPRIGHT, false, false);
					table.addDroppable(deckArea);
					new GlowAnimation(deckArea, 2000, Color.argb(255, 255, 255, 255)).execute();
					break;
				}				
				default:{}
			}
			answer=true;
		}
		
		return answer;
	}

	private void addAllPlayers(boolean addGuiCard) {
		table.clearDroppables();
		for (Position.Player position: Position.Player.values()){
			//add new players if they aren't bottom player
			if (position!=Position.Player.BOTTOM){
				Player player=new Player(null, position, position.getId()
						, new PlayerHandler(), DroppableLayout.LayoutType.HEAP);
				player.setPosition(position);
			
				table.addDroppable(player);
				
				if (addGuiCard){
					player.addCard(null,new GuideCard(new CardHandler(),false));
				}
			}
			
			
			
		}	
		//add me (I'm bottom player)
		table.addDroppable(me);
		if(addGuiCard)
			table.addDroppable(p);
	}

	private void removeSmallPublics() {
		table.clearDroppables();
		addAllPlayers(false);
		DeckArea deckArea=new DeckArea(Position.Button.TOPRIGHT);
		table.addDroppable(deckArea);
		deckArea.deltCard(guideCard);
		
		
	}

	private void removeCardsFromAllDroppables() {
		for (Droppable droppable : table.getDroppables()){
			if (droppable.getId()==Position.Player.BOTTOM.getId()){
				continue;
			}			
			droppable.clear();
		}
		
	}

	@Override
	public boolean onRotate(MotionEvent arg0, float angleRadians) {
		if (Tutorial.currentStage==Stages.ROTATE) {
			if (draggableInHand != null) {
				float angle = draggableInHand.getAngle()
						+ TouchManager.getDegreesFromRadians(angleRadians);
				//System.out.println(angle+"::"+TouchManager.getDegreesFromRadians(angleRadians));
				if (Math.round(angle)<380 && Math.round(angle)>340){					
					draggableInHand.onRelease();
					Tutorial.isBadJob=false;
				}else{
					draggableInHand.setAngle(angle);
				}
				
				
			}
		} else{
			return false;
		}
		return true;
	}

	@Override
	public boolean onPinch(MotionEvent arg0, float currentDistance,
			float previousDistance, float scale) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {	
	}
	
	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		touchmanager.onTouchEvent(event);
		return true;
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		drawThread = new DrawThread(holder, table);
		drawThread.setName("drawThread");
		drawThread.setRunning(true);
		drawThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.setRunning(false);
		// SurfaceViewThread.running=false;
		try {
			drawThread.join();
		} catch (InterruptedException e) {
		}
	}

	public void setxDimention(int screenWidth) {
		// TODO Auto-generated method stub
		
	}

	public void setyDimention(int screenHeight) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		table.setDimentions(getMeasuredWidth(), getMeasuredHeight());
	}
	

}

package carddeckplatform.game;

import java.util.ArrayList;

import communication.actions.InvalidMoveAction;
import communication.link.ServerConnection;
import communication.messages.Message;

import utils.Button;
import utils.Card;
import utils.Player;
import utils.Point;
import utils.Position;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.controller.ClientController;
import client.gui.animations.GlowAnimation;
import client.gui.animations.FlipAnimation;
import client.gui.animations.OvershootAnimation;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;
import client.gui.entities.Table;
import client.gui.entities.TouchHandler;
import client.gui.entities.TouchManager;

public class TableView extends SurfaceView implements SurfaceHolder.Callback,
		TouchHandler {
	private Table table;
	private Context cont;
	private Matrix translate;
	private Draggable draggableInHand = null;
	private Droppable from = null;
	private int xDimention;
	private int yDimention;
	private boolean uiEnabled = false;
	TouchManager touchmanager;
	static DrawThread drawThread;

	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		translate = new Matrix();
		this.xDimention = getMeasuredWidth();
		this.yDimention = getMeasuredHeight();
		cont = context;
		table = new Table(context);

		setFocusable(true); // necessary for getting the touch events.

		// making the UI thread
		getHolder().addCallback(this);
		table.setTableImage(R.drawable.boardtest);

		touchmanager = new TouchManager(context, this, 2);

	}

	public void onMove(float dx, float dy) {
		translate.postTranslate(dx, dy);
	}
	public void startDraggableMotion(String username, int id){
		Draggable draggable = table.getDraggableById(id);
		synchronized (draggable) {
			draggable.setCarried(true);
			draggable.setCarrier(username);
		}
		draggable.saveOldCoord();
	}
	
	
	
	
	
	public void draggableMotion(String username, int id, float x, float y) {
		Draggable draggable = table.getDraggableById(id);
		synchronized (draggable) {
//			draggable.setCarried(true);
//			draggable.setCarrier(username);
			draggable.setLocation(x, y);
		}
	}

	public void endDraggableMotion(int id) {
		Draggable draggable = table.getDraggableById(id);
		draggable.setCarried(false);
		draggable.setCarrier("");
	}

	public void addDroppable(Droppable droppable) {
		table.addDroppable(droppable);
	}

	private void addNewDraggable(Card card, Droppable destination) {
		destination.deltCard(card);
		table.mappDraggable(card);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		table.setDimentions(getMeasuredWidth(), getMeasuredHeight());
	}

	// the method that draws
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// if you want another background color
		System.out.println("TableView.onDraw()");
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
		touchmanager.onTouchEvent(event);
		return true;
	}

	public void popToast(final String displayMessage) {
		this.post(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(cont, displayMessage,
						Toast.LENGTH_SHORT);
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

	public Droppable getDroppableById(Integer id) {
		return table.getDroppableById(id);
	}

	public void moveCard(Card card, int from, int to, int byWhomId) {
		ArrayList<Card> cards = new ArrayList<Card>();
		System.out.println("card moved: " + card.getId());
		cards.add(((Card) (table.getDraggableById(card.getId()))));
		// cards.add(card);
		moveCards(cards, from, to, (Player)table.getDroppableById(byWhomId));
	}

	public void moveCards(ArrayList<Card> cards, int from, int to, Player byWhom) {
		Droppable destination = table.getDroppableById(to);
		Droppable source = table.getDroppableById(from);
		for (Card card : cards) {		
			if (destination.addCard(byWhom, card)){
				source.removeCard(byWhom, card);
			}else{
				card.invalidMove();
			}
		}
	}
	
	public void invalidMove(int cardId){
		Draggable draggable = table.getDraggableById(cardId);
		draggable.invalidMove();
	}

	public void addPlayer(Player newPlayer) {
		table.addDroppable(newPlayer);
	}

	public void dealCards(ArrayList<Card> cards, int to) {
		Droppable destination = table.getDroppableById(to);
		for (Card card : cards) {
			card.setOwner(destination.getPosition());
			addNewDraggable(card, destination);
		}
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("TableView.surfaceCreated()");
		drawThread = new DrawThread(holder);
		drawThread.setName("drawThread");
		drawThread.setRunning(true);
		drawThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("TableView.surfaceDestroyed()");
		drawThread.setRunning(false);
		// SurfaceViewThread.running=false;
		try {
			drawThread.join();
		} catch (InterruptedException e) {
		}
	}

	class DrawThread extends Thread {
		private SurfaceHolder surfaceHolder;

		private boolean running = false;

		public void setRunning(boolean value) {
			running = value;
		}

		public DrawThread(SurfaceHolder surfaceHolder) {
			this.surfaceHolder = surfaceHolder;
		}

		@Override
		public void run() {
			Canvas c;
			while (running) {
				try {
					// Don't hog the entire CPU
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				c = null;
				try {

					c = surfaceHolder.lockCanvas(null);
					synchronized (surfaceHolder) {
						// System.out.println(c.getDensity());

						table.draw(c);// draw it
					}
				} finally {
					if (c != null) {
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

	public void swapPositions(Player player, Position.Player swappedWith) {
		// TODO Auto-generated method stub

	}

	/*************************************************************************************
	 * 
	 * touch handlers
	 *************************************************************************************/
	@Override
	public boolean onDoubleTap(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("TableView.onDoubleTap()");
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("TableView.onDoubleTapEvent()");
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("TableView.onSingleTapConfirmed()");
		return true;
	}

	@Override
	public boolean onDown(MotionEvent event) {
		float X = event.getX();
		float Y = event.getY();
		if (uiEnabled) {
			draggableInHand = table.getNearestDraggable(X, Y);
			if (draggableInHand != null) {
				if (draggableInHand.isMoveable()) {
					from = table.getNearestDroppable(X, Y);
					
					draggableInHand.onClick();
				} else {
					popToast("You cannot move this card");
					draggableInHand = null;
				}
			}
		} else
			popToast("It's not your turn now!!");
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		System.out.println("TableView.onFling()");

		if (uiEnabled) {
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
					if (droppable.isFlingabble() ) {
						float totalAnimDx = droppable.getX()
								- draggableInHand.getX();
						float totalAnimDy = droppable.getY()
								- draggableInHand.getY();
						System.out.println("animating");
						new OvershootAnimation(from, droppable,
								(Card) draggableInHand, (long) 1000,
								totalAnimDx, totalAnimDy, true).execute();
					}
					else if (from.equals(droppable)) {
						// dont do anything cause rearrange is made at onSingleTapUp
						// method

					}else{
						draggableInHand.setLocation(x, y);
						draggableInHand.onRelease();
						if (!droppable.onDrop(ClientController.get().getMe(), from,
								((Card) draggableInHand))){
							draggableInHand.invalidMove();
						}
					}
					this.from = null;
				} else {
					if((droppable!=null && droppable.isFlingabble())|| droppable==null){
						draggableInHand.invalidMove();
						ServerConnection.getConnection().send(new Message(new InvalidMoveAction(draggableInHand.getId())));
					}
					else{
						draggableInHand.onRelease();
					}
						
				}
				
				draggableInHand = null;
			}
		} else{
			popToast("It's not your turn now!!");
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		System.out.println("TableView.onLongPress()");
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		float x = e2.getX();
		float y = e2.getY();
		System.out.println("TableView.onScroll()");
		if (uiEnabled) {
			if (draggableInHand != null) {
				Droppable droppable = table.getNearestDroppable(x, y);
//				if(droppable==from){
//					
//				}else{
					draggableInHand.setLocation(x, y);
					draggableInHand.onDrag();
					
//				}
			}
		} else
			popToast("It's not your turn now!!");

		return true;
	}
	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("TableView.onSingleTapUp()");
		float x = event.getX();
		float y = event.getY();
		Draggable draggable = table.getNearestDraggable(x, y);
		System.out.println("draggable" + draggable);
		if (draggable != null) {
			Droppable droppable = table.getNearestDroppable(x, y);
			int index = droppable.indexOfDraggabale(draggable);
			System.out.println("index:" + index);
			if (index == -1)
				return true;
			droppable.rearrange(index);

		}
		return true;
	}

	@Override
	public boolean onRotate(MotionEvent event, float angleRadians) {
		if (uiEnabled) {
			if (draggableInHand != null) {
				float angle = draggableInHand.getAngle()
						+ TouchManager.getDegreesFromRadians(angleRadians);
				// System.out.println(angle+"::"+TouchManager.getDegreesFromRadians(angleRadians));
				while (angle > 360)
					angle -= 360;
				draggableInHand.setAngle(angle);
			}
		} else
			popToast("It's not your turn now!!");
		return true;
	}

	@Override
	public boolean onPinch(MotionEvent event, float currentDistance,
			float previousDistance, float scale) {
		return true;
	}

	

	public Droppable getDroppableByPosition(Position position) {
		return table.getDroppableByPosition(position);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {	
	}

	public void removeCards(ArrayList<Card> cards, String from2) {
		// TODO Auto-generated method stub
		
	}

	public void setPlayerTurn(Droppable droppable) {
		new GlowAnimation(droppable, 3000).execute();
	}

	public void addButton(Button button) {
		table.addButon(button);
		
	}
	
}

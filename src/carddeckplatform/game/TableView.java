package carddeckplatform.game;

import java.util.ArrayList;

import utils.Card;
import utils.Player;
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
import client.gui.animations.FlipAnimation;
import client.gui.animations.OvershootAnimation;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
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
	// private AnimationTask animationTask;
	private boolean uiEnabled = false;
	TouchManager touchmanager;
	static DrawThread drawThread;

	public void redraw() {
		// post(new Runnable() {
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// invalidate();
		// }
		// });
	}

	public void redraw(final Rect rect) {
		// post(new Runnable() {
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// invalidate(rect);
		// }
		// });
	}

	// private class AnimationTask extends AsyncTask<Integer, Integer, Long>{
	// private BlockingQueue<String> calls = new
	// ArrayBlockingQueue<String>(10000);
	// public void redraw(){
	// // calls.add("xyz");
	// post(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// invalidate();
	// }
	// });
	// }
	//
	// public void stopDrawing(){
	// calls.clear();
	// }
	// @Override
	// protected Long doInBackground(Integer... arg0) {
	// while(true){
	// try {
	// calls.take();
	// onProgressUpdate(0);
	// } catch (InterruptedException ie) {
	// ie.printStackTrace();
	// } catch (IllegalStateException ise){
	// stopDrawing();
	// redraw();
	// }
	//
	// }
	// }
	// @Override
	// protected void onProgressUpdate(Integer... Integ) {
	// CarddeckplatformActivity.h.post(new Runnable() {
	//
	// @Override
	// public void run() {
	// invalidate();
	// }
	// });
	// }
	// }

	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		translate = new Matrix();
		// animationTask = new AnimationTask();
		// animationTask.execute(0);
		this.xDimention = getMeasuredWidth();
		this.yDimention = getMeasuredHeight();
		cont = context;
		table = new Table(context);

		setFocusable(true); // necessary for getting the touch events.

		// making the UI thread
		getHolder().addCallback(this);
		table.setTableImage(R.drawable.boardtest);
		// SurfaceViewThread.threadSurfaceHolder = getHolder();
		// SurfaceViewThread.threadView = this;

		touchmanager = new TouchManager(context, this, 2);

	}

	public void onMove(float dx, float dy) {
		translate.postTranslate(dx, dy);
	}

	public void draggableMotion(String username, int id, float x, float y) {
		Draggable draggable = table.getDraggableById(id);
		// table.setFrontOrRear(draggable,Focus.FRONT);

		// draggable.setCarrier(username);
		// draggable.setLocation(780-x, 460-y);
		synchronized (draggable) {
			draggable.setCarried(true);
			draggable.setCarrier(username);
			draggable.setLocation(x, y);
		}

		redraw();
	}

	public void endDraggableMotion(int id) {
		Draggable draggable = table.getDraggableById(id);
		synchronized (draggable) {
			// table.setFrontOrRear(draggable,Focus.FRONT);
			draggable.setCarried(false);
			draggable.setCarrier("");
		}
		redraw();
	}

	// public void drawMovement(final ArrayList<Card> cards, final int toId,
	// final long initialDelay, final long delay, final boolean
	// revealedWhileMoving, final boolean revealedAtEnd){
	// ArrayList<Thread> drawingThreads = new ArrayList<Thread>();
	// //Card card;
	// //final CountDownLatch cdl=new CountDownLatch(cards.size());
	// final Droppable destination=getDroppableById(toId);
	// for (final Card card : cards){
	// drawingThreads.add( new Thread(new Runnable() {
	// @Override
	// public void run() {
	// card.setRevealed(revealedWhileMoving);
	// int x = card.getCoord().getX();
	// int y = card.getCoord().getY();
	// final ArrayList<Point> vector = StaticFunctions.midLine(x, y,
	// destination.getX(), destination.getY());
	// try {
	// Thread.sleep(initialDelay);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// for(int i=0; i<vector.size(); i++){
	// card.setRevealed(revealedWhileMoving);
	// final int index = i;
	//
	// try {
	// Thread.sleep(delay);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// card.setCoord(vector.get(index).x, vector.get(index).y);
	// card.setAngle(i*10);
	// redraw();
	// }
	// card.setAngle(0);
	// card.setRevealed(revealedAtEnd);
	// }
	// }));
	//
	// }
	// for (Thread drawingThread : drawingThreads){
	// drawingThread.start();
	// }
	// // try {
	// // cdl.await();
	// // } catch (InterruptedException e) {
	// // e.printStackTrace();
	// // }
	// //for (Thread drawingThread : drawingThreads){
	// // try {
	// // drawingThread.join();
	// // } catch (InterruptedException e) {
	// // e.printStackTrace();
	// // }
	// //}
	//
	//
	// }
	// public void moveDraggable(final Draggable draggable, final int newX,
	// final int newY, final long initialDelay, final long delay, final boolean
	// revealedWhileMoving, final boolean revealedAtEnd){
	// Thread drawingThread=
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// //draggable..setRevealed(revealedWhileMoving);
	//
	// int x = draggable.getX();
	// int y = draggable.getY();
	// final ArrayList<Point> vector = StaticFunctions.midLine(x, y, newX,
	// newY);
	// try {
	// Thread.sleep(initialDelay);
	// } catch (InterruptedException e) {
	//
	// e.printStackTrace();
	// }
	// for(int i=0; i<vector.size(); i++){
	// //draggable.getCardLogic().setRevealed(revealedWhileMoving);
	// final int index = i;
	//
	// try {
	// Thread.sleep(delay);
	// } catch (InterruptedException e) {
	//
	// e.printStackTrace();
	// }
	//
	//
	// draggable.setLocation(vector.get(index).x, vector.get(index).y);
	// draggable.setAngle(i*10);
	// redraw()
	// }
	// draggable.setAngle(0);
	// //draggable.getCardLogic().setRevealed(revealedAtEnd);
	//
	// }
	// });
	// drawingThread.start();
	//
	// }

	// public void moveDraggable(Draggable draggable, Droppable droppable, final
	// long initialDelay, final long delay, final boolean revealedWhileMoving,
	// final boolean revealedAtEnd){
	// moveDraggable(draggable, droppable.getX(), droppable.getY(),
	// initialDelay, delay, revealedWhileMoving, revealedAtEnd);
	// }

	public void addDroppable(Droppable droppable) {
		table.addDroppable(droppable);
	}

	// public void addDraggable(ArrayList<CardLogic> cardLogics, Droppable
	// target){
	// animationTask.stopDrawing();
	// // get the first letter of the type and concatenates it with the value.
	// for(CardLogic cardLogic : cardLogics){
	// String key = cardLogic.getType().subSequence(0, 1) +
	// String.valueOf(cardLogic.getValue());
	// System.out.println("the key is: " + key + " the tardet is: " +
	// target.getLogic().getId());
	// int resourceId = getResources().getIdentifier("drawable/" + key,
	// "drawable", "carddeckplatform.game");
	// Card card = new Card(getContext(),resourceId,0,0);
	// card.setCardLogic(cardLogic);
	// table.addDraggable(card);
	// target.addDraggable(card);
	// }
	// //invalidate();
	//
	// redraw()
	// }

	private void addNewDraggable(Card card, Droppable destination) {
		// animationTask.stopDrawing();

		destination.deltCard(card);
		// GuiCard guiCard=new GuiCard(card);

		// table.addDraggable(card);
		table.mappDraggable(card);
		redraw();
	}

	// public void addDraggable(ArrayList<CardLogic> cardLogics,
	// Droppable from, Droppable to) {
	// for(CardLogic cardLogic : cardLogics){
	// Draggable card=table.getDraggableById(cardLogic.getId(),
	// GetMethod.PutInFront);
	// from.removeDraggable(card);
	// to.addDraggable(card);
	// }
	//
	// }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		table.setDimentions(getMeasuredWidth(), getMeasuredHeight());
		// table.setyDimention(getMeasuredHeight());
		// table.setxDimention(getMeasuredWidth());
	}

	// the method that draws
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// if you want another background color
		System.out.println("TableView.onDraw()");
		// draws the table.
		table.draw(canvas, draggableInHand);
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

		// int X = (int) event.getX();
		// int Y = (int) event.getY();
		// int eventAction = event.getAction();
		// if (uiEnabled) {
		// switch (eventAction) {
		//
		// case MotionEvent.ACTION_DOWN: {
		// draggableInHand = table.getNearestDraggable(X, Y);
		//
		// if (draggableInHand != null) {
		// // table.setFrontOrRear(draggableInHand, Focus.FRONT);
		// if (draggableInHand.isMoveable()) {
		// from = table.getNearestDroppable(X, Y);
		// draggableInHand.onClick();
		// } else {
		// popToast("You cannot move this card");
		// draggableInHand = null;
		// }
		// }
		// break;
		// }
		// case MotionEvent.ACTION_MOVE: {
		// if (draggableInHand != null) {
		// draggableInHand.setLocation(X, Y);
		// draggableInHand.onDrag();
		// }
		// break;
		// }
		// case MotionEvent.ACTION_UP: {
		// if (draggableInHand != null) {
		// draggableInHand.setLocation(X, Y);
		// draggableInHand.onRelease();
		//
		// Droppable droppable = table.getNearestDroppable(X, Y);
		// if (droppable != null && from != null) {
		// droppable.onDrop(ClientController.get().getMe(), from,
		// ((Card) draggableInHand));
		//
		// } else {
		// draggableInHand.invalidMove();
		// }
		// draggableInHand = null;
		// }
		//
		// // Droppable droppable2=table.getNearestDroppable(X, Y);
		// // if (droppable2!=null){
		// // droppable2.onClick();
		//
		// // }
		// break;
		// }
		// }// end if enabdles
		// } else {
		// // GUI is not enabled
		//
		// popToast("It's not your turn now!!");
		//
		// }
		// if (draggableInHand != null) {
		// int dragX = (int) draggableInHand.getX();
		// int dragY = (int) draggableInHand.getY();
		// Rect rect = new Rect(dragX - 200, dragY - 200, dragX + 200,
		// dragY + 200);
		// redraw(rect);
		// } else
		// redraw();

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

	public void removeCards(ArrayList<utils.Card> cards, String from) {
		for (utils.Card card : cards) {
			// table.getDroppableById(IDMaker.getMaker().getId(from)).removeCard(card);

		}

	}

	public Droppable getDroppableById(Integer id) {
		return table.getDroppableById(id);
	}

	// public Draggable getDraggableById(Integer id) {
	// return table.getDraggableById(id);
	// }

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
			// if (from == -1) {
			// // new card, create it
			// //System.out.println(GameStatus.username+": card id: "+card.getId());
			// addNewDraggable(card, destination);
			//
			// } else {
			// move card from one zone to another
			source.removeCard(byWhom, card);
			destination.onCardAdded(byWhom, card);
			redraw();
			// drawMovement(cards, destination.getPoint(), 1000, 10,
			// revealWhileMoving, revealAtEnd);
			// }
		}
	}

	// public void revealedDraggable(int id){
	// GuiCard revealed=(GuiCard)table.getDraggableById(id,
	// GetMethod.PutInFront);
	// revealed.getCard().setRevealed(true);
	// redraw()

	// }
	public void addPlayer(Player newPlayer) {
		table.addDroppable(newPlayer);
		redraw();

	}

	public void dealCards(ArrayList<Card> cards, int to) {
		Droppable destination = table.getDroppableById(to);
		for (Card card : cards) {
			card.setOwner(destination.getPosition());
			addNewDraggable(card, destination);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("TableView.surfaceCreated()");
		drawThread = new DrawThread(holder);
		drawThread.setName("drawThread");
		drawThread.setRunning(true);
		drawThread.start();
		// SurfaceViewThread.running=true;
		// surfaceThread.start();

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

						table.draw(c, draggableInHand);// draw it
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

			redraw();
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
				draggableInHand.onRelease();

				Droppable droppable = table.getNearestDroppable(
						draggableInHand.getX(), draggableInHand.getY(), x
								+ totalDx, y + totalDy);

				if (droppable != null && from != null) {
					float totalAnimDx = droppable.getX()
							- draggableInHand.getX();
					float totalAnimDy = droppable.getY()
							- draggableInHand.getY();
					
					new OvershootAnimation(from, droppable,(Card) draggableInHand, (long)1000, totalAnimDx, totalAnimDy, true).execute();
//					new FlipAnimation(from, droppable, (Card)draggableInHand, true).execute(null);
					
					
//					onAnimateMove(from, droppable, true, true, totalAnimDx,
//							totalAnimDy, (long) (2000 * distanceTimeFactor),
//							(Card) draggableInHand);
//					droppable.onDrop(ClientController.get().getMe(), from,
//							((Card) draggableInHand));
					this.from = null;

				} else {
					draggableInHand.invalidMove();
				}
				draggableInHand = null;
				redraw();
			}
		} else
			popToast("It's not your turn now!!");
		return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("TableView.onLongPress()");
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		float X = e2.getX();
		float Y = e2.getY();
		if (uiEnabled) {
			if (draggableInHand != null) {
				draggableInHand.setLocation(X, Y);
				draggableInHand.onDrag();
				redraw();
			}
		} else
			popToast("It's not your turn now!!");

		return true;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("TableView.onSingleTapUp()");
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
				redraw();
			}
		} else
			popToast("It's not your turn now!!");
		return true;
	}

	@Override
	public boolean onPinch(MotionEvent event, float currentDistance,
			float previousDistance, float scale) {
		// TODO Auto-generated method stub
		// System.out.println(scale);
		return true;
	}

	

	public Droppable getDroppableByPosition(Position position) {
		return table.getDroppableByPosition(position);
	}
}

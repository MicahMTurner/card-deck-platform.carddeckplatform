package client.gui.entities;

import java.io.Serializable;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import utils.Card;
import utils.Point;
import IDmaker.IDMaker;
import android.content.Context;
import android.graphics.Canvas;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.controller.ClientController;
import client.gui.animations.Animation;
import client.gui.animations.OvershootAnimation;

import communication.actions.StartDraggableMotionAction;
import communication.link.ServerConnection;
import communication.messages.Message;
	
/**
 * 	
 * This class represents a draggable object (e.i a card).
 *
 */

public abstract class Draggable implements Serializable{	
	
	private String carrier = "";
	private boolean moveable=true;
	protected Point prevCoord;
	protected int id;
	protected Point scale;
	protected transient Shape shape = null;
	protected AnimationFlags animationFlags = new AnimationFlags();
	
	/**
	 * gets the scale of the draggable.
	 * @return
	 */
	public Point getScale() {
		return scale;
	}
	
	/**
	 * gets the geometric shape that represents the draggable.
	 * @return
	 */
	public Shape getShape() {
		Point size = MetricsConvertion.pointRelativeToPx(this.scale);
		if (shape == null) {
			
			shape = new Rectangle(getX() - (size.getX() / 2), getY()
					- (size.getY() / 2), size.getX(), size.getY());
		}
		shape.setX(getX() - (size.getX() / 2));
		shape.setY(getY() - (size.getY() / 2));
		return shape;
	}
	
	
	public boolean isIntersect(Line line) {
		return line.intersects(getShape()) || getShape().contains(line);
	}
	
	public boolean isContain(float x, float y) {
		// return shape.contains(x, y);
		return getShape().contains(x, y);

	}

	protected String frontImg;
	protected String backImg;
	
	
	/**
	 * c'tor of the draggable.
	 */
	public Draggable() {
		this.id=IDMaker.getMaker().getId();
		this.prevCoord=new Point(0,0);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Draggable)) { 
            return false;
        }
        Draggable otherDraggable = (Draggable)other;
        return this.id==otherDraggable.id;		
	}
	
	/**
	 * gets the name of the player that holds this card.
	 * @return
	 */
	public String getCarrier() {
		return carrier;
	}
	
	/**
	 * saves the old coordinates (before any movement).
	 */
	public void saveOldCoord(){
		// save the current location in order to be able to return to this position if needed.
		prevCoord.setX(getCoord().getX());
		prevCoord.setY(getCoord().getY());
	}
	
	
	/**
	 * what happens when the other player clicks a card.
	 * @param from
	 * @param username
	 */
	public void onOtherClick(Droppable from, String username){
		saveOldCoord();
		animationFlags.resetFlags();
		animationFlags.carriedByOther=true;
		setCarrier(username);
		
		from.putCardOnTop((Card)this);
	}
	
	/**
	 * what happens when other player drags the card.
	 */
	public void onOtherDrag(){
		
	}
	
	/**
	 * what happens when other player clicks the card.
	 */
	public void onOtherRelease(){
		animationFlags.resetFlags();
		setCarrier("");
	}
	
	/**
	 * what happens when the current player clicks a card
	 * @param from
	 */
	public void onClick(Droppable from) {		
		saveOldCoord();
		ServerConnection.getConnection().send(new Message(new StartDraggableMotionAction(id,from.getId(), GameEnvironment.get().getPlayerInfo().getUsername())));
		animationFlags.resetFlags();
		animationFlags.carriedByMe=true;
		
		from.putCardOnTop((Card)this);
		//setCarried(true);
	}
	
	/**
	 * what happens when the current player drags a card.
	 */
	public void onDrag() {		
		
		ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(),id,MetricsConvertion.pointPxToRelative(getCoord()));
	}
	
	/**
	 * what happens when the current player releases a card.
	 */
	public void onRelease() {		
		//ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(), id, MetricsConvertion.pointPxToRelative(getCoord()));
		ClientController.sendAPI().endDragMotion(id);
		animationFlags.resetFlags();
	}
	
	/**
	 * returns the game state to its previous state before the invalid move happened.
	 */
	public void invalidMove(){		
//			setLocation(prevCoord.getX(),prevCoord.getY());	
			Animation animation=new OvershootAnimation(prevCoord.getX(), prevCoord.getY(),(Card) this, 1000, false);
			animation.execute();
			animation.waitForMe();
			
			
			
			//ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(), id, MetricsConvertion.pointPxToRelative(getCoord()));
			//ClientController.sendAPI().endDragMotion(getMyId());			
			//angle=0;			
		}
	
	/**
	 * gets the X coordinate.
	 * @return
	 */
	public float getX(){
		return getCoord().getX();
	}
	
	/**
	 * gets the Y coordinate.
	 * @return
	 */
	public float getY(){
		return getCoord().getY();
	}
	
	/**
	 * set the carrier name.
	 * @param carrier
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
	/**
	 * draws the card.
	 * @param canvas
	 * @param context
	 */
	public abstract void draw(Canvas canvas,Context context);
	/**
	 * gets the coordinate of the draggable.
	 * @return
	 */
	public abstract Point getCoord();
	
	public abstract int sensitivityRadius();
	
	/**
	 * set the location of the draggable.
	 * @param x
	 * @param y
	 */
	public abstract void setLocation(float x, float y);		
	/**
	 * set the angle of the draggable.
	 * @param angle
	 */
	public abstract void setAngle(float angle);
	/**
	 * gets the angle of the draggable.
	 * @return
	 */
	public abstract float getAngle();
	/**
	 * moves the draggable from one draggable to another.
	 * @param source souece draggable.
	 * @param destination destinaion draggable.
	 */
	public abstract void moveTo(final Droppable source,final Droppable destination);
	
	/**
	 * set that the draggable is carried.
	 * @param carried
	 */
	public void setCarried(boolean carried) {
		animationFlags.carriedByOther = carried;
	}
	
	/**
	 * gets whether the draggable is carried or not.
	 * @return
	 */
	public boolean isCarried() {
		return animationFlags.carriedByOther;
	}
	
	/**
	 * gets whether the draggable is in hand or not.
	 * @return
	 */
	public boolean isInHand(){
		return animationFlags.carriedByMe;
	}
	
	/**
	 * gets whether the draggable is movable or not.
	 * @return
	 */
	public boolean isMoveable() {
		return moveable;
	}
	
	/**
	 * set that the draggable is movable.
	 * @param moveable
	 */
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	/**
	 * gets the draggable's id.
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * clear animation.
	 */
	public void clearAnimation() {
		this.carrier="";
		
	}
	
	/**
	 * get the animation flags that tells which animations are effecting the current draggable.
	 * @see AnimationFlags
	 * @return
	 */
	public AnimationFlags getAnimationFlags() {
		return animationFlags;
	}
	
	
	
	
}

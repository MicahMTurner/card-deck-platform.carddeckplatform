package client.gui.entities;

import java.io.Serializable;
import java.util.ArrayList;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import communication.actions.StartDraggableMotionAction;
import communication.link.ServerConnection;
import communication.messages.Message;


import utils.Card;
import utils.Player;
import utils.Point;

import carddeckplatform.game.StaticFunctions;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.controller.ClientController;
import client.gui.animations.Animation;
import client.gui.animations.OvershootAnimation;
import IDmaker.IDMaker;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
	
	
public abstract class Draggable implements Serializable{	
	
	private String carrier = "";
	private boolean moveable;
	protected Point prevCoord;
	protected int id;
	protected boolean carried=false;
	protected boolean inHand=false;
	protected Point scale;
	protected transient Shape shape = null;
	
	enum CardStatus{
		MINIMIZED,NORMAl,SELECTED,DRAGGED,
	}
	
	public Point getScale() {
		return scale;
	}
	
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
	
	public String getCarrier() {
		return carrier;
	}
	public void saveOldCoord(){
		// save the current location in order to be able to return to this position if needed.
		prevCoord.setX(getCoord().getX());
		prevCoord.setY(getCoord().getY());
	}
	public void onClick(int fromId) {		
		saveOldCoord();
		inHand = true;
		ServerConnection.getConnection().send(new Message(new StartDraggableMotionAction(id,fromId, GameEnvironment.get().getPlayerInfo().getUsername())));
		//setCarried(true);
	}
	public void onDrag() {		
		
		ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(),id,MetricsConvertion.pointPxToRelative(getCoord()));
	}
	public void onRelease() {		
		//ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(), id, MetricsConvertion.pointPxToRelative(getCoord()));
		ClientController.sendAPI().endDragMotion(id);
		inHand = false;
	}
	
	public void invalidMove(){		
//			setLocation(prevCoord.getX(),prevCoord.getY());	
			Animation animation=new OvershootAnimation(prevCoord.getX(), prevCoord.getY(),(Card) this, 1000, false);
			animation.execute();
			animation.waitForMe();
			
			
			
			//ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(), id, MetricsConvertion.pointPxToRelative(getCoord()));
			//ClientController.sendAPI().endDragMotion(getMyId());			
			//angle=0;			
		}
	
	
	public float getX(){
		return getCoord().getX();
	}
	public float getY(){
		return getCoord().getY();
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public abstract void draw(Canvas canvas,Context context);
	public abstract Point getCoord();
	public abstract int sensitivityRadius();
	public abstract void setLocation(float x, float y);		
	public abstract void setAngle(float angle);
	public abstract float getAngle();
	public abstract void moveTo(final Droppable source,final Droppable destination);
	
	public void setCarried(boolean carried) {
		this.carried = carried;
	}
	public boolean isCarried() {
		return carried;
	}
	
	public boolean isInHand(){
		return inHand;
	}
	
	public boolean isMoveable() {
		return true;
	}
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	public int getId(){
		return id;
	}
	
	public void clearAnimation() {
		this.carrier="";
		
	}
	
	
	
	
}

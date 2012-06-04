package client.gui.entities;

import java.io.Serializable;
import java.util.ArrayList;


import utils.Card;
import utils.Player;
import utils.Point;

import carddeckplatform.game.StaticFunctions;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.controller.ClientController;
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
	
	
	enum CardStatus{
		MINIMIZED,NORMAl,SELECTED,DRAGGED,
	}
	
	public Point getScale() {
		return scale;
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
	public void onClick() {		
		// save the current location in order to be able to return to this position if needed.
		prevCoord.setX(getCoord().getX());
		prevCoord.setY(getCoord().getY());
		inHand = true;
		//setCarried(true);
	}
	public void onDrag() {		
		
		ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(),id,MetricsConvertion.pointPxToRelative(getCoord()));
	}
	public void onRelease() {		
		ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(), id, MetricsConvertion.pointPxToRelative(getCoord()));
		ClientController.sendAPI().endDragMotion(id);
		inHand = false;
	}
	public void invalidMove(){		
//			setLocation(prevCoord.getX(),prevCoord.getY());	
			new OvershootAnimation(prevCoord.getX(), prevCoord.getY(),(Card) this, 1000, false).execute();
			
//			ClientController.sendAPI().dragMotion(GameEnvironment.get().getPlayerInfo().getUsername(), id, MetricsConvertion.pointPxToRelative(getCoord()));
//			ClientController.sendAPI().endDragMotion(id);			
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

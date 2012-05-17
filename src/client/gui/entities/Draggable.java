package client.gui.entities;

import java.io.Serializable;


import utils.Point;

import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import IDmaker.IDMaker;
import android.content.Context;
import android.graphics.Canvas;


public abstract class Draggable implements Serializable{	
	
	private String carrier = "";
	private boolean moveable;
	protected Point prevCoord;
	protected int id;
	protected boolean carried=false;
	protected boolean inHand=false;
	
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
		
		ClientController.sendAPI().dragMotion(GameStatus.username,id,MetricsConvertion.pointPxToRelative(getCoord()));
	}
	public void onRelease() {		
		ClientController.sendAPI().dragMotion(GameStatus.username, id, MetricsConvertion.pointPxToRelative(getCoord()));
		ClientController.sendAPI().endDragMotion(id);
		inHand = false;
	}
	public void invalidMove(){		
			setLocation(prevCoord.getX(),prevCoord.getY());			
			ClientController.sendAPI().dragMotion(GameStatus.username, id, MetricsConvertion.pointPxToRelative(getCoord()));
			//ClientController.sendAPI().endDragMotion(getMyId());			
			//angle=0;			
		}
	
	
	public int getX(){
		return getCoord().getX();
	}
	public int getY(){
		return getCoord().getY();
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public abstract void draw(Canvas canvas,Context context);
	public abstract Point getCoord();
	public abstract int sensitivityRadius();
	public abstract void setLocation(int x, int y);		

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

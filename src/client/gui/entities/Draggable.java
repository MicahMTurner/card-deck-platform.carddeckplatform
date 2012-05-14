package client.gui.entities;

import java.io.Serializable;
import java.util.Random;

import utils.Point;

import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class Draggable implements Serializable{	
	//private Droppable container;
	private String carrier = "";
	private boolean moveable;
	protected Point prevCoord;
	//protected Point coord;
	protected float angle = 0;
	protected boolean carried;
	
	//protected abstract void draw(Canvas canvas,Context context);
	public Draggable() {
//		this.coord=new Point(0,0);
		this.prevCoord=new Point(0,0);
	}
	public abstract int sensitivityRadius();
	public String getCarrier() {
		return carrier;
	}
	public void onClick() {		
		// save the current location in order to be able to return to this position if needed.
		prevCoord.setX(getCoord().getX());
		prevCoord.setY(getCoord().getY());
	}
	public void onDrag() {		
		ClientController.sendAPI().dragMotion(GameStatus.username,getMyId(),getCoord());
	}
	public void onRelease() {		
		ClientController.sendAPI().dragMotion(GameStatus.username, getMyId(), getCoord());
		ClientController.sendAPI().endDragMotion(getMyId());
	}
	public void invalidMove(){		
			setLocation(prevCoord.getX(),prevCoord.getY());			
			ClientController.sendAPI().dragMotion(GameStatus.username, getMyId(), getCoord());
			//ClientController.sendAPI().endDragMotion(getMyId());			
			angle=0;			
		}
	
	public abstract Point getCoord();
	
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
	
	
	//public Droppable getContainer(){
	//	return container;
	//}
	
	//public void setContainer(Droppable container){
	//	this.container = container;
	//}
		
	public abstract void setLocation(int x, int y);
		

	public void randomizeAngle(){
		Random generator = new Random();
		float randomIndex = generator.nextInt(20);
		randomIndex -= 10;
		angle = randomIndex;
	}
	
	public void setAngle(float angle){
		this.angle = angle%360;
	}
	public void setCarried(boolean carried) {
		this.carried = carried;
	}
	public boolean isCarried() {
		return carried;
	}
	public boolean isMoveable() {
		return true;
	}
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	public abstract int getMyId() ;
	
	public void clearAnimation() {
		this.carrier="";
		
	}
}

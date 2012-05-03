package client.gui.entities;

import java.util.Random;

import utils.Point;

import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class Draggable extends View {
	private Droppable container;
	private String carrier = "";
	private boolean moveable;
	protected int myId;
	protected Point prevCoord;
	protected Point coord;
	protected float angle = 0;
	public void draw(Canvas canvas){
		onDraw(canvas);
		
	}
	@Override
	protected abstract void onDraw(Canvas canvas);
	
	public Draggable(Context context,int myId) {
		super(context);		
		this.myId=myId;
		
	}
	public abstract int sensitivityRadius();
	
	public void onClick() {		
		// save the current location in order to be able to return to this position if needed.
		prevCoord.setX(coord.getX());
		prevCoord.setY(coord.getY());
	}
	public void onDrag() {		
		ClientController.sendAPI().dragMotion(GameStatus.username,myId,coord);
	}
	public void onRelease() {		
		ClientController.sendAPI().dragMotion(GameStatus.username, myId, coord);
		ClientController.sendAPI().endDragMotion(myId);
	}
	public void invalidMove(){		
			coord.setX(prevCoord.getX());
			coord.setY(prevCoord.getY());
			ClientController.sendAPI().dragMotion(GameStatus.username, myId, coord);
			ClientController.sendAPI().endDragMotion(myId);			
			angle=0;			
		}
	
	public  int getX(){
		return coord.getX();
	}
	public  int getY(){
		return coord.getY();
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
	
	
	public Droppable getContainer(){
		return container;
	}
	
	public void setContainer(Droppable container){
		this.container = container;
	}
		
	public void setLocation(int x, int y){
		coord.setX(x);
		coord.setY(y);
	}
	
	public void randomizeAngle(){
		Random generator = new Random();
		float randomIndex = generator.nextInt(20);
		randomIndex -= 10;
		angle = randomIndex;
	}
	
	public void setAngle(float angle){
		this.angle = angle%360;
	}
	public boolean isMoveable() {
		return moveable;
	}
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	public int getMyId() {
		return myId;
	}
	
}

package client.gui.entities;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;

import carddeckplatform.game.GameStatus;

import utils.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;


public class Table {
	public enum Focus {FRONT, REAR}
	
	//private Stack<Draggable> draggables = new Stack<Draggable>();
	private ArrayList<Droppable> droppables;
	private Hashtable<Integer,Draggable>mappedDraggables;
	private Bitmap img;
	private Context context;
	private int xDimention;
	private int yDimention;
	

	public Table(Context context){
		this.context = context;
		this.droppables= new ArrayList<Droppable>();
		this.mappedDraggables= new Hashtable<Integer,Draggable>();
	}
//	public void addDraggable(Draggable draggable){
//		synchronized (draggables) {
//			draggables.add(draggable);
//		}
//	}
	
	public void addDroppable(Droppable droppable){
		droppables.add(droppable);
		//map all draggables and their id in given droppable
		for (Draggable draggable : droppable.getCards()){
			mappedDraggables.put(draggable.getId(),draggable);
		}
	}
	
	public void setTableImage(int drawable){
		img = BitmapFactory.decodeResource(context.getResources(), drawable); 
	}
	
//	public void setFrontOrRear(Draggable draggable,Focus focus){
//		draggables.remove(draggable);
//		if (focus.equals(Focus.FRONT)){
//			draggables.add(draggable);
//		}else{
//			draggables.add(0,draggable);
//		}
//	}
	
//	private void changeDraggableDrawingOrder(Draggable draggable, GetMethod g){
//		Draggable tmp = draggable;
//		draggables.remove(draggable);
//		if(g==GetMethod.PutInFront){		
//			draggables.add(tmp);
//		}
//		else if(g==GetMethod.PutInBack){
//			draggables.add(0,tmp);
//		}
//	}
	

	public void mappDraggable(Draggable draggable) {
		mappedDraggables.put(draggable.getId(), draggable);
		
	}
	
	
	public Draggable getDraggableById(int id){	
		Draggable answer=mappedDraggables.get(id);
//		if (answer==null){
//			//draggable wasn't mapped, refresh mapping table
//			for (Droppable droppable : droppables){
//				for (Draggable draggable : droppable.getCards()){
//					mappedDraggables.put(draggable.getId(), draggable);
//					if (draggable.getId()==id){
//						answer=draggable;
//					}
//				}
//			}
//		}
		return answer;
	}

	
	public Droppable getDroppableById(int id){
		for(Droppable d : droppables){
			if(d.getId()==id){
				return d;
			}
		}
		return null;
	}
	
//	public Draggable getNearestDraggable(int x, int y){
//		// go in reverse in order to get the most top draggable.
//		Draggable res = null;
//		for(int i=draggables.size()-1; i>=0; i--){	// TO CORRECT THE LOOP!!!
//			Draggable d = draggables.get(i);
//			double radius  = Math.sqrt( (double) (((d.getX()-x)*(d.getX()-x)) + (d.getY()-y)*(d.getY()-y)));
//			if(radius <= d.sensitivityRadius()){
//				// puts the draggable in the front.
//				res = d;
//				//changeDraggableDrawingOrder(d, g);
//				break;
//			}
//		}
//		return res;
//	}
	
	
	public Draggable getNearestDraggable(int x, int y){
		Draggable answer=null;
		//get nearest container where draggable can be found at
		Droppable nearestDroppable=getNearestDroppable(x, y);
		if (nearestDroppable!=null){
			double radius;
			/*go over cards in found droppable and check if any card there is in radius
			 *priority given to cards at the beginning of the droppable's list*/
			for (Draggable draggable : nearestDroppable.getCards()){
				radius=Math.sqrt( (double) (((draggable.getX()-x)*(draggable.getX()-x)) + (draggable.getY()-y)*(draggable.getY()-y)));
				if (radius<=draggable.sensitivityRadius()){
					answer=draggable;
					break;
				}
			}			
		}
		return answer;
	}
	
	public Droppable getNearestDroppable(int x, int y){
		for(Droppable d : droppables){
			double radius  = Math.sqrt( (double) (((d.getX()-x)*(d.getX()-x)) + (d.getY()-y)*(d.getY()-y)));
			if(radius <= d.sensitivityRadius()){
				return d;
			}
		}
		return null;
	}
	

	
	public void draw(Canvas canvas){		
				
	if (canvas!=null){
		canvas.drawColor(Color.TRANSPARENT);    	  
        canvas.scale(1, 1);

        	canvas.rotate(180, GameStatus.screenWidth/2, GameStatus.screenHeight/2);

		Matrix matrix = new Matrix();
		matrix.postScale((float) xDimention, (float) yDimention);
		//canvas.drawBitmap(android.graphics.Bitmap.createScaledBitmap(img, xDimention, yDimention,true),(float)0,(float)0, null);
		//synchronized (droppables) {
			for(Droppable d : droppables){
				d.draw(canvas, context);
			}
		//}
		//synchronized (droppables) {
			for (Droppable d: droppables){			
				AbstractList<Card>cards=d.getCards();
				synchronized (cards){
					for (Card card : cards){
						card.draw(canvas, context);
					}
				}
			}
		//}
		
		//synchronized (draggables){
		//	for(Draggable d : draggables){
		//		d.draw(canvas, context);

		//	}
		//}
	}
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

	
	
}

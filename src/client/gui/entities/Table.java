package client.gui.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import utils.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


public class Table {
	public enum Focus {FRONT, REAR}
	
	//private Stack<Draggable> draggables = new Stack<Draggable>();
	private ArrayList<Droppable> droppables = new ArrayList<Droppable>();
	private Bitmap img;
	private Context context;
	private int xDimention;
	private int yDimention;
	

	public Table(Context context){
		this.context = context;
	}
//	public void addDraggable(Draggable draggable){
//		synchronized (draggables) {
//			draggables.add(draggable);
//		}
//	}
	
	public void addDroppable(Droppable dropable){
		droppables.add(dropable);
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
	
	
	public Draggable getDraggableById(int id){		
		for (Droppable droppable : droppables){
			for (Card card : droppable.getCards()){
				if (card.getId()==id){
					return card;
				}
			}
		}
		return null;
	}
		
//		for(Draggable draggable : draggables){	// TO CORRECT THE LOOP!!!
//			if(draggable.getId()==id){
//				answer=draggable;
//				//changeDraggableDrawingOrder(draggable, g);
//				break; 
//			}
//		}
//		return answer;
//	}
	
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
		Droppable nearestDroppable=getNearestDroppable(x, y);
		if (nearestDroppable!=null){
			answer=nearestDroppable.getCards().get(0);
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
		Matrix matrix = new Matrix();
		matrix.postScale((float) xDimention, (float) yDimention);
		canvas.drawBitmap(android.graphics.Bitmap.createScaledBitmap(img, xDimention, yDimention,true),(float)0,(float)0, null);
		
		for(Droppable d : droppables){
			d.draw(canvas, context);
		}
		for (Droppable d: droppables){			
			ArrayList<Card>cards=d.getCards();
			synchronized (cards){
				for (Card card : cards){
					card.draw(canvas, context);
				}
			}
		}
		
		//synchronized (draggables){
		//	for(Draggable d : draggables){
		//		d.draw(canvas, context);

		//	}
		//}
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

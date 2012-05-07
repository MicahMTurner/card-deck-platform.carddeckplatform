package client.gui.entities;

import java.util.ArrayList;
import java.util.Stack;

import utils.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


public class Table {
	public enum GetMethod {KeepTheSame, PutInFront, PutInBack}
	
	private Stack<Draggable> draggables = new Stack<Draggable>();
	private ArrayList<Droppable> droppables = new ArrayList<Droppable>();
	private Bitmap img;
	private Context context;
	private int xDimention;
	private int yDimention;
	

	public Table(Context context){
		this.context = context;
	}
	public void addDraggable(Draggable draggable){
		synchronized (draggables) {
			draggables.add(draggable);
		}
	}
	
	public void addDroppable(Droppable dropable){
		droppables.add(dropable);
	}
	
	public void setTableImage(int drawable){
		img = BitmapFactory.decodeResource(context.getResources(), drawable); 
	}
	
	
	private void changeDraggableDrawingOrder(Draggable draggable, GetMethod g){
		Draggable tmp = draggable;
		draggables.remove(draggable);
		if(g==GetMethod.PutInFront){		
			draggables.add(tmp);
		}
		else if(g==GetMethod.PutInBack){
			draggables.add(0,tmp);
		}
	}
	
	
	public Draggable getDraggableById(int id, GetMethod g){
		Draggable answer=null;
		for(Draggable draggable : draggables){	// TO CORRECT THE LOOP!!!
			if(draggable.getMyId()==id){
				answer=draggable;
				changeDraggableDrawingOrder(draggable, g);
				break; 
			}
		}
		return answer;
	}
	
	public Droppable getDroppableById(int id){
		for(Droppable d : droppables){
			if(d.getMyId()==id){
				return d;
			}
		}
		return null;
	}
	
	public Draggable getNearestDraggable(int x, int y, GetMethod g){
		// go in reverse in order to get the most top draggable.
		Draggable res = null;
		for(int i=draggables.size()-1; i>=0; i--){	// TO CORRECT THE LOOP!!!
			Draggable d = draggables.get(i);
			double radius  = Math.sqrt( (double) (((d.getX()-x)*(d.getX()-x)) + (d.getY()-y)*(d.getY()-y)));
			if(radius <= d.sensitivityRadius()){
				// puts the draggable in the front.
				res = d;
				changeDraggableDrawingOrder(d, g);
				break;
			}
		}
		return res;
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
		System.out.println("drawing table");
		Matrix matrix = new Matrix();
		matrix.postScale((float) xDimention, (float) yDimention);
		canvas.drawBitmap(android.graphics.Bitmap.createScaledBitmap(img, xDimention, yDimention,true),(float)0,(float)0, null);
		for(Droppable d : droppables){
			d.draw(canvas, context);
		}
		
		synchronized (draggables){
			for(Draggable d : draggables){
				d.draw(canvas, context);
				//Bitmap resizedBitmap=null;
				
//				Matrix matrix = new Matrix();
//				matrix.postRotate(angle);
//				if(revealed)
//					resizedBitmap = Bitmap.createBitmap(frontImg, 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
//				else
//					resizedBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.back), 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
//				canvas.drawBitmap(resizedBitmap, getX()-25, getY()-20, new Paint());
//				
//				        
//				
//				
//				// if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
//		        if(isCarried){
//		        	Paint paint = new Paint(); 		   
//		        	// draws the name of the carrier.
//		            paint.setColor(android.graphics.Color.BLACK); 
//		            paint.setTextSize(20); 
//		            canvas.drawText(carrier,getX()-25, getY()-20, paint);
//		            // draws the hand.
//		            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hand),getX()-30, getY()+20 , paint);
//		        }        
			//for(Iterator<Draggable> it = draggables.iterator(); it.hasNext();){
					//Draggable d = it.next();
					//d.draw(canvas,context);
			}
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

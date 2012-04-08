package client.gui.entities;

import java.util.ArrayList;
import java.util.Stack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;

public class Table {
	private Stack<Draggable> draggables = new Stack<Draggable>();
	private ArrayList<Droppable> droppables = new ArrayList<Droppable>();
	private Bitmap img;
	private Context context;
	private int xDimention;
	private int yDimention;
	
	
//	public Table(Context context, int xDimention, int yDimention){
//		this.xDimention = xDimention;
//		this.yDimention = yDimention;
//		this.context = context;
//	}
	public Table(Context context){
		this.context = context;
	}
	public void addDraggable(Draggable draggable){
		draggables.add(draggable);
	}
	
	public void addDroppable(Droppable dropable){
		droppables.add(dropable);
	}
	
	public void setTableImage(int drawable){
		img = BitmapFactory.decodeResource(context.getResources(), drawable); 
	}
	
	
	
	public Draggable getDraggableById(int id, boolean putInFront){
		Draggable res=null;
		for(Draggable d : draggables){	// TO CORRECT THE LOOP!!!
			if(d.getId()==id){
				res=d;
				if(putInFront){
					Draggable tmp = d;
					draggables.remove(d);
					draggables.add(tmp);
				}
				break; 
			}
		}
		return res;
	}
	
	public Draggable getNearestDraggable(int x, int y, boolean putInFront){
		// go in reverse in order to get the most top draggable.
		Draggable res = null;
		for(int i=draggables.size()-1; i>=0; i--){	// TO CORRECT THE LOOP!!!
			Draggable d = draggables.get(i);
			double radius  = Math.sqrt( (double) (((d.getX()-x)*(d.getX()-x)) + (d.getY()-y)*(d.getY()-y)));
			if(radius <= d.sensitivityRadius()){
				// puts the draggable in the front.
				res = d;
				if(putInFront){
					Draggable tmpDrop = d;
					draggables.remove(d);
					draggables.add(tmpDrop);
					System.out.println(tmpDrop.getX());
				}
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
		Matrix matrix = new Matrix();
		matrix.postScale((float) xDimention, (float) yDimention);
		canvas.drawBitmap(android.graphics.Bitmap.createScaledBitmap(img, xDimention, yDimention,true),(float)0,(float)0, null);
		for(Droppable d : droppables){
			d.draw(canvas);
		}
		
		for(Draggable d : draggables){
			d.draw(canvas);
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

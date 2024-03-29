package client.gui.entities;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import org.newdawn.slick.geom.Line;

import utils.Button;
import utils.Card;
import utils.Position;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;


public class Table {
	public enum Focus {FRONT, REAR}
	Line line=null;//needed for DEBUG fling
	
	//private Stack<Draggable> draggables = new Stack<Draggable>();
	private ArrayBlockingQueue<Droppable> droppables;
	private ArrayBlockingQueue<Button> buttons;
	
	private Hashtable<Integer,Draggable>mappedDraggables;
	private Bitmap img;
	private Context context;
	private int xDimention;
	private int yDimention;
	
	//private Matrix matrix;
	
	static public ArrayList<Card> animatedCards;
	
	public Table(Context context){
		//this.matrix=new Matrix();		
		this.context = context;
		this.img=null;
		this.droppables= new ArrayBlockingQueue<Droppable>(20);
		this.mappedDraggables= new Hashtable<Integer,Draggable>();
		this.buttons = new ArrayBlockingQueue<Button>(4);
		Table.animatedCards = new ArrayList<Card>();
	}

	
	public void addDroppable(Droppable droppable){
		droppables.add(droppable);
	}
	
	public void setTableImage(int drawable){
		//want to change the table image dynamically (already got dimensions)
		if (img!=null){
			img = android.graphics.Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), drawable), xDimention, yDimention,true);
		}else{
			//first time we set the table image, dimensions are still 0
			img = BitmapFactory.decodeResource(context.getResources(), drawable);
		}
	}
	

	public void mappDraggable(Draggable draggable) {
		mappedDraggables.put(draggable.getId(), draggable);
		
	}
	
	
	public Draggable getDraggableById(int id){	
		Draggable answer=mappedDraggables.get(id);
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
	
	
	public Draggable getNearestDraggable(float x, float y){
	
		Draggable answer=null;
		//get nearest container where draggable can be found at
		Droppable nearestDroppable=getNearestDroppable(x, y);
		if (nearestDroppable!=null){
			/*go over cards in found droppable and check if any card there is in radius
			 *priority given to cards at the beginning of the droppable's list*/
			AbstractList<Card>nearestDroppableCards=nearestDroppable.getCards();
			int size=nearestDroppableCards.size();
			Draggable draggable;
			for (int i=0;i<size;i++){
				draggable=nearestDroppableCards.get(i);
				if(draggable.isContain(x, y)){
					answer=draggable;
					break;
				}
			}			
		}
		return answer;
	}
	
	public Droppable getNearestDroppable(float x, float y){
		for(Droppable d : droppables){
			if(d.isContain(x, y))
				return d;
		}
		return null;
	}
	public Droppable getNearestDroppable(float x1,float y1,float x2,float y2){
		Line line= new Line(x1, y1, x2, y2);
		this.line=line;
		for (Droppable d : droppables) {
			if(d.isIntersect(line))
				return d;
		}		
		return null;
	}
	

	
	public void draw(Canvas canvas){		
				
	if (canvas!=null){
		//ArrayList<Draggable>priority=null;
		canvas.drawColor(Color.TRANSPARENT);    	  
        //canvas.scale(1, 1);
        
		
			canvas.drawBitmap(img,(float)0,(float)0, null);	
			
			ArrayList<ConcurrentSkipListSet<Draggable>>priorityDraggableSets=
					new ArrayList<ConcurrentSkipListSet<Draggable>>();
			
			for (Button b : buttons){
				b.draw(canvas, context);
			}
			for (Droppable d : droppables ){
				d.draw(canvas, context);
			}
			for(Droppable d : droppables){
				d.drawMyCards(canvas, context);
			}
			
			for(Card c : animatedCards){
				c.draw(canvas, context);
			}
			animatedCards.clear();
			
			for (ConcurrentSkipListSet<Draggable> set : priorityDraggableSets){
				for (Draggable draggable : set){
					draggable.draw(canvas, context);
				}
			}
				
//			for(Droppable d : droppables){
//					ArrayList<Draggable>holding=d.drawMyCards(canvas, context);
//					if (holding!=null){
//						if (priority==null){
//							priority=new ArrayList<Draggable>();
//						}
//						for(Draggable priorityDraggable : holding){
//							priority.add(priorityDraggable);
//						}
//					}
//			}
//			if (priority!=null){
//				for (Draggable priorityDraggable : priority){
//					priorityDraggable.draw(canvas, context);
//				}
//			}
			
//			if(this.line!=null){
//				Paint paint= new Paint();
//				paint.setColor(Color.RED);
//				canvas.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), paint);
//			}
		}
	}

	public void setDimentions(int xDimention,int yDimention) {
		this.xDimention = xDimention;
		this.yDimention=yDimention;
		img=android.graphics.Bitmap.createScaledBitmap(img, xDimention, yDimention,true);
	}
//	public int getyDimention() {
//		return yDimention;
//	}	

	public Droppable getDroppableByPosition(Position position) {
		for(Droppable d : droppables){
			if(d.getPosition().equals(position)){
				return d;
			}
		}
		return null;
	}

	public void addButon(Button button) {
		buttons.add(button);
	}


	public Button getNearestButton(float x, float y) {

		Button answer=null;
		//get nearest container where draggable can be found at
		for(Button button: buttons){
			if(button.isContain(x, y))
				answer= button;
		}
		return answer;
	
	}

	public void clearCards() {
		for(Droppable d : droppables){
			d.clear();
		}
	}
	
	public void clearDroppables(){
		droppables.clear();
	}
	
	public ArrayBlockingQueue<Droppable> getDroppables() {
		return droppables;
	}
}

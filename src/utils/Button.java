package utils;

import handlers.ButtonEventsHandler;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import carddeckplatform.game.BitmapHolder;
import client.gui.entities.MetricsConvertion;
/**
 * represents a button 
 * @author Yoav
 *
 */
public class Button{
	private ButtonEventsHandler handler;
	private String text;
	private String image;
	private Position.Button position;
	private Point scale;
	private Paint paint;
	private boolean enabled;
	private transient Shape shape;
	/**
	 * 
	 * @return true if button is enabled, false OW
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * constructor 
	 * @param handler button's handler
	 * @param position button's position (make sure it is not the same as card deck area
	 * @param text text to display on button
	 */
	public Button(ButtonEventsHandler handler,Position.Button position,String text) {
		//super(position.getId(), position, new Point(10,13), DroppableLayout.LayoutType.NONE);
		this.handler=handler;
		this.text=text;
		this.image="button";
		this.position=position;
		this.scale=new Point(10, 10);
		this.paint=new Paint();
		paint.setTextSize(20);
		paint.setColor(Color.argb(170, 0, 0, 0));
		enabled = true;
	}
	/**
	 * get button's position
	 * @return button's position
	 */
	public Position.Button getPosition() {
		return position;
	}
	/**
	 * set button's position
	 * @param position new button's position
	 */
	public void setPosition(Position.Button position) {
		this.position = position;
	}
	/**
	 * perform click action, calling onClick function in handler
	 * @see ButtonEventsHandler
	 */
	public void onClick(){
		if(enabled)
			handler.onClick();
	}
	
	public void draw(Canvas canvas, Context context){
		Bitmap buttonBitmap=BitmapHolder.get().getBitmap(image);
		Matrix matrix = new Matrix();		 
		Point p= MetricsConvertion.pointRelativeToPx(scale);
		float x=MetricsConvertion.pointRelativeToPx(position.getPoint()).getX();
		float y=MetricsConvertion.pointRelativeToPx(position.getPoint()).getY();
		
		// transformations.		
		matrix.postScale((float)p.getX()/(float)buttonBitmap.getWidth(), (float)p.getY()/(float)buttonBitmap.getHeight());
		matrix.postTranslate(x-p.getX()/2, y-p.getY()/2);
		
		canvas.drawBitmap(buttonBitmap, matrix, null);
		canvas.drawText(text,x,y, paint);
	}

	public float getX() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getX();
	}

	public float getY() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getY();
	}
	
	
	public boolean isContain(float x, float y) {
		return getShape().contains(x, y);
	}
	private Shape getShape() {
		if (shape == null) {
			Point size = MetricsConvertion.pointRelativeToPx(this.scale);
			shape = new Rectangle(getX() - (size.getX() / 2), getY()
					- (size.getY() / 2), size.getX(), size.getY());
		}
		return shape;
	}
	
}

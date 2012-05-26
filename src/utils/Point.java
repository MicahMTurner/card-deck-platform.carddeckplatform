package utils;

import java.io.Serializable;

public class Point implements Serializable {
	float x;
	float y;
	public Point(float x, float y) {
		this.x=x;
		this.y=y;
	}
	public float getX() {
		synchronized(this){
			return x;
		}
	}
	public float getY() {
		synchronized(this){
			return y;
		}
	}	
	public void move(float x,float y){
		synchronized(this){
			this.x=x;
			this.y=y;
		}
	}
	public void setX(float x) {
		synchronized(this){
			this.x = x;
		}
	}
	public void setY(float y) {
		synchronized(this){
			this.y = y;
		}
	}
	public Point(Point point) {
		this.x=point.x;
		this.y=point.y;
	}
}

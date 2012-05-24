package utils;

import java.io.Serializable;

public class Point implements Serializable {
	int x;
	int y;
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public int getX() {
		synchronized(this){
			return x;
		}
	}
	public int getY() {
		synchronized(this){
			return y;
		}
	}	
	public void move(int x,int y){
		synchronized(this){
			this.x=x;
			this.y=y;
		}
	}
	public void setX(int x) {
		synchronized(this){
			this.x = x;
		}
	}
	public void setY(int y) {
		synchronized(this){
			this.y = y;
		}
	}
	public Point(Point point) {
		this.x=point.x;
		this.y=point.y;
	}
}

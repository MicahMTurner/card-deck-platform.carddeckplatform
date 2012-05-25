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
		return x;
	}
	public float getY() {
		return y;
	}
	public void move(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
}

package client.gui.entities;

import java.io.Serializable;

public class AnimationFlags implements Serializable{
	public boolean fling=false;
	public boolean rearrange=false;
	public boolean flip=false;
	public boolean carriedByMe=false;
	public boolean carriedByOther=false;
	
	public void resetFlags(){
		fling=false;
		rearrange=false;
		flip=false;
		carriedByMe=false;
		carriedByOther=false;
	}
	
	public boolean checkRearrangeCondition(){
		return (!fling && !flip && !carriedByMe && !carriedByOther);
	}
	
	public boolean isAnimated(){
		return (rearrange || fling || flip || carriedByMe || carriedByOther);
	}
}

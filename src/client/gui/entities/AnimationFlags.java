package client.gui.entities;

import java.io.Serializable;

public class AnimationFlags implements Serializable{
	public boolean fling=false;
	public boolean rearrange=false;
	public boolean flip=false;
	
	public void resetFlags(){
		fling=false;
		rearrange=false;
		flip=false;
	}
	
}

package client.gui.entities;

import logic.client.LogicDroppable;
import carddeckplatform.game.R;
import android.content.Context;
import android.graphics.Canvas;

public class PublicPlace extends Droppable {
	
	
	
	private LogicDroppable logicDroppable;
	
	public PublicPlace(Context context, int x,int y){
		this.x = x;
		this.y = y;
		this.context = context;
	}
	
	@Override
	public int sensitivityRadius() {
		// TODO Auto-generated method stub
		return 300;
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrop(Draggable draggable) {
		// TODO Auto-generated method stub
		draggable.setLocation(getX(), getY());
		
		logicDroppable.onDropHandler(draggable.getCardLogic());
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

}

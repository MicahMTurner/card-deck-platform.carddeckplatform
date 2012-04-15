package freeplay.gui;

import java.util.ArrayList;

import war.actions.RecieveCardAction;
import logic.card.CardLogic;
import logic.client.LogicDroppable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import client.controller.ClientController;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;

public class PublicArea extends Droppable {
	
	public PublicArea(Context context, int x,int y, LogicDroppable logicDroppable){
		this.x = x;
		this.y = y;
		this.logicDroppable = logicDroppable;
		this.context = context;
	}

	@Override
	public int sensitivityRadius() {
		// TODO Auto-generated method stub
		return 500;
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
		logicDroppable.onDropHandler(draggable.getCardLogic());
		
		ArrayList<CardLogic> cd = new ArrayList<CardLogic>();
		cd.add(draggable.getCardLogic());
		ClientController.outgoingAPI().outgoingCommand(new RecieveCardAction(cd,draggable.getContainer().getLogic().getId(),logicDroppable.getId()));
	}

	@Override
	public void addDraggable(Draggable draggable) {
		// TODO Auto-generated method stub
		draggable.setContainer(this);
		logicDroppable.onDropHandler(draggable.getCardLogic());
	}

	@Override
	public void removeDraggable(Draggable draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CardLogic getDraggable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawText("Public Area", getX(), getY(), new Paint());
	}

}

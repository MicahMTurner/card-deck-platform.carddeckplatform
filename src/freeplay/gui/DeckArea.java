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

public class DeckArea extends Droppable {

	public DeckArea(Context context, int x,int y, LogicDroppable logicDroppable){
		super(context,x,y,logicDroppable);
	}
	
	@Override
	public int sensitivityRadius() {
		// TODO Auto-generated method stub
		return 80;
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
		
		addDraggable(draggable);
		
		ArrayList<CardLogic> cd = new ArrayList<CardLogic>();
		cd.add(draggable.getCardLogic());
		ClientController.outgoingAPI().outgoingCommand(new RecieveCardAction(cd,draggable.getContainer().getLogic().getId(),logicDroppable.getId()));
	}

	@Override
	public void addDraggable(Draggable draggable) {
		// TODO Auto-generated method stub
		draggable.setContainer(this);
		draggable.setLocation(getX(), getY());
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
		canvas.drawText("Deck Area", getX(), getY(), new Paint());
	}

}

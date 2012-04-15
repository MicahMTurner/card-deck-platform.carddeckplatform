package war.gui;

import java.util.ArrayList;

import war.actions.RecieveCardAction;
import communication.link.ServerConnection;

import client.controller.ClientController;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import logic.card.CardLogic;
import logic.client.LogicDroppable;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class PublicPlace extends Droppable {
	
	public PublicPlace(Context context, int x,int y, LogicDroppable logicDroppable){
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
	public void addDraggable(Draggable draggable) {
		draggable.setLocation(getX(), getY());
		draggable.randomizeAngle();

		draggable.setContainer(this);
		logicDroppable.addCard(draggable.getCardLogic());
	}

	@Override
	public void onDrop(Draggable draggable) {
		
		
		
		// creates new array in order to send it in the action (this action receives ArrayList of CardLogic's)
		ArrayList<CardLogic> cd = new ArrayList<CardLogic>();
		cd.add(draggable.getCardLogic());
		System.out.println("PublicPlace drop");
		ClientController.outgoingAPI().outgoingCommand(new RecieveCardAction(cd,draggable.getContainer().getLogic().getId(),logicDroppable.getId()));
		System.out.println("PublicPlace after drop");
		addDraggable(draggable);
		logicDroppable.onDropHandler(draggable.getCardLogic());
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.place),getX()-28,getY()-27,null);
	}

	@Override
	public CardLogic getDraggable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeDraggable(Draggable draggable) {
		// TODO Auto-generated method stub
		logicDroppable.getCards().remove(draggable.getCardLogic());
	}



}

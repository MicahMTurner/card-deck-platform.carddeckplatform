//package freeplay.gui;
//
//import java.util.ArrayList;
//
//import war.actions.RecieveCardAction;
//import logic.card.CardLogic;
//import logic.client.LogicDroppable;
//import android.content.Context;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import carddeckplatform.game.R;
//import client.controller.ClientController;
//import client.gui.entities.Draggable;
//import client.gui.entities.Droppable;
//
//public class PlayerArea extends Droppable {
//
//	
//	public PlayerArea(Context context, int x,int y, LogicDroppable logicDroppable){
//		super(context,x,y,logicDroppable);
//	}
//	
//	@Override
//	public int sensitivityRadius() {
//		// TODO Auto-generated method stub
//		return 120;
//	}
//
//	@Override
//	public void onClick() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onHover() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onDrop(Draggable draggable) {
//		// TODO Auto-generated method stub
//		logicDroppable.onDropHandler(draggable.getCardLogic());
//		
//		ArrayList<CardLogic> cd = new ArrayList<CardLogic>();
//		cd.add(draggable.getCardLogic());
//		ClientController.outgoingAPI().outgoingCommand(new RecieveCardAction(cd,draggable.getContainer().getLogic().getId(),logicDroppable.getId()));
//	}
//
//	@Override
//	public void addDraggable(Draggable draggable) {
//		// TODO Auto-generated method stub
//		logicDroppable.onDropHandler(draggable.getCardLogic());
//	}
//
//	@Override
//	public void removeDraggable(Draggable draggable) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public CardLogic getDraggable() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void draw(Canvas canvas) {
//		// TODO Auto-generated method stub
//		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.playerarea),getX()-75,getY()-30,null);
//		//canvas.drawText("Player Area", getX(), getY(), new Paint());
//	}
//
//}

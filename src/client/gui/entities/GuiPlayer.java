package client.gui.entities;

import java.util.ArrayList;

import utils.Card;
import utils.Player;
import utils.Position;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import client.controller.ClientController;

public class GuiPlayer extends Droppable {
	
	//PlayerEventsHandler handler;
	private Player player;
	//private String username;

	
	//private Position.Player position;
	
	public GuiPlayer(Player player) {
		this.player=player;
		//this.username=username;
		//this.position=position;
		//this.handler=handler;		
	}
	
	public Player getPlayer() {
		return player;
	}
	@Override
	public int sensitivityRadius() {		
		return 30;
	}
	@Override
	public void addCard(Player player,Card card){		
		player.addCard(card);
	}
	public void removeCard(Player player,Card card){
		player.remove(card);		
		ArrayList<Card>cards=new ArrayList<Card>();
		cards.add(card);
		ClientController.sendAPI().cardRemoved(cards, player.getUserName());
	}
	//public void endTurn(){		
	//	handler.onTurnEnd(player);
	//	ClientController.sendAPI().endTurn(player.getPosition());
	//}
	public void roundEnded(){
		player.roundEnded(player);		
	}
	
	public Position.Player getPosition() {
		return player.getPosition();
	}
	//public void setPosition(Position.Player position) {
	//	this.position = position;
	//}
	public String getUsername() {
		return player.getUserName();
	}
	
	//public void setUsername(String username) {
	//	this.username = username;
	//}
	
	@Override
	public int getX() {		
		return player.getPosition().getX();
	}
	@Override
	public int getY() {	
		return player.getPosition().getY();
	}
	@Override
	public void draw(Canvas canvas,Context context) {		
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), 0x7f02002e),getX()-28,getY()-27,null);
	}

	@Override
	public void deltCard(Card card) {
		player.deltCard(card);
		
	}

	@Override
	public int getMyId() {		
		return player.getId();
	}

	@Override
	public int cardsHolding() {		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		player.clear();
		
	}

}

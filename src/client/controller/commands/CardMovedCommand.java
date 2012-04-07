package client.controller.commands;

public class CardMovedCommand {
	private String username; 
	private int id;
	private int x;
	private int y;
	
	public CardMovedCommand(String username, int id , int x , int y){
		this.username = username;
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}

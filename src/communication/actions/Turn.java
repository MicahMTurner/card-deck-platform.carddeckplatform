package communication.actions;


import client.controller.ClientController;



public class Turn implements Action{
	int playerId;
	public Turn(int playerId) {
		this.playerId=playerId;
		
	}
	@Override
	public void execute() {
		
//		for(Player player : ClientController.get().getPlayers()){
//			player.setMyTurn(false);
//		}
		
		ClientController.get().playerTurn(playerId);
	}

}

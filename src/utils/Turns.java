package utils;


import java.util.LinkedList;
import java.util.Queue;



public class Turns {	
	
	private static Queue<Position.Player> clockTurns(Position.Player startingPlayer, boolean clockWise){
		Queue<Position.Player>turns=new LinkedList<Position.Player>();
		
		Position.Player[] positions=Position.Player.values();		
		int nextPlayer=startingPlayer.ordinal();
		
		while (turns.isEmpty() || nextPlayer!=(startingPlayer.ordinal())){
			turns.add(positions[nextPlayer]);
			if (clockWise){
				nextPlayer=(nextPlayer+1)%(positions.length);
			}
			else{
				nextPlayer-=(nextPlayer+1)%(positions.length);
			}
		}
		return turns;
		
	}
	
	public static Queue<Position.Player> clockWise(Position.Player startingPlayer){
		return clockTurns(startingPlayer,true);
			
	}
	public static Queue<Position.Player> counterClockWise(Position.Player startingPlayer){
		return clockTurns(startingPlayer,false);
		
	}
}

package war;

import logic.client.Game;



public class War extends Game{
	private WarPrefs prefs=new WarPrefs();
	private WarLogic logic=new WarLogic();
	
	public War() {
		// TODO Auto-generated constructor stub
	}
	public WarLogic getLogic() {
		return logic;
	}
	public WarPrefs getPrefs() {
		return prefs;
	}
	@Override
	protected void setNewTools() {
		// TODO Auto-generated method stub
		
	}
}

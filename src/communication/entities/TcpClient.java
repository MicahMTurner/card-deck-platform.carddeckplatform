package communication.entities;

public class TcpClient implements Client {
	String ip;
	String name;
	
	public TcpClient(){}
	
	public TcpClient(String ip , String name){
		this.ip = ip;
		this.name = name;
	}
	
	
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ip;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}

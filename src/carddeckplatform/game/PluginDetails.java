package carddeckplatform.game;

public class PluginDetails {
	public String name;
	public String date;
	public String address;
	int rank;
	long sum;
	long numOfvotes;
	
	public PluginDetails(String name, String date, String address, long sum,
			long numOfvotes) {
		super();
		this.name = name;
		this.date = date;
		this.address = address;
		this.sum = sum;
		this.numOfvotes = numOfvotes;
		System.out.println((double)sum/(numOfvotes*10));
		this.rank=(int) Math.round((double)sum/(numOfvotes*10)*10)-1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}

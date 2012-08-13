package carddeckplatform.game;

public class PluginDetails {
	public String name;
	public String date;
	public String address;
	int rank;
	long sum;
	long numOfvotes;
	long size;
	
	public PluginDetails(String name, String date, String address, long sum,
			long numOfvotes,long size) {
		super();
		this.name = name;
		this.date = date;
		this.address = address;
		this.sum = sum;
		this.numOfvotes = numOfvotes;
		this.rank=(int) Math.round((double)sum/(numOfvotes*10)*10)-1;
		this.size=size;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
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

package carddeckplatform.game;

public class PluginDetails {
	public String detail;
	public String date;
	public String address;
	int rank;
	long sum;
	long numOfvotes;
	long size;
	String filename;
	
	public PluginDetails(String detail, String date, String address, long sum,
			long numOfvotes,long size,String filename) {
		super();
		this.detail = detail;
		this.date = date;
		this.address = address;
		this.sum = sum;
		this.numOfvotes = numOfvotes;
		this.rank=(int) Math.round((double)sum/(numOfvotes*10)*10)-1;
		this.size=size;
		this.filename=filename;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getName() {
		return detail;
	}
	public void setName(String name) {
		this.detail = name;
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

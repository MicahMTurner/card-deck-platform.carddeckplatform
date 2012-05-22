package communication.link;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public abstract class HostFinder implements Runnable{
	private ArrayList<HostId> hosts;
	private CountDownLatch cdl;
	
	public HostFinder() {
		hosts=new ArrayList<HostId>();
		this.cdl=new CountDownLatch(1);
	}
	
	public abstract  void findAvailableHosts(ArrayList<HostId> hosts);
	
	public ArrayList<HostId> findHosts(){		
		new Thread(this).start();
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return hosts;
	}
	
	@Override
	public void run() {
		findAvailableHosts(hosts);
		cdl.countDown();
	}
	
}

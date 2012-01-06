package carddeckplatform.game;


import communication.link.Receiver;
import communication.link.Sender;
import communication.link.TcpReceiver;
import communication.link.TcpSender;
import communication.messages.Message;
import communication.messages.SampleMessage;

import android.app.Activity;
import android.os.Bundle;

public class CarddeckplatformActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SampleObserver so = new SampleObserver();
        so.setCntxt(getBaseContext());
        Receiver rc = new TcpReceiver(9999);
        System.out.println("Start Listening");
        rc.reg(so);
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Creating message");
        Sender se = new TcpSender("localhost" , 9999);
        SampleMessage msg = new SampleMessage();
        msg.name = "Michael";
        msg.messageType = "SampleMessage";
        se.send(msg.messageType , msg);
        
    }
}


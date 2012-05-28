package communication.link;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Streams{
		private ObjectOutputStream out;
		private ObjectInputStream in;
		
		public Streams(ObjectOutputStream out, ObjectInputStream in){
			this.out = out;
			this.in = in;
		}

		
		// -------------------------- GETTERS AND SETTERS ------------------------
		public ObjectOutputStream getOut() {
			return out;
		}

		public void setOut(ObjectOutputStream out) {
			this.out = out;
		}

		public ObjectInputStream getIn() {
			return in;
		}

		public void setIn(ObjectInputStream in) {
			this.in = in;
		}
		// --------------------END OF GETTERS AND SETTERS ------------------------
		
	}
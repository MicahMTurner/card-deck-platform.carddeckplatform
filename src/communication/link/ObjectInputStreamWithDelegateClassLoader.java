package communication.link;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

import carddeckplatform.game.ClassLoaderDelegate;

public class ObjectInputStreamWithDelegateClassLoader extends ObjectInputStream {

	
	public ObjectInputStreamWithDelegateClassLoader(InputStream input)
			throws StreamCorruptedException, IOException {
		super(input);
	}


	@Override
	protected Class<?> resolveClass(ObjectStreamClass osClass)
			throws IOException, ClassNotFoundException {
		
		return Class.forName(osClass.getName(), false, ClassLoaderDelegate.getDelegate());
	}
	
	
	
	

	
	
}

package carddeckplatform.game;

public class ClassLoaderDelegate {
	
	private static final  ClassLoaderDelegate delegate = new ClassLoaderDelegate();
	
	private ClassLoader classLoader  = this.getClass().getClassLoader();
	
	private ClassLoaderDelegate () {}
	
	public static ClassLoaderDelegate getInstance(){
		return delegate;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public static ClassLoader getDelegate(){
		return getInstance().getClassLoader();
	}
	

}

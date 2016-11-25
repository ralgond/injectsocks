package ht.misc.injectsocks;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.net.URLClassLoader;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.ServiceLoader;

public class Main {
	@SuppressWarnings({ "resource", "rawtypes" })
	public static void premain(String args, Instrumentation inst) throws Exception {
		System.out.println("INFO: JarFile="+args);
		
		File jarFile = new File(args);
		URL url = jarFile.toURI().toURL();
		URLClassLoader agentJarLoader = new URLClassLoader(new URL[]{url}, ClassLoader.getSystemClassLoader().getParent());
		
		ServiceLoader<ClassFileTransformer> t = ServiceLoader.load(ClassFileTransformer.class, agentJarLoader);
		Iterator it = t.iterator();
		while (it.hasNext()) {
			inst.addTransformer((ClassFileTransformer)it.next());
			break;
		}
	}
}

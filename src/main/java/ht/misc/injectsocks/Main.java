package ht.misc.injectsocks;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.io.File;
import java.net.URL;

public class Main {
	public static void premain(String args, Instrumentation inst) throws Exception {
		System.out.println("INFO: JarFile="+args);
		
		File jarFile = new File(args);
		URL url = jarFile.toURI().toURL();
		URLClassLoader agentJarLoader = new URLClassLoader(new URL[]{url}, ClassLoader.getSystemClassLoader().getParent());
		
		agentJarLoader.loadClass("ht.misc.injectsocks.ConfigReader");
		ProxyManager.getInstance().updateProxy(ConfigReader.getSocksHost(), ConfigReader.getSocksPort());
		
		Object obj = agentJarLoader.loadClass("ht.misc.injectsocks.InjectSockstTransformerImpl").newInstance();
		InjectSocksTransformer tranformer = new InjectSocksTransformer(obj);
		inst.addTransformer(tranformer);
	}
}

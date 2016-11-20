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
		
		/*
		Object obj = agentJarLoader.loadClass("ht.misc.injectsocks.InjectSocksTransformer").newInstance();
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Void.TYPE;
		Object paramValues[] = new Object[0];
		Method method = obj.getClass().getMethod("newTest", null);
		Object ret = method.invoke(obj, paramValues);
		
		System.out.println(ClassLoader.getSystemClassLoader());
		System.out.println(agentJarLoader);
		System.out.println(obj.getClass().getClassLoader());
		System.out.println(ret.getClass().getClassLoader());
		*/
		ProxyManager.getInstance().updateProxy(ConfigReader.getSocksHost(), ConfigReader.getSocksPort());
		
		Object obj = agentJarLoader.loadClass("ht.misc.injectsocks.InjectSockstTransformerImpl").newInstance();
		InjectSocksTransformer tranformer = new InjectSocksTransformer(obj);
		inst.addTransformer(tranformer);
	}
}

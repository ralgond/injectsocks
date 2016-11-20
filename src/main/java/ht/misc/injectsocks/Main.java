package ht.misc.injectsocks;

import java.lang.instrument.Instrumentation;

public class Main {
	public static void premain(String args, Instrumentation inst) {
		ProxyManager.getInstance().updateProxy(ConfigReader.getSocksHost(), ConfigReader.getSocksPort());
		
		inst.addTransformer(new InjectSocksTransformer());
	}
}

package ht.misc.injectsocks;

import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyManager {
	final static ProxyManager INSTANCE = new ProxyManager();
	
	Proxy proxy;
	private ProxyManager() {
		SocketAddress addr = new InetSocketAddress("192.168.100.1", 80);
		proxy = new Proxy(Proxy.Type.SOCKS, addr);
	}
	
	public static ProxyManager getInstance() {
		return INSTANCE;
	}
	
	public static Proxy getProxy() {
		return INSTANCE.proxy;
	}
	
	public void updateProxy(String socksHost, int socksPort) {
		SocketAddress addr = new InetSocketAddress(socksHost, socksPort);
		proxy = new Proxy(Proxy.Type.SOCKS, addr);
	}
}

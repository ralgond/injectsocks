/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ht.misc.injectsocks;

import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyManager {
	final static ProxyManager INSTANCE = new ProxyManager();
	
	Proxy proxy;
	private ProxyManager() {
		SocketAddress addr = new InetSocketAddress(ConfigReader.getSocksHost(), ConfigReader.getSocksPort());
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

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

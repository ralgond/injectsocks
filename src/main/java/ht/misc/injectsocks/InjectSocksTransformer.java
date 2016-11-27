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

import java.lang.reflect.Method;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class InjectSocksTransformer implements ClassFileTransformer {
	Object impl;
	
	public InjectSocksTransformer(Object obj) {
		impl = obj;
	}
	
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
	        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		
		Method methods[] = impl.getClass().getMethods();
		for (int i = 0; i < methods.length; ++i) {
			Method method = methods[i];
			if (method.getName().equals("transform")) {
				Object paramValues[] = new Object[5];
				paramValues[0] = loader;
				paramValues[1] = className;
				paramValues[2] = classBeingRedefined;
				paramValues[3] = protectionDomain;
				paramValues[4] = classfileBuffer;
				
				try {
					Object ret = method.invoke(impl, paramValues);
					return (byte[])ret;
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
	
	public byte[] inject(byte[] classfileBuffer) {
		Method methods[] = impl.getClass().getMethods();
		for (int i = 0; i < methods.length; ++i) {
			Method method = methods[i];
			if (method.getName().equals("inject")) {
				Object paramValues[] = new Object[1];
				paramValues[0] = classfileBuffer;
				
				try {
					Object ret = method.invoke(impl, paramValues);
					return (byte[])ret;
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
	
	public void printClassByteCode(byte code[]) {
		//impl.printClassByteCode(code);
		Method methods[] = impl.getClass().getMethods();
		for (int i = 0; i < methods.length; ++i) {
			Method method = methods[i];
			if (method.getName().equals("printClassByteCode")) {
				Object paramValues[] = new Object[1];
				paramValues[0] = code;
				
				try {
					method.invoke(impl, paramValues);
				} catch (Exception e) {
				}
			}
		}
	}
}


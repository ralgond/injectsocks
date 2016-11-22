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


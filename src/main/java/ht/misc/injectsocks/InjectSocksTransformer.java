package ht.misc.injectsocks;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.InsnList;


public class InjectSocksTransformer implements ClassFileTransformer {
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
	        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		if (!ConfigReader.isNeedInject(className)) {
			return classfileBuffer;
		}

		System.out.println("INFO: Injecting class: "+className);
		return inject(classfileBuffer);
	}
	
	public byte[] inject(byte[] classfileBuffer) {
		try {
			ClassReader cr = new ClassReader(classfileBuffer);
			ClassNode cn = new ClassNode();
			cr.accept(cn, 0);
			
			ArrayList<AbstractInsnNode> injectPos = new ArrayList<AbstractInsnNode>();
			
			@SuppressWarnings("unchecked")
			List<MethodNode> methods = (List<MethodNode>)cn.methods;
	        for (int i = 0; i < methods.size(); ++i) {
	            MethodNode method = methods.get(i);
	            InsnList instructions = method.instructions;
	            if (instructions.size() <= 0)
	            	continue;
	            
            	//System.out.println("Method: "+method.name+" ");
                for (int j = 0; j < instructions.size(); ++j) {
                	AbstractInsnNode insn = (AbstractInsnNode)instructions.get(j);
                    //System.out.println("\tInsn: opc="+OpcodeUtil.getOpcode(insn.getOpcode())+", type="+insn.getType());
                    if (insn.getType() == AbstractInsnNode.METHOD_INSN) {
                		MethodInsnNode min = (MethodInsnNode)insn;
                		//System.out.printf("\t\towner=%s, name=%s, desc=%s\n", min.owner, min.name, min.desc);
                		
                		if (min.owner.equals("java/net/Socket") && min.name.equals("<init>") && min.desc.equals("()V")) {
                			min.desc = "(Ljava/net/Proxy;)V";
                			injectPos.add(min);
                		}
                    }
	            }
	            
		        for (int k = 0; k < injectPos.size(); k++) {
		        	AbstractInsnNode pos = injectPos.get(k);
		        	MethodInsnNode newMin = new MethodInsnNode(
		        			Opcodes.INVOKESTATIC,
		        			"ht/misc/injectsocks/ProxyManager",
		        			"getProxy",
		        			"()Ljava/net/Proxy;",
		        			false);
		        	instructions.insertBefore(pos, newMin);
		        }
		        
	        }
	        
	        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	        cn.accept(cw);
			byte[] injectedClassfileBuffer = cw.toByteArray();
			System.out.printf("INFO: classfileBuffer.legnth=%d, injectedClassfileBuffer.length=%d\n", 
					classfileBuffer.length, injectedClassfileBuffer.length);
			
			return injectedClassfileBuffer;
		} catch (Throwable e) {
			e.printStackTrace();
			return classfileBuffer;
		}
	}
	
	public void printClassByteCode(byte code[]) {
		ClassReader cr = new ClassReader(code);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		@SuppressWarnings("unchecked")
		List<MethodNode> methods = (List<MethodNode>)cn.methods;
        for (int i = 0; i < methods.size(); ++i) {
            MethodNode method = methods.get(i);
            InsnList instructions = method.instructions;
            if (instructions.size() <= 0)
            	continue;
            
        	System.out.println("Method: "+method.name+" ");
            for (int j = 0; j < instructions.size(); ++j) {
            	AbstractInsnNode insn = (AbstractInsnNode)instructions.get(j);
                System.out.println("\tInsn: opc="+OpcodeUtil.getOpcode(insn.getOpcode())+", type="+insn.getType());
                if (insn.getType() == AbstractInsnNode.METHOD_INSN) {
            		MethodInsnNode min = (MethodInsnNode)insn;
            		System.out.printf("\t\towner=%s, name=%s, desc=%s\n", min.owner, min.name, min.desc);
                }
            }
        }
	}
}


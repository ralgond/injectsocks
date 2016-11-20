package ht.misc.injectsocks.test;

import java.util.List;
import java.net.Socket;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import ht.misc.injectsocks.OpcodeUtil;
import ht.misc.injectsocks.ProxyManager;

public class Analysis implements Opcodes {

    public static void main(final String[] args) throws Exception {
        ClassReader cr = new ClassReader("ht.misc.injectsocks.test.Analysis");
        ClassNode cn = new ClassNode();
        cr.accept(cn, ClassReader.SKIP_DEBUG);
        
        @SuppressWarnings("unchecked")
		List<MethodNode> methods = (List<MethodNode>)cn.methods;
        for (int i = 0; i < methods.size(); ++i) {
            MethodNode method = methods.get(i);
            if (method.instructions.size() > 0) {
            	System.out.println("Method: "+method.name+" ");
                for (int j = 0; j < method.instructions.size(); ++j) {
                	AbstractInsnNode insn = (AbstractInsnNode)method.instructions.get(j);
                    System.out.println("\tInsn: opc="+OpcodeUtil.getOpcode(insn.getOpcode())+", type="+insn.getType());
                    if (insn.getType() == AbstractInsnNode.METHOD_INSN) {
                    	try {
                    		MethodInsnNode min = (MethodInsnNode)insn;
                    		System.out.printf("\t\towner=%s, name=%s, desc=%s\n", min.owner, min.name, min.desc);
                    	} catch (Exception e) {
                    		e.printStackTrace();
                    	}
                    }
                }
            }
        }
    }
    
    public void test2() {
    	Socket sock = new Socket();
    }
    
    public void test3() {
    	Socket sock  = new Socket(ProxyManager.getProxy());
    }
}

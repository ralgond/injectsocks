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

public class OpcodeUtil {
	private static String arr[] = new String[250];
	static {
		arr[0]="nop";
		arr[1]="aconst_null";
		arr[2]="iconst_m1";
		arr[3]="iconst_0";
		arr[4]="iconst_1";
		arr[5]="iconst_2";
		arr[6]="iconst_3";
		arr[7]="iconst_4";
		arr[8]="iconst_5";
		arr[9]="lconst_0";
		arr[10]="lconst_1";
		arr[11]="fconst_0";
		arr[12]="fconst_1";
		arr[13]="fconst_2";
		arr[14]="dconst_0";
		arr[15]="dconst_1";
		arr[16]="bipush";
		arr[17]="sipush";
		arr[18]="ldc";
		arr[19]="ldc_w";
		arr[20]="ldc2_w";
		arr[21]="iload";
		arr[22]="lload";
		arr[23]="fload";
		arr[24]="dload";
		arr[25]="aload";
		arr[26]="iload_0";
		arr[27]="iload_1";
		arr[28]="iload_2";
		arr[29]="iload_3";
		arr[30]="lload_0";
		arr[31]="lload_1";
		arr[32]="lload_2";
		arr[33]="lload_3";
		arr[34]="fload_0";
		arr[35]="fload_1";
		arr[36]="fload_2";
		arr[37]="fload_3";
		arr[38]="dload_0";
		arr[39]="dload_1";
		arr[40]="dload_2";
		arr[41]="dload_3";
		arr[42]="aload_0";
		arr[43]="aload_1";
		arr[44]="aload_2";
		arr[45]="aload_3";
		arr[46]="iaload";
		arr[47]="laload";
		arr[48]="faload";
		arr[49]="daload";
		arr[50]="aaload";
		arr[51]="baload";
		arr[52]="caload";
		arr[53]="saload";
		arr[54]="istore";
		arr[55]="lstore";
		arr[56]="fstore";
		arr[57]="dstore";
		arr[58]="astore";
		arr[59]="istore_0";
		arr[60]="istore_1";
		arr[61]="istore_2";
		arr[62]="istore_3";
		arr[63]="lstore_0";
		arr[64]="lstore_1";
		arr[65]="lstore_2";
		arr[66]="lstore_3";
		arr[67]="fstore_0";
		arr[68]="fstore_1";
		arr[69]="fstore_2";
		arr[70]="fstore_3";
		arr[71]="dstore_0";
		arr[72]="dstore_1";
		arr[73]="dstore_2";
		arr[74]="dstore_3";
		arr[75]="astore_0";
		arr[76]="astore_1";
		arr[77]="astore_2";
		arr[78]="astore_3";
		arr[79]="iastore";
		arr[80]="lastore";
		arr[81]="fastore";
		arr[82]="dastore";
		arr[83]="aastore";
		arr[84]="bastore";
		arr[85]="castore";
		arr[86]="sastore";
		arr[87]="pop";
		arr[88]="pop2";
		arr[89]="dup";
		arr[90]="dup_x1";
		arr[91]="dup_x2";
		arr[92]="dup2";
		arr[93]="dup2_x1";
		arr[94]="dup2_x2";
		arr[95]="swap";
		arr[96]="iadd";
		arr[97]="ladd";
		arr[98]="fadd";
		arr[99]="dadd";
		arr[100]="isub";
		arr[101]="lsub";
		arr[102]="fsub";
		arr[103]="dsub";
		arr[104]="imul";
		arr[105]="lmul";
		arr[106]="fmul";
		arr[107]="dmul";
		arr[108]="idiv";
		arr[109]="ldiv";
		arr[110]="fdiv";
		arr[111]="ddiv";
		arr[112]="irem";
		arr[113]="lrem";
		arr[114]="frem";
		arr[115]="drem";
		arr[116]="ineg";
		arr[117]="lneg";
		arr[118]="fneg";
		arr[119]="dneg";
		arr[120]="ishl";
		arr[121]="lshl";
		arr[122]="ishr";
		arr[123]="lshr";
		arr[124]="iushr";
		arr[125]="lushr";
		arr[126]="iand";
		arr[127]="land";
		arr[128]="ior";
		arr[129]="lor";
		arr[130]="ixor";
		arr[131]="lxor";
		arr[132]="iinc";
		arr[133]="i2l";
		arr[134]="i2f";
		arr[135]="i2d";
		arr[136]="l2i";
		arr[137]="l2f";
		arr[138]="l2d";
		arr[139]="f2i";
		arr[140]="f2l";
		arr[141]="f2d";
		arr[142]="d2i";
		arr[143]="d2l";
		arr[144]="d2f";
		arr[145]="i2b";
		arr[146]="i2c";
		arr[147]="i2s";
		arr[148]="lcmp";
		arr[149]="fcmpl";
		arr[150]="fcmpg";
		arr[151]="dcmpl";
		arr[152]="dcmpg";
		arr[153]="ifeq";
		arr[154]="ifne";
		arr[155]="iflt";
		arr[156]="ifge";
		arr[157]="ifgt";
		arr[158]="ifle";
		arr[159]="if_icmpeq";
		arr[160]="if_icmpne";
		arr[161]="if_icmplt";
		arr[162]="if_icmpge";
		arr[163]="if_icmpgt";
		arr[164]="if_icmple";
		arr[165]="if_acmpeq";
		arr[166]="if_acmpne";
		arr[167]="goto";
		arr[168]="jsr";
		arr[169]="ret";
		arr[170]="tableswitch";
		arr[171]="lookupswitch";
		arr[172]="ireturn";
		arr[173]="lreturn";
		arr[174]="freturn";
		arr[175]="dreturn";
		arr[176]="areturn";
		arr[177]="return";
		arr[178]="getstatic";
		arr[179]="putstatic";
		arr[180]="getfield";
		arr[181]="putfield";
		arr[182]="invokevirtual";
		arr[183]="invokespecial";
		arr[184]="invokestatic";
		arr[185]="invokeinterface";
		arr[186]="invokedynamic";
		arr[187]="new";
		arr[188]="newarray";
		arr[189]="anewarray";
		arr[190]="arraylength";
		arr[191]="athrow";
		arr[192]="checkcast";
		arr[193]="instanceof";
		arr[194]="monitorenter";
		arr[195]="monitorexit";
		arr[196]="wide";
		arr[197]="multianewarray";
		arr[198]="ifnull";
		arr[199]="ifnonnull";
		arr[200]="goto_w";
		arr[201]="jsr_w";
	}
	
	public static String getOpcode(int opcode) {
		if (opcode <= 0)
			return "unknown";
		
		return arr[opcode];
	}
}

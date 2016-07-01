package ar.edu.itba.kUltra.firstASMTests;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ASMGenerator implements Opcodes {

	public static void main(String[] args) {
		generateExampleClass();
		generateFun1Class();
	}


	private static void generateFun1Class() {
		/* generate class */
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		cw.visit(V1_1, ACC_PUBLIC, "Fun1", null, "java/lang/Object", null);

		/* constructor method */
		generateFun1Constructor(cw);
		/* end of constructor method */

		/* generate puts method */
		generatePutsMethod(cw);

		/* generate gets method */
		generateGetsMethod(cw);

		/* create and use object test */
		testObjectCreation(cw);

//		/* generate fun1 method */
		generateFun1Method(cw);
		/* END of fun1 */

		/* generate main method */
		generateMainMethod(cw);

		/* Visit the End of the class */
		cw.visitEnd();

		/* Write the current bytecode */
		byte[] classBytes = cw.toByteArray();
		try {
			Files.write(Paths.get("Fun1.class"), classBytes, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Could not write Fun1.class file");
		}
	}

	private static void generateMainMethod(final ClassWriter cw) {
		Method m = Method.getMethod("void main (String[])");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

		/* test method */
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void hello ()"));

		mg.push("Testing 'gets' method...\n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("String gets ()"));

		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		mg.push("\n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		/* i1 = 8 ; i2 = 7 ; i3 = 5; */
		mg.push(8);
		mg.push(7);
		mg.push(5);
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("int fun1 (int,int,int)"));
		mg.returnValue();
		mg.endMethod();
		mg.visitEnd();
	}

	private static void generateFun1Constructor(final ClassWriter cw) {
		Method m = Method.getMethod("void <init> ()");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
		mg.loadThis();
		mg.invokeConstructor(Type.getType(Object.class), m);
		mg.returnValue();
		mg.endMethod();
	}

	private static void generateFun1Method(final ClassWriter cw) {

		Method m = Method.getMethod("int fun1 (int, int, int)");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PRIVATE + ACC_STATIC, m, null, null, cw);
		int v1 = mg.newLocal(Type.INT_TYPE);
		mg.loadArg(0);
		mg.loadArg(1);
		mg.math(GeneratorAdapter.ADD, Type.INT_TYPE);
		mg.loadArg(2); /* dividend */
		mg.loadArg(1); /* divider */
		mg.math(GeneratorAdapter.DIV, Type.INT_TYPE);
		mg.math(GeneratorAdapter.SUB, Type.INT_TYPE);
		mg.storeLocal(v1); /* this makes a POP on the stack */

		/* loads int variable at the stack */
		mg.loadLocal(v1);
		mg.box(Type.INT_TYPE);
		/* this consumes the variable on the stack */
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		mg.push("\n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts(Object)"));

		/* if */
		Label le = mg.newLabel();
		Label end = mg.newLabel();

		/* compare the result value with 30 */
		mg.loadLocal(v1);
		mg.push(30);

		mg.ifICmp(GeneratorAdapter.LE, le);
		mg.goTo(end);

		mg.visitLabel(le);

		mg.push("LE\n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		mg.visitLabel(end);
		/* END of if */


		/* if with boolean */
		Label boolBody = new Label();
		Label boolElseBody = new Label();
		Label boolEndBody = new Label();
		mg.push(false);
		mg.ifZCmp(GeneratorAdapter.NE, boolElseBody); // should execute boolBody ?

		// if here, false == 0
		mg.visitLabel(boolBody);

		mg.push("false == 0 on the stack\n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		mg.goTo(boolEndBody);

		// if here, false != 0
		mg.visitLabel(boolElseBody);
		mg.push("false != 0 on the stack\n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));


		mg.visitLabel(boolEndBody);
		/* END of if with boolean */


		/* while */
		Label whileStartLabel = mg.newLabel();
		Label whileBodyLabel = mg.newLabel();
		Label whileEndLabel = mg.newLabel();
		int whileI = mg.newLocal(Type.INT_TYPE);
		mg.push(0);
		mg.storeLocal(whileI);

		mg.visitLabel(whileStartLabel);

		/* values to be compared */
		mg.loadLocal(whileI);
		mg.push(10);

		mg.ifICmp(GeneratorAdapter.LT, whileBodyLabel);
		mg.goTo(whileEndLabel);

		mg.visitLabel(whileBodyLabel);

		mg.push("while... \n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		/* increment whileI by one */
		mg.loadLocal(whileI);
		mg.push(1);
		mg.math(GeneratorAdapter.ADD, Type.INT_TYPE);
		mg.storeLocal(whileI);

		mg.goTo(whileStartLabel);

		mg.visitLabel(whileEndLabel);
		/* END of while */

		/* loads the variable again so as to be returned */
		mg.loadLocal(v1);
		mg.returnValue();
		mg.endMethod();
		mg.visitEnd();
	}

	private static void testObjectCreation(final ClassWriter cw) {
		Method m = Method.getMethod("void hello ()");
		final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		mg.newInstance(Type.getType(ArrayList.class));
		mg.dup();
		mg.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
//		mg.invokeVirtual(Type.getType(ArrayList.class), Method.getMethod("java.util.ArrayList ()"));
		int list = mg.newLocal(Type.getType(List.class));
		mg.storeLocal(list);

		mg.loadLocal(list);
		mg.push(5);
		mg.box(Type.INT_TYPE);

		String objectPackage = "java.lang.Object";

		String methodSignature =  "boolean add(" + objectPackage + ")";
		mg.invokeInterface(Type.getType(List.class), Method.getMethod(methodSignature));

		mg.loadLocal(list);
		mg.push(0);
		methodSignature = objectPackage + " get(int)";
		mg.invokeInterface(Type.getType(List.class), Method.getMethod(methodSignature));


		mg.checkCast(Type.getType(Integer.class));
		mg.unbox(Type.INT_TYPE);
		int i = mg.newLocal(Type.INT_TYPE);
		mg.storeLocal(i);

		/* gets the value of the int as a string */
		mg.loadLocal(i);
		mg.box(Type.INT_TYPE);
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));

		mg.push("Inside gets method... Should have printed '5'\n");
		mg.invokeStatic(Type.getObjectType("Fun1"),
				Method.getMethod("void puts (Object)"));
//
//		System.out.println("Compiled");

		mg.visitInsn(RETURN);
//		mg.returnValue();
		mg.endMethod();
		mg.visitEnd();
	}

	private static void generateGetsMethod(final ClassWriter cw) {
		Method m = Method.getMethod("String gets ()");
		final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		Label l0 = new Label();
		Label l1 = new Label();
		Label l2 = new Label();
//		mv.visitTryCatchBlock(l0, l1, l2, "java/io/IOException");
		mg.visitTryCatchBlock(l0, l1, l2, Type.getDescriptor(IOException.class));
		mg.visitLabel(l0);
//		mv.visitTypeInsn(NEW, "java/io/BufferedReader");
//		mv.visitInsn(DUP);
		mg.newInstance(Type.getType(BufferedReader.class));
		mg.dup();
//		mv.visitTypeInsn(NEW, "java/io/InputStreamReader");
//		mv.visitInsn(DUP);
		mg.newInstance(Type.getType(InputStreamReader.class));
		mg.dup();
//		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
		mg.getStatic(Type.getType(System.class), "in", Type.getType(InputStream.class));
		mg.visitMethodInsn(INVOKESPECIAL, "java/io/InputStreamReader", "<init>", "(Ljava/io/InputStream;)V", false);
		mg.visitMethodInsn(INVOKESPECIAL, "java/io/BufferedReader", "<init>", "(Ljava/io/Reader;)V", false);

		int bufferReader = mg.newLocal(Type.getType(BufferedReader.class));
//		mv.visitVarInsn(ASTORE, 2);
		mg.storeLocal(bufferReader);
//		mv.visitVarInsn(ALOAD, 2);
		mg.loadLocal(bufferReader);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/BufferedReader", "readLine", "()Ljava/lang/String;", false);
		mg.invokeVirtual(Type.getType(BufferedReader.class),
				Method.getMethod("String readLine()"));
		mg.visitLabel(l1);
		mg.visitInsn(ARETURN);
		mg.visitLabel(l2);
//		mv.visitVarInsn(ASTORE, 2);
		int e = mg.newLocal(Type.getType(IOException.class));
		mg.storeLocal(e);
		mg.visitInsn(ACONST_NULL);
		mg.visitInsn(ARETURN);
		mg.visitMaxs(5, 3);

		mg.endMethod();
		mg.visitEnd();
	}

	private static void generatePutsMethod(final ClassWriter cw) {
		Method m = Method.getMethod("void puts (Object)");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		mg.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
		mg.loadArg(0);
		mg.invokeVirtual(Type.getType(PrintStream.class),
				Method.getMethod("void print (Object)"));
		mg.returnValue();
		mg.endMethod();
		mg.visitEnd();
	}


	private static void generateExampleClass() {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		cw.visit(V1_1, ACC_PUBLIC, "Example", null, "java/lang/Object", null);

		Method m = Method.getMethod("void <init> ()");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
		mg.loadThis();
		mg.invokeConstructor(Type.getType(Object.class), m);
		mg.returnValue();
		mg.endMethod();

		m = Method.getMethod("void main (String[])");
		mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		mg.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
		mg.push("Hello world!");
		mg.invokeVirtual(Type.getType(PrintStream.class),
				Method.getMethod("void println (String)"));
		mg.returnValue();
		mg.endMethod();

		cw.visitEnd();

		byte[] classBytes = cw.toByteArray();
		try {
			Files.write(Paths.get("Example.class"), classBytes, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Could not write Example.class file");
		}
	}
}

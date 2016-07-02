package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.DefinedMethods;
import ar.edu.itba.kUltra.symbols.MethodSymbol;
import ar.edu.itba.kUltra.symbols.ParameterListSymbol;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

public class ProgramNode /* +++xcheck: should implement Node? */ {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgramNode.class);
	private static final String OBJECT_INTERNAL_NAME = "java/lang/Object";
	private static final String MAIN_METHOD_SIGNATURE = "void main (String[])";

	/**
	 * Methods that were defined at the top, but were not processed yet, and that will be used on this program
	 */
	private final NodeList<MethodNode> methodNodes;

	/**
	 * Body that was defined at the bottom, that conforms the actual program
	 */
	private final BodyNode bodyNode;

	public ProgramNode(final NodeList<MethodNode> methodNodes, final BodyNode bodyNode) {
		this.methodNodes = methodNodes;
		this.bodyNode = bodyNode;
	}

	public void compileAs(final String className) {
		/* generate class */
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		cw.visit(V1_1, ACC_PUBLIC, className, null, OBJECT_INTERNAL_NAME, null);


		/**
		 * Methods defined and already processed and added to the program; ready to be called
		 */
		final DefinedMethods definedMethods = new DefinedMethods(className);

		generatePredefinedMethods(cw, definedMethods, className);

		generateAuxiliaryMethods(cw, definedMethods);

		generateMainMethod(cw, definedMethods);

		generateClassFile(cw, className);
	}

	private void generatePredefinedMethods(final ClassWriter cw, final DefinedMethods definedMethods, final String className) {

		generateGetsMethod(cw, definedMethods);
		generateGetiMethod(cw, definedMethods, className);
		generatePutsMethod(cw, definedMethods);
	}

	/* Modified from ASMified code */
	private void generateGetiMethod(final ClassWriter cw, final DefinedMethods definedMethods, final String className) {
		final String methodName = "geti";
		final String methodSignature = "Integer geti ()";
		Method m = Method.getMethod(methodSignature);
		final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		Label l0 = new Label();
		Label l1 = new Label();
		Label l2 = new Label();
		mg.visitTryCatchBlock(l0, l1, l2, "java/lang/NumberFormatException");
		mg.invokeStatic(Type.getObjectType(className),
				Method.getMethod("String gets()"));
		mg.visitVarInsn(ASTORE, 0);
		mg.visitVarInsn(ALOAD, 0);
		mg.visitJumpInsn(IFNONNULL, l0);
		mg.visitInsn(ACONST_NULL);
		mg.visitInsn(ARETURN);
		mg.visitLabel(l0);
		mg.visitVarInsn(ALOAD, 0);
		mg.invokeStatic(Type.getType(Integer.class), Method.getMethod("Integer valueOf(String)"));
		mg.visitLabel(l1);
		mg.visitInsn(ARETURN);
		mg.visitLabel(l2);
		mg.visitVarInsn(ASTORE, 1);
		mg.visitInsn(ACONST_NULL);
		mg.visitInsn(ARETURN);

		mg.endMethod();
		mg.visitEnd();

		definedMethods.put(methodName, new MethodSymbol(methodName, methodSignature));
	}

	private static void generateGetsMethod(final ClassWriter cw, final DefinedMethods definedMethods) {
		final String methodName = "gets";
		final String methodSignature = "java.lang.String gets ()";
		Method m = Method.getMethod(methodSignature);
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
				Method.getMethod("java.lang.String readLine()"));
		mg.visitLabel(l1);
		mg.visitInsn(ARETURN);
		mg.visitLabel(l2);
//		mv.visitVarInsn(ASTORE, 2);
		int e = mg.newLocal(Type.getType(IOException.class));
		mg.storeLocal(e);
		mg.visitInsn(ACONST_NULL);
		mg.visitInsn(ARETURN);
		mg.visitMaxs(5, 3); /* +++xcheck: should be removed because of the COMPUTE_MAXS */

		mg.endMethod();
		mg.visitEnd();

		definedMethods.put(methodName, new MethodSymbol(methodName, methodSignature));
	}

	private static void generatePutsMethod(final ClassWriter cw, final DefinedMethods definedMethods) {
		/* string version */
		final String putsMethodName = "puts";
		final String putsMethodSignature = "void puts (Object)";
		Method m = Method.getMethod(putsMethodSignature);
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		mg.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
		mg.loadArg(0);
		mg.invokeVirtual(Type.getType(PrintStream.class),
				Method.getMethod("void print (Object)"));
		mg.returnValue();
		mg.endMethod();
		mg.visitEnd();

		definedMethods.put(putsMethodName, new MethodSymbol(putsMethodName, putsMethodSignature));
	}

	private void generateAuxiliaryMethods(final ClassWriter cw, final DefinedMethods definedMethods) {

		final Context context = new Context(cw, null, null, definedMethods, null); /* +++ximprove: works, but it is not cool */
		if (methodNodes != null) {
			methodNodes.process(context);
		}
	}

	private void generateMainMethod(final ClassWriter cw, final DefinedMethods definedMethods) {
		final Method m = Method.getMethod(MAIN_METHOD_SIGNATURE);
		final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

		final ParameterListSymbol parameterListSymbol = new ParameterListSymbol();
		parameterListSymbol.addParameter("String[]", "args");

		final Context context = new Context(cw, mg, parameterListSymbol, definedMethods, Type.VOID_TYPE);
		/*
			en este caso en particular, el contexto debería de tener sólo args como argumento,
			pero este bodyNode no va a necesitarlo, pues el programador no espera recibir argumentos por esta variable
			según la gramática definida
		 */
		bodyNode.process(context);

//		mg.visitInsn(RETURN);
		mg.returnValue();
		mg.endMethod();
		mg.visitEnd();
	}

	private static void generateClassFile(final ClassWriter cw, final String className) {
		byte[] classBytes = cw.toByteArray();
		final File compileFolder = new File("compiled"); // +++xchange: do this with maven
		compileFolder.mkdir();

		/* delete previous .class file, if any */
		final Path pathToFileClass = Paths.get("compiled", className + ".class");
		try {
			Files.deleteIfExists(pathToFileClass);
		} catch (IOException e) {
			LOGGER.warn("Could not delete previous .class file: '{}'. Caused by: ", pathToFileClass, e);
			System.out.println("Could not delete previous .class file: '" + pathToFileClass + "'.\n" +
					"So, new .class could not be saved. Aborting...");
			return;
		}

		/* write the new .class file */
		try {
			Files.write(pathToFileClass, classBytes, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Could not write '" + className + ".class' file");
		}
	}

	@Override
	public String toString() {
		return "ProgramNode{" +
				"methodNodes=" + methodNodes +
				", bodyNode=" + bodyNode +
				'}';
	}
}

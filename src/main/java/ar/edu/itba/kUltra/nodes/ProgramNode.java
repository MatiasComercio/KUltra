package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.DefinedMethods;
import ar.edu.itba.kUltra.symbols.MethodSymbol;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

public class ProgramNode /* +++xcheck: should implement Node? */ {
	private static final String OBJECT_INTERNAL_NAME = "java/lang/Object";
	private static final String MAIN_METHOD_SIGNATURE = "void main (String[])";

	/**
	 * Methods that were defined at the top, but were not processed yet, and that will be used on this program
	 */
	private final List<MethodNode> methodNodes;

	/**
	 * Body that was defined at the bottom, that conforms the actual program
	 */
	private final BodyNode bodyNode;

	public ProgramNode(final List<MethodNode> methodNodes, final BodyNode bodyNode) {
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

		generatePredefinedMethods(cw, definedMethods);

		generateAuxiliaryMethods(cw, definedMethods);

		generateMainMethod(cw, definedMethods);

		generateClassFile(cw, className);
	}

	private void generatePredefinedMethods(final ClassWriter cw, final DefinedMethods definedMethods) {

		generateGetsMethod(cw, definedMethods);
		generatePutsMethod(cw, definedMethods);
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
		// +++ximprove
		if (methodNodes != null) {
			methodNodes.forEach(methodNode -> {
				final List<ArgumentNode> argumentNodes = new LinkedList<>();

				final StringBuilder signature = new StringBuilder();
				signature.append(methodNode.getJavaType()).append(' ')
						.append(methodNode.getIdentifier()).append(" (");
				final List<ParameterNode> parameterNodes = methodNode.getParameterNodes();
				if (parameterNodes != null) {
					int position = 0;
					for (ParameterNode parameterNode : parameterNodes) {
						signature.append(parameterNode.getType()).append(", ");

						argumentNodes.add(new ArgumentNode(parameterNode.getIdentifier(), position));
					}
				}

				signature.append(")");

				final Method m = Method.getMethod(signature.toString());
				final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

				final Context context = new Context(mg, argumentNodes, definedMethods, methodNode.getReturnType());

				methodNode.getBodyNode().process(context);

				mg.endMethod();
				mg.visitEnd();

				definedMethods.put(methodNode.getIdentifier(),
						new MethodSymbol(methodNode.getIdentifier(), signature.toString()));
			});
		}
	}

	private void generateMainMethod(final ClassWriter cw, final DefinedMethods definedMethods) {
		final Method m = Method.getMethod(MAIN_METHOD_SIGNATURE);
		final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

		final List<ArgumentNode> argumentNodes = new LinkedList<>();
		argumentNodes.add(new ArgumentNode("args", 0));

		final Context context = new Context(mg, argumentNodes, definedMethods, Type.VOID_TYPE);
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
		try {
			Files.write(Paths.get(className + ".class"), classBytes, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Could not write Fun1.class file");
		}
	}
}

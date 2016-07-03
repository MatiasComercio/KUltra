package ar.edu.itba.kUltra.helpers;

import ar.edu.itba.kUltra.nodes.*;
import ar.edu.itba.kUltra.symbols.MethodSymbol;
import ar.edu.itba.kUltra.symbols.ParameterListSymbol;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.util.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * This class manages the variables of a method - both args and local variables
 */
public class Context {

	private final ClassWriter cw;

	private final GeneratorAdapter mg;

	private final DefinedMethods definedMethods;

	private final Set<String> definedVariablesName;

	private final Map<String, ParameterListSymbol.ParameterSymbol> parameters;

	private final Type returnType;
	/**
	 * Stores VariableNodes, and when saved, it assigns an index corresponding to the local variable
	 */
	private final Map<String, Integer> indexedVariableNodes;

	public Context(final ClassWriter cw, final GeneratorAdapter mg, final ParameterListSymbol parameterListSymbol, final DefinedMethods definedMethods, final Type returnType) {
		this.cw = cw;
		this.mg = mg;

		if (definedMethods == null) {
			throw new IllegalArgumentException("defineMethods cannot be null");
		}
		this.definedMethods = definedMethods;

		this.definedVariablesName = new HashSet<>();

		this.parameters = new HashMap<>();

		if (parameterListSymbol != null) {
			parameterListSymbol.getParameterSymbols().forEach(parameterSymbol -> {
				final String aName = parameterSymbol.getIdentifier();
				this.parameters.put(aName, parameterSymbol);
				this.definedVariablesName.add(aName);
			});
		}

		this.indexedVariableNodes = new HashMap<>();

		this.returnType = returnType;

	}

	public void setVariable(final Type type, final String identifier) {
		if (definedVariablesName.contains(identifier)) {
			throw new IllegalArgumentException("Variable: '" + identifier + "' was already defined on this context");
		}

		final int vIndex = mg.newLocal(type);
		indexedVariableNodes.put(identifier, vIndex);
		definedVariablesName.add(identifier);
	}

	public void loadVariable(final VariableNode v) {
		final String vName = v.getName();
		if (!definedVariablesName.contains(vName)) {
			throw new IllegalArgumentException("Variable '" + vName + "' is not defined on this context");
		}

		loadToStack(vName);
	}

	/**
	 * Loads the current identifier's value to the stack.
	 * Decides whether it is an argument identifier or a variable identifier.
	 *
	 * @param identifier The identifier of the argument/variable whose value will be loaded into the stack
	 */
	private void loadToStack(final String identifier) {
		if (indexedVariableNodes.containsKey(identifier)) { // was a variable, not an argument
			int index = indexedVariableNodes.get(identifier);
			mg.loadLocal(index);
			return;
		}

		// else, it was an argument, not a variable
		int index = parameters.get(identifier).getPosition();
		mg.loadArg(index);
	}


	/**
	 * Stores the current stack's value into the given identifier.
	 * Decides whether it is an argument identifier or a variable identifier.
	 *
	 * @param identifier The identifier of the argument/variable where the stack's value will be stored
	 */
	private void storeFromStack(final String identifier) {
		if (indexedVariableNodes.containsKey(identifier)) { // was a variable, not an argument
			int index = indexedVariableNodes.get(identifier);
			mg.storeLocal(index);
			return;
		}

		// else, it was an argument, not a variable
		int index = parameters.get(identifier).getPosition();
		mg.storeArg(index);
	}


	/********************************* all node operations *********************************/

	public void assignTo(final String identifier) {
		if (!definedVariablesName.contains(identifier)) {
			throw new IllegalArgumentException("This variable is not defined on this context");
		}

		storeFromStack(identifier);
	}

	public void methodCall(final String identifier, final NodeList<ExpressionNode> arguments) {
		// check if method exists
		if (!definedMethods.containsKey(identifier)) {
			throw new IllegalArgumentException("Method '" + identifier + "' is not defined");
		}

		// load requested variables, in the given order
		if (arguments != null) {
			arguments.process(this);
		}

		// do the actual call
		mg.invokeStatic(Type.getObjectType(definedMethods.getClassName()),
				Method.getMethod(definedMethods.get(identifier).getSignature()));
	}

	public void push(final Integer value) {
		mg.push(value);
		mg.box(Type.INT_TYPE);
	}

	public void push(final String value) {
		mg.push(value);
	}

	public void push(final boolean value) {
		mg.push(value);
	}

	/**
	 *
	 * @param type The type that has to be left on the top of the stack
	 */
	public void unbox(final Type type) {
		mg.unbox(type);
	}

	/**
	 *
	 * @param type The type of the top stack value
	 */
	public void box(final Type type) {
		mg.box(type);
	}

	/*

		 */

	/**
	 * Executes the if statement as follows:
	 *  if (condition) {
	 *		bodyNode.process();
	 *	} else {
	 *		if (elseBodyNode != null) {
	 *			elseBodyNode.process();
	 *		}
	 *	}
	 *
	 * @param condition if condition
	 * @param bodyNode body to be executed if the condition is evaluated as "true"
	 * @param elseBodyNode body (if not null) to be executed if the condition is evaluated as "false"
	 */
	public void ifProcess(final ExpressionNode condition, final BodyNode bodyNode, final BodyNode elseBodyNode) {
		final Label executeBodyNode = mg.newLabel();
		final Label executeElseBodyNode = mg.newLabel();
		final Label endExecution = mg.newLabel();

		// this leaves a true or false on the stack
		// false == 0 && true != 0
		condition.process(this);

		// if top of stack != 0 => top of stack is "true" => execute bodyNode
		mg.ifZCmp(GeneratorAdapter.NE, executeBodyNode);
		// top of stack == 0 => top of stack is "false" => execute elseBodyNode
		mg.goTo(executeElseBodyNode);

		/* condition == true label */
		mg.visitLabel(executeBodyNode);
		bodyNode.process(this);
		mg.goTo(endExecution);

		/* condition == false label */
		mg.visitLabel(executeElseBodyNode);
		if (elseBodyNode != null) { // else is defined
			elseBodyNode.process(this);
		}

		mg.visitLabel(endExecution);
	}

	/**
	 * Evaluates the current two top of stack values based on the given operation,
	 * considering both values of the given type.
	 * Sets true at the top of the stack if the comparison has a "true" result, or "false" otherwise
	 *
	 * @param type type of the values at the top of the stack
	 * @param operation operation that should be performed between the described values
	 */
	public void conditionOp(final Type type, final int operation) {
		final Label evaluatesTrue = mg.newLabel();
		final Label evaluatesFalse = mg.newLabel();
		final Label endExecution = mg.newLabel();

		// if operations evaluates "true" => set "true" at the top of the stack
		mg.ifCmp(type, operation, evaluatesTrue);
		// if operations evaluates "false" => set "false" at the top of the stack
		mg.goTo(evaluatesFalse);

		/* condition == true label */
		mg.visitLabel(evaluatesTrue);
		mg.push(true);
		mg.goTo(endExecution);

		/* condition == false label */
		mg.visitLabel(evaluatesFalse);
		mg.push(false);

		mg.visitLabel(endExecution);
	}

	public void whileProcess(final ExpressionNode condition, final BodyNode bodyNode) {
		Label whileStartLabel = mg.newLabel();
		Label whileBodyLabel = mg.newLabel();
		Label whileEndLabel = mg.newLabel();

		mg.visitLabel(whileStartLabel);
		// this leaves a true or false on the stack
		// false == 0 && true != 0
		condition.process(this);

		// if top of stack != 0 => top of stack is "true" => goto whileBodyLabel
		mg.ifZCmp(GeneratorAdapter.NE, whileBodyLabel);
		// top of stack == 0 => top of stack is "false" => execute whileEndLabel
		mg.goTo(whileEndLabel);

		/* condition == true label */
		mg.visitLabel(whileBodyLabel);
		bodyNode.process(this);
		mg.goTo(whileStartLabel);

		/* condition == false label => end of while loop */
		mg.visitLabel(whileEndLabel);

	}

	public void createMethod(final String identifier, final String signature, final ParameterListSymbol argumentNodes,
	                         final BodyNode bodyNode, final Type returnType) {
		/* first, save it as defined so as to be able to use it later */
		definedMethods.put(identifier,
				new MethodSymbol(identifier, signature));

		final Method m = Method.getMethod(signature);
		final GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

		final Context context = new Context(cw, mg, argumentNodes, definedMethods, returnType);

		bodyNode.process(context);

		mg.endMethod();
		mg.visitEnd();
	}


	// +++ximprove: this could be placed inside the TypeConverter or ReturnNode class
	private final static Map<Type, Integer> TYPE_TO_RETURN_OPCODE;
	static {
		TYPE_TO_RETURN_OPCODE = new HashMap<>();

		TYPE_TO_RETURN_OPCODE.put(Type.INT_TYPE, IRETURN);
		TYPE_TO_RETURN_OPCODE.put(Type.VOID_TYPE, RETURN);
	}

	public void returnProcess() {
		if (returnType == null) {
			// should never reach here, but just in case...
			mg.visitInsn(RETURN);
			return;
		}

		Integer returnOpcode = TYPE_TO_RETURN_OPCODE.get(returnType);
		if (returnOpcode == null) { // default is to return an object
			returnOpcode = Opcodes.ARETURN;
		}

		mg.visitInsn(returnOpcode);
	}

	public void arithmeticOp(final int operation) {
		mg.math(operation, Type.INT_TYPE);
	}

	public void notLogicalOp() {
		final Label evaluatesTrue = mg.newLabel();
		final Label evaluatesFalse = mg.newLabel();
		final Label endExecution = mg.newLabel();

		// if top of stack != 0 => top of stack is "true" => set "false" at the top of the stack
		mg.ifZCmp(GeneratorAdapter.NE, evaluatesTrue);
		// if top of stack == 0 => top of stack is "false" => set "true" at the top of the stack
		mg.goTo(evaluatesFalse);

		/* condition == true label */
		mg.visitLabel(evaluatesTrue);
		mg.push(false);
		mg.goTo(endExecution);

		/* condition == false label */
		mg.visitLabel(evaluatesFalse);
		mg.push(true);

		mg.visitLabel(endExecution);
	}
}

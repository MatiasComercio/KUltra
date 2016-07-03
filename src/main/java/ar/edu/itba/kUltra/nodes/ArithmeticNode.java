package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import org.objectweb.asm.Type;

public class ArithmeticNode implements OperationNode {
	private final Type type;
	private final int operation;

	private final ExpressionNode v1;
	private final ExpressionNode v2;

	public ArithmeticNode(final int operation, final ExpressionNode v1, final ExpressionNode v2) {
		this.type = Type.INT_TYPE; // add operation requires int values, not Integer objects
		this.operation = operation;
		this.v1 = v1;
		this.v2 = v2;
	}

	@Override
	public void process(final Context context) {
		v1.process(context);
//		context.unbox(type);
		v2.process(context);
//		context.unbox(type);
		context.arithmeticOp(operation);
//		context.box(type);
	}

	@Override
	public String toString() {
		return "ArithmeticNode{" +
				"operation=" + operation +
				", v1=" + v1 +
				", v2=" + v2 +
				'}';
	}

	@Override
	public Type getType() {
		return type;
	}
}

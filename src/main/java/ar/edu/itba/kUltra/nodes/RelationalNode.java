package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

public class RelationalNode implements OperationNode {
	private final Type type;
	private final int operation;

	private final ExpressionNode e1;
	private final ExpressionNode e2;


	public RelationalNode(final Type type, final int operation, final ExpressionNode e1, final ExpressionNode e2) {
		this.type = type;
		this.operation = operation;
		this.e1 = e1;
		this.e2 = e2;
	}

	public int getOperation() {
		return operation;
	}

	public ExpressionNode getE1() {
		return e1;
	}

	public ExpressionNode getE2() {
		return e2;
	}

	@Override
	public void process(final Context context) {
		e1.process(context);
//		context.unbox(type);
		e2.process(context);
//		context.unbox(type);
		// +++xcheck
		context.conditionOp(type, operation);
	}

	@Override
	public String toString() {
		return "RelationalNode{" +
				"operation=" + operation +
				", e1=" + e1 +
				", e2=" + e2 +
				'}';
	}

	@Override
	public Type getType() {
		return type;
	}
}

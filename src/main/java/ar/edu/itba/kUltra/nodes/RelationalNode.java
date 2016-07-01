package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

public class RelationalNode implements ExpressionNode {
	private final int operation;

	private final ExpressionNode e1;
	private final ExpressionNode e2;


	public RelationalNode(final int operation, final ExpressionNode e1, final ExpressionNode e2) {
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
		context.unbox(Type.INT_TYPE);
		e2.process(context);
		context.unbox(Type.INT_TYPE);
		// +++xmagicnumber
		context.conditionOp(Type.INT_TYPE, operation); // +++xcheck: is Type necessary?
	}
}

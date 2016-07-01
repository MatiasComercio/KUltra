package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import org.objectweb.asm.Type;

public class ArithmeticNode implements ExpressionNode {
	private final int operation;

	private final ExpressionNode v1;
	private final ExpressionNode v2;

	public ArithmeticNode(final int operation, final ExpressionNode v1, final ExpressionNode v2) {
		this.operation = operation;
		this.v1 = v1;
		this.v2 = v2;
	}

	@Override
	public void process(final Context context) {
		v1.process(context);
//		context.unbox(Type.getType(Integer.class));
		context.unbox(Type.INT_TYPE);
		v2.process(context);
//		context.unbox(Type.getType(Integer.class));
		context.unbox(Type.INT_TYPE);
		// add operation requires int values, not Integer objects
		context.arithmeticOp(operation);
		context.box(Type.INT_TYPE);

//		/* +++xdebug */
//		context.push(3);
	}
}

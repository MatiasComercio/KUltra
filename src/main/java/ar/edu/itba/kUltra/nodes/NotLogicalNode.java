package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import org.objectweb.asm.Type;

public class NotLogicalNode implements LogicalNode {
	private final Type type;

	private final ExpressionNode e;

	public NotLogicalNode(final ExpressionNode e) {
		this.type = Type.BOOLEAN_TYPE;
		this.e = e;
	}

	@Override
	public void process(final Context context) {
		// unbox is not necessary as expressions should leave on the stack "true" or "false" values,
		// as 'boolean', not 'Boolean'
		e.process(context);
		context.notLogicalOp();
	}

	@Override
	public String toString() {
		return "NotLogicalNode{" +
				"type=" + type +
				", e=" + e +
				'}';
	}

	@Override
	public Type getType() {
		return type;
	}
}

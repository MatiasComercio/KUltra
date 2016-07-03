package ar.edu.itba.kUltra.nodes;

import org.objectweb.asm.Type;

public interface OperationNode extends ExpressionNode {
	/**
	 *
	 * @return the ASM Type that the expression manages, i.e.,
	 * the types of the values that should be on the stack so as to be able to
	 * perform the process method
	 */
	Type getType();
}

package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.TypeConverter;
import org.objectweb.asm.Type;

public class ReturnNode implements StatementNode {
	// can be null in the case of "void" return methods
	private final ExpressionNode e;

	/**
	 *
	 * @param e expression whose value will be returned
	 */
	public ReturnNode(final ExpressionNode e) {
		this.e = e;
	}

	@Override
	public void process(final Context context) {
		if (e != null) {
			e.process(context);
		}
		context.returnProcess();
	}
}

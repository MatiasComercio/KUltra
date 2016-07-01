package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.TypeConverter;
import org.objectweb.asm.Type;

public class ReturnNode implements Node {
	private final Type type;

	private final String javaType;

	// can be null in the case of "void" return methods
	private final ExpressionNode e;

	/**
	 *
	 * @param type own program language type
	 * @param e expression whose value will be returned
	 */
	public ReturnNode(final String type, final ExpressionNode e) {
		this.type = TypeConverter.getType(type);
		this.javaType = TypeConverter.getJavaTypeString(type);
		this.e = e;
	}


	public String getJavaType() {
		return javaType;
	}

	@Override
	public void process(final Context context) {
		if (e != null) {
			e.process(context);
		}
		context.returnProcess(type);
	}
}

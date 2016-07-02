package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.TypeConverter;
import ar.edu.itba.kUltra.symbols.ParameterListSymbol;
import org.objectweb.asm.Type;

public class MethodNode implements Node {
	private final String javaType;
	private final Type returnType;
	private final String identifier;
	private final ParameterListSymbol parameterListSymbol;
	private final BodyNode bodyNode;

	/**
	 * +++xdocument
	 * @param returnType
	 * @param identifier
	 * @param parameterListSymbol not null
	 * @param bodyNode
	 */
	public MethodNode(final String returnType, final String identifier, final ParameterListSymbol parameterListSymbol, final BodyNode bodyNode) {
		this.javaType = TypeConverter.getJavaTypeString(returnType);
		this.returnType = TypeConverter.getType(returnType);
		this.identifier = identifier;
		this.parameterListSymbol = parameterListSymbol;
		this.bodyNode = bodyNode;
	}

	public String getJavaType() {
		return javaType;
	}

	public Type getReturnType() {
		return returnType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public ParameterListSymbol getParameterListSymbol() {
		return parameterListSymbol;
	}

	public BodyNode getBodyNode() {
		return bodyNode;
	}

	@Override
	public void process(final Context context) {
		final StringBuilder signature = new StringBuilder();
		signature.append(javaType).append(' ')
				.append(identifier).append(' ')
				.append("(").append(parameterListSymbol.toString()).append(")");

		context.createMethod(identifier, signature.toString(), parameterListSymbol, bodyNode, returnType);
	}
}

package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.TypeConverter;
import ar.edu.itba.kUltra.symbols.MethodSymbol;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.util.LinkedList;
import java.util.List;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

public class MethodNode implements Node {
	private final String javaType;
	private final Type returnType;
	private final String identifier;
	private final List<ParameterNode> parameterNodes;
	private final BodyNode bodyNode;

	public MethodNode(final String returnType, final String identifier, final List<ParameterNode> parameterNodes, final BodyNode bodyNode) {
		this.javaType = TypeConverter.getJavaTypeString(returnType);
		this.returnType = TypeConverter.getType(returnType);
		this.identifier = identifier;
		this.parameterNodes = parameterNodes;
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

	public List<ParameterNode> getParameterNodes() {
		return parameterNodes;
	}

	public BodyNode getBodyNode() {
		return bodyNode;
	}

	@Override
	public void process(final Context context) {
		final List<ArgumentNode> argumentNodes = new LinkedList<>();

		final StringBuilder signature = new StringBuilder();
		signature.append(javaType).append(' ')
				.append(identifier).append(" (");
		if (parameterNodes != null) {
			int position = 0;
			for (ParameterNode parameterNode : parameterNodes) {
				signature.append(parameterNode.getType()).append(", ");

				argumentNodes.add(new ArgumentNode(parameterNode.getIdentifier(), position));
			}
		}

		signature.append(")");

		context.createMethod(identifier, signature.toString(), argumentNodes, bodyNode, returnType);
	}
}

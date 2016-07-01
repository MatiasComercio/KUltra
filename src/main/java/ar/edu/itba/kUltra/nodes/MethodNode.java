package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.TypeConverter;
import org.objectweb.asm.Type;

import java.util.List;

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
	public void process(final Context context) { // +++ximprove: not used currently; have to improve Context class
//		final List<ArgumentNode> argumentNodes = new LinkedList<>();
//
//		final StringBuilder signature = new StringBuilder();
//		signature.append(returnType).append(' ')
//				.append(identifier).append(" (");
//		if (parameterNodes != null) {
//			int position = 0;
//			for (ParameterNode parameterNode : parameterNodes) {
//				signature.append(parameterNode.getReturnType()).append(", ");
//
//				argumentNodes.add(new ArgumentNode(parameterNode.getIdentifier(), position));
//			}
//		}
//
//		signature.append(")");
//
//		context.createMethod(signature.toString(), argumentNodes, bodyNode, returnNode);
	}
}

package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.List;

public class MethodNode implements Node {
	private final String identifier;
	private final List<ParameterNode> parameterNodes;
	private final BodyNode bodyNode;
	private final ReturnNode returnNode;

	public MethodNode(final String identifier,
	                  final List<ParameterNode> parameterNodes, final BodyNode bodyNode,
	                  final ReturnNode returnNode) {
		this.identifier = identifier;
		this.parameterNodes = parameterNodes;
		this.bodyNode = bodyNode;
		this.returnNode = returnNode;
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

	public ReturnNode getReturnNode() {
		return returnNode;
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
//				signature.append(parameterNode.getType()).append(", ");
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

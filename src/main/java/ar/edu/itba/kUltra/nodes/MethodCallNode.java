package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

/*
	Method call behaves both as a StatementNode and an ExpressionNode
 */
public class MethodCallNode implements StatementNode, ExpressionNode {
	private final String identifier;
	private final NodeList<ExpressionNode> arguments;

	public MethodCallNode(final String identifier, final NodeList<ExpressionNode> arguments) {
		this.identifier = identifier;
		this.arguments = arguments;
	}


	@Override
	public void process(final Context context) {
		context.methodCall(identifier, arguments);
	}
}

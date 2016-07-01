package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.List;

/*
	Method call behaves both as a StatementNode and an ExpressionNode
 */
public class MethodCallNode implements StatementNode, ExpressionNode {
	private final String identifier;
	private final List<ExpressionNode> argumentNodes;

	public MethodCallNode(final String identifier, final List<ExpressionNode> argumentNodes) {
		this.identifier = identifier;
		this.argumentNodes = argumentNodes;
	}


	@Override
	public void process(final Context context) {
		context.methodCall(identifier, argumentNodes);
	}
}

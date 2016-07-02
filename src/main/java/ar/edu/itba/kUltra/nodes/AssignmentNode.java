package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

public class AssignmentNode implements StatementNode {
	private final String identifier;
	private final ExpressionNode expressionNode;

	public AssignmentNode(final String identifierNode, final ExpressionNode expressionNode) {
		this.identifier = identifierNode;
		this.expressionNode = expressionNode;
	}

	public String getIdentifier() {
		return identifier;
	}

	public ExpressionNode getExpressionNode() {
		return expressionNode;
	}

	@Override
	public void process(final Context context) {
		/* performs the necessary operations */
		expressionNode.process(context);

		/* the result is at the stack => save it on the given identifier */
		context.assignTo(identifier);
	}

	@Override
	public String toString() {
		return "AssignmentNode{" +
				"identifier='" + identifier + '\'' +
				", expressionNode=" + expressionNode +
				'}';
	}
}

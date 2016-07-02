package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

public class WhileNode implements StatementNode {
	private final ExpressionNode condition;
	private final BodyNode bodyNode;

	public WhileNode(final ExpressionNode condition, final BodyNode bodyNode) {
		this.condition = condition;
		this.bodyNode = bodyNode;
	}

	public ExpressionNode getCondition() {
		return condition;
	}

	public BodyNode getBodyNode() {
		return bodyNode;
	}

	@Override
	public void process(final Context context) {
		context.whileProcess(condition, bodyNode);
	}

	@Override
	public String toString() {
		return "WhileNode{" +
				"condition=" + condition +
				", bodyNode=" + bodyNode +
				'}';
	}
}

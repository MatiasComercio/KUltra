package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

public class IfNode implements StatementNode {
	private final ExpressionNode condition;
	private final BodyNode bodyNode;
	private final BodyNode elseBodyNode;

	public IfNode(final ExpressionNode condition, final BodyNode bodyNode, final BodyNode elseBodyNode) {
		this.condition = condition;
		this.bodyNode = bodyNode;
		this.elseBodyNode = elseBodyNode;
	}

	public ExpressionNode getCondition() {
		return condition;
	}

	public BodyNode getBodyNode() {
		return bodyNode;
	}

	public BodyNode getElseBodyNode() {
		return elseBodyNode;
	}

	@Override
	public void process(final Context context) {
		context.ifProcess(condition, bodyNode, elseBodyNode);
	}

	@Override
	public String toString() {
		return "IfNode{" +
				"condition=" + condition +
				", bodyNode=" + bodyNode +
				", elseBodyNode=" + elseBodyNode +
				'}';
	}
}

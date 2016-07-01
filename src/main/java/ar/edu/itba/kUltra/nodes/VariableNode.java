package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

public class VariableNode implements ExpressionNode {

	private final String name;

	public VariableNode(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof VariableNode)) return false;

		final VariableNode that = (VariableNode) o;

		return name != null ? name.equals(that.name) : that.name == null;

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public void process(final Context context) {
		context.loadVariable(this);
	}
}

package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

public class LiteralNode<T> implements ExpressionNode {
	private final T value;
//	private final Class valueClass;

	public LiteralNode(final T value/*, final Class valueClass*/) {
		this.value = value;
//		this.valueClass = valueClass;
	}

	public T getValue() {
		return value;
	}

//	public Class getValueClass() {
//		return valueClass;
//	}

	@Override
	public void process(final Context context) {
		if (value instanceof Integer) {
			context.push((Integer) value);
			return;
		}

		if (value instanceof String) {
			context.push((String) value);
			return;
		}

		throw new IllegalArgumentException("Not a valid type");
	}

	@Override
	public String toString() {
		return "LiteralNode{" +
				"value=" + value +
				'}';
	}
}

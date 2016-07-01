package ar.edu.itba.kUltra.nodes;

public class ParameterNode {
	private final String type;
	private final String identifier;

	public ParameterNode(final String type, final String identifier) {
		this.type = type;
		this.identifier = identifier;
	}

	public String getType() {
		return type;
	}

	public String getIdentifier() {
		return identifier;
	}
}

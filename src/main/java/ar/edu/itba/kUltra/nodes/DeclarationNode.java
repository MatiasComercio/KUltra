package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;
import ar.edu.itba.kUltra.helpers.TypeConverter;
import org.objectweb.asm.Type;

public class DeclarationNode implements StatementNode {
	private final Type type;
	private final String identifier;

	public DeclarationNode(final String type, final String identifier) {
		this.type = TypeConverter.getType(type);
		this.identifier = identifier;
	}

	@Override
	public void process(final Context context) {
		context.setVariable(type, identifier);
	}

	public Type getType() {
		return type;
	}

	public String getIdentifier() {
		return identifier;
	}
}

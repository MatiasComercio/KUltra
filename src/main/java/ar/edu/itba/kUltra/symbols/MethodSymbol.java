package ar.edu.itba.kUltra.symbols;

public class MethodSymbol {
	private final String identifier;
	private final String signature;

	public MethodSymbol(final String identifier, final String signature) {
		this.identifier = identifier;
		this.signature = signature;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getSignature() {
		return signature;
	}
}

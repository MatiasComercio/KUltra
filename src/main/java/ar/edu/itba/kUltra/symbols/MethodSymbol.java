package ar.edu.itba.kUltra.symbols;

import ar.edu.itba.kUltra.nodes.BodyNode;
import org.objectweb.asm.Type;

public class MethodSymbol {
	private final String identifier;
	private final String signature;
	private final ParameterListSymbol parameterListSymbol;
	private final Type returnType;


	public MethodSymbol(final String identifier, final String signature,
	                    final ParameterListSymbol parameterListSymbol, final Type returnType) {
		this.identifier = identifier;
		this.signature = signature;
		this.parameterListSymbol = parameterListSymbol;
		this.returnType = returnType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getSignature() {
		return signature;
	}

	public ParameterListSymbol getParameterListSymbol() {
		return parameterListSymbol;
	}

	public Type getReturnType() {
		return returnType;
	}
}

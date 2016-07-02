package ar.edu.itba.kUltra.symbols;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParameterListSymbol {
	private final List<ParameterSymbol> parameterSymbols;

	public ParameterListSymbol() {
		parameterSymbols = new LinkedList<>();
	}

	public void addParameter(final String type, final String identifier) {
		final int position = parameterSymbols.size();
		parameterSymbols.add(new ParameterSymbol(type, identifier, position));
	}

	public List<ParameterSymbol> getParameterSymbols() {
		return Collections.unmodifiableList(parameterSymbols);
	}

	public static class ParameterSymbol {
		private final String type;
		private final String identifier;
		private final int position;

		private ParameterSymbol(final String type, final String identifier, final int position) {
			this.type = type;
			this.identifier = identifier;
			this.position = position;
		}

		public String getType() {
			return type;
		}

		public String getIdentifier() {
			return identifier;
		}

		public int getPosition() {
			return position;
		}
	}

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		s.append("");
		int i = 0;
		for (ParameterSymbol parameterSymbol : parameterSymbols) {
			s.append(parameterSymbol.getType());
			if (i < parameterSymbols.size() - 1) { // there are more parameterSymbols
				s.append(',');
			}
		}
		return s.toString();
	}
}

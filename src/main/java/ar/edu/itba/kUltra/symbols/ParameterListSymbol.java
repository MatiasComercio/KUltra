package ar.edu.itba.kUltra.symbols;

import java.util.*;

public class ParameterListSymbol {
	private final Deque<ParameterSymbol> parameterSymbols;

	public ParameterListSymbol() {
		parameterSymbols = new LinkedList<>();
	}

	public void addParameter(final String type, final String identifier) {
		// position is unknown until the list is complete
		parameterSymbols.push(new ParameterSymbol(type, identifier, -1));
	}

	public List<ParameterSymbol> getParameterSymbols() {
		final List<ParameterSymbol> list = new LinkedList<>();
		int i = 0;
		for (ParameterSymbol p : parameterSymbols) {
			list.add(new ParameterSymbol(p.getType(), p.getIdentifier(), i++));
		}
		return list;
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
			i++;
		}
		return s.toString();
	}
}

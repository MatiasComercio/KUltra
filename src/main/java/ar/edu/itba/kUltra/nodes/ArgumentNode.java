package ar.edu.itba.kUltra.nodes;

public class ArgumentNode /* +++xcheck: should implement Node? */ {
	/**
	 * Name of the current argument
	 */
	private final String name;

	/**
	 * Position among the other arguments
	 *
	 * For example, at int max(int i1, int i2),
	 * i1 has position 0 and i2 has position 1
	 */
	private final int position;

	public ArgumentNode(final String name, final int position) {
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}
}

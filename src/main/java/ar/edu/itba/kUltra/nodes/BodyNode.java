package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BodyNode implements Node {
	private final List<StatementNode> statementNodes;

	public BodyNode(final List<StatementNode> statementNodes) {
		this.statementNodes = statementNodes;
	}

	public BodyNode() {
		statementNodes = new LinkedList<>();
	}

	/* forwarded methods */
	public boolean add(final StatementNode statementNode) {
		return statementNodes.add(statementNode);
	}

	public boolean addAll(final Collection<? extends StatementNode> c) {
		return statementNodes.addAll(c);
	}
	/*********************/

	@Override
	public void process(final Context context) {
		/* acá tendria que entrar con el contexto, para saber las variables y el orden */

		statementNodes.forEach(statementNode -> {
			statementNode.process(context);
		});
	}
}

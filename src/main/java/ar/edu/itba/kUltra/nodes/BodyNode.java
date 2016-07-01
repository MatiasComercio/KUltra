package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.List;

public class BodyNode implements Node {
	private final List<StatementNode> statementNodes;

	public BodyNode(final List<StatementNode> statementNodes) {
		this.statementNodes = statementNodes;
	}

	@Override
	public void process(final Context context) {
		/* acá tendria que entrar con el contexto, para saber las variables y el orden */

		statementNodes.forEach(statementNode -> {
			statementNode.process(context);
		});
	}
}

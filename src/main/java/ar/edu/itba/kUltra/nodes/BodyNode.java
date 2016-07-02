package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BodyNode implements Node {
	private final Deque<StatementNode> statementNodes;

	public BodyNode() {
		statementNodes = new LinkedList<>();
	}

	public void add(final StatementNode statementNode) {
		statementNodes.push(statementNode);
	}

	@Override
	public void process(final Context context) {
		/* acá tendria que entrar con el contexto, para saber las variables y el orden */

		statementNodes.forEach(statementNode -> {
			statementNode.process(context);
		});
	}

	@Override
	public String toString() {
		return "BodyNode{" +
				"statementNodes=" + statementNodes +
				'}';
	}
}

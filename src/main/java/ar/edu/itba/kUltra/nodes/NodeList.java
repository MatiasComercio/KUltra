package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class NodeList implements Node {
	private final List<Node> nodeList;

	public NodeList() {
		nodeList = new LinkedList<>();
	}

	public boolean add(final Node node) {
		return nodeList.add(node);
	}

	public boolean addAll(final Collection<Node> c) {
		return nodeList.addAll(c);
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	@Override
	public void process(final Context context) {
		nodeList.forEach(node -> node.process(context));
	}
}

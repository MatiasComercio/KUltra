package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class NodeList<T extends Node> implements Node {
	private final List<T> nodeList;

	public NodeList() {
		nodeList = new LinkedList<>();
	}

	public boolean add(final T node) {
		return nodeList.add(node);
	}

	public boolean addAll(final Collection<T> c) {
		return nodeList.addAll(c);
	}

	public List<T> getNodeList() {
		return nodeList;
	}

	@Override
	public void process(final Context context) {
		nodeList.forEach(node -> node.process(context));
	}
}

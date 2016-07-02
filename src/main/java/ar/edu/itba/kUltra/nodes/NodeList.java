package ar.edu.itba.kUltra.nodes;

import ar.edu.itba.kUltra.helpers.Context;

import java.util.*;

public class NodeList<T extends Node> implements Node {
	private final Deque<T> nodeList;

	public NodeList() {
		nodeList = new LinkedList<T>();
	}

	public void add(final T node) {
		nodeList.push(node);
	}


	@Override
	public void process(final Context context) {
		nodeList.forEach(node -> node.process(context));
	}

	@Override
	public String toString() {
		return "NodeList{" +
				"nodeList=" + nodeList +
				'}';
	}
}

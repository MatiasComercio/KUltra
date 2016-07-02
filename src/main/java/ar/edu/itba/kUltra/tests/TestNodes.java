package ar.edu.itba.kUltra.tests;

import ar.edu.itba.kUltra.nodes.*;
import ar.edu.itba.kUltra.symbols.ParameterListSymbol;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.util.LinkedList;
import java.util.List;

public class TestNodes {
	public static void main(String[] args) {

		final ProgramNode programNode = new ProgramNode(generateMethods(), generateMainBody());

		programNode.compileAs("TestNodes");
	}

	private static NodeList<MethodNode> generateMethods() {

		final List<StatementNode> helloWorldStatementNodes = new LinkedList<>();
		helloWorldStatementNodes.add(new MethodCallNode("puts",
				putsArgument("Hello world from 'helloWorld' method!\n")));
		helloWorldStatementNodes.add(new ReturnNode(null));
		final BodyNode helloWorldBodyNode = new BodyNode(helloWorldStatementNodes);

		final MethodNode helloWorldMethodNode = new MethodNode("void", "helloWorld", new ParameterListSymbol(), helloWorldBodyNode);

		final NodeList<MethodNode> methodNodes = new NodeList<>();
		methodNodes.add(helloWorldMethodNode);
		return methodNodes;
	}

	private static BodyNode generateMainBody() {

		final List<StatementNode> statementNodes = new LinkedList<>();

		final DeclarationNode iDeclare = new DeclarationNode("int", "i");
		statementNodes.add(iDeclare);

		final DeclarationNode a1Declare = new DeclarationNode("int", "a1");
		statementNodes.add(a1Declare);

		final DeclarationNode a2Declare = new DeclarationNode("int", "a2");
		statementNodes.add(a2Declare);

		final LiteralNode<Integer> a1Value = new LiteralNode<>(5);
		final AssignmentNode a1Assignment = new AssignmentNode("a1", a1Value);
		statementNodes.add(a1Assignment);

		final LiteralNode<Integer> a2Value = new LiteralNode<>(3);
		final AssignmentNode a2Assignment = new AssignmentNode("a2", a2Value);
		statementNodes.add(a2Assignment);

		final VariableNode a1 = new VariableNode("a1");
		final VariableNode a2 = new VariableNode("a2");
		final ArithmeticNode addNode = new ArithmeticNode(GeneratorAdapter.ADD, a1, a2);
		final AssignmentNode assignmentNode = new AssignmentNode("i", addNode);
		statementNodes.add(assignmentNode);

		final VariableNode i = new VariableNode("i");
		final NodeList<ExpressionNode> argumentNodes = new NodeList<>();
		argumentNodes.add(i);
		final MethodCallNode putsMethod = new MethodCallNode("puts", argumentNodes);
		statementNodes.add(putsMethod);

		/* puts("\n"); */
		statementNodes.add(new MethodCallNode("puts", putsArgument("\n")));

		/* get input and print it with another message */
		statementNodes.add(new DeclarationNode("str", "s"));

		final AssignmentNode getsAssignment = new AssignmentNode("s", new MethodCallNode("gets", null));
		statementNodes.add(getsAssignment);

		/*
			if (i < 18) {
				printGetInput(statementNodes);
			} else {
				statementNodes.add(new MethodCallNode("puts", putsArgument("i>= 18\n")));
			}
		 */

		final ExpressionNode condition = new RelationalNode(GeneratorAdapter.LT, i, new LiteralNode<>(18));

		final List<StatementNode> ifBodyStatementNodes = new LinkedList<>();
		printGetInput(ifBodyStatementNodes);
		final BodyNode ifBodyNode = new BodyNode(ifBodyStatementNodes);

		final List<StatementNode> elseBodyStatementNodes = new LinkedList<>();
		elseBodyStatementNodes.add(new MethodCallNode("puts", putsArgument("i>= 18\n")));
		final BodyNode elseBodyNode = new BodyNode(elseBodyStatementNodes);

		final StatementNode ifNode = new IfNode(condition, ifBodyNode, elseBodyNode);
		statementNodes.add(ifNode);
//		printGetInput(statementNodes);


		/* while statement */
		final ExpressionNode whileCondition = new RelationalNode(GeneratorAdapter.LT, i, new LiteralNode<>(18));

		final List<StatementNode> whileBodyStatementNodes = new LinkedList<>();
		whileBodyStatementNodes.add(new AssignmentNode("i", new ArithmeticNode(GeneratorAdapter.ADD, i, new LiteralNode<>(1))));
		whileBodyStatementNodes.add(new MethodCallNode("puts", putsArgument(i)));
		whileBodyStatementNodes.add(new MethodCallNode("puts", putsArgument("\n")));

		final StatementNode whileNode = new WhileNode(whileCondition, new BodyNode(whileBodyStatementNodes));
		statementNodes.add(whileNode);
		/* END of while statement */

		/* helloWorld call */
		statementNodes.add(generateHelloWorldCall());

		return new BodyNode(statementNodes);
	}

	private static StatementNode generateHelloWorldCall() {
		return new MethodCallNode("helloWorld", null);
	}

	private static void printGetInput(final List<StatementNode> statementNodes) {
		/* puts("\nThis is what was captured: "); */
		statementNodes.add(new MethodCallNode("puts", putsArgument("\nThis is what was captured: ")));
		/* prints what was captured (it was saved at "s") */
		statementNodes.add(new MethodCallNode("puts", putsArgument(new VariableNode("s"))));
		/* puts("\n"); */
		statementNodes.add(new MethodCallNode("puts", putsArgument("\n")));
	}

	private static NodeList<ExpressionNode> putsArgument(final Object o) {
		final NodeList<ExpressionNode> argumentNodes = new NodeList<>();
		argumentNodes.add(new LiteralNode<>(o));
		return argumentNodes;
	}

	private static NodeList<ExpressionNode> putsArgument(final VariableNode variableNode) {
		final NodeList<ExpressionNode> argumentNodes = new NodeList<>();
		argumentNodes.add(variableNode);
		return argumentNodes;
	}
}

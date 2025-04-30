package cfg;

import ast.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CFG {
	
	private Node entry;
	private Node exit;
	
	public CFG() {
		entry = new Node("Entry");
		exit = new Node("Exit");
		exit.setSucc(null);
	}
	
	
	/**
	 * Links existing nodes and
	 * connects with entry and 
	 * exit nodes
	 * @param ast
	 */
	public void fromAST(AST ast) {
		MethodNode root = ast.getRoot();
		Stack<List<Node>> stack = new Stack<>();

		List<Node> queue = new ArrayList<>(root.getStmt());
		stack.push(queue);
		Node current = entry;

		while (!stack.isEmpty()) {
			List<Node> currentQueue = stack.pop();
			while (!currentQueue.isEmpty()) {
				Node next = currentQueue.remove(0);
				current.setSucc(next);
				current = next;
				if (current instanceof IfNode) {
					stack.push(currentQueue);
					IfNode ifNode = (IfNode) current;
					List<Node> falseBlock = ifNode.getFalseBlock();
					if (falseBlock.size() > 0) {
						stack.push(falseBlock);
						ifNode.setSuccFalse(falseBlock.get(0));
					} else {
						ifNode.setSuccFalse(exit);
					}
					currentQueue = ifNode.getTrueBlock();
					currentQueue.get(currentQueue.size()-1).setSucc(falseBlock.get(0));
				} else if (current instanceof WhileNode) {
					WhileNode whileNode = (WhileNode) current;
					Node succFalse = currentQueue.remove(0);
					whileNode.setSuccFalse(succFalse);
					List<Node> whileBlock = whileNode.getStmt();
					stack.push(currentQueue);
					currentQueue = whileBlock;
				} else if (current instanceof ReturnNode) {
					current.setSucc(exit);
				}
			}
		}
		current.setSucc(exit);
	}


	/**
	 * Creates a file in the root directory
	 * named as specified
	 */
	public void toDot(String fileName) {
		String entryName = entry.getName();
		StringBuffer graph = new StringBuffer("digraph " + fileName + " {\n");
		List<Node> queue = new ArrayList<Node>();

		Node succ = entry.getSucc();
		if (succ != null) {
			graph.append(genEdge(entryName, succ.getName()) + ";\n");
			queue.add(succ);
		}

		while(!queue.isEmpty()) {
			//difference cases for different node instances
			succ = queue.remove(0);
			if (succ instanceof IfNode) {
				IfNode ifSucc = (IfNode) succ;
				String ifSuccName = ifSucc.getName();
				graph.append(genEdge(ifSuccName, ifSucc.getSucc().getName())).append(";\n");
				graph.append(genEdge(ifSuccName, ifSucc.getSuccFalse().getName())).append("[style = dashed];\n");
				queue.add(ifSucc.getSucc());
				queue.add(ifSucc.getSuccFalse());
			} else if (succ instanceof WhileNode) {
				WhileNode whileSucc = (WhileNode) succ;
				String whileSuccName = whileSucc.getName();
				graph.append(genEdge(whileSuccName, whileSucc.getSucc().getName())).append(";\n");
				graph.append(genEdge(whileSuccName, whileSucc.getSuccFalse().getName())).append("[style = dashed];\n");
				queue.add(whileSucc.getSucc());
				queue.add(whileSucc.getSuccFalse());
			} else if (succ instanceof ReturnNode) {
				graph.append(genEdge(succ.getName(), exit.getName())).append(";\n");
			} else {
				Node next = succ.getSucc();
				if (next != null) {
					graph.append(genEdge(succ.getName(), next.getName())).append(";\n");
					queue.add(next);
				}
			}
		}
		//end of graph
		graph.append(" }");
		try {
			FileWriter dotFile = new FileWriter(fileName+"-cfg.dot");
			dotFile.write(graph.toString());
			dotFile.flush();
			dotFile.close();
		} catch (IOException e) {
			System.out.println("IOError ");
			e.printStackTrace();
		}

	}

	private static String genEdge(String from, String to) {
		return "\""+ from +"\" -> \"" + to + "\"";
	}
}

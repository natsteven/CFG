package cfg;

import ast.AST;
import ast.Node;

public class CFG {
	
	private Node entry;
	private Node exit;
	
	public CFG() {
		entry = new Node("Entry");
		exit = new Node("Exit");
	}
	
	
	/**
	 * Links existing nodes and
	 * connects with entry and 
	 * exit nodes
	 * @param ast
	 */
	public void fromAST(AST ast) {
		
	}

}
